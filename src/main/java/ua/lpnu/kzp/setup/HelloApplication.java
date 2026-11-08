package ua.lpnu.kzp.setup;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Консольна програма для обробки даних про спортивні тренування.
 */
public final class HelloApplication
{
    private static final String VERSION = "1.0.0";

    private HelloApplication()
    {
    }

    /**
     * Запускає програму.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args)
    {
        try
        {
            ProgramArguments arguments = ArgumentParser.parse(args);

            if (arguments.isHelp())
            {
                printHelp();
                return;
            }

            if (arguments.isVersion())
            {
                System.out.printf(
                        "maven-actions-hello %s%n",
                        VERSION
                );
                return;
            }

            TrainingController.run(arguments);
        }
        catch (IllegalArgumentException e)
        {
            System.out.printf(
                    "Помилка параметрів: %s%n",
                    e.getMessage()
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