
package ru.bmstu.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Поиск k наибольших элементов с помощью собственной min-кучи.
 */
public final class TopK {

    private TopK() {
    }

    /**
     * Возвращает k наибольших элементов по убыванию.
     * Повторяющиеся значения сохраняются.
     *
     * @param input исходный список
     * @param k количество искомых элементов
     * @param <T> тип сравнимых элементов
     * @return список k наибольших элементов
     */
    public static <T extends Comparable<? super T>> List<T> topK(
            List<T> input, int k) {

        Objects.requireNonNull(input, "Список не может быть null");

        if (k < 0 || k > input.size()) {
            throw new IllegalArgumentException("Некорректное значение k");
        }

        List<T> result = new ArrayList<>();

        if (k == 0) {
            return result;
        }

        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>();

        for (T item : input) {
            Objects.requireNonNull(item, "Элемент не может быть null");

            if (heap.size() < k) {
                heap.add(item);
            } else if (item.compareTo(heap.peek()) > 0) {
                heap.replaceMin(item);
            }
        }

        // Извлекаем элементы в возрастающем порядке.
        for (int i = 0; i < k; i++) {
            result.add(heap.poll());
        }

        // Разворачиваем результат, чтобы получить убывание.
        for (int left = 0, right = result.size() - 1;
                left < right; left++, right--) {

            T temp = result.get(left);
            result.set(left, result.get(right));
            result.set(right, temp);
        }

        return result;
    }
}
