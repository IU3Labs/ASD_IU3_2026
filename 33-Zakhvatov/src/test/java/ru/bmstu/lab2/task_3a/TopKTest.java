package ru.bmstu.lab2.task_3a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** проверки задачи 3A. */
class TopKTest {
    @Test
    void shouldFindTopKWithDuplicates() {
        assertEquals(List.of(9, 9, 5), TopK.topK(List.of(1, 9, 3, 9, 5), 3));
    }

    @Test
    void shouldFindTopKAmongNegativeValues() {
        assertEquals(List.of(-1, -3), TopK.topK(List.of(-9, -1, -3), 2));
    }

    @Test
    void shouldHandleZeroKAndEmptyInput() {
        assertEquals(List.of(), TopK.topK(List.of(1, 2), 0));
        assertEquals(List.of(), TopK.topK(List.<Integer>of(), 0));
    }

    @Test
    void shouldHandleKEqualToInputSizeAndExtremeValues() {
        List<Integer> input = List.of(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
        assertEquals(List.of(Integer.MAX_VALUE, 0, Integer.MIN_VALUE), TopK.topK(input, 3));
    }

    @Test
    void shouldHandleKEqualToOne() {
        assertEquals(List.of(7), TopK.topK(List.of(4, 7, 5), 1));
    }

    @Test
    void shouldRejectInvalidK() {
        assertThrows(IllegalArgumentException.class, () -> TopK.topK(List.of(1), -1));
        assertThrows(IllegalArgumentException.class, () -> TopK.topK(List.of(1), 2));
    }

    @Test
    void shouldRejectNullTopKInputAndItems() {
        assertThrows(NullPointerException.class, () -> TopK.topK(null, 1));
        assertThrows(NullPointerException.class, () -> TopK.topK(Arrays.asList(1, null), 1));
    }

    @Test
    void shouldProcessLinkedInputWithoutChangingIt() {
        List<Integer> input = new LinkedList<>(List.of(4, 2, 7, 1));
        assertEquals(List.of(7, 4), TopK.topK(input, 2));
        assertEquals(List.of(4, 2, 7, 1), input);
    }

    @Test
    void shouldSupportComparableStrings() {
        assertEquals(List.of("z", "b"), TopK.topK(List.of("a", "z", "b"), 2));
    }

    @Test
    void shouldMatchSortingForTopK() {
        Random random = new Random(71);
        for (int size = 0; size <= 100; size++) {
            List<Integer> input = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                input.add(random.nextInt(40) - 20);
            }
            List<Integer> sorted = new ArrayList<>(input);
            sorted.sort(Comparator.reverseOrder());
            for (int k = 0; k <= size; k++) {
                assertEquals(sorted.subList(0, k), TopK.topK(input, k));
            }
        }
    }
}
