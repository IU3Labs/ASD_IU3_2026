package ru.bmstu.lab02.task01c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;
import ru.bmstu.lab02.task01c.RemovablePriorityQueue;

/** Проверяет решение задачи 01C. */
class RemovablePriorityQueueTest {
    private final RemovablePriorityQueue<Integer> queue = new RemovablePriorityQueue<>();

    @Test
    @DisplayName("После удаления корня порядок восстанавливается")
    void shouldRepairHeapAfterRemovingRoot() {
        List.of(1, 3, 2, 5, 4).forEach(queue::add);
        assertTrue(queue.remove(1));
        assertEquals(List.of(2, 3, 4, 5), drain(queue));
    }

    @Test
    @DisplayName("Замена удалённого внутреннего элемента просеивается вверх")
    void shouldSiftUpReplacementAfterRemovingInteriorValue() {
        List.of(1, 5, 2, 8, 6, 3, 4).forEach(queue::add);
        assertTrue(queue.remove(8));
        assertEquals(List.of(1, 2, 3, 4, 5, 6), drain(queue));
    }

    @Test
    @DisplayName("Удаляется одно вхождение повторяющегося значения")
    void shouldRemoveOneOccurrenceOfDuplicate() {
        List.of(2, 1, 2, 3).forEach(queue::add);
        assertTrue(queue.remove(2));
        assertEquals(List.of(1, 2, 3), drain(queue));
    }

    @Test
    @DisplayName("Удаление отсутствующего значения возвращает false")
    void shouldReturnFalseWhenValueIsMissing() {
        queue.add(1);
        assertFalse(queue.remove(9));
        assertEquals(1, queue.poll());
    }

    @Test
    @DisplayName("Удаление из пустой очереди возвращает false")
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        assertFalse(queue.remove(9));
    }

    @Test
    @DisplayName("Последний элемент удаляется с сохранением порядка")
    void shouldRemoveTheLastSlot() {
        List.of(1, 2, 3).forEach(queue::add);
        assertTrue(queue.remove(3));
        assertEquals(List.of(1, 2), drain(queue));
    }

    private static List<Integer> drain(PriorityQueueCustom<Integer> heap) {
        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        return result;
    }
}
