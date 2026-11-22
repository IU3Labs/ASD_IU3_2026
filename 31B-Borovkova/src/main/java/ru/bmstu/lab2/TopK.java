/*Задача 3A. Top-K элементов
Используя собственную очередь с приоритетом, реализовать List<T> topK(List<T> input, int k) —
k наибольших элементов. Требуемая асимптотика: O(n log k). */

package ru.bmstu.lab2;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Решение задачи 3A: k наибольших элементов через min-кучу размера k.
 * На вершине кучи лежит наименьший из k лучших кандидатов; очередной элемент вытесняет его,
 * только если он больше. Каждый элемент входа обрабатывается за O(log k), всего O(n log k).
 */
public final class TopK {

    private TopK() {
    }

    /**
     * Возвращает k наибольших элементов в порядке невозрастания (естественный порядок).
     *
     * @param input входной список (без {@code null})
     * @param k     сколько элементов вернуть; при k больше размера входа возвращаются все
     * @param <T>   тип элементов
     * @return неизменяемый список длины {@code min(k, input.size())}, наибольший — первым
     * @throws NullPointerException     если список или его элемент равен {@code null}
     * @throws IllegalArgumentException если {@code k < 0}
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        return topK(input, k, Comparator.naturalOrder());
    }

    /**
     * Возвращает k наибольших по компаратору элементов в порядке невозрастания.
     *
     * @param input      входной список (без {@code null})
     * @param k          сколько элементов вернуть; при k больше размера входа возвращаются все
     * @param comparator порядок элементов
     * @param <T>        тип элементов
     * @return неизменяемый список длины {@code min(k, input.size())}, наибольший — первым
     * @throws NullPointerException     если список, компаратор или элемент списка равен {@code null}
     * @throws IllegalArgumentException если {@code k < 0}
     */
    public static <T> List<T> topK(List<T> input, int k, Comparator<? super T> comparator) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(comparator, "comparator");
        if (k < 0) {
            throw new IllegalArgumentException("k не может быть отрицательным: " + k);
        }
        if (k == 0) {
            return List.of();
        }

        // Min-куча: на вершине худший из текущих k лучших
        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>(comparator);
        for (T item : input) {
            Objects.requireNonNull(item, "элемент списка");
            if (heap.size() < k) {
                heap.add(item);
            } else if (comparator.compare(item, heap.peek()) > 0) {
                heap.poll();
                heap.add(item);
            }
        }

        // Куча отдаёт элементы по возрастанию — заполняем массив с конца
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[heap.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = heap.poll();
        }
        return List.of(result);
    }
}
