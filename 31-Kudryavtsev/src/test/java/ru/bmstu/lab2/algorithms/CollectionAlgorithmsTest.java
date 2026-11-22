package ru.bmstu.lab2.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.bmstu.lab2.algorithms.CollectionAlgorithms.distinct;
import static ru.bmstu.lab2.algorithms.CollectionAlgorithms.topK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab2.map.CollisionKey;

/** Проверяет задачи 3A, 3C; эталоны — сортировка и LinkedHashSet. */
class CollectionAlgorithmsTest {
    @Test
    void shouldReturnLargestElementsInAscendingOrder() {
        assertEquals(List.of(5, 8, 9), topK(List.of(8, 1, 9, 3, 5), 3));
    }

    @Test
    void shouldReturnEmptyTopForEmptyInput() {
        assertEquals(List.of(), topK(List.<Integer>of(), 3));
    }

    @Test
    void shouldReturnEmptyTopForZeroK() {
        assertEquals(List.of(), topK(List.of(3, 1, 2), 0));
    }

    @Test
    void shouldRejectNegativeK() {
        assertThrows(IllegalArgumentException.class, () -> topK(List.of(1), -1));
    }

    @Test
    void shouldReturnMaximumForKOne() {
        assertEquals(List.of(9), topK(List.of(9, 3, 7), 1));
    }

    @Test
    void shouldSortAllWhenKEqualsSize() {
        assertEquals(List.of(1, 2, 3), topK(List.of(3, 1, 2), 3));
    }

    @Test
    void shouldClampKToInputSize() {
        assertEquals(List.of(1, 2), topK(List.of(2, 1), Integer.MAX_VALUE));
    }

    @Test
    void shouldKeepRepeatedLargestValues() {
        assertEquals(List.of(4, 4, 4), topK(List.of(4, 1, 4, 4, 2), 3));
    }

    @Test
    void shouldHandleExtremeValuesInTopK() {
        assertEquals(List.of(-1, Integer.MAX_VALUE), topK(List.of(Integer.MIN_VALUE, -1, Integer.MAX_VALUE), 2));
    }

    @Test
    void shouldSupportStringsAndLinkedInputWithoutChangingIt() {
        List<String> input = new LinkedList<>(List.of("b", "z", "a", "c"));
        assertEquals(List.of("c", "z"), topK(input, 2));
        assertEquals(List.of("b", "z", "a", "c"), input);
    }

    @Test
    void shouldRejectNullTopInputAndElements() {
        assertThrows(NullPointerException.class, () -> topK(null, 1));
        List<Integer> input = new ArrayList<>();
        input.add(null);
        assertThrows(NullPointerException.class, () -> topK(input, 1));
    }

    @Test
    void shouldMatchSortingForRandomInputsAndEveryK() {
        Random random = new Random(2026);
        for (int trial = 0; trial < 40; trial++) {
            List<Integer> input = new ArrayList<>();
            for (int index = 0; index < trial; index++) {
                input.add(random.nextInt(21) - 10);
            }
            List<Integer> sorted = new ArrayList<>(input);
            Collections.sort(sorted);
            for (int k = 0; k <= input.size() + 2; k++) {
                int start = Math.max(0, sorted.size() - k);
                assertEquals(sorted.subList(start, sorted.size()), topK(input, k));
            }
        }
    }

    @Test
    void shouldHandleMillionElementsInTopK() {
        List<Integer> input = new ArrayList<>();
        Random random = new Random(2026);
        for (int index = 0; index < 1_000_000; index++) {
            input.add(random.nextInt());
        }
        List<Integer> sorted = new ArrayList<>(input);
        Collections.sort(sorted);
        assertEquals(sorted.subList(sorted.size() - 100, sorted.size()), topK(input, 100));
    }

    @Test
    void shouldReturnEmptyDistinctForEmptyInput() {
        assertEquals(List.of(), distinct(List.of()));
    }

    @Test
    void shouldKeepOneElement() {
        assertEquals(List.of(7), distinct(List.of(7)));
    }

    @Test
    void shouldKeepFirstOccurrenceOrder() {
        assertEquals(List.of(3, 1, 2), distinct(List.of(3, 1, 3, 2, 1)));
    }

    @Test
    void shouldCollapseEqualElements() {
        assertEquals(List.of(4), distinct(List.of(4, 4, 4)));
    }

    @Test
    void shouldKeepAllUniqueElements() {
        assertEquals(List.of(3, 2, 1), distinct(List.of(3, 2, 1)));
    }

    @Test
    void shouldCompareStringContents() {
        assertEquals(List.of("a", "b"), distinct(List.of("a", "b", new String("a"))));
    }

    @Test
    void shouldHandleCollisionsInDistinct() {
        CollisionKey first = new CollisionKey("a");
        CollisionKey second = new CollisionKey("b");
        assertEquals(List.of(first, second), distinct(List.of(first, second, new CollisionKey("a"))));
    }

    @Test
    void shouldNotModifyDistinctInput() {
        List<Integer> input = new LinkedList<>(List.of(2, 1, 2));
        assertEquals(List.of(2, 1), distinct(input));
        assertEquals(List.of(2, 1, 2), input);
    }

    @Test
    void shouldRejectNullDistinctInputAndElements() {
        assertThrows(NullPointerException.class, () -> distinct(null));
        List<Integer> input = new ArrayList<>();
        input.add(null);
        assertThrows(NullPointerException.class, () -> distinct(input));
    }

    @Test
    void shouldMatchLinkedHashSetAfterResizing() {
        List<Integer> input = new ArrayList<>();
        Random random = new Random(2026);
        for (int index = 0; index < 100_000; index++) {
            input.add(random.nextInt(20_000) - 10_000);
        }
        assertEquals(new ArrayList<>(new LinkedHashSet<>(input)), distinct(input));
    }
}
