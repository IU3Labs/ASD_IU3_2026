package ru.bmstu.lab2.algorithms;

import java.util.List;
import java.util.Objects;
import ru.bmstu.lab2.list.ArrayListCustom;
import ru.bmstu.lab2.map.HashMapCustom;
import ru.bmstu.lab2.queue.PriorityQueueCustom;

/** Задачи 3A и 3C: применение собственной кучи и собственной хеш-таблицы. */
public final class CollectionAlgorithms {
    private CollectionAlgorithms() {
    }

    /**
     * Возвращает k наибольших элементов по возрастанию, сохраняя повторы.
     * При k > size возвращает все элементы, при k = 0 — пустой список; k < 0 запрещено.
     * Время O(n log(k + 1)), дополнительная память O(k), где k ограничено размером входа.
     * Null-элементы запрещены; исходный список не изменяется.
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        Objects.requireNonNull(input, "input");
        if (k < 0) {
            throw new IllegalArgumentException("k должно быть неотрицательным");
        }
        ArrayListCustom<T> result = new ArrayListCustom<>();
        if (k == 0) {
            return result;
        }
        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>();
        for (T item : input) {
            Objects.requireNonNull(item, "item");
            if (heap.size() < k) {
                heap.add(item);
            } else if (item.compareTo(heap.peek()) > 0) {
                heap.poll();
                heap.add(item);
            }
        }
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        return result;
    }

    /**
     * Возвращает уникальные элементы в порядке первого появления, не изменяя вход.
     * Null-элементы запрещены. Ожидаемое время O(n), худшее O(n²); память O(u), u — число уникальных.
     */
    public static <T> List<T> distinct(List<T> items) {
        Objects.requireNonNull(items, "items");
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>();
        ArrayListCustom<T> result = new ArrayListCustom<>();
        for (T item : items) {
            if (!seen.containsKey(item)) {
                seen.put(item, Boolean.TRUE);
                result.add(item);
            }
        }
        return result;
    }
}
