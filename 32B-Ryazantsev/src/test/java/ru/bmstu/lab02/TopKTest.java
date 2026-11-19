package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет отбор элементов и интерфейс списка результата. */
class TopKTest {
    @Test
    void shouldReturnDescendingResultAndLeaveInputIntact() {
        List<Integer> input = new ArrayList<>(List.of(4, 1, 9, 2, 8));
        assertEquals(List.of(9, 8, 4), TopK.topK(input, 3));
        assertEquals(List.of(4, 1, 9, 2, 8), input);
    }

    @Test
    void shouldKeepEqualElementsAtCutoff() {
        assertEquals(List.of(7, 7, 7), TopK.topK(List.of(7, 3, 7, 7), 3));
    }

    @Test
    void shouldReturnOnlyMaximumWhenKIsOne() {
        assertEquals(List.of(-1), TopK.topK(List.of(-4, -1, -6), 1));
    }

    @Test
    void shouldReturnEmptyResultWhenKIsZero() {
        assertTrue(TopK.topK(List.of(4, 1), 0).isEmpty());
    }

    @Test
    void shouldReturnEmptyResultForEmptyInput() {
        assertEquals(List.of(), TopK.topK(List.<Integer>of(), 5));
    }

    @Test
    void shouldReturnAllElementsWhenKExceedsInputSize() {
        assertEquals(List.of("z", "b", "a"), TopK.topK(List.of("b", "z", "a"), 10));
    }

    @Test
    void shouldRejectNegativeKAndNullInput() {
        assertThrows(IllegalArgumentException.class, () -> TopK.topK(List.of(1), -1));
        assertThrows(NullPointerException.class, () -> TopK.topK(null, 1));
    }

    @Test
    void shouldRejectNullElementDuringSelection() {
        List<Integer> input = Arrays.asList(3, 1, null);
        assertThrows(NullPointerException.class, () -> TopK.topK(input, 2));
    }

    @Test
    void shouldMatchSortingOnSequentialAccessList() {
        Random random = new Random(3301);
        List<Integer> input = new LinkedList<>();
        for (int i = 0; i < 10_000; i++) {
            input.add(random.nextInt(5000));
        }
        List<Integer> sorted = new ArrayList<>(input);
        sorted.sort(Comparator.reverseOrder());
        assertEquals(sorted.subList(0, 163), TopK.topK(input, 163));
    }

    @Test
    void shouldProvideListReadOperations() {
        List<Integer> result = sample();
        assertFalse(result.isEmpty());
        assertEquals(2, result.get(1));
        assertTrue(result.contains(3));
        assertFalse(result.contains(1));
        assertEquals(1, result.indexOf(2));
        assertEquals(2, result.lastIndexOf(2));
        assertEquals(-1, result.lastIndexOf(1));
        assertTrue(result.containsAll(List.of(2, 3)));
        assertFalse(result.containsAll(List.of(2, 1)));
        assertEquals(List.of(2, 2), result.subList(1, 3));
        assertEquals(List.of(3, 2, 2).hashCode(), result.hashCode());
        assertTrue(result.equals(List.of(3, 2, 2)));
        assertFalse(result.equals(List.of(3, 2, 3)));
        assertFalse(result.equals(List.of(3)));
        assertFalse(result.equals("3"));
        assertEquals("[3, 2, 2]", result.toString());
    }

    @Test
    void shouldCopyArrayWithoutExposingStorage() {
        List<Integer> result = sample();
        Object[] copy = result.toArray();
        copy[0] = 10;
        assertArrayEquals(new Integer[]{3, 2, 2}, result.toArray(new Integer[0]));
        Integer[] buffer = {9, 9, 9, 9};
        assertSame(buffer, result.toArray(buffer));
        assertNull(buffer[3]);
        assertEquals(3, result.getFirst());
    }

    @Test
    void shouldIterateBothWaysAndRespectListBounds() {
        ListIterator<Integer> cursor = sample().listIterator();
        assertEquals(0, cursor.nextIndex());
        assertEquals(-1, cursor.previousIndex());
        assertThrows(NoSuchElementException.class, cursor::previous);
        assertEquals(3, cursor.next());
        assertEquals(3, cursor.previous());
        ListIterator<Integer> tail = sample().listIterator(3);
        assertThrows(NoSuchElementException.class, tail::next);
        assertEquals(2, tail.previous());
        assertThrows(IndexOutOfBoundsException.class, () -> sample().listIterator(4));
    }

    @Test
    void shouldRejectListMutations() {
        List<Integer> result = sample();
        assertThrows(UnsupportedOperationException.class, () -> result.add(1));
        assertThrows(UnsupportedOperationException.class, () -> result.add(0, 1));
        assertThrows(UnsupportedOperationException.class, () -> result.addAll(List.of(1)));
        assertThrows(UnsupportedOperationException.class, () -> result.addAll(0, List.of(1)));
        assertThrows(UnsupportedOperationException.class, () -> result.set(0, 1));
        assertThrows(UnsupportedOperationException.class, () -> result.remove(0));
        assertThrows(UnsupportedOperationException.class, () -> result.remove(Integer.valueOf(2)));
        assertThrows(UnsupportedOperationException.class, () -> result.removeAll(List.of(2)));
        assertThrows(UnsupportedOperationException.class, () -> result.retainAll(List.of(2)));
        assertThrows(UnsupportedOperationException.class, result::clear);
        ListIterator<Integer> cursor = result.listIterator();
        cursor.next();
        assertThrows(UnsupportedOperationException.class, cursor::remove);
        assertThrows(UnsupportedOperationException.class, () -> cursor.set(2));
        assertThrows(UnsupportedOperationException.class, () -> cursor.add(1));
    }

    private List<Integer> sample() {
        return TopK.topK(List.of(2, 2, 3), 3);
    }
}
