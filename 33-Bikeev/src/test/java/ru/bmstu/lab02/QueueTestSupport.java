package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.List;

/** Общие операции для проверки содержимого очереди через её публичный интерфейс. */
final class QueueTestSupport {
    private QueueTestSupport() {
    }

    static <T> List<T> drain(PriorityQueueCustom<T> queue) {
        List<T> actual = new ArrayList<>();
        while (!queue.isEmpty()) {
            actual.add(queue.poll());
        }
        return actual;
    }
}