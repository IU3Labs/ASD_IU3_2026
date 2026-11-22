package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Общие вспомогательные методы для тестов очереди с приоритетом. */
final class QueueTestUtils {

    /** Фиксированное зерно: случайные тесты воспроизводимы при каждом запуске. */
    static final long SEED = 20261122L;

    private QueueTestUtils() {
    }

    /** Извлекает все элементы очереди через poll — в порядке, который она выдаёт. */
    static <T> List<T> drain(PriorityQueueCustom<T> queue) {
        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }

    /** Массив случайных чисел из диапазона [-bound, bound). */
    static Integer[] randomIntegers(int count, int bound, long seed) {
        Random random = new Random(seed);
        Integer[] values = new Integer[count];
        for (int i = 0; i < count; i++) {
            values[i] = random.nextInt(2 * bound) - bound;
        }
        return values;
    }
}
