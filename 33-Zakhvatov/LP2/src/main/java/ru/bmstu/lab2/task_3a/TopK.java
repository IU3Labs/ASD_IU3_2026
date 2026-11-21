package ru.bmstu.lab2.task_3a;

import java.util.List;
import java.util.Objects;
import ru.bmstu.lab2.result.ResultList;
import ru.bmstu.lab2.task_1a_1b_1d.PriorityQueueCustom;

/** задача 3A: k наибольших элементов через свою очередь. */
public final class TopK {
    private TopK() {
    }

    /** Возвращает k наибольших по убыванию за O(n log(k + 1)); 0 <= k <= n. */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        Objects.requireNonNull(input, "input");
        if (k < 0 || k > input.size()) {
            throw new IllegalArgumentException("k вне диапазона [0, n]");
        }
        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>();
        if (k > 0) {
            for (T item : input) {
                Objects.requireNonNull(item, "item");
                if (heap.size() < k) {
                    heap.add(item);
                } else if (item.compareTo(heap.peek()) > 0) {
                    heap.poll();
                    heap.add(item);
                }
            }
        }
        Object[] result = new Object[k];
        for (int index = k - 1; index >= 0; index--) {
            result[index] = heap.poll();
        }
        return new ResultList<>(result);
    }
}
