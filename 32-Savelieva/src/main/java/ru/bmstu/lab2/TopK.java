package ru.bmstu.lab2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class TopK {

    private TopK() {}

    /**
     * Возвращает k наибольших элементов в порядке убывания.
     * Асимптотика: O(n log k).
     */
    public static <T extends Comparable<T>> List<T> topK(List<T> input, int k) {
        if (input == null) {
            throw new NullPointerException("input must not be null");
        }
        if (k <= 0) {
            return new ArrayList<>();
        }
        if (k >= input.size()) {
            List<T> all = new ArrayList<>(input);
            all.sort(Comparator.reverseOrder());
            return all;
        }

        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>(k);
        for (T item : input) {
            if (item == null) {
                throw new NullPointerException("input contains null");
            }
            if (heap.size() < k) {
                heap.add(item);
            } else if (item.compareTo(heap.peek()) > 0) {
                heap.poll();
                heap.add(item);
            }
        }

        List<T> result = new ArrayList<>(k);
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        result.sort(Comparator.reverseOrder()); // по убыванию
        return result;
    }
}