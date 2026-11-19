package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Выбор k наибольших элементов с помощью {@link PriorityQueueCustom}. */
public final class TopK {

    private TopK() {
    }

    /**
     * Возвращает k наибольших элементов в порядке убывания.
     *
     * <p>Время O(n log k), память O(k): в min-куче хранятся k лучших элементов на данный момент, а корень —
     * наихудший из них, то есть кандидат на вытеснение.
     *
     * @param input исходный список (не изменяется)
     * @param k     сколько элементов нужно; если {@code k >= input.size()}, вернутся все элементы
     * @param <T>   тип элементов с естественным порядком
     * @return список из {@code min(k, n)} наибольших элементов по убыванию
     * @throws IllegalArgumentException если {@code k < 0}
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        return topK(input, k, Comparator.naturalOrder());
    }

    /**
     * То же, что {@link #topK(List, int)}, но «наибольший» определяется компаратором.
     *
     * @param input      исходный список (не изменяется)
     * @param k          сколько элементов нужно
     * @param comparator порядок элементов
     * @param <T>        тип элементов
     * @return список из {@code min(k, n)} наибольших элементов по убыванию
     * @throws IllegalArgumentException если {@code k < 0}
     */
    public static <T> List<T> topK(List<T> input, int k, Comparator<? super T> comparator) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(comparator, "comparator");
        if (k < 0) {
            throw new IllegalArgumentException("k не может быть отрицательным: " + k);
        }
        if (k == 0) {
            return new ArrayList<>();
        }
        PriorityQueueCustom<T> best = new PriorityQueueCustom<>(comparator);
        for (T item : input) {
            if (best.size() < k) {
                best.add(item);
            } else if (comparator.compare(item, best.peek()) > 0) {
                best.poll();
                best.add(item);
            }
        }
        return drainDescending(best);
    }

    /** Из min-кучи элементы выходят по возрастанию; заполняем результат с конца, чтобы получить убывание. */
    private static <T> List<T> drainDescending(PriorityQueueCustom<T> heap) {
        List<T> result = new ArrayList<>(heap.size());
        for (int i = 0; i < heap.size(); i++) {
            result.add(null);
        }
        for (int i = result.size() - 1; i >= 0; i--) {
            result.set(i, heap.poll());
        }
        return result;
    }
}
