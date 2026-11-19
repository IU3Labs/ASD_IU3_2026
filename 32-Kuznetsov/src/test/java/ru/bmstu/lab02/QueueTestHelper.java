package ru.bmstu.lab02;

import ru.bmstu.lab02.task_01a.PriorityQueueCustom;

import java.util.ArrayList;
import java.util.List;

public final class QueueTestHelper {

    private QueueTestHelper() {
    }

    public static <T> List<T> drain(PriorityQueueCustom<T> queue) {
        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }
}
