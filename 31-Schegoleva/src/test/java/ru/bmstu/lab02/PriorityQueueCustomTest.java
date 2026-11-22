package ru.bmstu.lab02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriorityQueueCustomTest {

    private PriorityQueueCustom<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new PriorityQueueCustom<>();
    }

    // 1. Новая очередь пуста
    @Test
    void shouldBeEmptyWhenCreated() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    // 2. Добавление одного элемента
    @Test
    void shouldContainElementAfterAddingSingleElement() {
        queue.add(42);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(42, queue.peek());
    }

    // 3. poll на пустой очереди бросает исключение
    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> queue.poll());
    }

    // 4. peek на пустой очереди бросает исключение
    @Test
    void shouldThrowWhenPeekingEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }

    // 5. peek возвращает минимум и не удаляет его
    @Test
    void shouldReturnMinimumOnPeekWithoutRemoving() {
        queue.add(5);
        queue.add(2);
        queue.add(8);

        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(3, queue.size());
    }

    // 6. poll возвращает элементы в порядке неубывания
    @Test
    void shouldPollElementsInAscendingOrder() {
        int[] values = {7, 3, 9, 1, 5, 8, 2, 6, 4, 0};
        for (int v : values) {
            queue.add(v);
        }
        for (int expected = 0; expected < values.length; expected++) {
            assertEquals(expected, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    // 7. Дубликаты
    @Test
    void shouldHandleDuplicates() {
        int[] values = {3, 1, 3, 1, 2, 2, 3, 1};
        for (int v : values) {
            queue.add(v);
        }
        int[] expected = {1, 1, 1, 2, 2, 3, 3, 3};
        for (int e : expected) {
            assertEquals(e, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    // 8. Уже отсортированный вход
    @Test
    void shouldHandleSortedInput() {
        for (int i = 0; i < 100; i++) {
            queue.add(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, queue.poll());
        }
    }

    // 9. Вход в обратном порядке
    @Test
    void shouldHandleReverseSortedInput() {
        for (int i = 99; i >= 0; i--) {
            queue.add(i);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, queue.poll());
        }
    }

    // 10. Автоматическое расширение массива (начальная ёмкость 1)
    @Test
    void shouldGrowArrayAutomatically() {
        PriorityQueueCustom<Integer> small = new PriorityQueueCustom<>(1);
        for (int i = 1000; i > 0; i--) {
            small.add(i);
        }
        assertEquals(1000, small.size());
        for (int i = 1; i <= 1000; i++) {
            assertEquals(i, small.poll());
        }
    }

    // 11. Чередование add и poll
    @Test
    void shouldSupportInterleavedAddAndPoll() {
        queue.add(10);
        queue.add(4);
        assertEquals(4, queue.poll());

        queue.add(1);
        queue.add(7);
        assertEquals(1, queue.poll());
        assertEquals(7, queue.poll());
        assertEquals(10, queue.poll());
        assertTrue(queue.isEmpty());
    }

    // 12. size корректно меняется при add и poll
    @Test
    void shouldTrackSizeOnAddAndPoll() {
        for (int i = 0; i < 5; i++) {
            queue.add(i);
            assertEquals(i + 1, queue.size());
        }
        for (int i = 4; i >= 0; i--) {
            queue.poll();
            assertEquals(i, queue.size());
        }
        assertTrue(queue.isEmpty());
    }

    // 13. Отрицательные числа и экстремальные значения
    @Test
    void shouldHandleNegativeAndExtremeValues() {
        queue.add(0);
        queue.add(Integer.MAX_VALUE);
        queue.add(-5);
        queue.add(Integer.MIN_VALUE);
        queue.add(5);

        assertEquals(Integer.MIN_VALUE, queue.poll());
        assertEquals(-5, queue.poll());
        assertEquals(0, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(Integer.MAX_VALUE, queue.poll());
    }

    // 14. Очередь можно использовать повторно после опустошения
    @Test
    void shouldBeReusableAfterBecomingEmpty() {
        queue.add(3);
        queue.add(1);
        queue.poll();
        queue.poll();
        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, () -> queue.poll());

        queue.add(9);
        queue.add(8);
        assertEquals(8, queue.poll());
        assertEquals(9, queue.poll());
    }

    // 15. null запрещён
    @Test
    void shouldThrowWhenAddingNull() {
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertTrue(queue.isEmpty());
    }

    // 16. Некорректная начальная ёмкость
    @Test
    void shouldThrowWhenInitialCapacityIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueCustom<Integer>(0));
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueCustom<Integer>(-1));
    }

    // 17. Строки
    @Test
    void shouldWorkWithStrings() {
        PriorityQueueCustom<String> strings = new PriorityQueueCustom<>();
        strings.add("pear");
        strings.add("apple");
        strings.add("orange");
        strings.add("banana");

        assertEquals("apple", strings.poll());
        assertEquals("banana", strings.poll());
        assertEquals("orange", strings.poll());
        assertEquals("pear", strings.poll());
    }

    // 18. Пользовательский Comparable
    private record Task(int priority, String name) implements Comparable<Task> {
        @Override
        public int compareTo(Task other) {
            return Integer.compare(priority, other.priority);
        }
    }

    @Test
    void shouldWorkWithCustomComparable() {
        PriorityQueueCustom<Task> tasks = new PriorityQueueCustom<>();
        tasks.add(new Task(3, "low"));
        tasks.add(new Task(1, "urgent"));
        tasks.add(new Task(2, "normal"));

        assertEquals("urgent", tasks.poll().name());
        assertEquals("normal", tasks.poll().name());
        assertEquals("low", tasks.poll().name());
    }

    // 19. Случайные данные с дубликатами: сравнение с Collections.sort
    @Test
    void shouldMatchSortedListOnRandomData() {
        Random random = new Random(42);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            int value = random.nextInt(1000); // много дубликатов
            expected.add(value);
            queue.add(value);
        }
        Collections.sort(expected);

        for (int value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    // 20. Большой объём данных (100 000 элементов)
    @Test
    void shouldExtractLargeInputInNonDecreasingOrder() {
        Random random = new Random(123);
        int n = 100_000;
        for (int i = 0; i < n; i++) {
            queue.add(random.nextInt());
        }
        assertEquals(n, queue.size());

        int previous = queue.poll();
        for (int i = 1; i < n; i++) {
            int current = queue.poll();
            assertTrue(previous <= current, "Order violated at step " + i);
            previous = current;
        }
        assertTrue(queue.isEmpty());
    }

    // 21. Случайные чередующиеся операции, сверка с эталоном (отсортированный список)
    @Test
    void shouldMatchReferenceModelOnRandomOperations() {
        Random random = new Random(7);
        List<Integer> reference = new ArrayList<>();

        for (int step = 0; step < 20_000; step++) {
            if (reference.isEmpty() || random.nextInt(3) != 0) {
                int value = random.nextInt(500);
                queue.add(value);
                reference.add(value);
            } else {
                int expectedMin = Collections.min(reference);
                reference.remove(Integer.valueOf(expectedMin));
                assertEquals(expectedMin, queue.poll());
            }
            assertEquals(reference.size(), queue.size());
        }
    }

    // 22. remove: удаление корня
    @Test
    void shouldRemoveRootElement() {
        for (int value : new int[]{5, 3, 8, 1, 9}) {
            queue.add(value);
        }

        assertTrue(queue.remove(1));

        assertEquals(4, queue.size());
        assertPollsInOrder(3, 5, 8, 9);
    }

    // 23. remove: удаление элемента из середины кучи
    @Test
    void shouldRemoveElementFromMiddle() {
        for (int value : new int[]{7, 2, 9, 4, 1, 8, 5, 3, 6}) {
            queue.add(value);
        }

        assertTrue(queue.remove(5));

        assertEquals(8, queue.size());
        assertPollsInOrder(1, 2, 3, 4, 6, 7, 8, 9);
    }

    // 24. remove: удаление последнего элемента массива
    @Test
    void shouldRemoveLastElement() {
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertTrue(queue.remove(3));

        assertPollsInOrder(1, 2);
    }

    // 25. remove: несуществующий элемент
    @Test
    void shouldReturnFalseWhenRemovingMissingElement() {
        queue.add(1);
        queue.add(2);

        assertFalse(queue.remove(42));

        assertEquals(2, queue.size());
    }

    // 26. remove: из пустой очереди
    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        assertFalse(queue.remove(1));
        assertTrue(queue.isEmpty());
    }

    // 27. remove: удаляется только одно вхождение дубликата
    @Test
    void shouldRemoveOnlyFirstOccurrenceOfDuplicate() {
        queue.add(2);
        queue.add(2);
        queue.add(3);

        assertTrue(queue.remove(2));

        assertEquals(2, queue.size());
        assertPollsInOrder(2, 3);
    }

    // 28. remove: null запрещён
    @Test
    void shouldThrowWhenRemovingNull() {
        assertThrows(NullPointerException.class, () -> queue.remove(null));
    }

    // 29. remove: единственный элемент
    @Test
    void shouldRemoveSingleElement() {
        queue.add(7);

        assertTrue(queue.remove(7));

        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, () -> queue.poll());
    }

    // 30. remove: случайные удаления, сверка с эталонным списком
    @Test
    void shouldKeepHeapOrderAfterRandomRemovals() {
        Random random = new Random(11);
        List<Integer> reference = new ArrayList<>();
        for (int i = 0; i < 2_000; i++) {
            int value = random.nextInt(300);
            queue.add(value);
            reference.add(value);
        }

        for (int i = 0; i < 1_500; i++) {
            int target = random.nextInt(300);
            boolean expected = reference.remove(Integer.valueOf(target));
            assertEquals(expected, queue.remove(target));
        }

        Collections.sort(reference);
        assertEquals(reference.size(), queue.size());
        for (int value : reference) {
            assertEquals(value, queue.poll());
        }
    }

    private void assertPollsInOrder(int... expected) {
        for (int value : expected) {
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
