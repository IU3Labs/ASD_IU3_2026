package ru.bmstu.lab2.task_3c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class DistinctTest {

    @Test
    void shouldKeepFirstOccurrenceOrder() {
        List<Integer> actual = Distinct.distinct(List.of(3, 1, 3, 2, 1, 4));

        assertEquals(List.of(3, 1, 2, 4), actual);
    }

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        assertTrue(Distinct.distinct(List.of()).isEmpty());
    }

    @Test
    void shouldReturnSingleElementWhenAllEqual() {
        assertEquals(List.of("x"), Distinct.distinct(List.of("x", "x", "x")));
    }

    @Test
    void shouldReturnSameListWhenAllElementsUnique() {
        List<Integer> input = List.of(5, 4, 3, 2, 1);

        assertEquals(input, Distinct.distinct(input));
    }

    @Test
    void shouldKeepSingleNullAtItsFirstPosition() {
        List<String> input = Arrays.asList("a", null, "b", null, "a");

        assertEquals(Arrays.asList("a", null, "b"), Distinct.distinct(input));
    }

    @Test
    void shouldReturnSingleNullForAllNullInput() {
        assertEquals(Arrays.asList((String) null), Distinct.distinct(Arrays.asList(null, null)));
    }

    @Test
    void shouldGrowResultArrayAndKeepOrder() {
        List<Integer> input = List.of(9, 8, 7, 6, 5, 4, 9, 3, 2, 1, 0, 8);

        assertEquals(List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0), Distinct.distinct(input));
    }

    @Test
    void shouldNotModifyInputList() {
        List<Integer> input = new ArrayList<>(List.of(2, 2, 1));

        Distinct.distinct(input);

        assertEquals(List.of(2, 2, 1), input);
    }

    @Test
    void shouldCompareElementsByEqualsNotByReference() {
        String first = new String("same");
        String second = new String("same");

        assertEquals(1, Distinct.distinct(List.of(first, second)).size());
    }

    @Test
    void shouldMatchLinkedHashSetOnLargeRandomInput() {
        Random random = new Random(3);
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            input.add(random.nextInt(10_000));
        }

        List<Integer> expected = new ArrayList<>(new LinkedHashSet<>(input));

        assertEquals(expected, Distinct.distinct(input));
    }
}
