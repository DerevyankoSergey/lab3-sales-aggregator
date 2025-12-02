package org.example.sales.service;

import org.example.sales.model.SaleRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для чтения CSV-файла с продажами и преобразования строк в объекты {@link SaleRecord}.
 */
public class CsvSalesReader {

    private final char separator;

    /**
     * @param separator разделитель колонок в CSV (например, ',' или ';')
     */
    public CsvSalesReader(char separator) {
        this.separator = separator;
    }

    /**
     * Читает CSV-файл и возвращает список записей о продажах.
     *
     * @param path путь к CSV-файлу
     * @return список записей о продажах
     * @throws IOException если файл не найден или не удаётся прочитать
     */
    public List<SaleRecord> read(Path path) throws IOException {
        List<SaleRecord> records = new ArrayList<>();

        List<String> lines = Files.readAllLines(path);
        if (lines.isEmpty()) {
            return records;
        }

        // Первая строка — заголовок, пропускаем её
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            SaleRecord record = parseLine(line);
            records.add(record);
        }

        return records;
    }

    private SaleRecord parseLine(String line) {
        String[] parts = line.split(String.valueOf(separator));
        if (parts.length < 5) {
            throw new IllegalArgumentException("Некорректная строка CSV: " + line);
        }

        String transactionId = parts[0].trim();
        String date = parts[1].trim();
        String productId = parts[2].trim();
        BigDecimal amount = new BigDecimal(parts[3].trim());
        String region = parts[4].trim();

        return new SaleRecord(transactionId, date, productId, amount, region);
    }
}
