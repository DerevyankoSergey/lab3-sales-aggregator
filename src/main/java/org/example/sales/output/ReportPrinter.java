package org.example.sales.output;

import org.example.sales.model.SaleRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Утилита для вывода отчётов в stdout.
 */
public class ReportPrinter {

    public void printSalesByRegion(Map<String, BigDecimal> salesByRegion) {
        System.out.println("\n--- Sales by Region ---");
        for (Map.Entry<String, BigDecimal> entry : salesByRegion.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void printTopSales(List<SaleRecord> topSales) {
        System.out.println("\n--- Top Sales (ID) ---");
        for (SaleRecord record : topSales) {
            System.out.println(record.getTransactionId());
        }
    }

    public void printFilteredByProduct(String productId, List<SaleRecord> filtered) {
        System.out.println("\n--- Filtered by Product " + productId + " ---");
        for (SaleRecord r : filtered) {
            System.out.println(
                    r.getTransactionId() + "," +
                            r.getDate() + "," +
                            r.getProductId() + "," +
                            r.getAmount() + "," +
                            r.getRegion()
            );
        }
    }
}
