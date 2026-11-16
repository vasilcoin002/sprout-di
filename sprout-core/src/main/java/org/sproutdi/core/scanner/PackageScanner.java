package org.sproutdi.core.scanner;

import org.sproutdi.core.annotation.Component;
import org.sproutdi.core.annotation.Configuration;
import org.sproutdi.core.exception.SproutException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class PackageScanner {

    public Set<Class<?>> scan(List<String> basePackages) {
        Set<Class<?>> result = new HashSet<>();

        if (basePackages == null || basePackages.isEmpty()) {
            return result;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        for (String basePackage : basePackages) {
            scanPackage(basePackage, classLoader, result);
        }

        return result;
    }

    private void scanPackage(String basePackage, ClassLoader classLoader, Set<Class<?>> result) {
        if (basePackage == null || basePackage.isBlank()) {
            return;
        }

        String packagePath = basePackage.replace(".", "/");

        Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(packagePath);
        } catch (IOException e) {
            throw new SproutException("Failed to load package " + basePackage, e);
        }

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            if (!Objects.equals(url.getProtocol(), "file")) {
                continue;
            }

            Path rootDir;
            try {
                rootDir = Path.of(url.toURI());
            } catch (URISyntaxException e) {
                throw new SproutException("Failed to get URI from URL " + url.toString(), e);
            }

            scanDirectoryForClasses(rootDir, basePackage, classLoader, result);
        }
    }

    private void scanDirectoryForClasses(Path dir, String basePackage, ClassLoader classLoader, Set<Class<?>> result) {

        try (Stream<Path> paths = Files.walk(dir)) {
            List<Path> classFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".class"))
                    .toList();

            for (Path path : classFiles) {
                loadClassIfManaged(path, dir, basePackage, classLoader, result);
            }
        } catch (IOException e) {
            throw new SproutException("Failed to walk through files in " + dir, e);
        }
    }

    private void loadClassIfManaged(Path path, Path dir, String basePackage, ClassLoader classLoader, Set<Class<?>> result) {
        Path relativePath = dir.relativize(path);
        String strRelativePath = relativePath.toString().replace(File.separatorChar, '.');
        String className =
                basePackage +
                        "." +
                        strRelativePath.substring(0, strRelativePath.length() - 6);

        try {
            Class<?> class_ = Class.forName(className, false, classLoader);
            if (!class_.isInterface() && !class_.isAnnotation() &&
                    (class_.isAnnotationPresent(Component.class)
                            || class_.isAnnotationPresent(Configuration.class))) {
                result.add(class_);
            }
        } catch (ClassNotFoundException e) {
            throw new SproutException("Failed to find class " + className, e);
        }
    }
}
