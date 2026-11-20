package ru.bmstu.lab2.task_1;

/** Общее извлечение всей очереди для сравнения результата в тестах. */
final class QueueTestSupport {
    private QueueTestSupport() {}

    static int[] drain(PriorityQueueCustom<Integer> queue) {
        int[] result = new int[queue.size()];
        for (int index = 0; index < result.length; index++) {
            result[index] = queue.poll();
        }
        return result;
    }
}
