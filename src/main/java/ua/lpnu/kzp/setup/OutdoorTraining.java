package ua.lpnu.kzp.setup;

/** Тренування надворі з урахуванням дистанції в показнику навантаження. */
public final class OutdoorTraining extends Training {
    /** Створює тренування надворі з перевіркою базових інваріантів. */
    public OutdoorTraining(String date, String kind, int minutes,
                           double distanceKm, int calories) {
        super(date, kind, minutes, distanceKm, calories);
    }

    /** Повертає категорію OUTDOOR. */
    @Override
    public TrainingKind getTrainingKind() {
        return TrainingKind.OUTDOOR;
    }

    /** Формула: хвилини + дистанція × 10 + калорії × 0.1. */
    @Override
    public double calculateLoad() {
        return getMinutes() + getDistanceKm() * 10.0 + getCalories() * 0.10;
    }
}
