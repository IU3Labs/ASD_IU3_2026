package ru.bmstu.lab02.task01d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;
import ru.bmstu.lab02.task01d.BulkPriorityQueue;

/** Проверяет решение задачи 01D. */
class BulkPriorityQueueTest {
    @Test
    @DisplayName("Конструктор PriorityQueueCustom из массива сразу восстанавливает порядок")
    void shouldBuildPriorityQueueUsingArrayConstructor() {
        PriorityQueueCustom<Integer> heap = new PriorityQueueCustom<>(new Integer[]{6, 4, 1, 5, 2});
        assertEquals(List.of(1, 2, 4, 5, 6), drain(heap));
    }

    @Test
    @DisplayName("Куча строится на копии исходного массива")
    void shouldHeapifyACopyOfInput() {
        Integer[] input = {7, 6, 3, 1, 8};
        PriorityQueueCustom<Integer> heap = new BulkPriorityQueue<>(input);
        assertEquals(List.of(7, 6, 3, 1, 8), List.of(input));
        input[0] = -99;
        assertEquals(List.of(1, 3, 6, 7, 8), drain(heap));
    }

    @Test
    @DisplayName("Построение из массива отклоняет элементы null")
    void shouldRejectNullElementsWhenBuilding() {
        assertThrows(NullPointerException.class, () -> new BulkPriorityQueue<>(new Integer[]{1, null}));
        assertTrue(new BulkPriorityQueue<>(new Integer[0]).isEmpty());
    }

    private static List<Integer> drain(PriorityQueueCustom<Integer> heap) {
        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        return result;
    }
}
