package ua.lpnu.kzp.setup;

import java.util.Locale;

/**
 * Представляє одне спортивне тренування.
 */
public final class Training
{
    private final String date;
    private final String kind;
    private final int minutes;
    private final double distanceKm;
    private final int calories;

    /**
     * Створює тренування та перевіряє його дані.
     *
     * @param date дата тренування
     * @param kind вид тренування
     * @param minutes тривалість у хвилинах
     * @param distanceKm дистанція у кілометрах
     * @param calories витрачені калорії
     */
    public Training(
            String date,
            String kind,
            int minutes,
            double distanceKm,
            int calories
    )
    {
        if (date == null || date.isBlank()
                || kind == null || kind.isBlank())
        {
            throw new IllegalArgumentException(
                    "порожнє обов'язкове поле."
            );
        }

        if (minutes < 0 || distanceKm < 0 || calories < 0)
        {
            throw new IllegalArgumentException(
                    "числові значення не можуть бути від'ємними."
            );
        }

        this.date = date.trim();
        this.kind = kind.trim();
        this.minutes = minutes;
        this.distanceKm = distanceKm;
        this.calories = calories;
    }

    /**
     * Створює Training з одного CSV-рядка.
     *
     * @param line рядок CSV
     * @return створене тренування
     */
    public static Training fromCsv(String line)
    {
        if (line == null || line.isBlank())
        {
            throw new IllegalArgumentException(
                    "порожній рядок."
            );
        }

        String[] fields = line.split(";", -1);

        if (fields.length != 5)
        {
            throw new IllegalArgumentException(
                    "неправильна кількість полів."
            );
        }

        String date = fields[0].trim();
        String kind = fields[1].trim();

        try
        {
            int minutes = Integer.parseInt(fields[2].trim());
            double distanceKm = Double.parseDouble(fields[3].trim());
            int calories = Integer.parseInt(fields[4].trim());

            return new Training(
                    date,
                    kind,
                    minutes,
                    distanceKm,
                    calories
            );
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException(
                    "неправильне числове значення."
            );
        }
    }

    public String getDate()
    {
        return date;
    }

    public String getKind()
    {
        return kind;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public double getDistanceKm()
    {
        return distanceKm;
    }

    public int getCalories()
    {
        return calories;
    }

    @Override
    public String toString()
    {
        return String.format(
                Locale.ROOT,
                "%s;%s;%d;%.2f;%d",
                date,
                kind,
                minutes,
                distanceKm,
                calories
        );
    }
}