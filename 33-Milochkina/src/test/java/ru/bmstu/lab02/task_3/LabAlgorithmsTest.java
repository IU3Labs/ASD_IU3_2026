package ru.bmstu.lab02.task_3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

import ru.bmstu.lab02.task_2.CollisionKey;

/** Проверяет прикладные алгоритмы на малых и больших входах */
class LabAlgorithmsTest {
    @Test
    void shouldPreserveRepeatsInTopK() {
        assertEquals(List.of(9, 9, 2), LabAlgorithms.topK(List.of(2, 9, -1, 9, 0), 3));
    }

    @Test
    void shouldHandleZeroAndFullTopK() {
        assertEquals(List.of(), LabAlgorithms.topK(List.of(3, 1), 0));
        assertEquals(List.of(3, 1), LabAlgorithms.topK(List.of(1, 3), 2));
    }

    @Test
    void shouldRejectInvalidTopK() {
        assertThrows(IllegalArgumentException.class, () -> LabAlgorithms.topK(List.of(1), 2));
        assertThrows(IllegalArgumentException.class, () -> LabAlgorithms.topK(List.of(1), -1));
    }

    @Test
    void shouldGetLargestFromSequentialList() {
        assertEquals(List.of(5), LabAlgorithms.topK(new LinkedList<>(List.of(1, 3, 5, 2)), 1));
    }

    @Test
    void shouldMatchSortingOnOneMillionValues() {
        Random random = new Random(33);
        List<Integer> values = new ArrayList<>(1_000_000);
        for (int index = 0; index < 1_000_000; index++) {
            values.add(random.nextInt());
        }
        List<Integer> expected = new ArrayList<>(values);
        expected.sort(Comparator.reverseOrder());
        assertEquals(expected.subList(0, 100), LabAlgorithms.topK(values, 100));
    }

    @Test
    void shouldCountWordsIgnoringCaseAndPunctuation() {
        assertEquals(Map.of("кот", 2, "пёс", 1), LabAlgorithms.countWords("Кот, КОТ! Пёс."));
    }

    @Test
    void shouldAcceptEmptyTextAndOnlySeparators() {
        assertTrue(LabAlgorithms.countWords("").isEmpty());
        assertTrue(LabAlgorithms.countWords("—  ,!\n").isEmpty());
    }

    @Test
    void shouldReadAllWordsFromLargeText() {
        String unit = "Лес! дом. ";
        assertEquals(Map.of("лес", 65_536, "дом", 65_536), LabAlgorithms.countWords(unit.repeat(65_536)));
    }

    @Test
    void shouldKeepDistinctValuesInFirstAppearanceOrder() {
        assertEquals(List.of(3, 1, 2), LabAlgorithms.distinct(List.of(3, 1, 3, 2, 1)));
    }

    @Test
    void shouldKeepOneNullAndHandleEmptyInput() {
        assertEquals(Arrays.asList(null, "x"), LabAlgorithms.distinct(Arrays.asList(null, "x", null)));
        assertEquals(List.of(), LabAlgorithms.distinct(List.of()));
    }

    @Test
    void shouldMatchLinkedHashSetWithCollidingValues() {
        List<CollisionKey> values = List.of(new CollisionKey("a"), new CollisionKey("b"), new CollisionKey("a"));
        assertEquals(new ArrayList<>(new LinkedHashSet<>(values)), LabAlgorithms.distinct(values));
    }

    @Test
    void shouldFindMinimumAfterOldMinimumExpires() {
        assertArrayEquals(new int[]{-1, -1, 2, 2}, LabAlgorithms.slidingMinimum(new int[]{4, -1, 3, 2, 5}, 2));
    }

    @Test
    void shouldHandleWholeWindowAndUnitWindows() {
        int[] values = {4, -3, -3, 5};
        assertArrayEquals(values, LabAlgorithms.slidingMinimum(values, 1));
        assertArrayEquals(new int[]{-3}, LabAlgorithms.slidingMinimum(values, values.length));
    }

    @Test
    void shouldRejectInvalidWindow() {
        assertThrows(IllegalArgumentException.class, () -> LabAlgorithms.slidingMinimum(new int[]{1}, 0));
        assertThrows(IllegalArgumentException.class, () -> LabAlgorithms.slidingMinimum(new int[]{1}, 2));
    }

    @Test
    void shouldMatchBruteForceOnTwoHundredThousandValues() {
        int[] values = new Random(33).ints(200_000, -1_000, 1_000).toArray();
        int window = 1_000;
        int[] actual = LabAlgorithms.slidingMinimum(values, window);
        assertEquals(values.length - window + 1, actual.length);
        for (int start = 0; start < actual.length; start++) {
            int min = values[start];
            for (int index = start + 1; index < start + window; index++) {
                min = Math.min(min, values[index]);
            }
            assertEquals(min, actual[start]);
        }
    }
}
