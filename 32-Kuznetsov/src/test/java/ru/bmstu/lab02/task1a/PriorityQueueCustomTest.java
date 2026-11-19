package ru.bmstu.lab02.task1a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab02.HeapTestUtils.pollAll;

/**
 * Тесты задачи 1A: базовые операции очереди, пустая очередь, расширение массива и порядок неубывания.
 */
class PriorityQueueCustomTest {

    private static final long SEED = 25;

    @Test
    @DisplayName("Новая очередь пустая")
    void shouldBeEmptyWhenCreated() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    @DisplayName("poll из пустой очереди бросает NoSuchElementException")
    void shouldThrowWhenPollFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, () -> queue.poll());
    }

    @Test
    @DisplayName("peek из пустой очереди бросает NoSuchElementException")
    void shouldThrowWhenPeekFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    @Test
    @DisplayName("Добавление null запрещено и не меняет очередь")
    void shouldThrowWhenAddingNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(IllegalArgumentException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("peek возвращает минимум и не удаляет его")
    void shouldReturnMinimumOnPeekWithoutRemoving() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(14);
        queue.add(3);
        queue.add(9);

        assertEquals(3, queue.peek());
        assertEquals(3, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    @DisplayName("Очередь из одного элемента становится пустой после poll")
    void shouldBecomeEmptyAfterPollingSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(25);

        assertEquals(25, queue.poll());
        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, () -> queue.poll());
    }

    @Test
    @DisplayName("Очередь работает после полного опустошения")
    void shouldWorkAgainAfterBecomingEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(4);
        queue.poll();
        queue.add(7);
        queue.add(2);

        assertEquals(List.of(2, 7), pollAll(queue));
    }

    @Test
    @DisplayName("Повторяющиеся элементы не теряются")
    void shouldKeepDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(6);
        queue.add(2);
        queue.add(6);
        queue.add(2);
        queue.add(6);

        assertEquals(List.of(2, 2, 6, 6, 6), pollAll(queue));
    }

    @Test
    @DisplayName("Девятый элемент вызывает расширение массива с 8 ячеек")
    void shouldExpandWhenStartCapacityIsFull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int i = 9; i >= 1; i--) {
            queue.add(i);
        }

        assertEquals(9, queue.size());
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), pollAll(queue));
    }

    @Test
    @DisplayName("Отсортированный и обратный ввод извлекаются по возрастанию")
    void shouldHandleSortedAndReversedInput() {
        PriorityQueueCustom<Integer> sorted = new PriorityQueueCustom<>();
        PriorityQueueCustom<Integer> reversed = new PriorityQueueCustom<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            sorted.add(i);
            reversed.add(199 - i);
            expected.add(i);
        }

        assertEquals(expected, pollAll(sorted));
        assertEquals(expected, pollAll(reversed));
    }

    @Test
    @DisplayName("1000 случайных чисел извлекаются в порядке неубывания")
    void shouldPollElementsInNonDecreasingOrder() {
        Random random = new Random(SEED);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(150) - 75;
            queue.add(value);
            expected.add(value);
        }
        Collections.sort(expected);

        assertEquals(expected, pollAll(queue));
    }

    @Test
    @DisplayName("Строки извлекаются в алфавитном порядке")
    void shouldUseNaturalOrderForStrings() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("pear");
        queue.add("apple");
        queue.add("orange");
        queue.add("banana");

        assertEquals(List.of("apple", "banana", "orange", "pear"), pollAll(queue));
    }
}
