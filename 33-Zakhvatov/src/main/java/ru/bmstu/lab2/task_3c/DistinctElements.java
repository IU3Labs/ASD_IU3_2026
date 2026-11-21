package ru.bmstu.lab2.task_3c;

import java.util.List;
import ru.bmstu.lab2.result.ResultList;
import ru.bmstu.lab2.task_2a_2b.HashMapCustom;

/** задача 3C: уникальные элементы в порядке появления. */
public final class DistinctElements {
    private static final int INITIAL_CAPACITY = 16;

    private DistinctElements() {
    }

    /** Убирает повторы за ожидаемое O(n), порядок сохраняется; null допускается. */
    public static <T> List<T> distinct(List<T> items) {
        if (items == null) {
            throw new NullPointerException("items");
        }
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>();
        Object[] result = new Object[INITIAL_CAPACITY];
        int size = 0;
        boolean nullSeen = false;
        for (T item : items) {
            if (item == null && nullSeen || item != null && seen.containsKey(item)) {
                continue;
            }
            if (size == result.length) {
                result = copy(result, size * 2);
            }
            result[size] = item;
            size++;
            if (item == null) {
                nullSeen = true;
            } else {
                seen.put(item, Boolean.TRUE);
            }
        }
        return new ResultList<>(copy(result, size));
    }

    private static Object[] copy(Object[] input, int length) {
        Object[] result = new Object[length];
        for (int i = 0; i < input.length && i < length; i++) {
            result[i] = input[i];
        }
        return result;
    }
}
