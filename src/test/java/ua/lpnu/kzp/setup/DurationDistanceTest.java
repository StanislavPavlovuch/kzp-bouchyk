package ua.lpnu.kzp.setup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DurationDistanceTest
{
    @Test
    void storesComponents()
    {
        DurationDistance value = new DurationDistance(
                45,
                8.5
        );

        assertEquals(45, value.minutes());
        assertEquals(8.5, value.distanceKm(), 0.0001);
    }

    @Test
    void equalRecordsAreEqual()
    {
        DurationDistance first = new DurationDistance(
                45,
                8.5
        );

        DurationDistance second = new DurationDistance(
                45,
                8.5
        );

        assertEquals(first, second);
    }

    @Test
    void addsTrainingValues()
    {
        DurationDistance value = new DurationDistance(
                45,
                8.5
        );

        Training training = new Training(
                "2026-09-12",
                "Велосипед",
                60,
                21.4,
                540
        );

        DurationDistance result = value.add(training);

        assertEquals(105, result.minutes());
        assertEquals(29.9, result.distanceKm(), 0.0001);
    }

    @Test
    void rejectsNegativeValues()
    {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DurationDistance(-1, 5.0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new DurationDistance(10, -1.0)
        );
    }
}