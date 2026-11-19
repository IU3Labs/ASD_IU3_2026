package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** Поиск наибольших элементов с помощью ограниченной минимальной кучи. */
public final class TopK {
    private TopK() {
    }

    /**
     * Возвращает до k наибольших элементов по убыванию, сохраняя повторы.
     *
     * @param <T> тип элементов с естественным порядком
     * @param input список, из которого выбираются элементы
     * @param k максимальное число элементов в результате
     * @return список выбранных элементов по убыванию, с сохранением повторов
     * @throws IllegalArgumentException если {@code k < 0}
     * @throws NullPointerException если список равен {@code null} или при {@code k > 0} содержит {@code null}
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        Objects.requireNonNull(input);
        if (k < 0) {
            throw new IllegalArgumentException("k must be nonnegative");
        }
        if (k == 0) {
            return List.of();
        }
        PriorityQueueCustom<T> best = new PriorityQueueCustom<>();
        for (T item : input) {
            Objects.requireNonNull(item);
            if (best.size() < k) {
                best.add(item);
            } else if (item.compareTo(best.peek()) > 0) {
                best.poll();
                best.add(item);
            }
        }
        // poll возвращает элементы по возрастанию, поэтому заполняем ответ с конца.
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Comparable<?>[best.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = best.poll();
        }
        return Arrays.asList(result);
    }
}
