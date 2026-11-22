package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Задача 3A: k наибольших элементов за O(n log k).
 *
 * Идея: держим min-heap размера не более k. Минимум кучи — это "самый слабый"
 * из k лучших на данный момент. Новый элемент попадает в кучу, только если он
 * больше этого минимума (тогда минимум вытесняется). Каждый из n элементов
 * обрабатывается за O(log k), итого O(n log k) по времени и O(k) по памяти.
 */
public final class TopK {

    private TopK() {
    }

    /**
     * Возвращает k наибольших элементов списка в порядке убывания.
     * Если k больше размера списка, возвращаются все элементы (по убыванию).
     * Исходный список не изменяется. Дубликаты учитываются как отдельные элементы.
     *
     * @throws NullPointerException     если input == null или содержит null
     * @throws IllegalArgumentException если k < 0
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        if (input == null) {
            throw new NullPointerException("Input list must not be null");
        }
        if (k < 0) {
            throw new IllegalArgumentException("k must be non-negative: " + k);
        }
        if (k == 0 || input.isEmpty()) {
            return new ArrayList<>();
        }

        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>(Math.min(k, input.size()));

        for (T item : input) {
            if (heap.size() < k) {
                heap.add(item);                      // O(log k)
            } else if (item.compareTo(heap.peek()) > 0) {
                heap.poll();                          // O(log k)
                heap.add(item);                       // O(log k)
            }
            // иначе item не больше текущего минимума из k лучших — пропускаем
        }

        // Из кучи элементы выходят по возрастанию, нужен порядок убывания.
        List<T> result = new ArrayList<>(heap.size());
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        Collections.reverse(result);
        return result;
    }
}
