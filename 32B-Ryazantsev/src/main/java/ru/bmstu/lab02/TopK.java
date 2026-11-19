package ru.bmstu.lab02;

import java.util.List;
import java.util.Objects;

/** Отбор K наибольших элементов за один проход по входному списку. */
public final class TopK {
    private TopK() {
    }

    /**
     * Возвращает до k наибольших элементов по убыванию, сохраняя повторы.
     * Для k = 0 результат пуст, для k больше размера возвращаются все элементы.
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        Objects.requireNonNull(input, "input");
        if (k < 0) {
            throw new IllegalArgumentException("k не может быть отрицательным");
        }
        int limit = Math.min(k, input.size());
        if (limit == 0) {
            return new ResultList<>(new Object[0]);
        }
        PriorityQueueCustom<T> best = new PriorityQueueCustom<>();
        for (T item : input) {
            Objects.requireNonNull(item, "item");
            if (best.size() < limit) {
                best.add(item);
            } else if (item.compareTo(best.peek()) > 0) {
                best.poll();
                best.add(item);
            }
        }
        Object[] result = new Object[limit];
        for (int i = limit - 1; i >= 0; i--) {
            result[i] = best.poll();
        }
        return new ResultList<>(result);
    }
}
