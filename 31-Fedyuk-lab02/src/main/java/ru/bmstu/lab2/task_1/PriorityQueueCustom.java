/*
 * ЛР2, задачи 1A–1D. Очередь с приоритетом на расширяемом массиве.
 * Минимальная или максимальная куча, удаление одного совпадения, построение за O(n).
 */
package ru.bmstu.lab2.task_1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Двоичная куча: порядок задаёт компаратор, по умолчанию — естественный порядок. */
public final class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;
    private Object[] elements;
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт пустую min-heap с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /** Создаёт пустую очередь; null означает естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[INITIAL_CAPACITY];
    }

    /** Строит min-heap за O(n), копируя массив и сохраняя исходные данные. */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /** Строит кучу за O(n) с заданным порядком; массив и элементы не должны быть null. */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        this(comparator);
        Objects.requireNonNull(array, "Массив не должен быть null");
        elements = Arrays.copyOf(array, Math.max(INITIAL_CAPACITY, array.length), Object[].class);
        size = array.length;
        for (T item : array) {
            Objects.requireNonNull(item, "Элемент не должен быть null");
        }
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /** Добавляет ненулевой элемент за O(log n) амортизированно с учётом расширений. */
    public void add(T item) {
        Objects.requireNonNull(item, "Элемент не должен быть null");
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * GROWTH_FACTOR);
        }
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /** Извлекает первый по заданному порядку элемент за O(log n); пустая очередь — ошибка. */
    public T poll() {
        T result = peek();
        removeAt(0);
        return result;
    }

    /** Возвращает корень за O(1); при пустой очереди бросает NoSuchElementException. */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return elementAt(0);
    }

    /** Возвращает количество элементов за O(1). */
    public int size() {
        return size;
    }

    /** Проверяет отсутствие элементов за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Удаляет первое по индексу массива совпадение по equals за O(n); null не найден. */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (Objects.equals(item, elements[index])) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    private void removeAt(int index) {
        int lastIndex = --size;
        T replacement = elementAt(lastIndex);
        elements[lastIndex] = null;
        if (index == lastIndex) {
            return;
        }
        elements[index] = replacement;
        // Последний элемент может нарушить порядок как с родителем, так и с детьми.
        if (index > 0 && compare(replacement, elementAt((index - 1) / 2)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(elementAt(index), elementAt(parent)) >= 0) {
                return;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int left = 2 * index + 1;
            int right = left + 1;
            int bestChild = left;
            if (right < size && compare(elementAt(right), elementAt(left)) < 0) {
                bestChild = right;
            }
            if (compare(elementAt(index), elementAt(bestChild)) <= 0) {
                return;
            }
            swap(index, bestChild);
            index = bestChild;
        }
    }

    private void swap(int first, int second) {
        Object temporary = elements[first];
        elements[first] = elements[second];
        elements[second] = temporary;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
    }
}
