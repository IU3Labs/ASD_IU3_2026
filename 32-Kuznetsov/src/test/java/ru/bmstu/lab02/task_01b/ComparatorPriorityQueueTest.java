package ru.bmstu.lab02.task_01b;

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
import static ru.bmstu.lab02.QueueTestHelper.drain;

class ComparatorPriorityQueueTest {

    private static final long SEED = 25;

    @Test
    @DisplayName("Без компаратора используется естественный порядок")
    void shouldUseNaturalOrderWithoutComparator() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>();
        queue.add(8);
        queue.add(1);
        queue.add(5);

        assertEquals(List.of(1, 5, 8), drain(queue));
    }

    @Test
    @DisplayName("minHeap() извлекает элементы по возрастанию")
    void shouldWorkAsMinHeap() {
        ComparatorPriorityQueue<Integer> queue = ComparatorPriorityQueue.minHeap();
        queue.add(12);
        queue.add(-3);
        queue.add(7);
        queue.add(0);

        assertEquals(-3, queue.peek());
        assertEquals(List.of(-3, 0, 7, 12), drain(queue));
    }

    @Test
    @DisplayName("maxHeap() извлекает элементы по убыванию")
    void shouldWorkAsMaxHeap() {
        ComparatorPriorityQueue<Integer> queue = ComparatorPriorityQueue.maxHeap();
        queue.add(12);
        queue.add(-3);
        queue.add(7);
        queue.add(0);

        assertEquals(12, queue.peek());
        assertEquals(List.of(12, 7, 0, -3), drain(queue));
    }

    @Test
    @DisplayName("Max-heap на 1000 случайных числах даёт порядок невозрастания")
    void shouldPollMaxHeapInNonIncreasingOrder() {
        Random random = new Random(SEED);
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(Comparator.reverseOrder());
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(500);
            queue.add(value);
            expected.add(value);
        }
        expected.sort(Comparator.reverseOrder());

        assertEquals(expected, drain(queue));
    }

    @Test
    @DisplayName("Min- и max-куча на одних данных дают зеркальный порядок")
    void shouldGiveMirroredOrderForMinAndMax() {
        Random random = new Random(SEED);
        ComparatorPriorityQueue<Integer> min = ComparatorPriorityQueue.minHeap();
        ComparatorPriorityQueue<Integer> max = ComparatorPriorityQueue.maxHeap();
        for (int i = 0; i < 300; i++) {
            int value = random.nextInt();
            min.add(value);
            max.add(value);
        }
        List<Integer> fromMax = drain(max);
        Collections.reverse(fromMax);

        assertEquals(drain(min), fromMax);
    }

    @Test
    @DisplayName("Свой компаратор: строки по длине")
    void shouldUseCustomComparatorForStrings() {
        ComparatorPriorityQueue<String> queue = new ComparatorPriorityQueue<>(Comparator.comparingInt(String::length));
        queue.add("кузнецов");
        queue.add("ит");
        queue.add("бмсту");
        queue.add("иу3");

        assertEquals(List.of("ит", "иу3", "бмсту", "кузнецов"), drain(queue));
    }

    @Test
    @DisplayName("Свой компаратор: числа по модулю")
    void shouldUseCustomComparatorByAbsoluteValue() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>(Comparator.comparingInt(Math::abs));
        queue.add(-10);
        queue.add(4);
        queue.add(-1);
        queue.add(7);

        assertEquals(List.of(-1, 4, 7, -10), drain(queue));
    }

    @Test
    @DisplayName("Max-heap сохраняет повторяющиеся элементы")
    void shouldKeepDuplicatesInMaxHeap() {
        ComparatorPriorityQueue<Integer> queue = ComparatorPriorityQueue.maxHeap();
        queue.add(3);
        queue.add(9);
        queue.add(3);
        queue.add(9);

        assertEquals(List.of(9, 9, 3, 3), drain(queue));
    }

    @Test
    @DisplayName("Пустая max-куча бросает исключение на poll и peek")
    void shouldThrowWhenMaxHeapIsEmpty() {
        ComparatorPriorityQueue<Integer> queue = ComparatorPriorityQueue.maxHeap();

        assertThrows(NoSuchElementException.class, () -> queue.poll());
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    @Test
    @DisplayName("Max-куча из одного элемента")
    void shouldWorkWithSingleElementInMaxHeap() {
        ComparatorPriorityQueue<Integer> queue = ComparatorPriorityQueue.maxHeap();
        queue.add(42);

        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Добавление null запрещено и для очереди с компаратором")
    void shouldThrowWhenAddingNullWithComparator() {
        ComparatorPriorityQueue<Integer> queue = ComparatorPriorityQueue.maxHeap();

        assertThrows(IllegalArgumentException.class, () -> queue.add(null));
        assertEquals(0, queue.size());
    }
}
