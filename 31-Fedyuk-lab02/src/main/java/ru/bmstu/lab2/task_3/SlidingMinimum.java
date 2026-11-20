/*
 * ЛР2, задача 3D. Минимум в каждом окне длины k через собственную очередь с приоритетом.
 * Время O(n log n), дополнительная память O(n), включая результат.
 */
package ru.bmstu.lab2.task_3;

import java.util.Comparator;
import java.util.Objects;
import ru.bmstu.lab2.task_1.PriorityQueueCustom;

/** Скользящий минимум с ленивым удалением элементов, вышедших за границу окна. */
public final class SlidingMinimum {
    private SlidingMinimum() {}

    /**
     * Возвращает минимумы всех полных окон, не изменяя входной массив.
     *
     * @param values непустой массив
     * @param windowSize размер окна от 1 до длины массива
     * @return массив длины values.length - windowSize + 1
     */
    public static int[] solve(int[] values, int windowSize) {
        Objects.requireNonNull(values, "Массив не должен быть null");
        if (windowSize < 1 || windowSize > values.length) {
            throw new IllegalArgumentException("Размер окна должен быть от 1 до длины массива");
        }
        PriorityQueueCustom<WindowEntry> heap =
                new PriorityQueueCustom<>(
                        Comparator.comparingInt(WindowEntry::value)
                                .thenComparingInt(WindowEntry::index));
        int[] result = new int[values.length - windowSize + 1];
        for (int index = 0; index < values.length; index++) {
            heap.add(new WindowEntry(values[index], index));
            int left = index - windowSize + 1;
            while (heap.peek().index() < left) {
                heap.poll();
            }
            if (left >= 0) {
                result[left] = heap.peek().value();
            }
        }
        return result;
    }
}
