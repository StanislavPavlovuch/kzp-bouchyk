# KZP

Лабораторні роботи з дисципліни «Кросплатформні засоби програмування».

[![Java CI](https://github.com/StanislavPavlovuch/kzp-bouchyk/actions/workflows/ci.yml/badge.svg)](https://github.com/StanislavPavlovuch/kzp-bouchyk/actions/workflows/ci.yml)

## Організація репозиторію

Основна стабільна гілка:

```text
main
```

Для лабораторних робіт використовуються окремі робочі гілки:

```text
java/lab01
java/lab02
java/lab03
```

Зміни додаються до `main` через Pull Request.

## Java

У проєкті використовуються:

- Java 21;
- Maven;
- Maven Wrapper;
- JUnit 5;
- Maven Surefire Plugin;
- SpotBugs;
- Maven Shade Plugin;
- GitHub Actions.

Версія продукту зберігається лише у `pom.xml`. Під час Maven-збірки вона записується у manifest виконуваного JAR і зчитується програмою автоматично.

Поточна версія:

```text
3.0.0
```

## Збірка і перевірка

Компіляція:

```bash
./mvnw clean compile
```

Тести:

```bash
./mvnw clean test
```

Повна перевірка з SpotBugs:

```bash
./mvnw clean verify
```

Пакування:

```bash
./mvnw clean package
```

Після збірки створюється:

```text
target/maven-actions-hello-3.0.0-all.jar
```

## Запуск

```bash
java -jar target/maven-actions-hello-3.0.0-all.jar
```

Перевірка версії:

```bash
java -jar target/maven-actions-hello-3.0.0-all.jar --version
```

Результат:

```text
maven-actions-hello 3.0.0
```

## Аргументи командного рядка

```text
--help
```

Показати довідку.

```text
--version
```

Показати версію програми.

```text
--input <файл>
```

Вказати інший вхідний CSV-файл.

```text
--output <файл>
```

Вказати інший файл для збереження звіту.

---

# Лабораторна робота №1

У лабораторній роботі №1 створено консольну програму для обробки даних про спортивні тренування.

Формат вхідного рядка:

```text
date;kind;minutes;distanceKm;calories
```

За замовчуванням програма читає:

```text
data/input.csv
```

Для коректних записів обчислюються:

- кількість тренувань;
- сумарний час;
- сумарна дистанція;
- максимальна витрата калорій.

Некоректний рядок не завершує роботу всієї програми: виводиться його номер і причина помилки.

Контрольний результат:

```text
Звіт про спортивні тренування
Коректних записів: 3
Сумарний час: 155 хв
Сумарна дистанція: 32.00 км
Максимальна витрата калорій: 620
```

---

# Лабораторна робота №2

## Мета

У лабораторній роботі №2 предметну частину програми перебудовано з використанням власного класу-сутності та `record`.

Зовнішня поведінка програми збережена: вона читає ті самі CSV-дані, відкидає ті самі некоректні записи та формує ті самі підсумкові показники.

## Структура

Основні класи:

```text
src/main/java/ua/lpnu/kzp/setup/
├── HelloApplication.java
├── ArgumentParser.java
├── ProgramArguments.java
├── TrainingController.java
├── Training.java
└── DurationDistance.java
```

### HelloApplication

Точка входу. Метод `main` запускає основні процеси, але предметна обробка винесена в окремі класи.

### ArgumentParser

Розбирає аргументи командного рядка.

### ProgramArguments

Зберігає результат розбору параметрів.

### TrainingController

Керує читанням файла, обробкою даних, формуванням звіту та його записом у файл.

### Training

Клас-сутність одного спортивного тренування.

Поля:

```text
date
kind
minutes
distanceKm
calories
```

Основні правила:

- дата не може бути порожньою;
- вид тренування не може бути порожнім;
- хвилини не можуть бути від'ємними;
- дистанція не може бути від'ємною;
- калорії не можуть бути від'ємними.

Для створення об'єкта з CSV використовується:

```java
Training.fromCsv(...)
```

### DurationDistance

Допоміжне незмінне значення реалізовано через `record`:

```java
DurationDistance
```

Він зберігає:

```text
minutes
distanceKm
```

та використовується для накопичення сумарного часу й дистанції.

## Обробка даних

Схема:

```text
CSV-рядок
    ↓
Training.fromCsv()
    ↓
Training
    ↓
перевірка стану
    ↓
DurationDistance
    ↓
підсумковий звіт
```

Помилка одного запису перехоплюється окремо і не перериває обробку наступних рядків.

## Тестування

У Лабі №1 було 7 тестів.

Для Лаби №2 додано тести:

```text
TrainingTest
DurationDistanceTest
```

Фінальний результат:

```text
Tests run: 21
Failures: 0
Errors: 0
Skipped: 0
```

Перевіряються позитивні, граничні та негативні випадки, конструктор, `fromCsv()`, інваріанти та поведінка `record`.

## SpotBugs

Команда:

```bash
./mvnw clean verify
```

Результат:

```text
BugInstance size is 0
Error size is 0
No errors/warnings found
BUILD SUCCESS
```

## Сумісність із Лабою №1

Після рефакторингу контрольний результат залишився:

```text
Коректних записів: 3
Сумарний час: 155 хв
Сумарна дистанція: 32.00 км
Максимальна витрата калорій: 620
```

Тобто внутрішня модель програми змінилася, але зовнішня поведінка залишилась сумісною.

## GitHub Actions

Workflow:

```text
.github/workflows/ci.yml
```

CI перевіряє проєкт на:

- Ubuntu;
- Windows;
- macOS.

Під час перевірки запускаються тести, Maven `verify`, SpotBugs і створення виконуваного JAR.

GitHub Actions:

https://github.com/StanislavPavlovuch/kzp-bouchyk/actions
---
# Лабораторна робота №3 — спадкування, інтерфейси й поліморфізм

**Варіант 2:** спортивні тренування. **Версія:** `3.0.0`.

Ієрархія:

```text
LoadCalculable (interface: calculateLoad())
        ↑ implements
Training (спільні private final поля, валідація, fromCsv, equals/hashCode)
    ├── IndoorTraining  (навантаження: minutes × 1.2 + calories × 0.1)
    └── OutdoorTraining (навантаження: minutes + distanceKm × 10 + calories × 0.1)

TrainingKind (enum: INDOOR / OUTDOOR / OTHER)
TrainingLoadAnalyzer (поліморфний обхід List<Training>)
TrainingLoadDemo (окрема точка входу для демонстрації)
```

Інтерфейс обрано замість перетворення `Training` на абстрактний клас, оскільки 21 тест із ЛР №1–2 використовує старий публічний конструктор `new Training(...)`. Конкретний базовий клас зберігає зворотну сумісність; `IndoorTraining` та `OutdoorTraining` перевизначають `calculateLoad()` за контрактом інтерфейсу. Вибір підтипу виконується лише у фабриці `Training.fromCsv()`; обробка списку не містить `instanceof` або `switch` за підтипом.

`TrainingKind.fromActivity()` класифікує назви зі старого CSV. Для контрольного набору `Біг`/`Велосипед` — OUTDOOR, `Плавання` — INDOOR (басейн у приміщенні). Невідомі назви залишаються допустимими та створюють звичайний `Training` з категорією OTHER. Формули навантаження — **навчальні умовні коефіцієнти**, а не фізіологічні нормативи.

## Команди macOS

```bash
./mvnw clean test
./mvnw clean verify
./mvnw clean package
java -jar target/maven-actions-hello-3.0.0-all.jar
java -jar target/maven-actions-hello-3.0.0-all.jar --version
java -cp target/maven-actions-hello-3.0.0-all.jar ua.lpnu.kzp.setup.TrainingLoadDemo
```

Звичайна команда `java -jar` і її файл `data/report.txt` зберігають формат попередніх лабораторних. Окрема команда з `TrainingLoadDemo` виводить новий показник навантаження; це **не** змінює старий звіт. Додано 13 JUnit-тестів; попередні 21 збережено.

Очікуваний старий звіт: 3 коректні записи, 155 хв, 32.00 км, 620 калорій. Очікуваний поліморфний підсумок на `data/input.csv`: 3 коректні записи, 623.00 у.о. сумарного й 328.00 у.о. максимального навантаження.

**Версіонування:** джерело версії лише `pom.xml`, значення зчитується з manifest виконуваного JAR. `main` не містить константи версії.

**Процес GitHub:** окрема гілка `java/lab03`, Issue(s), Pull Request із `Closes #N`, GitHub Actions на Windows/macOS/Ubuntu, JAR-артефакти, тег `v3.0.0` після merge.


## Перевірка ЛР №3 на GitHub

- [Pull Request #24](https://github.com/StanislavPavlovuch/kzp-bouchyk/pull/24), Issues #20–#23.
- [Java CI run #40](https://github.com/StanislavPavlovuch/kzp-bouchyk/actions/runs/45199288159): Ubuntu, Windows і macOS — успішно.
- JAR-артефакти: `jar-ubuntu-latest-40`, `jar-windows-latest-40`, `jar-macos-latest-40` (розділ Artifacts у run #40).
- Фактичний результат: 34 JUnit-тести; SpotBugs — 0 помилок; Maven — BUILD SUCCESS.
- Git-тег після merge: `v3.0.0`.
