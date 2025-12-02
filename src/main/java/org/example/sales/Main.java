package org.example.sales;

import org.example.sales.config.AppConfig;
import org.example.sales.model.SaleRecord;
import org.example.sales.output.ReportPrinter;
import org.example.sales.service.CsvSalesReader;
import org.example.sales.service.SalesReportService;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Sales Aggregator started...");

        // 1. Загружаем конфиг
        AppConfig config = new AppConfig(Path.of("app.properties"));

        // 2. Читаем CSV
        Path csvPath = Path.of(config.getCsvInputPath());
        CsvSalesReader reader = new CsvSalesReader(config.getCsvSeparator());
        List<SaleRecord> records = reader.read(csvPath);

        System.out.println("Records read: " + records.size());

        // 3. Строим отчёты
        SalesReportService reportService = new SalesReportService();
        Map<String, java.math.BigDecimal> salesByRegion =
                reportService.calculateSalesByRegion(records);

        List<SaleRecord> topSales =
                reportService.findTopSales(records, config.getTopSalesCount());

        List<SaleRecord> filteredByProduct =
                reportService.filterByProductId(records, config.getFilterProductId());

        // 4. Печатаем отчёты
        ReportPrinter printer = new ReportPrinter();
        printer.printSalesByRegion(salesByRegion);
        printer.printTopSales(topSales);
        printer.printFilteredByProduct(config.getFilterProductId(), filteredByProduct);
    }
}
