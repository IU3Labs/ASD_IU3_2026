package ru.bmstu.lab02.task1b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab02.HeapTestUtils.pollAll;

/**
 * Тесты задачи 1B: режимы min-heap и max-heap и пользовательские компараторы.
 */
class OrderedPriorityQueueTest {

    private static final long SEED = 25;

    @Test
    @DisplayName("Без компаратора используется естественный порядок")
    void shouldUseNaturalOrderWithoutComparator() {
        OrderedPriorityQueue<Integer> queue = new OrderedPriorityQueue<>();
        queue.add(8);
        queue.add(1);
        queue.add(5);

        assertEquals(List.of(1, 5, 8), pollAll(queue));
    }

    @Test
    @DisplayName("minHeap() извлекает элементы по возрастанию")
    void shouldWorkAsMinHeap() {
        OrderedPriorityQueue<Integer> queue = OrderedPriorityQueue.minHeap();
        queue.add(12);
        queue.add(-3);
        queue.add(7);
        queue.add(0);

        assertEquals(-3, queue.peek());
        assertEquals(List.of(-3, 0, 7, 12), pollAll(queue));
    }

    @Test
    @DisplayName("maxHeap() извлекает элементы по убыванию")
    void shouldWorkAsMaxHeap() {
        OrderedPriorityQueue<Integer> queue = OrderedPriorityQueue.maxHeap();
        queue.add(12);
        queue.add(-3);
        queue.add(7);
        queue.add(0);

        assertEquals(12, queue.peek());
        assertEquals(List.of(12, 7, 0, -3), pollAll(queue));
    }

    @Test
    @DisplayName("Max-heap на 1000 случайных числах даёт порядок невозрастания")
    void shouldPollMaxHeapInNonIncreasingOrder() {
        Random random = new Random(SEED);
        OrderedPriorityQueue<Integer> queue = new OrderedPriorityQueue<>(Comparator.reverseOrder());
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(500);
            queue.add(value);
            expected.add(value);
        }
        expected.sort(Comparator.reverseOrder());

        assertEquals(expected, pollAll(queue));
    }

    @Test
    @DisplayName("Min- и max-куча на одних данных дают зеркальный порядок")
    void shouldGiveMirroredOrderForMinAndMax() {
        Random random = new Random(SEED);
        OrderedPriorityQueue<Integer> min = OrderedPriorityQueue.minHeap();
        OrderedPriorityQueue<Integer> max = OrderedPriorityQueue.maxHeap();
        for (int i = 0; i < 300; i++) {
            int value = random.nextInt();
            min.add(value);
            max.add(value);
        }
        List<Integer> fromMax = pollAll(max);
        Collections.reverse(fromMax);

        assertEquals(pollAll(min), fromMax);
    }

    @Test
    @DisplayName("Свой компаратор: строки по длине")
    void shouldUseCustomComparatorForStrings() {
        OrderedPriorityQueue<String> queue = new OrderedPriorityQueue<>(Comparator.comparingInt(String::length));
        queue.add("кузнецов");
        queue.add("ит");
        queue.add("бмсту");
        queue.add("иу3");

        assertEquals(List.of("ит", "иу3", "бмсту", "кузнецов"), pollAll(queue));
    }

    @Test
    @DisplayName("Свой компаратор: числа по модулю")
    void shouldUseCustomComparatorByAbsoluteValue() {
        OrderedPriorityQueue<Integer> queue = new OrderedPriorityQueue<>(Comparator.comparingInt(Math::abs));
        queue.add(-10);
        queue.add(4);
        queue.add(-1);
        queue.add(7);

        assertEquals(List.of(-1, 4, 7, -10), pollAll(queue));
    }

    @Test
    @DisplayName("Max-heap сохраняет повторяющиеся элементы")
    void shouldKeepDuplicatesInMaxHeap() {
        OrderedPriorityQueue<Integer> queue = OrderedPriorityQueue.maxHeap();
        queue.add(3);
        queue.add(9);
        queue.add(3);
        queue.add(9);

        assertEquals(List.of(9, 9, 3, 3), pollAll(queue));
    }

    @Test
    @DisplayName("Пустая max-куча бросает исключение на poll и peek")
    void shouldThrowWhenMaxHeapIsEmpty() {
        OrderedPriorityQueue<Integer> queue = OrderedPriorityQueue.maxHeap();

        assertThrows(NoSuchElementException.class, () -> queue.poll());
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    @Test
    @DisplayName("Max-куча из одного элемента")
    void shouldWorkWithSingleElementInMaxHeap() {
        OrderedPriorityQueue<Integer> queue = OrderedPriorityQueue.maxHeap();
        queue.add(42);

        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Добавление null запрещено и для очереди с компаратором")
    void shouldThrowWhenAddingNullWithComparator() {
        OrderedPriorityQueue<Integer> queue = OrderedPriorityQueue.maxHeap();

        assertThrows(IllegalArgumentException.class, () -> queue.add(null));
        assertEquals(0, queue.size());
    }
}
