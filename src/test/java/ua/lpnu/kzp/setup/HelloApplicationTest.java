package ua.lpnu.kzp.setup;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HelloApplicationTest
{

    @Test
    void calculatesTrainingReport()
    {
        List<String> lines = List.of(
                "2026-09-10;Біг;45;8.5;620",
                "2026-09-12;Велосипед;60;21.4;540",
                "2026-09-15;Плавання;50;2.1;430"
        );

        String report = HelloApplication.buildReport(lines);

        assertTrue(report.contains("Коректних записів: 3"));
        assertTrue(report.contains("Сумарний час: 155 хв"));
        assertTrue(report.contains("Сумарна дистанція: 32.00 км"));
        assertTrue(report.contains("Максимальна витрата калорій: 620"));
    }

    @ParameterizedTest
    @ValueSource(strings =
    {
        "",
        "2026-09-10;Біг;45",
        "2026-09-10;Біг;abc;8.5;620",
        "2026-09-10;Біг;-45;8.5;620",
        ";Біг;45;8.5;620"
    })
    void skipsInvalidRows(String row)
    {
        String report = HelloApplication.buildReport(List.of(row));

        assertTrue(report.contains("Коректних записів: 0"));
        assertTrue(report.contains("Сумарний час: 0 хв"));
        assertTrue(report.contains("Сумарна дистанція: 0.00 км"));
        assertTrue(report.contains("Максимальна витрата калорій: 0"));
    }

    @Test
    void supportsUkrainianText()
    {
        List<String> lines = List.of(
                "2026-09-10;Силове тренування;30;0.0;300"
        );

        String report = HelloApplication.buildReport(lines);

        assertTrue(report.contains("Коректних записів: 1"));
        assertTrue(report.contains("Сумарний час: 30 хв"));
    }
}