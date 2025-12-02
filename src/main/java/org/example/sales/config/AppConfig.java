package org.example.sales.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Класс для загрузки и доступа к настройкам приложения из файла app.properties.
 */
public class AppConfig {

    private final Properties properties = new Properties();

    /**
     * Загружает конфигурацию из указанного файла.
     *
     * @param path путь к файлу app.properties
     * @throws IOException если файл не найден или не удаётся прочитать
     */
    public AppConfig(Path path) throws IOException {
        try (InputStream in = Files.newInputStream(path)) {
            properties.load(in);
        }
    }

    public String getCsvInputPath() {
        return properties.getProperty("csv.input.path");
    }

    public String getFilterProductId() {
        return properties.getProperty("filter.product.id");
    }

    public int getTopSalesCount() {
        return Integer.parseInt(properties.getProperty("report.top.sales.count", "10"));
    }

    public char getCsvSeparator() {
        String value = properties.getProperty("csv.separator", ",");
        return value.charAt(0);
    }

    public String getReportOutput() {
        return properties.getProperty("report.output", "STDOUT");
    }
}
