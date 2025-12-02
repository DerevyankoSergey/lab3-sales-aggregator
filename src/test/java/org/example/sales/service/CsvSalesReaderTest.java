package org.example.sales.service;

import org.example.sales.model.SaleRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvSalesReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void read_shouldParseCsvFileIntoSaleRecords() throws IOException {
        // given: временный CSV-файл
        Path csvFile = tempDir.resolve("sales.csv");
        String content = String.join(System.lineSeparator(),
                "TransactionID,Date,ProductID,Amount,Region",
                "1001,2025-10-10,P-123,150.00,North",
                "1002,2025-10-11,P-456,20.50,South"
        );
        Files.writeString(csvFile, content, StandardCharsets.UTF_8);

        CsvSalesReader reader = new CsvSalesReader(',');

        // when
        List<SaleRecord> records = reader.read(csvFile);

        // then
        assertEquals(2, records.size());

        SaleRecord first = records.get(0);
        assertEquals("1001", first.getTransactionId());
        assertEquals("2025-10-10", first.getDate());
        assertEquals("P-123", first.getProductId());
        assertEquals(0, new BigDecimal("150.00").compareTo(first.getAmount()));
        assertEquals("North", first.getRegion());
    }

    @Test
    void read_shouldReturnEmptyListWhenFileHasOnlyHeader() throws IOException {
        Path csvFile = tempDir.resolve("empty.csv");
        Files.writeString(csvFile, "TransactionID,Date,ProductID,Amount,Region", StandardCharsets.UTF_8);

        CsvSalesReader reader = new CsvSalesReader(',');

        List<SaleRecord> records = reader.read(csvFile);

        assertTrue(records.isEmpty());
    }
}
