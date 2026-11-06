package ru.bmstu.lab1.task_12;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;
import org.junit.jupiter.api.Test;

/** Независимая проверка задачи 12 полным перебором и массивами предельного размера. */
class PairSearchTest {
    private static final int MAX_VALID_SIZE = 99_999;
    private static final int RANDOM_CASES = 1_000;
    private static final int RANDOM_MAX_LENGTH = 30;
    private static final int RANDOM_VALUE_BOUND = 21;
    private static final int RANDOM_VALUE_OFFSET = 10;
    private static final long SEED = 20261019L;

    @Test
    void shouldMatchBruteForceOnRandomSmallArraysAndPreserveInput() {
        Random random = new Random(SEED);
        for (int example = 0; example < RANDOM_CASES; example++) {
            int[] values = new int[2 + random.nextInt(RANDOM_MAX_LENGTH - 1)];
            for (int index = 0; index < values.length; index++) {
                values[index] = random.nextInt(RANDOM_VALUE_BOUND) - RANDOM_VALUE_OFFSET;
            }
            long target = random.nextInt(RANDOM_VALUE_BOUND) - RANDOM_VALUE_OFFSET;
            int[] original = values.clone();
            assertArrayEquals(bruteForce(values, target), PairWithSum.solve(values, target));
            assertArrayEquals(original, values);
        }
    }

    @Test
    void shouldHandleLargestPermittedArrayWithoutPair() {
        int[] values = new int[MAX_VALID_SIZE];
        for (int index = 0; index < values.length; index++) {
            values[index] = index;
        }
        assertArrayEquals(new int[] {-1, -1}, PairWithSum.solve(values, -1));
    }

    @Test
    void shouldChooseFirstTwoIndicesAmongManyEqualElements() {
        assertArrayEquals(new int[] {0, 1}, PairWithSum.solve(new int[MAX_VALID_SIZE], 0));
    }

    @Test
    void shouldFindOnlyPairAtEndOfLargestPermittedArray() {
        int[] values = new int[MAX_VALID_SIZE];
        values[values.length - 2] = 1;
        values[values.length - 1] = 2;
        assertArrayEquals(
                new int[] {values.length - 2, values.length - 1}, PairWithSum.solve(values, 3));
    }

    private static int[] bruteForce(int[] values, long target) {
        for (int first = 0; first < values.length; first++) {
            for (int second = first + 1; second < values.length; second++) {
                if ((long) values[first] + values[second] == target) {
                    return new int[] {first, second};
                }
            }
        }
        return new int[] {-1, -1};
    }
}
