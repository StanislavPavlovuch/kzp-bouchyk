package ua.lpnu.kzp.setup;

import java.util.List;
import java.util.Locale;

/** Обробляє колекцію різних підтипів Training через спільний контракт. */
public final class TrainingLoadAnalyzer {
    private TrainingLoadAnalyzer() {
    }

    /** Незмінний підсумок поліморфного аналізу. */
    public record LoadSummary(int count, double totalLoad, double maximumLoad) {
        /** Перевіряє підсумкові значення. */
        public LoadSummary {
            if (count < 0 || totalLoad < 0 || maximumLoad < 0
                    || !Double.isFinite(totalLoad) || !Double.isFinite(maximumLoad)) {
                throw new IllegalArgumentException("Некоректні значення підсумку.");
            }
        }
    }

    /**
     * Розраховує статистику, не використовуючи instanceof або switch за підтипами.
     *
     * @param trainings тренування різних конкретних класів
     * @return загальний і максимальний умовний показник навантаження
     */
    public static LoadSummary summarize(List<? extends Training> trainings) {
        double total = 0.0;
        double maximum = 0.0;
        for (Training training : trainings) {
            double load = training.calculateLoad(); // Динамічний поліморфізм.
            total += load;
            maximum = Math.max(maximum, load);
        }
        return new LoadSummary(trainings.size(), total, maximum);
    }

    /** Повертає деталізацію без змін звичайного звіту лабораторної № 2. */
    public static String detail(List<? extends Training> trainings) {
        StringBuilder result = new StringBuilder();
        for (Training training : trainings) {
            result.append(String.format(Locale.ROOT,
                    "%s | %s | %s | навантаження: %.2f%n",
                    training.getDate(), training.getKind(),
                    training.getTrainingKind().label(), training.calculateLoad()));
        }
        return result.toString();
    }
}
