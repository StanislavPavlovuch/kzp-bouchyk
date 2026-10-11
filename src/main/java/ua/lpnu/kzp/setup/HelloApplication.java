package ua.lpnu.kzp.setup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * Консольна програма для обробки даних про спортивні тренування.
 * Програма читає CSV-файл, перевіряє записи, обчислює показники
 * та формує звіт.
 */

public final class HelloApplication
{

    private static final String VERSION = "1.0.0";

    private HelloApplication()
    {
    }

/**
 * Запускає програму та обробляє аргументи командного рядка.
 *
 * @param args аргументи командного рядка
 */
    public static void main(String[] args)
    {
        Path inputPath = Path.of("data/input.csv");
        Path outputPath = Path.of("data/report.txt");

        for (int i = 0; i < args.length; i++)
        {
            switch (args[i])
            {
                case "--help" ->
                {
                    printHelp();
                    return;
                }
                case "--version" ->
                {
                    System.out.printf("maven-actions-hello %s%n", VERSION);
                    return;
                }
                case "--input" ->
                {
                    if (i + 1 >= args.length)
                    {
                        System.out.println("Після --input потрібно вказати шлях до файла.");
                        return;
                    }

                    inputPath = Path.of(args[++i]);
                }
                case "--output" ->
                {
                    if (i + 1 >= args.length)
                    {
                        System.out.println("Після --output потрібно вказати шлях до файла.");
                        return;
                    }

                    outputPath = Path.of(args[++i]);
                }
                default ->
                {
                    System.out.printf("Невідомий аргумент: %s%n", args[i]);
                    printHelp();
                    return;
                }
            }
        }

        try
        {
            List<String> lines = Files.readAllLines(
                    inputPath,
                    StandardCharsets.UTF_8
            );

            String report = buildReport(lines);

            System.out.println();
            System.out.print(report);

            Files.writeString(
                    outputPath,
                    report,
                    StandardCharsets.UTF_8
            );

            System.out.printf(
                    "%nЗвіт збережено у: %s%n",
                    outputPath
            );
        }
        catch (IOException e)
        {
            System.out.printf(
                    "Помилка роботи з файлом: %s%n",
                    e.getMessage()
            );
        }
    }
/**
 * Перевіряє записи про тренування та обчислює підсумкові показники.
 *
 * @param lines рядки вхідного CSV-файла
 * @return сформований текст звіту
 */
    static String buildReport(List<String> lines)
    {
        int validCount = 0;
        int totalMinutes = 0;
        double totalDistance = 0;
        int maxCalories = 0;

        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            int lineNumber = i + 1;

            if (line.isBlank())
            {
                System.out.printf(
                        "Рядок %d пропущено: порожній рядок.%n",
                        lineNumber
                );
                continue;
            }

            String[] fields = line.split(";", -1);

            if (fields.length != 5)
            {
                System.out.printf(
                        "Рядок %d пропущено: неправильна кількість полів.%n",
                        lineNumber
                );
                continue;
            }

            String date = fields[0].trim();
            String kind = fields[1].trim();

            if (date.isEmpty() || kind.isEmpty())
            {
                System.out.printf(
                        "Рядок %d пропущено: порожнє обов'язкове поле.%n",
                        lineNumber
                );
                continue;
            }

            try
            {
                int minutes = Integer.parseInt(fields[2].trim());
                double distanceKm = Double.parseDouble(fields[3].trim());
                int calories = Integer.parseInt(fields[4].trim());

                if (minutes < 0 || distanceKm < 0 || calories < 0)
                {
                    System.out.printf(
                            "Рядок %d пропущено: числові значення не можуть бути від'ємними.%n",
                            lineNumber
                    );
                    continue;
                }

                validCount++;
                totalMinutes += minutes;
                totalDistance += distanceKm;

                if (calories > maxCalories)
                {
                    maxCalories = calories;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.printf(
                        "Рядок %d пропущено: неправильне числове значення.%n",
                        lineNumber
                );
            }
        }

        return formatReport(
                validCount,
                totalMinutes,
                totalDistance,
                maxCalories
        );
    }
/**
 * Формує текст звіту з обчислених показників.
 *
 * @param validCount кількість коректних записів
 * @param totalMinutes сумарний час тренувань
 * @param totalDistance сумарна дистанція
 * @param maxCalories найбільша витрата калорій
 * @return готовий текст звіту
 */
    static String formatReport(
            int validCount,
            int totalMinutes,
            double totalDistance,
            int maxCalories
    )
    {
        return String.format(
                Locale.ROOT,
                "Звіт про спортивні тренування%n"
                        + "Коректних записів: %d%n"
                        + "Сумарний час: %d хв%n"
                        + "Сумарна дистанція: %.2f км%n"
                        + "Максимальна витрата калорій: %d%n",
                validCount,
                totalMinutes,
                totalDistance,
                maxCalories
        );
    }

    private static void printHelp()
    {
        System.out.println("Використання:");
        System.out.println("  --help                 показати довідку");
        System.out.println("  --version              показати версію");
        System.out.println("  --input <файл>         вказати вхідний CSV-файл");
        System.out.println("  --output <файл>        вказати файл для звіту");
    }
}