package org.example.sales.service;

import org.example.sales.model.SaleRecord;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SalesReportServiceTest {

    private List<SaleRecord> createSampleRecords() {
        return Arrays.asList(
                new SaleRecord("1001", "2025-10-10", "P-123", new BigDecimal("150.00"), "North"),
                new SaleRecord("1002", "2025-10-10", "P-456", new BigDecimal("20.50"), "South"),
                new SaleRecord("1003", "2025-10-11", "P-123", new BigDecimal("150.00"), "West"),
                new SaleRecord("1004", "2025-10-12", "P-789", new BigDecimal("300.00"), "North")
        );
    }

    @Test
    void calculateSalesByRegion_shouldSumAmountsForEachRegion() {
        SalesReportService service = new SalesReportService();
        List<SaleRecord> records = createSampleRecords();

        Map<String, BigDecimal> result = service.calculateSalesByRegion(records);

        // сравниваем BigDecimal через compareTo, чтобы не споткнуться о scale (450.0 vs 450.00)
        assertEquals(0, new BigDecimal("450.00").compareTo(result.get("North")));
        assertEquals(0, new BigDecimal("20.50").compareTo(result.get("South")));
        assertEquals(0, new BigDecimal("150.00").compareTo(result.get("West")));
        assertEquals(3, result.size());
    }

    @Test
    void findTopSales_shouldReturnRecordsSortedByAmountDesc() {
        SalesReportService service = new SalesReportService();
        List<SaleRecord> records = createSampleRecords();

        List<SaleRecord> top3 = service.findTopSales(records, 3);

        assertEquals(3, top3.size());

        // первая продажа точно самая большая
        assertEquals("1004", top3.get(0).getTransactionId());

        // остальные две могут быть в любом порядке (оба по 150.00)
        List<String> ids = Arrays.asList(
                top3.get(1).getTransactionId(),
                top3.get(2).getTransactionId()
        );
        assertTrue(ids.contains("1001"));
        assertTrue(ids.contains("1003"));
    }

    @Test
    void filterByProductId_shouldReturnOnlyRecordsWithGivenProductId() {
        SalesReportService service = new SalesReportService();
        List<SaleRecord> records = createSampleRecords();

        List<SaleRecord> filtered = service.filterByProductId(records, "P-123");

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().allMatch(r -> r.getProductId().equals("P-123")));
    }
}
