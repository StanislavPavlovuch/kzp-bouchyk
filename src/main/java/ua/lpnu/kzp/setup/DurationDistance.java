package ua.lpnu.kzp.setup;

/**
 * Зберігає сумарну тривалість і дистанцію тренувань.
 *
 * @param minutes сумарна тривалість у хвилинах
 * @param distanceKm сумарна дистанція у кілометрах
 */
public record DurationDistance(
        int minutes,
        double distanceKm
)
{
    /**
     * Перевіряє коректність значень.
     */
    public DurationDistance
    {
        if (minutes < 0
                || distanceKm < 0
                || !Double.isFinite(distanceKm))
        {
            throw new IllegalArgumentException(
                    "Тривалість і дистанція не можуть бути від'ємними."
            );
        }
    }

    /**
     * Додає показники одного тренування.
     *
     * @param training тренування
     * @return нові сумарні значення
     */
    public DurationDistance add(Training training)
    {
        return new DurationDistance(
                minutes + training.getMinutes(),
                distanceKm + training.getDistanceKm()
        );
    }
}