package ru.bmstu.lab2.task_3c;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ru.bmstu.lab2.result.ResultList;
import ru.bmstu.lab2.task_2a_2b.HashMapCustom;

/** задача 3C: уникальные элементы в порядке появления. */
public final class DistinctElements {
    private static final int INITIAL_CAPACITY = 16;

    private DistinctElements() {
    }

    /** Убирает повторы за ожидаемое O(n), порядок сохраняется; null допускается. */
    public static <T> List<T> distinct(List<T> items) {
        Objects.requireNonNull(items, "items");
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>();
        Object[] result = new Object[INITIAL_CAPACITY];
        int size = 0;
        boolean nullSeen = false;
        for (T item : items) {
            if (item == null ? !nullSeen : !seen.containsKey(item)) {
                if (size == result.length) {
                    result = Arrays.copyOf(result, Math.multiplyExact(size, 2));
                }
                result[size++] = item;
                if (item == null) {
                    nullSeen = true;
                } else {
                    seen.put(item, Boolean.TRUE);
                }
            }
        }
        return new ResultList<>(Arrays.copyOf(result, size));
    }
}
