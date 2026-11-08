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
        int maxCalories = 0;

        DurationDistance totals = new DurationDistance(0, 0);

        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            int lineNumber = i + 1;

            try
            {
                Training training = Training.fromCsv(line);

                validCount++;
                totals = totals.add(training);

                if (training.getCalories() > maxCalories)
                {
                    maxCalories = training.getCalories();
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.printf(
                        "Рядок %d пропущено: %s%n",
                        lineNumber,
                        e.getMessage()
                );
            }
        }

        return formatReport(
                validCount,
                totals.minutes(),
                totals.distanceKm(),
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