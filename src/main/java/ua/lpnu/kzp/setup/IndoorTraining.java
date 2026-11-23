package ua.lpnu.kzp.setup;

/** Тренування в приміщенні з власним правилом обчислення навантаження. */
public final class IndoorTraining extends Training {
    /** Створює тренування в приміщенні з перевіркою базових інваріантів. */
    public IndoorTraining(String date, String kind, int minutes,
                          double distanceKm, int calories) {
        super(date, kind, minutes, distanceKm, calories);
    }

    /** Повертає категорію INDOOR. */
    @Override
    public TrainingKind getTrainingKind() {
        return TrainingKind.INDOOR;
    }

    /** Формула: хвилини × 1.2 + калорії × 0.1. */
    @Override
    public double calculateLoad() {
        return getMinutes() * 1.2 + getCalories() * 0.10;
    }
}
