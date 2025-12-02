package org.example.sales.service;

import org.example.sales.model.SaleRecord;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис, который строит различные отчёты по списку продаж.
 */
public class SalesReportService {

    /**
     * Задание 1.
     * Подсчитать общую сумму продаж, сгруппированную по региону.
     *
     * @param records список продаж
     * @return отображение Region -> сумма Amount
     */
    public Map<String, BigDecimal> calculateSalesByRegion(List<SaleRecord> records) {
        Map<String, BigDecimal> result = new HashMap<>();

        for (SaleRecord record : records) {
            String region = record.getRegion();
            BigDecimal amount = record.getAmount();

            result.merge(region, amount, BigDecimal::add);
        }

        return result;
    }

    /**
     * Задание 2.
     * Найти topN самых дорогих продаж по полю Amount.
     *
     * @param records список продаж
     * @param topN    количество записей в топе
     * @return список записей, отсортированных по убыванию Amount
     */
    public List<SaleRecord> findTopSales(List<SaleRecord> records, int topN) {
        return records.stream()
                .sorted(Comparator.comparing(SaleRecord::getAmount).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    /**
     * Задание 3.
     * Отфильтровать все продажи по заданному ProductID.
     *
     * @param records   список продаж
     * @param productId идентификатор продукта
     * @return список продаж с указанным ProductID
     */
    public List<SaleRecord> filterByProductId(List<SaleRecord> records, String productId) {
        return records.stream()
                .filter(r -> r.getProductId().equals(productId))
                .collect(Collectors.toList());
    }
}
