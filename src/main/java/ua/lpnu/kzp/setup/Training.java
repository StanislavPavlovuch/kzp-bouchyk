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
     * Створює тренування.
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
        this.date = date;
        this.kind = kind;
        this.minutes = minutes;
        this.distanceKm = distanceKm;
        this.calories = calories;
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