package ru.bmstu.lab1.task_11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class ClosestToAverageTest {

    @Test
    void shouldFindClosestToIntegerAverage() {
        // среднее 4, ближе всех 5
        assertEquals(5, ClosestToAverage.find(new int[] {1, 5, 10, 0}));
    }

    @Test
    void shouldReturnFirstWhenTwoAreEquallyClose() {
        // среднее 2.5, оба на расстоянии 0.5
        assertEquals(3, ClosestToAverage.find(new int[] {3, 2}));
    }

    @Test
    void shouldWorkWithFractionalAverage() {
        // среднее 10/3 = 3.33
        assertEquals(3, ClosestToAverage.find(new int[] {4, 3, 3}));
    }

    @Test
    void shouldNotOverflowOnBigNumbers() {
        int[] array = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        assertEquals(0, ClosestToAverage.find(array));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> ClosestToAverage.find(new int[0]));
    }

    @Test
    void shouldPrintClosestElement() {
        assertEquals("5", MainRunner.run(Main::run, "4\n1 5 10 0"));
    }
}
