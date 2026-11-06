package ru.bmstu.lab1.task40;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DuplicateRemoverTest {

    @Test
    void shouldRemoveRepeatedValues() {
        int[] arr = {1, 1, 2, 2, 2, 3};
        int count = DuplicateRemover.removeDuplicates(arr);
        int[] expected = {1, 2, 3};
        assertEquals(3, count);
        assertArrayEquals(expected, firstElements(arr, count));
    }

    @Test
    void shouldKeepArrayWithoutDuplicates() {
        int[] arr = {-3, 0, 4, 9};
        int count = DuplicateRemover.removeDuplicates(arr);
        int[] expected = {-3, 0, 4, 9};
        assertEquals(4, count);
        assertArrayEquals(expected, firstElements(arr, count));
    }

    @Test
    void shouldLeaveOneElementWhenAllEqual() {
        int[] arr = {7, 7, 7, 7};
        int count = DuplicateRemover.removeDuplicates(arr);
        assertEquals(1, count);
        assertEquals(7, arr[0]);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[] arr = {-5, -5, -1, 0, 0, 2};
        int count = DuplicateRemover.removeDuplicates(arr);
        int[] expected = {-5, -1, 0, 2};
        assertEquals(4, count);
        assertArrayEquals(expected, firstElements(arr, count));
    }

    @Test
    void shouldReturnZeroForEmptyArray() {
        int[] arr = {};
        assertEquals(0, DuplicateRemover.removeDuplicates(arr));
    }

    /** Копирует первые count элементов, чтобы сравнить их с ожидаемым массивом. */
    private static int[] firstElements(int[] arr, int count) {
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = arr[i];
        }
        return result;
    }
}
