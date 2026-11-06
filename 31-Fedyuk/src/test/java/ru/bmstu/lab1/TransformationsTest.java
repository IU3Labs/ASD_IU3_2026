package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.task_37.ArrayReverse;
import ru.bmstu.lab1.task_38.LeftRotation;
import ru.bmstu.lab1.task_41.RemoveElement;

/** Проверки изменения массива на месте, большого размера и порядка оставшихся элементов. */
class TransformationsTest {
    private static final int MAX_VALID_SIZE = 99_999;
    private static final int RANDOM_CASES = 200;
    private static final int MAX_RANDOM_LENGTH = 30;
    private static final long SEED = 20261022L;

    @Test
    void shouldReverseLargeArrayInPlaceAndRestoreItOnSecondCall() {
        int[] values = increasingArray();
        int[] original = values.clone();
        int[] expected = new int[values.length];
        for (int index = 0; index < values.length; index++) {
            expected[index] = values.length - index - 1;
        }
        ArrayReverse.solve(values);
        assertArrayEquals(expected, values);
        ArrayReverse.solve(values);
        assertArrayEquals(original, values);
    }

    @Test
    void shouldMatchRotationByDirectIndexingOnRandomArrays() {
        Random random = new Random(SEED);
        for (int example = 0; example < RANDOM_CASES; example++) {
            int[] values = new int[2 + random.nextInt(MAX_RANDOM_LENGTH - 1)];
            for (int index = 0; index < values.length; index++) {
                values[index] = random.nextInt();
            }
            int distance = random.nextInt();
            int[] expected = new int[values.length];
            for (int index = 0; index < values.length; index++) {
                int source = (int) (((long) index + distance) % values.length);
                if (source < 0) {
                    source += values.length;
                }
                expected[index] = values[source];
            }
            LeftRotation.solve(values, distance);
            assertArrayEquals(expected, values);
        }
    }

    @Test
    void shouldShiftLargestPermittedArrayLeftByOne() {
        int[] values = increasingArray();
        int[] expected = new int[values.length];
        for (int index = 0; index < values.length - 1; index++) {
            expected[index] = index + 1;
        }
        LeftRotation.solve(values, MAX_VALID_SIZE + 1);
        assertArrayEquals(expected, values);
    }

    @Test
    void shouldRemoveAllMatchesFromLargestArray() {
        int[] values = new int[MAX_VALID_SIZE];
        Arrays.fill(values, 7);
        assertArrayEquals(new int[0], RemoveElement.solve(values, 7));
    }

    @Test
    void shouldPreserveOrderAndInputWhenRemovingValue() {
        int[] values = {3, 1, 3, -2, 3, 1};
        int[] original = values.clone();
        assertArrayEquals(new int[] {1, -2, 1}, RemoveElement.solve(values, 3));
        assertArrayEquals(original, values);
    }

    private static int[] increasingArray() {
        int[] values = new int[MAX_VALID_SIZE];
        for (int index = 0; index < values.length; index++) {
            values[index] = index;
        }
        return values;
    }
}
