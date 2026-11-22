package ru.bmstu.lab02.task03a;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;

/** Поиск k наибольших элементов с помощью двоичной кучи. */
public final class Task03A {
    private Task03A() {
    }

    /** Возвращает до k наибольших элементов по убыванию с сохранением повторов. */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Значение k должно быть неотрицательным");
        }
        int count = Math.min(k, input.size());
        if (count == 0) {
            return new ArrayResult<>(new Object[0]);
        }
        PriorityQueueCustom<T> largest = new PriorityQueueCustom<>();
        for (T item : input) {
            // В куче остаются только k лучших элементов. Корень — текущий минимум.
            if (largest.size() < count) {
                largest.add(item);
            } else if (item.compareTo(largest.peek()) > 0) {
                largest.poll();
                largest.add(item);
            }
        }
        Object[] result = new Object[count];
        for (int index = count - 1; index >= 0; index--) {
            result[index] = largest.poll();
        }
        return new ArrayResult<>(result);
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
