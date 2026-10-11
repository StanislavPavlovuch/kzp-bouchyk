# KZP

Лабораторні роботи з дисципліни «Кросплатформенні засоби програмування».

[![Java CI](https://github.com/StanislavPavlovuch/kzp-bouchyk/actions/workflows/ci.yml/badge.svg)](https://github.com/StanislavPavlovuch/kzp-bouchyk/actions/workflows/ci.yml)

## Організація репозиторію

Основна стабільна гілка:

`main`

Для лабораторної роботи № 1 використовується робоча гілка:

`java/lab01`

Після завершення та перевірки зміни з робочої гілки об'єднуються з `main` через Pull Request.

## Java

Для Java-проєкту використовуються:

- Java 21
- Maven
- Maven Wrapper
- JUnit 5
- Maven Surefire Plugin
- SpotBugs
- Maven Shade Plugin
- GitHub Actions

## Лабораторна робота № 1

### Призначення програми

Програма обробляє дані про спортивні тренування з CSV-файла.

Для коректних записів обчислюються:

- кількість тренувань;
- сумарний час тренувань;
- сумарна дистанція;
- максимальна витрата калорій.

Некоректні записи не завершують роботу програми аварійно. Для них виводиться номер рядка та причина пропуску.

### Формат вхідних даних

За замовчуванням програма читає:

```text
data/input.csv
```

Формат одного рядка:

```text
date;kind;minutes;distanceKm;calories
```

де:

- `date` — дата тренування;
- `kind` — вид тренування;
- `minutes` — тривалість у хвилинах;
- `distanceKm` — дистанція у кілометрах;
- `calories` — кількість витрачених калорій.

Приклад:

```text
2026-09-10;Біг;45;8.5;620
2026-09-12;Велосипед;60;21.4;540
2026-09-15;Плавання;50;2.1;430
2026-09-17;Ходьба;-30;3.0;180
2026-09-20;Йога;abc;0.0;200
```

Перші три записи коректні. Четвертий і п'ятий використовуються для перевірки обробки помилкових даних.

## Збірка та перевірка

Компіляція:

```bash
./mvnw clean compile
```

Запуск тестів:

```bash
./mvnw test
```

Повна перевірка з тестами та SpotBugs:

```bash
./mvnw clean verify
```

Створення виконуваного JAR:

```bash
./mvnw clean package
```

Після успішної збірки створюється файл:

```text
target/maven-actions-hello-1.0.0-all.jar
```

## Запуск програми

Зі стандартними шляхами:

```bash
java -jar target/maven-actions-hello-1.0.0-all.jar
```

Або без попереднього пакування:

```bash
java -cp target/classes ua.lpnu.kzp.setup.HelloApplication
```

Програма за замовчуванням читає `data/input.csv` та зберігає звіт у `data/report.txt`.

### Аргументи командного рядка

`--help` — показати довідку.

`--version` — показати версію програми.

`--input <файл>` — вказати інший вхідний CSV-файл.

`--output <файл>` — вказати інший файл для збереження звіту.

Приклад:

```bash
java -cp target/classes ua.lpnu.kzp.setup.HelloApplication --input data/input.csv --output data/report.txt
```

Перевірка версії:

```bash
java -jar target/maven-actions-hello-1.0.0-all.jar --version
```

Поточний результат:

```text
maven-actions-hello 1.0.0
```

## Приклад результату

```text
Рядок 4 пропущено: числові значення не можуть бути від'ємними.
Рядок 5 пропущено: неправильне числове значення.

Звіт про спортивні тренування
Коректних записів: 3
Сумарний час: 155 хв
Сумарна дистанція: 32.00 км
Максимальна витрата калорій: 620

Звіт збережено у: data/report.txt
```

## GitHub Actions

Workflow знаходиться у файлі:

```text
.github/workflows/ci.yml
```

GitHub Actions перевіряє проєкт на:

- Ubuntu
- Windows
- macOS

Для кожної операційної системи виконується Maven `verify`, тому запускаються компіляція, JUnit-тести та SpotBugs. Після успішної перевірки створюється виконуваний JAR і публікується як GitHub Actions artifact.

Стан workflow:

https://github.com/StanislavPavlovuch/kzp-bouchyk/actions