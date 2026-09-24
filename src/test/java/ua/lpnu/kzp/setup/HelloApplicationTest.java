package ua.lpnu.kzp.setup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Перевіряє мінімальну поведінку навчальної програми. */
class HelloApplicationTest {

    /** Перевіряє приклад арифметичним smoke-test. */
    @Test
    void sampleCalculationIsCorrect() {
        assertEquals(4, 2 + 2);
    }
}