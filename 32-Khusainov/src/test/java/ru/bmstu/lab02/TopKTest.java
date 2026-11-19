package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки контракта TopK и граничных случаев. */
class TopKTest {
    @Test
    void shouldHandleZero() {
        assertEquals(List.of(), TopK.topK(List.of(4, 2), 0));
    }

    @Test
    void shouldHandleEmptyInput() {
        assertEquals(List.of(), TopK.topK(List.<Integer>of(), 3));
    }

    @Test
    void shouldRejectNegativeK() {
        assertThrows(IllegalArgumentException.class, () -> TopK.topK(List.of(2), -1));
    }

    @Test
    void shouldKeepMaximum() {
        assertEquals(List.of(8), TopK.topK(List.of(2, 8, 6), 1));
    }

    @Test
    void shouldKeepDuplicates() {
        assertEquals(List.of(7, 7, 4), TopK.topK(List.of(4, 7, 2, 7), 3));
    }

    @Test
    void shouldReturnAllWhenKExceedsSize() {
        assertEquals(List.of(-1, -4), TopK.topK(List.of(-4, -1), 8));
    }

    @Test
    void shouldApplyNaturalOrderToStrings() {
        assertEquals(List.of("z", "b"), TopK.topK(List.of("b", "a", "z"), 2));
    }

    @Test
    void shouldPreserveInput() {
        List<Integer> input = new ArrayList<>(List.of(2, 4, 1));
        TopK.topK(input, 2);
        assertEquals(List.of(2, 4, 1), input);
    }

    @Test
    void shouldAcceptSequentialLists() {
        assertEquals(List.of(5, 4), TopK.topK(new LinkedList<>(List.of(1, 4, 5, 2)), 2));
    }

    @Test
    void shouldMatchSortingAcrossK() {
        List<Integer> input = new Random(24).ints(300, -30, 31).boxed().toList();
        List<Integer> sorted = input.stream().sorted(Comparator.reverseOrder()).toList();
        for (int k = 0; k <= input.size(); k++) {
            assertEquals(sorted.subList(0, k), TopK.topK(input, k));
        }
    }
}
