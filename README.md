# Sales Aggregator (Lab 3, variant 5)

Небольшое Java-приложение для обработки CSV-файла с данными о продажах.

## Функционал

- Чтение CSV-файла с продажами (TransactionID, Date, ProductID, Amount, Region).
- Подсчёт общей суммы продаж по регионам.
- Поиск N самых дорогих продаж (по Amount).
- Фильтрация всех продаж по заданному `ProductID`.

## Конфигурация

Настройки берутся из файла `app.properties`:

```properties
csv.input.path=./data/sales.csv
filter.product.id=P-123
report.top.sales.count=3
csv.separator=,
report.output=STDOUT
