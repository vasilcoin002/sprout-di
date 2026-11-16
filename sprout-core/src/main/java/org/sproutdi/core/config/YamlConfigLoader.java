package org.sproutdi.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.sproutdi.core.exception.SproutException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlConfigLoader {
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public SproutConfig load(String path) {
        Path filePath = Path.of(path);
        try {
            if (Files.exists(filePath)) {
                try (InputStream is = Files.newInputStream(filePath)) {
                    return yamlMapper.readValue(is, SproutConfig.class);
                }
            }

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream is = classLoader.getResourceAsStream(path)) {
                if (is == null) {
                    throw new SproutException("Configuration file not found: " + path);
                }
                return yamlMapper.readValue(is, SproutConfig.class);
            }
        }
        catch (IOException e) {
            throw new SproutException("Failed to load YAML configuration from " + path, e);
        }
    }
}
