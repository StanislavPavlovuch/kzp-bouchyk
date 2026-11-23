package ua.lpnu.kzp.setup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

/** Додаткові тести ЛР № 3; 21 попередній тест не змінюється. */
class TrainingPolymorphismTest {
    @Test
    void factoryCreatesOutdoorSubtype() {
        Training training = Training.fromCsv("2026-09-10;Біг;45;8.5;620");
        assertInstanceOf(OutdoorTraining.class, training);
        assertEquals(TrainingKind.OUTDOOR, training.getTrainingKind());
    }

    @Test
    void factoryCreatesIndoorSubtype() {
        Training training = Training.fromCsv("2026-09-15;Плавання;50;2.1;430");
        assertInstanceOf(IndoorTraining.class, training);
        assertEquals(TrainingKind.INDOOR, training.getTrainingKind());
    }

    @Test
    void legacyUnknownActivityRemainsValid() {
        Training training = Training.fromCsv("2026-09-10;Теніс;30;0;100");
        assertEquals(Training.class, training.getClass());
        assertEquals(TrainingKind.OTHER, training.getTrainingKind());
    }

    @Test
    void sameValuesGiveDifferentPolymorphicLoad() {
        Training indoor = new IndoorTraining("2026-09-10", "Тренування", 30, 4.0, 100);
        Training outdoor = new OutdoorTraining("2026-09-10", "Тренування", 30, 4.0, 100);
        assertEquals(46.0, indoor.calculateLoad(), 0.0001);
        assertEquals(80.0, outdoor.calculateLoad(), 0.0001);
        assertNotEquals(indoor.calculateLoad(), outdoor.calculateLoad());
    }

    @Test
    void polymorphicListCalculatesRealInput() {
        List<Training> trainings = List.of(
                Training.fromCsv("2026-09-10;Біг;45;8.5;620"),
                Training.fromCsv("2026-09-12;Велосипед;60;21.4;540"),
                Training.fromCsv("2026-09-15;Плавання;50;2.1;430"));
        TrainingLoadAnalyzer.LoadSummary summary = TrainingLoadAnalyzer.summarize(trainings);
        assertEquals(3, summary.count());
        assertEquals(623.0, summary.totalLoad(), 0.0001);
        assertEquals(328.0, summary.maximumLoad(), 0.0001);
    }

    @Test
    void equalObjectsHaveMatchingHashCodes() {
        Training first = new IndoorTraining("2026-09-10", "Йога", 30, 0, 100);
        Training second = new IndoorTraining("2026-09-10", "Йога", 30, 0, 100);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void hashSetEliminatesEqualDuplicates() {
        Set<Training> set = new HashSet<>();
        set.add(new OutdoorTraining("2026-09-10", "Біг", 45, 8.5, 620));
        set.add(new OutdoorTraining("2026-09-10", "Біг", 45, 8.5, 620));
        assertEquals(1, set.size());
    }

    @Test
    void differentSubtypesAreNotEqual() {
        Training indoor = new IndoorTraining("2026-09-10", "Спорт", 30, 4.0, 100);
        Training outdoor = new OutdoorTraining("2026-09-10", "Спорт", 30, 4.0, 100);
        assertNotEquals(indoor, outdoor);
        assertNotEquals(indoor, new Training("2026-09-10", "Спорт", 30, 4.0, 100));
    }

    @Test
    void differentValuesAreNotEqual() {
        Training first = new IndoorTraining("2026-09-10", "Йога", 30, 0, 100);
        Training second = new IndoorTraining("2026-09-10", "Йога", 35, 0, 100);
        assertNotEquals(first, second);
        assertFalse(first.equals(null));
    }

    @Test
    void baseAndSubtypesRejectInvalidState() {
        assertThrows(IllegalArgumentException.class,
                () -> new Training("", "Йога", 30, 0, 100));
        assertThrows(IllegalArgumentException.class,
                () -> new IndoorTraining("2026-09-10", "Йога", -1, 0, 100));
        assertThrows(IllegalArgumentException.class,
                () -> new OutdoorTraining("2026-09-10", "Біг", 30, -1, 100));
    }

    @Test
    void nonFiniteDistanceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new OutdoorTraining("2026-09-10", "Біг", 10,
                        Double.POSITIVE_INFINITY, 100));
        assertThrows(IllegalArgumentException.class,
                () -> Training.fromCsv("2026-09-10;Біг;10;NaN;100"));
    }

    @Test
    void enumContainsDisplayLabelsAndFallback() {
        assertEquals("У приміщенні", TrainingKind.INDOOR.label());
        assertEquals(TrainingKind.OUTDOOR, TrainingKind.fromActivity("БіГ"));
        assertEquals(TrainingKind.OTHER, TrainingKind.fromActivity("Невідомо"));
        assertEquals(TrainingKind.OTHER, TrainingKind.fromActivity(null));
    }

    @Test
    void newHierarchyPreservesPreviousReport() {
        String report = HelloApplication.buildReport(List.of(
                "2026-09-10;Біг;45;8.5;620",
                "2026-09-12;Велосипед;60;21.4;540",
                "2026-09-15;Плавання;50;2.1;430",
                "2026-09-17;Ходьба;-30;3.0;180",
                "2026-09-20;Йога;abc;0.0;200"));
        assertTrue(report.contains("Коректних записів: 3"));
        assertTrue(report.contains("Сумарний час: 155 хв"));
        assertTrue(report.contains("Сумарна дистанція: 32.00 км"));
        assertTrue(report.contains("Максимальна витрата калорій: 620"));
    }
}
