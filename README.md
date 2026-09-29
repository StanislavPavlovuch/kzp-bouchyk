# KZP

Лабораторні роботи з дисципліни «Кросплатформенні засоби програмування».

[![Java CI](https://github.com/StanislavPavlovuch/kzp-bouchyk/actions/workflows/ci.yml/badge.svg)](https://github.com/StanislavPavlovuch/kzp-bouchyk/actions/workflows/ci.yml)

## Організація репозиторію

Використовується один публічний репозиторій для лабораторних робіт Java та Python.

Для окремих мов і налаштувань використовуються відповідні робочі гілки.

Поточна Java-інфраструктура розробляється у гілці:

`java/infra`

Основна стабільна гілка:

`main`

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

## Збірка

Компіляція Java-проєкту:

```bash
./mvnw clean compile
```

## Тести

Запуск автоматичних тестів JUnit 5:

```bash
./mvnw test
```

## Повна перевірка

Компіляція, тести та статичний аналіз SpotBugs:

```bash
./mvnw clean verify
```

Успішний результат:

```text
BUILD SUCCESS
```

## Створення виконуваного JAR

```bash
./mvnw clean package
```

Після успішної збірки створюється виконуваний файл:

```text
target/maven-actions-hello-0.1.0-all.jar
```

## Запуск програми

```bash
java -jar target/maven-actions-hello-0.1.0-all.jar
```

Очікуваний результат:

```text
Hello from Maven
```

## Версія програми

```bash
java -jar target/maven-actions-hello-0.1.0-all.jar --version
```

Очікуваний результат:

```text
maven-actions-hello 0.1.0
```

## GitHub Actions

Workflow знаходиться у файлі:

```text
.github/workflows/ci.yml
```

GitHub Actions автоматично перевіряє Java-проєкт на трьох операційних системах:

- Ubuntu
- Windows
- macOS

Для кожної операційної системи workflow:

1. Завантажує код репозиторію.
2. Встановлює Temurin JDK 21.
3. Запускає Maven Wrapper.
4. Виконує Maven `verify`.
5. Запускає JUnit-тести.
6. Виконує аналіз SpotBugs.
7. Створює виконуваний JAR.
8. Перевіряє запуск JAR через `--version`.
9. Публікує JAR як GitHub Actions artifact.

Поточний стан workflow можна переглянути тут:

https://github.com/StanislavPavlovuch/kzp-bouchyk/actions