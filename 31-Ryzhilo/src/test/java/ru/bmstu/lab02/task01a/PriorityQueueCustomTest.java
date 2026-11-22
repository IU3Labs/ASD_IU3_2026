package ru.bmstu.lab02.task01a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;

/** Проверяет решение задачи 01A. */
class PriorityQueueCustomTest {
    private final PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

    @Test
    @DisplayName("Извлечение из пустой очереди вызывает исключение")
    void shouldThrowWhenPollingEmptyQueue() {
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    @DisplayName("Новая очередь пуста")
    void shouldReportEmptyQueue() {
        assertNull(queue.peek());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    @DisplayName("Просмотр корня сохраняет размер очереди")
    void shouldPeekWithoutRemoving() {
        queue.add(7);
        queue.add(-1);
        assertEquals(-1, queue.peek());
        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    @DisplayName("Очередь сохраняет естественный порядок после роста массива")
    void shouldPollInNaturalOrderAfterGrowing() {
        Random random = new Random(42);
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int index = 0; index < 1_000; index++) {
            int value = random.nextInt(200) - 100;
            expected.add(value);
            queue.add(value);
        }
        assertEquals(drainStandard(expected), drain(queue));
    }

    @Test
    @DisplayName("Очередь отклоняет элементы null")
    void shouldRejectNullElements() {
        assertThrows(NullPointerException.class, () -> queue.add(null));
    }

    private static List<Integer> drain(PriorityQueueCustom<Integer> heap) {
        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        return result;
    }

    private static List<Integer> drainStandard(PriorityQueue<Integer> heap) {
        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.remove());
        }
        return result;
    }
}
