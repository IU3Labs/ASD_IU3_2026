package ru.bmstu.lab01.task_22;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SortByFrequencyTest {

    @Test
    void shouldSortByDescendingFrequency() {
        // частоты: 4→2, 5→1, 6→3
        assertArrayEquals(new int[] {6, 6, 6, 4, 4, 5},
                SortByFrequency.sortByFrequency(new int[] {4, 6, 5, 6, 4, 6}));
    }

    @Test
    void shouldSortEqualFrequenciesByValue() {
        // все по 1 — по возрастанию значения
        assertArrayEquals(new int[] {1, 2, 3},
                SortByFrequency.sortByFrequency(new int[] {3, 1, 2}));
    }

    @Test
    void shouldSortMixedFrequenciesAndTies() {
        // частоты: 2→2, 3→2, 1→1, 4→1
        // сначала пары (2 и 3 — по возрастанию), потом одиночки (1 и 4)
        assertArrayEquals(new int[] {2, 2, 3, 3, 1, 4},
                SortByFrequency.sortByFrequency(new int[] {1, 2, 3, 4, 2, 3}));
    }

    @Test
    void shouldHandleAllEqualElements() {
        assertArrayEquals(new int[] {5, 5, 5},
                SortByFrequency.sortByFrequency(new int[] {5, 5, 5}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[] {42},
                SortByFrequency.sortByFrequency(new int[] {42}));
    }

    @Test
    void shouldHandleNegativeValues() {
        // частоты: -1→2, 0→1, 1→1
        assertArrayEquals(new int[] {-1, -1, 0, 1},
                SortByFrequency.sortByFrequency(new int[] {-1, 0, -1, 1}));
    }

    @Test
    void shouldHandleLargeFrequencies() {
        int[] data = new int[100];
        for (int i = 0; i < 50; i++) {
            data[i] = 7;
            data[i + 50] = 3;
        }
        int[] result = SortByFrequency.sortByFrequency(data);
        // обе частоты равны 50 — по возрастанию значения: 3...3, 7...7
        for (int i = 0; i < 50; i++) {
            org.junit.jupiter.api.Assertions.assertEquals(3, result[i]);
            org.junit.jupiter.api.Assertions.assertEquals(7, result[i + 50]);
        }
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SortByFrequency.sortByFrequency(null));
    }

    @Test
    void shouldRejectEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> SortByFrequency.sortByFrequency(new int[0]));
    }
}