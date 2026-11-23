package ua.lpnu.kzp.setup;

import java.util.Locale;
import java.util.Objects;

/**
 * Спільна модель спортивного тренування.
 *
 * <p>Клас залишено конкретним для сумісності з лабораторною роботою № 2:
 * старі тести та клієнти можуть викликати new Training(...). Контракт
 * поліморфного навантаження задано інтерфейсом LoadCalculable.
 * Клас sealed: наслідувати його можуть лише final-підтипи IndoorTraining
 * та OutdoorTraining, що усуває ризик успадкованого finalizer під час
 * винятку з конструктора.</p>
 */
public sealed class Training implements LoadCalculable
        permits IndoorTraining, OutdoorTraining {
    private final String date;
    private final String kind;
    private final int minutes;
    private final double distanceKm;
    private final int calories;

    /** Створює тренування з перевіркою спільних інваріантів. */
    public Training(String date, String kind, int minutes,
                    double distanceKm, int calories) {
        if (date == null || date.isBlank() || kind == null || kind.isBlank()) {
            throw new IllegalArgumentException("порожнє обов'язкове поле.");
        }
        if (minutes < 0 || distanceKm < 0 || calories < 0) {
            throw new IllegalArgumentException("числові значення не можуть бути від'ємними.");
        }
        if (!Double.isFinite(distanceKm)) {
            throw new IllegalArgumentException("дистанція має бути скінченним числом.");
        }
        this.date = date.trim();
        this.kind = kind.trim();
        this.minutes = minutes;
        this.distanceKm = distanceKm;
        this.calories = calories;
    }

    /**
     * Створює тренування з CSV без зміни формату лабораторної № 2.
     * Вибір підтипу є лише у фабриці, а розрахунок навантаження — у підтипах.
     *
     * @param line рядок date;kind;minutes;distanceKm;calories
     * @return відповідний об'єкт Training, IndoorTraining або OutdoorTraining
     */
    public static Training fromCsv(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("порожній рядок.");
        }
        String[] fields = line.split(";", -1);
        if (fields.length != 5) {
            throw new IllegalArgumentException("неправильна кількість полів.");
        }
        String date = fields[0].trim();
        String kind = fields[1].trim();
        try {
            int minutes = Integer.parseInt(fields[2].trim());
            double distanceKm = Double.parseDouble(fields[3].trim());
            int calories = Integer.parseInt(fields[4].trim());
            return switch (TrainingKind.fromActivity(kind)) {
                case INDOOR -> new IndoorTraining(date, kind, minutes, distanceKm, calories);
                case OUTDOOR -> new OutdoorTraining(date, kind, minutes, distanceKm, calories);
                case OTHER -> new Training(date, kind, minutes, distanceKm, calories);
            };
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("неправильне числове значення.", exception);
        }
    }

    /** Повертає дату тренування. */
    public final String getDate() { return date; }

    /** Повертає текстовий вид тренування (як у вхідному CSV). */
    public final String getKind() { return kind; }

    /** Повертає тривалість. */
    public final int getMinutes() { return minutes; }

    /** Повертає дистанцію. */
    public final double getDistanceKm() { return distanceKm; }

    /** Повертає витрачені калорії. */
    public final int getCalories() { return calories; }

    /** Категорія базового тренування, якщо вид не класифікований. */
    public TrainingKind getTrainingKind() { return TrainingKind.OTHER; }

    /**
     * Типова умовна оцінка навантаження для невідомого виду тренування.
     * Одиниці умовні; це не медичний або спортивно-фізіологічний норматив.
     */
    @Override
    public double calculateLoad() {
        return minutes + calories * 0.10;
    }

    /**
     * Логічна рівність за конкретним класом і всіма полями CSV.
     * Різні підтипи не стають рівними через однакові значення полів.
     */
    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Training that = (Training) other;
        return minutes == that.minutes
                && Double.compare(distanceKm, that.distanceKm) == 0
                && calories == that.calories
                && date.equals(that.date)
                && kind.equals(that.kind);
    }

    /** Хеш-код використовує ті самі компоненти, що й equals. */
    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), date, kind, minutes, distanceKm, calories);
    }

    /** Зберігає текстове подання лабораторної № 2 без змін. */
    @Override
    public final String toString() {
        return String.format(Locale.ROOT, "%s;%s;%d;%.2f;%d",
                date, kind, minutes, distanceKm, calories);
    }
}
