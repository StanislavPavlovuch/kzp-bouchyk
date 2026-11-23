package ua.lpnu.kzp.setup;

import java.util.Locale;

/** Категорія тренування за місцем проведення. */
public enum TrainingKind {
    INDOOR("У приміщенні"),
    OUTDOOR("На відкритому повітрі"),
    OTHER("Без визначеної категорії");

    private final String label;

    TrainingKind(String label) {
        this.label = label;
    }

    /** Повертає український підпис категорії. */
    public String label() {
        return label;
    }

    /**
     * Визначає категорію за вже наявним CSV-полем kind.
     * Незнайомі назви не відхиляються: це зберігає поведінку ЛР № 2.
     * Для демонстраційного набору плавання віднесено до басейну в приміщенні.
     *
     * @param activity назва активності
     * @return категорія або OTHER
     */
    public static TrainingKind fromActivity(String activity) {
        if (activity == null) {
            return OTHER;
        }
        return switch (activity.trim().toLowerCase(Locale.ROOT)) {
            case "біг", "велосипед", "ходьба", "прогулянка",
                    "біг на вулиці", "велопрогулянка" -> OUTDOOR;
            case "плавання", "йога", "силове тренування", "фітнес",
                    "тренажерний зал", "пілатес" -> INDOOR;
            default -> OTHER;
        };
    }
}
