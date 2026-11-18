package ru.bmstu.lab2;

import java.util.List;

/** Поиск k наибольших элементов с помощью min-heap. */
public final class TopK {
    private TopK() {
    }

    /** Возвращает до k элементов по убыванию, сохраняя повторы. При k <= 0 результат пуст. */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        if (k <= 0) {
            return List.of();
        }
        PriorityQueueCustom<T> best = new PriorityQueueCustom<>();
        for (T item : input) {
            if (best.size() < k) {
                best.add(item);
            } else if (item.compareTo(best.peek()) > 0) {
                best.poll();
                best.add(item);
            }
        }
        // Минимум извлекается первым, поэтому заполняем ответ с конца.
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Comparable<?>[best.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = best.poll();
        }
        return List.of(result);
    }
}
