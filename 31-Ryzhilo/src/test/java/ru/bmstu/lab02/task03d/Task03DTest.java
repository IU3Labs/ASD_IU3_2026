package ru.bmstu.lab02.task03d;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task03d.Task03D;

/** Проверяет решение задачи 03D. */
class Task03DTest {
    @Test
    @DisplayName("Минимум учитывает границы окна и повторы")
    void shouldHandleWindowBoundariesAndDuplicates() {
        int[] items = {4, 2, 2, 5, 1, 3};
        assertArrayEquals(new int[]{2, 2, 1, 1}, Task03D.slidingMinimum(items, 3));
        assertArrayEquals(items, Task03D.slidingMinimum(items, 1));
        assertArrayEquals(new int[]{1}, Task03D.slidingMinimum(items, items.length));
        assertThrows(IllegalArgumentException.class, () -> Task03D.slidingMinimum(items, 0));
        assertThrows(IllegalArgumentException.class, () -> Task03D.slidingMinimum(items, items.length + 1));
    }

    @Test
    @DisplayName("Минимумы на 200 000 элементах совпадают с прямым перебором")
    void shouldMatchDirectScanForTwoHundredThousandElements() {
        int[] input = new Random(42).ints(200_000, -1_000_000, 1_000_000).toArray();
        int window = 1_000;
        int[] expected = new int[input.length - window + 1];
        for (int left = 0; left < expected.length; left++) {
            int minimum = input[left];
            for (int index = left + 1; index < left + window; index++) {
                minimum = Math.min(minimum, input[index]);
            }
            expected[left] = minimum;
        }
        assertArrayEquals(expected, Task03D.slidingMinimum(input, window));
    }
}
