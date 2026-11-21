package ru.bmstu.lab02.task01b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;
import ru.bmstu.lab02.task01b.ComparatorPriorityQueue;

/** Проверяет решение задачи 01B. */
class ComparatorPriorityQueueTest {
    @Test
    @DisplayName("Очередь без компаратора извлекает наименьший элемент")
    void shouldUseNaturalOrderByDefault() {
        ComparatorPriorityQueue<Integer> queue = new ComparatorPriorityQueue<>();
        queue.add(3);
        queue.add(1);
        assertEquals(1, queue.poll());
    }

    @Test
    @DisplayName("Компаратор задаёт обратный порядок извлечения")
    void shouldPollInReverseOrderWithComparator() {
        PriorityQueueCustom<Integer> reverse = new ComparatorPriorityQueue<>(Comparator.reverseOrder());
        List.of(3, 1, 5, 5, -2).forEach(reverse::add);
        assertEquals(List.of(5, 5, 3, 1, -2), drain(reverse));
    }

    private static List<Integer> drain(PriorityQueueCustom<Integer> heap) {
        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        return result;
    }
}
