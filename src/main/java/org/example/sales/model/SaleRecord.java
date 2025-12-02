package org.example.sales.model;

import java.math.BigDecimal;

/**
 * Представляет одну запись о продаже из CSV-файла.
 */
public class SaleRecord {

    private final String transactionId;
    private final String date;
    private final String productId;
    private final BigDecimal amount;
    private final String region;

    public SaleRecord(String transactionId,
                      String date,
                      String productId,
                      BigDecimal amount,
                      String region) {
        this.transactionId = transactionId;
        this.date = date;
        this.productId = productId;
        this.amount = amount;
        this.region = region;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "SaleRecord{" +
                "transactionId='" + transactionId + '\'' +
                ", date='" + date + '\'' +
                ", productId='" + productId + '\'' +
                ", amount=" + amount +
                ", region='" + region + '\'' +
                '}';
    }
}
