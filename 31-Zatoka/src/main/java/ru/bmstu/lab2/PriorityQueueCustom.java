package ru.bmstu.lab2;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на двоичной куче и динамическом массиве.
 * Порядок задаётся компаратором либо естественным сравнением элементов.
 *
 * @param <T> тип элементов
 */
public class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 8;

    private Object[] elements;
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт минимальную кучу с естественным порядком. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Создаёт очередь с заданным порядком.
     *
     * @param comparator компаратор либо null для естественного порядка
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[INITIAL_CAPACITY];
    }

    /**
     * Строит минимальную кучу из массива за O(n), не изменяя исходный массив.
     *
     * @param array исходные элементы (без null)
     */
    public PriorityQueueCustom(T[] array) {
        this((Comparator<? super T>) null);
        Objects.requireNonNull(array, "Массив не должен быть null");
        for (T item : array) {
            Objects.requireNonNull(item, "Элемент не должен быть null");
        }
        elements = new Object[Math.max(INITIAL_CAPACITY, array.length)];
        System.arraycopy(array, 0, elements, 0, array.length);
        size = array.length;
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /** Добавляет ненулевой элемент за O(log n) амортизированно. */
    public void add(T item) {
        Objects.requireNonNull(item, "Элемент не должен быть null");
        ensureCapacity();
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /** Извлекает первый по приоритету элемент за O(log n). */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T first = elementAt(0);
        int last = --size;
        if (last == 0) {
            elements[0] = null;
        } else {
            elements[0] = elements[last];
            elements[last] = null;
            siftDown(0);
        }
        return first;
    }

    /** Возвращает первый элемент или null для пустой очереди за O(1). */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /** Возвращает количество элементов за O(1). */
    public int size() {
        return size;
    }

    /** Проверяет пустоту очереди за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаляет первое найденное по equals вхождение за O(n).
     *
     * @param item удаляемое значение
     * @return true, если элемент найден и удалён
     */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (Objects.equals(elements[index], item)) {
                int last = --size;
                if (index == last) {
                    elements[last] = null;
                    return true;
                }
                elements[index] = elements[last];
                elements[last] = null;
                if (index > 0 && compare(elementAt(index), elementAt((index - 1) / 2)) < 0) {
                    siftUp(index);
                } else {
                    siftDown(index);
                }
                return true;
            }
        }
        return false;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] bigger = new Object[elements.length * 2];
            System.arraycopy(elements, 0, bigger, 0, size);
            elements = bigger;
        }
    }

    // Элемент поднимается, пока нарушен порядок относительно родителя.
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(elementAt(parent), elementAt(index)) <= 0) {
                return;
            }
            swap(parent, index);
            index = parent;
        }
    }

    // Для обмена выбираем более приоритетного из двух детей.
    private void siftDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            if (left >= size) {
                return;
            }
            int right = left + 1;
            int best = left;
            if (right < size && compare(elementAt(right), elementAt(left)) < 0) {
                best = right;
            }
            if (compare(elementAt(index), elementAt(best)) <= 0) {
                return;
            }
            swap(index, best);
            index = best;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    private void swap(int first, int second) {
        Object temporary = elements[first];
        elements[first] = elements[second];
        elements[second] = temporary;
    }
}
