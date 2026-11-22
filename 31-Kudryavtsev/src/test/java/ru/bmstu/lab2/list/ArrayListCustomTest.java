package ru.bmstu.lab2.list;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/** Проверяет собственный вспомогательный список и адаптацию к интерфейсу List. */
class ArrayListCustomTest {
    @Test
    void shouldStartEmpty() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void shouldAppendAndRead() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        assertTrue(list.add(7));
        assertFalse(list.isEmpty());
        assertEquals(7, list.get(0));
        assertEquals(1, list.size());
    }

    @Test
    void shouldGrowWithoutLosingOrder() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        for (int index = 0; index < 1000; index++) {
            list.add(index);
        }
        assertEquals(1000, list.size());
        for (int index = 0; index < 1000; index++) {
            assertEquals(index, list.get(index));
        }
    }

    @Test
    void shouldRejectNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> new ArrayListCustom<>().get(-1));
    }

    @Test
    void shouldRejectIndexEqualToSize() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        list.add(7);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void shouldRejectReadingEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> new ArrayListCustom<>().get(0));
    }

    @Test
    void shouldAllowNullInHelperList() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        list.add(null);
        assertNull(list.get(0));
    }

    @Test
    void shouldIterateInInsertionOrder() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        list.add(2);
        list.add(1);
        Iterator<Integer> iterator = list.iterator();
        assertEquals(2, iterator.next());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void shouldCompareUsingListContract() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        list.add(2);
        list.add(1);
        assertEquals(List.of(2, 1), list);
        assertEquals(list, List.of(2, 1));
    }

    @Test
    void shouldDetectModificationDuringIteration() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        list.add(1);
        Iterator<Integer> iterator = list.iterator();
        list.add(2);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    void shouldFindFirstLastAndMissingElements() {
        ArrayListCustom<Integer> list = sample();
        list.add(2);
        list.add(null);
        assertEquals(0, list.indexOf(2));
        assertEquals(2, list.lastIndexOf(2));
        assertEquals(3, list.indexOf(null));
        assertEquals(3, list.lastIndexOf(null));
        assertTrue(list.contains(1));
        assertFalse(list.contains(9));
        assertEquals(-1, list.lastIndexOf(9));
    }

    @Test
    void shouldCheckAllElements() {
        ArrayListCustom<Integer> list = sample();
        assertTrue(list.containsAll(List.of(1, 2)));
        assertTrue(list.containsAll(List.of()));
        assertFalse(list.containsAll(List.of(1, 9)));
        assertThrows(NullPointerException.class, () -> list.containsAll(null));
    }

    @Test
    void shouldReturnIndependentArray() {
        ArrayListCustom<Integer> list = sample();
        Object[] array = list.toArray();
        assertArrayEquals(new Object[] {2, 1}, array);
        array[0] = 99;
        assertEquals(2, list.get(0));
        assertArrayEquals(new Object[0], new ArrayListCustom<>().toArray());
    }

    @Test
    void shouldFillTypedArraysOfDifferentSizes() {
        ArrayListCustom<Integer> list = sample();
        assertArrayEquals(new Integer[] {2, 1}, list.toArray(new Integer[0]));
        Integer[] exact = new Integer[2];
        assertSame(exact, list.toArray(exact));
        assertArrayEquals(new Integer[] {2, 1}, exact);
        Number[] larger = {99, 99, 99, 99};
        assertSame(larger, list.toArray(larger));
        assertArrayEquals(new Number[] {2, 1, null, 99}, larger);
    }

    @Test
    void shouldRejectIncompatibleAndNullArrays() {
        ArrayListCustom<Integer> list = sample();
        assertThrows(ArrayStoreException.class, () -> list.toArray(new String[0]));
        assertThrows(NullPointerException.class, () -> list.toArray((Integer[]) null));
    }

    @Test
    void shouldMoveIteratorInBothDirectionsAndCheckBounds() {
        ListIterator<Integer> iterator = sample().listIterator();
        assertFalse(iterator.hasPrevious());
        assertEquals(0, iterator.nextIndex());
        assertEquals(-1, iterator.previousIndex());
        assertThrows(NoSuchElementException.class, iterator::previous);
        assertEquals(2, iterator.next());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertTrue(iterator.hasPrevious());
        assertEquals(1, iterator.previous());
        assertEquals(2, iterator.previous());
        assertEquals(1, sample().listIterator(2).previous());
    }

    @Test
    void shouldRejectInvalidIteratorPosition() {
        ArrayListCustom<Integer> list = sample();
        assertThrows(IndexOutOfBoundsException.class, () -> list.listIterator(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.listIterator(3));
    }

    @Test
    void shouldReadNestedSubListsAndTheirArrays() {
        ArrayListCustom<Integer> list = sample();
        list.add(3);
        List<Integer> view = list.subList(1, 3);
        assertEquals(List.of(1, 3), view);
        assertEquals(List.of(3), view.subList(1, 2));
        assertTrue(view.subList(1, 1).isEmpty());
        assertArrayEquals(new Object[] {1, 3}, view.toArray());
        assertArrayEquals(new Integer[] {1, 3}, view.toArray(new Integer[0]));
        assertThrows(UnsupportedOperationException.class, () -> view.add(4));
    }

    @Test
    void shouldRejectInvalidSubListBounds() {
        ArrayListCustom<Integer> list = sample();
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.subList(0, 3));
        assertThrows(IllegalArgumentException.class, () -> list.subList(2, 1));
    }

    @Test
    void shouldInvalidateViewsAndBackwardIteratorsAfterAppend() {
        ArrayListCustom<Integer> list = sample();
        List<Integer> view = list.subList(0, 1);
        ListIterator<Integer> iterator = list.listIterator(2);
        list.add(3);
        assertThrows(ConcurrentModificationException.class, view::size);
        assertThrows(ConcurrentModificationException.class, iterator::previous);
    }

    @Test
    void shouldFollowEqualsAndHashCodeContracts() {
        ArrayListCustom<Integer> list = sample();
        assertEquals(list, list);
        assertNotEquals(list, null);
        assertNotEquals(list, "text");
        assertNotEquals(list, List.of(2));
        assertNotEquals(list, List.of(2, 9));
        assertEquals(List.of(2, 1).hashCode(), list.hashCode());
        list.add(null);
        assertEquals(java.util.Arrays.asList(2, 1, null).hashCode(), list.hashCode());
    }

    @Test
    void shouldFormatEmptyNullAndSelfReferences() {
        assertEquals("[]", new ArrayListCustom<>().toString());
        assertEquals("[2, 1]", sample().toString());
        ArrayListCustom<Object> list = new ArrayListCustom<>();
        list.add(null);
        list.add(list);
        assertEquals("[null, (this Collection)]", list.toString());
    }

    @Test
    void shouldRejectUnsupportedMutations() {
        ArrayListCustom<Integer> list = sample();
        assertThrows(UnsupportedOperationException.class, () -> list.set(0, 9));
        assertThrows(UnsupportedOperationException.class, () -> list.add(0, 9));
        assertThrows(UnsupportedOperationException.class, () -> list.remove(0));
        assertThrows(UnsupportedOperationException.class, () -> list.remove(Integer.valueOf(2)));
        assertThrows(UnsupportedOperationException.class, () -> list.addAll(List.of(9)));
        assertThrows(UnsupportedOperationException.class, () -> list.addAll(0, List.of(9)));
        assertThrows(UnsupportedOperationException.class, () -> list.removeAll(List.of(2)));
        assertThrows(UnsupportedOperationException.class, () -> list.retainAll(List.of(2)));
        assertThrows(UnsupportedOperationException.class, list::clear);
        ListIterator<Integer> iterator = list.listIterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
        assertThrows(UnsupportedOperationException.class, () -> iterator.set(9));
        assertThrows(UnsupportedOperationException.class, () -> iterator.add(9));
        assertEquals(List.of(2, 1), list);
    }

    private ArrayListCustom<Integer> sample() {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();
        list.add(2);
        list.add(1);
        return list;
    }
}
