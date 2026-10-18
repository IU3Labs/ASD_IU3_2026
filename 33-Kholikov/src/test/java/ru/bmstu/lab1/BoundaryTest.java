package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Проверки задач 26, 34–37 на максимальной допустимой длине массива. */
class BoundaryTest {
    @Test
    void shouldHandleLargestAllowedArrays() {
        int size = 99_999;
        int[] numbers = new int[size];
        Arrays.fill(numbers, 1);
        assertEquals(1L, ru.bmstu.lab1.task_26.Main.productModulo(numbers));
        assertEquals(size, ru.bmstu.lab1.task_34.Main.maxSubarraySum(numbers));
        long[] prefix = ru.bmstu.lab1.task_35.Main.prefixSums(numbers);
        assertEquals(size, ru.bmstu.lab1.task_35.Main.rangeSum(prefix, 0, size - 1));
        long[] running = ru.bmstu.lab1.task_36.Main.runningSums(numbers);
        assertEquals(1L, running[0]);
        assertEquals(size, running[size - 1]);
        numbers[0] = Integer.MIN_VALUE;
        numbers[size - 1] = Integer.MAX_VALUE;
        ru.bmstu.lab1.task_37.Main.reverse(numbers);
        assertEquals(Integer.MAX_VALUE, numbers[0]);
        assertEquals(Integer.MIN_VALUE, numbers[size - 1]);
        assertEquals(1, numbers[size / 2]);
    }
}
