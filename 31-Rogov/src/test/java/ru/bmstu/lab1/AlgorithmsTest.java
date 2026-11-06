package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки выбора индексов, точности среднего и верхней границы размера массива. */
class AlgorithmsTest {
    @Test
    void shouldMatchBruteForceForPairs() {
        Random random = new Random(20261019);
        for (int attempt = 0; attempt < 1000; attempt++) {
            int[] values = random.ints(2 + random.nextInt(29), -20, 21).toArray();
            long target = random.nextInt(81) - 40;
            assertArrayEquals(bruteForcePair(values, target),
                    ru.bmstu.lab1.task_12.Main.solve(values, target));
        }
    }

    @Test
    void shouldMatchBruteForceForExtremePairs() {
        int[] candidates = {Integer.MIN_VALUE, Integer.MAX_VALUE, -1, 0, 1};
        for (int first : candidates) {
            for (int second : candidates) {
                int[] values = {first, second, 0, first};
                long target = (long) first + second;
                assertArrayEquals(bruteForcePair(values, target),
                        ru.bmstu.lab1.task_12.Main.solve(values, target));
            }
        }
    }

    @Test
    void shouldChooseFirstElementAtEqualDistanceFromMean() {
        Random random = new Random(31);
        for (int attempt = 0; attempt < 500; attempt++) {
            int[] values = random.ints(2 + random.nextInt(30), -100, 101).toArray();
            // Для небольших целых чисел это независимый эталон с точным сравнением.
            double average = Arrays.stream(values).average().orElseThrow();
            int expected = values[0];
            for (int value : values) {
                if (Math.abs(value - average) < Math.abs(expected - average)) {
                    expected = value;
                }
            }
            assertEquals(expected, ru.bmstu.lab1.task_11.Main.solve(values));
        }
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int[] values = new int[99_999];
        Arrays.fill(values, Integer.MAX_VALUE);
        assertEquals(107374182350000L, ru.bmstu.lab1.task_5.Main.solve(values));
        assertEquals(0, ru.bmstu.lab1.task_6.Main.solve(values));
        assertEquals(Integer.MAX_VALUE, ru.bmstu.lab1.task_11.Main.solve(values));
        assertArrayEquals(new int[] {0, 1},
                ru.bmstu.lab1.task_12.Main.solve(values, 4294967294L));
    }

    @Test
    void shouldLeaveInputUnchanged() {
        int[] values = {3, -1, 0, 2};
        int[] original = values.clone();
        ru.bmstu.lab1.task_1.Main.solve(values);
        ru.bmstu.lab1.task_2.Main.solve(values, 2);
        ru.bmstu.lab1.task_3.Main.solve(values, 2);
        ru.bmstu.lab1.task_4.Main.solve(values, 2);
        ru.bmstu.lab1.task_5.Main.solve(values);
        ru.bmstu.lab1.task_6.Main.solve(values);
        ru.bmstu.lab1.task_7.Main.solve(values);
        ru.bmstu.lab1.task_8.Main.solve(values);
        ru.bmstu.lab1.task_9.Main.solve(values);
        ru.bmstu.lab1.task_10.Main.solve(values, 2);
        ru.bmstu.lab1.task_11.Main.solve(values);
        ru.bmstu.lab1.task_12.Main.solve(values, 2);
        assertArrayEquals(original, values);
    }

    private int[] bruteForcePair(int[] values, long target) {
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if ((long) values[i] + values[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1, -1};
    }
}
