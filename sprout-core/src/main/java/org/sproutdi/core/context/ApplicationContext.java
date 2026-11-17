package org.sproutdi.core.context;

import java.nio.file.Path;
import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(Class<T> type);

    <T> T getBean(String name, Class<T> type);

    <T> Map<String, T> getAllBeansOfType(Class<T> type);

    void exportReport(Path outputPath);

}
