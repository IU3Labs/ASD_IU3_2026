package ru.bmstu.lab02.task03c;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import ru.bmstu.lab02.task02a.HashMapCustom;

/** Удаление повторов с сохранением порядка первого появления. */
public final class Task03C {
    private Task03C() {
    }

    /** Возвращает уникальные элементы в порядке появления, включая первый null. */
    public static <T> List<T> distinct(List<T> items) {
        Object nullKey = new Object();
        HashMapCustom<Object, Boolean> seen = new HashMapCustom<>();
        Object[] result = new Object[items.size()];
        int count = 0;
        for (T item : items) {
            Object key = item == null ? nullKey : item;
            if (!seen.containsKey(key)) {
                seen.put(key, Boolean.TRUE);
                result[count++] = item;
            }
        }
        return new ArrayResult<>(Arrays.copyOf(result, count));
    }

    /** Представление массива результатов через List без возможности изменения. */
    private static final class ArrayResult<T> extends AbstractList<T> implements RandomAccess {
        private final Object[] elements;

        /** Принимает во владение массив результата. */
        private ArrayResult(Object[] elements) {
            this.elements = elements;
        }

        /** Возвращает элемент по заданному индексу. */
        @Override
        @SuppressWarnings("unchecked")
        public T get(int index) {
            return (T) elements[index];
        }

        /** Возвращает число элементов в результате. */
        @Override
        public int size() {
            return elements.length;
        }
    }
}
