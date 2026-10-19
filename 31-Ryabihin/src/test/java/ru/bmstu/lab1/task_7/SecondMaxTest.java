package ru.bmstu.lab1.task_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class SecondMaxTest {

    @Test
    void shouldFindSecondMax() {
        assertEquals(8, SecondMax.find(new int[] {5, 8, 1, 10, 3}));
    }

    @Test
    void shouldWorkWithTwoElements() {
        assertEquals(1, SecondMax.find(new int[] {2, 1}));
        assertEquals(1, SecondMax.find(new int[] {1, 2}));
    }

    @Test
    void shouldUpdateBothWhenArrayIsIncreasing() {
        assertEquals(3, SecondMax.find(new int[] {1, 2, 3, 4}));
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        assertEquals(-2, SecondMax.find(new int[] {-5, -2, -1}));
    }

    @Test
    void shouldThrowWhenLessThanTwoElements() {
        assertThrows(IllegalArgumentException.class, () -> SecondMax.find(new int[] {1}));
    }

    @Test
    void shouldPrintSecondMax() {
        assertEquals("8", MainRunner.run(Main::run, "5\n5 8 1 10 3"));
    }
}
