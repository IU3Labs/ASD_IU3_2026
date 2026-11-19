package ru.bmstu.lab02;

import ru.bmstu.lab02.task1a.PriorityQueueCustom;

import java.util.ArrayList;
import java.util.List;

/**
 * Общие вспомогательные методы для тестов очередей задач 1A–1C.
 */
public final class HeapTestUtils {

    private HeapTestUtils() {
    }

    /**
     * Извлекает все элементы очереди через poll.
     *
     * @param queue очередь
     * @param <T> тип элементов
     * @return элементы в порядке извлечения
     */
    public static <T> List<T> pollAll(PriorityQueueCustom<T> queue) {
        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }
}
