package ua.lpnu.kzp.setup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrainingTest
{
    @Test
    void createsCorrectTraining()
    {
        Training training = new Training(
                "2026-09-10",
                "Біг",
                45,
                8.5,
                620
        );

        assertEquals("2026-09-10", training.getDate());
        assertEquals("Біг", training.getKind());
        assertEquals(45, training.getMinutes());
        assertEquals(8.5, training.getDistanceKm(), 0.0001);
        assertEquals(620, training.getCalories());
    }

    @Test
    void acceptsBoundaryZeroValues()
    {
        Training training = new Training(
                "2026-09-10",
                "Відпочинок",
                0,
                0.0,
                0
        );

        assertEquals(0, training.getMinutes());
        assertEquals(0.0, training.getDistanceKm(), 0.0001);
        assertEquals(0, training.getCalories());
    }

    @Test
    void rejectsNullDate()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Training(
                        null,
                        "Біг",
                        45,
                        8.5,
                        620
                )
        );
    }

    @Test
    void rejectsBlankDate()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Training(
                        "   ",
                        "Біг",
                        45,
                        8.5,
                        620
                )
        );
    }

    @Test
    void rejectsBlankKind()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Training(
                        "2026-09-10",
                        "",
                        45,
                        8.5,
                        620
                )
        );
    }

    @Test
    void rejectsNegativeValues()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Training(
                        "2026-09-10",
                        "Біг",
                        -1,
                        8.5,
                        620
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Training(
                        "2026-09-10",
                        "Біг",
                        45,
                        -1.0,
                        620
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Training(
                        "2026-09-10",
                        "Біг",
                        45,
                        8.5,
                        -1
                )
        );
    }

    @Test
    void parsesCorrectCsv()
    {
        Training training = Training.fromCsv(
                "2026-09-10;Біг;45;8.5;620"
        );

        assertEquals("2026-09-10", training.getDate());
        assertEquals("Біг", training.getKind());
        assertEquals(45, training.getMinutes());
        assertEquals(8.5, training.getDistanceKm(), 0.0001);
        assertEquals(620, training.getCalories());
    }

    @Test
    void rejectsWrongFieldCount()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> Training.fromCsv(
                        "2026-09-10;Біг;45"
                )
        );
    }

    @Test
    void rejectsWrongNumber()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> Training.fromCsv(
                        "2026-09-10;Біг;abc;8.5;620"
                )
        );
    }

    @Test
    void rejectsCsvWithInvariantViolation()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> Training.fromCsv(
                        "2026-09-10;Біг;-45;8.5;620"
                )
        );
    }
}