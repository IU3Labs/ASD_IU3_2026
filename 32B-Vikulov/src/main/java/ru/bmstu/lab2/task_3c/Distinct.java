package ru.bmstu.lab2.task_3c;

import java.util.Arrays;
import java.util.List;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/**
 * Задача 3C. Уникальные элементы в порядке первого вхождения.
 *
 * <p>Проходим по списку и для каждого элемента проверяем по хеш-таблице «уже встречался?». Если нет —
 * запоминаем его в таблице и добавляем в результат. Порядок первого вхождения задаёт результирующий список,
 * а не таблица (её порядок обхода произвольный). Проверка и вставка в {@link HashMapCustom} — O(1) в среднем,
 * поэтому весь алгоритм O(n) в среднем по времени и O(u) по памяти, где u — число уникальных элементов.
 */
public final class Distinct {

    private static final int INITIAL_RESULT_CAPACITY = 8;

    private Distinct() {
    }

    /**
     * Возвращает уникальные элементы в порядке их первого вхождения. Исходный список не меняется.
     *
     * @param items исходный список; может содержать {@code null}
     * @param <T>   тип элементов (должен корректно реализовывать {@code equals} и {@code hashCode})
     * @return новый список без повторов
     */
    public static <T> List<T> distinct(List<T> items) {
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>();
        // таблица не принимает null-ключи, поэтому факт встречи null хранится отдельно
        boolean seenNull = false;
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[INITIAL_RESULT_CAPACITY];
        int size = 0;
        for (T item : items) {
            if (item == null) {
                if (seenNull) {
                    continue;
                }
                seenNull = true;
            } else {
                if (seen.containsKey(item)) {
                    continue;
                }
                seen.put(item, Boolean.TRUE);
            }
            if (size == result.length) {
                result = Arrays.copyOf(result, size * 2);
            }
            result[size++] = item;
        }
        return Arrays.asList(Arrays.copyOf(result, size));
    }
}
