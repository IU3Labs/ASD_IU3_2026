package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class PriorityQueueCustomTest {

    // Новая очередь должна быть пустой, а peek() возвращать null
    @Test
    void shouldBeEmptyWhenCreated() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
        assertNull(pq.peek());
    }

    // Извлечение из пустой очереди должно бросать NoSuchElementException
    @Test
    void shouldThrowExceptionWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, pq::poll);
    }

    // Вставка null запрещена
    @Test
    void shouldThrowExceptionWhenAddingNull() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> pq.add(null));
    }

    // Граничный случай: добавление и извлечение одного элемента
    @Test
    void shouldHandleSingleElement() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(42);

        assertFalse(pq.isEmpty());
        assertEquals(1, pq.size());
        assertEquals(42, pq.peek());
        assertEquals(42, pq.poll());
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    // Базовая проверка min-heap: извлечение в порядке неубывания
    @Test
    void shouldExtractElementsInNonDecreasingOrder() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int[] input = {5, 1, 9, -3, 0, 7, 2};
        for (int x : input) {
            pq.add(x);
        }

        int[] expected = {-3, 0, 1, 2, 5, 7, 9};
        assertEquals(expected.length, pq.size());
        for (int exp : expected) {
            assertEquals(exp, pq.peek());
            assertEquals(exp, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    // Повторяющиеся значения не должны теряться и нарушать порядок
    @Test
    void shouldHandleDuplicateElements() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(5);
        pq.add(1);
        pq.add(5);
        pq.add(1);
        pq.add(3);

        assertEquals(1, pq.poll());
        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(5, pq.poll());
        assertTrue(pq.isEmpty());
    }

    // Проверка автоматического расширения массива при превышении DEFAULT_CAPACITY
    @Test
    void shouldGrowAutomaticallyBeyondDefaultCapacity() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int count = 100;
        for (int i = count - 1; i >= 0; i--) {
            pq.add(i);
        }

        assertEquals(count, pq.size());
        for (int i = 0; i < count; i++) {
            assertEquals(i, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    // Проверка работы обобщённого типа с Comparable-строками
    @Test
    void shouldWorkWithStrings() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>();
        pq.add("cherry");
        pq.add("apple");
        pq.add("banana");

        assertEquals("apple", pq.poll());
        assertEquals("banana", pq.poll());
        assertEquals("cherry", pq.poll());
    }

    // Режим max-heap через обратный компаратор (1B)
    @Test
    void shouldWorkAsMaxHeapWithReverseComparator() {
        PriorityQueueCustom<Integer> maxHeap = new PriorityQueueCustom<>(Comparator.<Integer>reverseOrder());
        int[] input = {5, 1, 9, -3, 0, 7, 2};
        for (int x : input) {
            maxHeap.add(x);
        }

        int[] expected = {9, 7, 5, 2, 1, 0, -3};
        for (int exp : expected) {
            assertEquals(exp, maxHeap.peek());
            assertEquals(exp, maxHeap.poll());
        }
        assertTrue(maxHeap.isEmpty());
    }

    // Режим min-heap с явно переданным компаратором (1B)
    @Test
    void shouldWorkAsMinHeapWithExplicitComparator() {
        PriorityQueueCustom<Integer> minHeap = new PriorityQueueCustom<>(Comparator.<Integer>naturalOrder());
        minHeap.add(10);
        minHeap.add(3);
        minHeap.add(7);

        assertEquals(3, minHeap.poll());
        assertEquals(7, minHeap.poll());
        assertEquals(10, minHeap.poll());
    }

    // Пользовательский компаратор: сортировка строк по длине (1B)
    @Test
    void shouldOrderStringsByLengthUsingCustomComparator() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>(
                (a, b) -> Integer.compare(a.length(), b.length())
        );
        pq.add("elephant");
        pq.add("cat");
        pq.add("tiger");

        assertEquals("cat", pq.poll());
        assertEquals("tiger", pq.poll());
        assertEquals("elephant", pq.poll());
    }

    // Если компаратор null, должен работать естественный порядок (1B)
    @Test
    void shouldUseNaturalOrderWhenComparatorIsNull() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>((Comparator<Integer>) null);
        pq.add(4);
        pq.add(1);
        pq.add(2);

        assertEquals(1, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(4, pq.poll());
    }

    // Удаление из пустой очереди возвращает false (1C)
    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertFalse(pq.remove(10));
        assertTrue(pq.isEmpty());
    }

    // Удаление отсутствующего элемента или null не меняет очередь (1C)
    @Test
    void shouldReturnFalseWhenRemovingNonExistentOrNullElement() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(1);
        pq.add(5);
        pq.add(3);

        assertFalse(pq.remove(99));
        assertFalse(pq.remove(null));
        assertEquals(3, pq.size());
    }

    // Удаление корня кучи с последующим восстановлением порядка (1C)
    @Test
    void shouldRemoveRootAndRestoreHeap() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int[] input = {5, 1, 9, 2, 7};
        for (int x : input) {
            pq.add(x);
        }

        assertTrue(pq.remove(1));
        assertEquals(4, pq.size());
        assertEquals(2, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(7, pq.poll());
        assertEquals(9, pq.poll());
        assertTrue(pq.isEmpty());
    }

    // Удаление из середины: проверяем просеивание вверх после подстановки хвоста (1C)
    @Test
    void shouldRemoveMiddleElementAndRestoreHeap() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int[] input = {1, 10, 2, 20, 30, 3, 4};
        for (int x : input) {
            pq.add(x);
        }

        assertTrue(pq.remove(30));
        assertEquals(6, pq.size());

        int[] expected = {1, 2, 3, 4, 10, 20};
        for (int exp : expected) {
            assertEquals(exp, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    // При наличии дубликатов remove() удаляет только первое вхождение (1C)
    @Test
    void shouldRemoveOnlyFirstOccurrenceOfDuplicate() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(5);
        pq.add(2);
        pq.add(5);
        pq.add(1);

        assertTrue(pq.remove(5));
        assertEquals(3, pq.size());
        assertEquals(1, pq.poll());
        assertEquals(2, pq.poll());
        assertEquals(5, pq.poll());
        assertTrue(pq.isEmpty());
    }

    // Построение кучи из массива за O(n) с отрицательными и повторяющимися числами (1D)
    @Test
    void shouldBuildHeapFromArrayInLinearTime() {
        Integer[] input = {9, 4, 7, 1, -2, 6, 5, 4};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(input);

        int[] expected = {-2, 1, 4, 4, 5, 6, 7, 9};
        assertEquals(expected.length, pq.size());
        for (int exp : expected) {
            assertEquals(exp, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    // Построение из пустого массива оставляет очередь рабочей для новых add() (1D)
    @Test
    void shouldBuildEmptyHeapFromEmptyArrayAndAllowAdding() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());

        pq.add(10);
        pq.add(3);
        assertEquals(3, pq.poll());
        assertEquals(10, pq.poll());
    }

    // Null-массив или null-элемент внутри массива должны бросать NullPointerException (1D)
    @Test
    void shouldThrowExceptionWhenBuildingFromNullArrayOrNullElement() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>((Integer[]) null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{1, null, 3}));
    }

    // Изменение исходного массива после создания кучи не должно влиять на неё (1D)
    @Test
    void shouldNotAffectHeapWhenSourceArrayIsModified() {
        Integer[] input = {5, 2, 8};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(input);
        input[0] = -100;

        assertEquals(2, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(8, pq.poll());
    }
}