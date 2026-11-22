/*Задача 3D. Скользящий минимум
Дан массив int[] arr и окно размера k. Для каждого положения окна найти минимальный элемент,
используя собственную очередь с приоритетом. Допустимая асимптотика: O(n log n). */

package ru.bmstu.lab2;

import java.util.Comparator;
import java.util.Objects;

/**
 * Решение задачи 3D: скользящий минимум через min-кучу с ленивым удалением.
 * В куче лежат пары (значение, индекс). Элементы, вышедшие из окна, не удаляются сразу:
 * вершина выбрасывается только тогда, когда её индекс оказался левее окна.
 * Каждый элемент добавляется и извлекается не более одного раза — O(n log n).
 */
public final class SlidingMinimum {

    /**
     * Элемент кучи: значение и его индекс в исходном массиве.
     *
     * @param value значение элемента
     * @param index индекс элемента в массиве
     */
    private record Entry(int value, int index) {
    }

    /** Порядок в куче: по значению. */
    private static final Comparator<Entry> BY_VALUE = Comparator.comparingInt(Entry::value);

    private SlidingMinimum() {
    }

    /**
     * Находит минимум каждого окна длины k.
     *
     * @param arr исходный массив
     * @param k   размер окна, {@code 1 <= k <= arr.length}
     * @return массив длины {@code arr.length - k + 1}; элемент i — минимум arr[i .. i + k - 1]
     * @throws NullPointerException     если массив равен {@code null}
     * @throws IllegalArgumentException если k вне диапазона [1, arr.length]
     */
    public static int[] slidingMin(int[] arr, int k) {
        Objects.requireNonNull(arr, "arr");
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Размер окна должен быть от 1 до " + arr.length + ": " + k);
        }

        int[] result = new int[arr.length - k + 1];
        PriorityQueueCustom<Entry> heap = new PriorityQueueCustom<>(BY_VALUE);
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Entry(arr[i], i));
            int left = i - k + 1; // левая граница текущего окна
            if (left < 0) {
                continue; // первое окно ещё не набрано
            }
            // Ленивое удаление: выбрасываем вершины, вышедшие за левую границу окна
            while (heap.peek().index() < left) {
                heap.poll();
            }
            result[left] = heap.peek().value();
        }
        return result;
    }
}
