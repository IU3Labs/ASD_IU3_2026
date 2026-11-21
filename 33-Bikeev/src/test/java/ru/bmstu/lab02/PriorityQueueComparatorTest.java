package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab02.QueueTestSupport.drain;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Задача 1B: естественный порядок, min-heap, max-heap и пользовательские приоритеты. */
class PriorityQueueComparatorTest {
    @Test
    void shouldSupportExplicitMinComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.naturalOrder());
        for (int value : new int[]{4, 1, 7, 4}) {
            queue.add(value);
        }

        assertEquals(List.of(1, 4, 4, 7), drain(queue));
    }

    @Test
    void shouldSupportMaxComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        for (int value : new int[]{4, 1, 7, 4}) {
            queue.add(value);
        }

        assertEquals(7, queue.peek());
        assertEquals(List.of(7, 4, 4, 1), drain(queue));
    }

    @Test
    void shouldUseNaturalOrderWhenComparatorIsNull() {
        Comparator<Integer> comparator = null;
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        queue.add(9);
        queue.add(-2);
        queue.add(3);

        assertEquals(List.of(-2, 3, 9), drain(queue));
    }

    @Test
    void shouldOrderStringsByLengthAndThenAlphabetically() {
        Comparator<String> comparator = Comparator.comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder());
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(comparator);
        for (String value : List.of("pear", "a", "fig", "plum", "kiwi")) {
            queue.add(value);
        }

        assertEquals(List.of("a", "fig", "kiwi", "pear", "plum"), drain(queue));
    }

    @Test
    void shouldAcceptNonComparableObjectsWithComparator() {
        Comparator<Map.Entry<String, Integer>> comparator = Comparator.comparingInt(Map.Entry::getValue);
        PriorityQueueCustom<Map.Entry<String, Integer>> queue = new PriorityQueueCustom<>(comparator);
        Map.Entry<String, Integer> low = Map.entry("low", 1);
        Map.Entry<String, Integer> middle = Map.entry("middle", 2);
        Map.Entry<String, Integer> high = Map.entry("high", 3);
        queue.add(high);
        queue.add(low);
        queue.add(middle);

        assertEquals(List.of(low, middle, high), drain(queue));
    }

    @Test
    void shouldKeepDifferentElementsWithEqualPriority() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        queue.add("cat");
        queue.add("dog");
        queue.add("ox");

        assertEquals(3, queue.size());
        assertEquals("ox", queue.poll());
        assertEquals(Set.of("cat", "dog"), new HashSet<>(drain(queue)));
    }
}