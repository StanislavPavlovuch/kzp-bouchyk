package ua.lpnu.kzp.setup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Окрема демонстрація поліморфізму; старий запуск JAR не змінюється. */
public final class TrainingLoadDemo {
    private TrainingLoadDemo() {
    }

    /**
     * Запускає новий режим демонстрації навантаження.
     *
     * @param args необов'язковий перший параметр — шлях до CSV
     * @throws IOException якщо файл не прочитано
     */
    public static void main(String[] args) throws IOException {
        Path input = args.length == 0 ? Path.of("data/input.csv") : Path.of(args[0]);
        List<String> lines = Files.readAllLines(input, StandardCharsets.UTF_8);
        List<Training> trainings = new ArrayList<>();

        for (int index = 0; index < lines.size(); index++) {
            try {
                trainings.add(Training.fromCsv(lines.get(index)));
            } catch (IllegalArgumentException exception) {
                System.out.printf("Рядок %d пропущено: %s%n",
                        index + 1, exception.getMessage());
            }
        }

        TrainingLoadAnalyzer.LoadSummary result = TrainingLoadAnalyzer.summarize(trainings);
        System.out.println("Поліморфний аналіз навантаження (ЛР № 3)");
        System.out.print(TrainingLoadAnalyzer.detail(trainings));
        System.out.printf(Locale.ROOT, "Коректних записів: %d%n", result.count());
        System.out.printf(Locale.ROOT, "Сумарне навантаження: %.2f у.о.%n",
                result.totalLoad());
        System.out.printf(Locale.ROOT, "Максимальне навантаження: %.2f у.о.%n",
                result.maximumLoad());
    }
}
