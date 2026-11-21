package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Контракты собственных неизменяемых List/Map/Set, возвращаемых задачами 3A–3C. */
class ResultContractTest {
    @Test
    void shouldImplementListEqualityAndHashCode() {
        List<Integer> actual = DataAlgorithms.distinct(List.of(3, 1, 3));
        assertEquals(List.of(3, 1), actual);
        assertEquals(actual, List.of(3, 1));
        assertEquals(List.of(3, 1).hashCode(), actual.hashCode());
        assertNotEquals(actual, List.of(1, 3));
        assertNotEquals(actual, List.of(3));
        assertNotEquals(actual, "3, 1");
        assertEquals("[3, 1]", actual.toString());
    }

    @Test
    void shouldImplementListSearchAndSubLists() {
        List<Integer> actual = new ResultList<>(new Object[]{3, null, 3, 2});
        assertEquals(0, actual.indexOf(3));
        assertEquals(2, actual.lastIndexOf(3));
        assertEquals(1, actual.indexOf(null));
        assertEquals(-1, actual.indexOf(100));
        assertEquals(-1, actual.lastIndexOf(100));
        assertTrue(actual.containsAll(List.of(3, 2)));
        assertFalse(actual.containsAll(List.of(100)));
        assertEquals(List.of(3, 2), actual.subList(2, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> actual.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> actual.subList(2, 5));
    }

    @Test
    void shouldImplementTypedAndUntypedArrays() {
        List<Integer> actual = DataAlgorithms.distinct(List.of(1, 2));
        assertArrayEquals(new Object[]{1, 2}, actual.toArray());
        assertArrayEquals(new Integer[]{1, 2}, actual.toArray(new Integer[0]));
        Integer[] target = {9, 9, 9, 9};
        assertSame(target, actual.toArray(target));
        assertArrayEquals(new Integer[]{1, 2, null, 9}, target);
        assertArrayEquals(new Integer[]{1, 2}, actual.toArray(new Integer[2]));
        assertThrows(ArrayStoreException.class, () -> actual.toArray(new String[0]));
    }

    @Test
    void shouldImplementBidirectionalListIterator() {
        List<Integer> actual = DataAlgorithms.distinct(List.of(1, 2));
        ListIterator<Integer> iterator = actual.listIterator();
        assertFalse(iterator.hasPrevious());
        assertEquals(-1, iterator.previousIndex());
        assertEquals(0, iterator.nextIndex());
        assertThrows(NoSuchElementException.class, iterator::previous);
        assertEquals(1, iterator.next());
        assertEquals(1, iterator.previous());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertEquals(2, actual.listIterator(2).previous());
        assertThrows(IndexOutOfBoundsException.class, () -> actual.listIterator(3));
        assertThrows(UnsupportedOperationException.class, iterator::remove);
        assertThrows(UnsupportedOperationException.class, () -> iterator.set(7));
        assertThrows(UnsupportedOperationException.class, () -> iterator.add(7));
    }

    @Test
    void shouldRejectAllListMutations() {
        List<Integer> result = DataAlgorithms.topK(List.of(1, 2), 1);
        assertThrows(UnsupportedOperationException.class, () -> result.add(7));
        assertThrows(UnsupportedOperationException.class, () -> result.add(0, 7));
        assertThrows(UnsupportedOperationException.class, () -> result.set(0, 7));
        assertThrows(UnsupportedOperationException.class, () -> result.remove(0));
        assertThrows(UnsupportedOperationException.class, () -> result.remove(Integer.valueOf(2)));
        assertThrows(UnsupportedOperationException.class, () -> result.addAll(List.of(7)));
        assertThrows(UnsupportedOperationException.class, () -> result.addAll(0, List.of(7)));
        assertThrows(UnsupportedOperationException.class, () -> result.removeAll(List.of(2)));
        assertThrows(UnsupportedOperationException.class, () -> result.retainAll(List.of(7)));
        assertThrows(UnsupportedOperationException.class, result::clear);
    }

    @Test
    void shouldImplementMapReadOperationsAndEquality() {
        Map<String, Integer> result = DataAlgorithms.countWords("a b a");
        Map<String, Integer> expected = Map.of("a", 2, "b", 1);
        assertEquals(expected, result);
        assertEquals(result, expected);
        assertEquals(expected.hashCode(), result.hashCode());
        assertTrue(result.containsKey("a"));
        assertFalse(result.containsKey(7));
        assertFalse(result.containsKey("missing"));
        assertTrue(result.containsValue(2));
        assertFalse(result.containsValue(3));
        assertNull(result.get(null));
        assertNull(result.get(7));
        assertNotEquals(result, Map.of("a", 1, "b", 1));
        assertNotEquals(result, Map.of("a", 2));
        assertNotEquals(result, "a=2");
        assertTrue(result.toString().contains("a=2"));
    }

    @Test
    void shouldExposeCorrectMapViews() {
        Map<String, Integer> result = DataAlgorithms.countWords("a b a");
        assertEquals(Set.of("a", "b"), result.keySet());
        assertEquals(result.keySet(), Set.of("a", "b"));
        assertEquals(Set.of("a", "b").hashCode(), result.keySet().hashCode());
        assertNotEquals(result.keySet(), Set.of("a"));
        assertNotEquals(result.keySet(), List.of("a", "b"));
        Collection<Integer> values = result.values();
        assertEquals(2, values.size());
        assertTrue(values.containsAll(List.of(1, 2)));
        assertEquals(Map.of("a", 2, "b", 1).entrySet(), result.entrySet());
        assertEquals(result.entrySet(), Map.of("a", 2, "b", 1).entrySet());
    }

    @Test
    void shouldKeepMapAndEntriesImmutable() {
        Map<String, Integer> result = DataAlgorithms.countWords("a a");
        assertThrows(UnsupportedOperationException.class, () -> result.put("b", 1));
        assertThrows(UnsupportedOperationException.class, () -> result.remove("a"));
        assertThrows(UnsupportedOperationException.class, () -> result.putAll(Map.of("b", 1)));
        assertThrows(UnsupportedOperationException.class, result::clear);
        Map.Entry<String, Integer> entry = result.entrySet().iterator().next();
        assertEquals("a", entry.getKey());
        assertEquals(2, entry.getValue());
        assertEquals(Map.entry("a", 2), entry);
        assertNotEquals(entry, Map.entry("a", 1));
        assertNotEquals(entry, "a=2");
        assertThrows(UnsupportedOperationException.class, () -> entry.setValue(5));
        assertThrows(UnsupportedOperationException.class, () -> result.keySet().remove("a"));
        Iterator<String> iterator = result.keySet().iterator();
        iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
        assertEquals(Map.of("a", 2), result);
    }
}
