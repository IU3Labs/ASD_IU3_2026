package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на двоичной min-куче. Элементы упорядочены естественным порядком.
 * Массив расширяется по мере заполнения. Значения {@code null} не допускаются.
 *
 * @param <T> тип сравнимых элементов
 */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] elements;
    private int size;

    /** Создаёт пустую очередь. */
    public PriorityQueueCustom() {
        elements = new Object[INITIAL_CAPACITY];
    }

    /**
     * Строит очередь из копии массива за {@code O(n)} просеиванием внутренних узлов снизу вверх.
     *
     * @param array исходные элементы
     * @throws NullPointerException если массив или один из его элементов равен {@code null}
     */
    public PriorityQueueCustom(T[] array) {
        Objects.requireNonNull(array, "array");
        for (T item : array) {
            Objects.requireNonNull(item, "item");
        }

        size = array.length;
        elements = Arrays.copyOf(array, Math.max(INITIAL_CAPACITY, size), Object[].class);
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /**
     * Добавляет элемент и восстанавливает порядок кучи просеиванием вверх.
     * Время работы амортизированно {@code O(log n)}. Расширение массива занимает {@code O(n)}.
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если элемент равен {@code null}
     */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        if (size == elements.length) {
            int newCapacity = (int) Math.min(2L * elements.length, Integer.MAX_VALUE);
            elements = Arrays.copyOf(elements, newCapacity);
        }

        elements[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает минимум и восстанавливает порядок кучи за {@code O(log n)}.
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }

        T minimum = elementAt(0);
        removeAt(0);
        return minimum;
    }

    /**
     * Удаляет за {@code O(n)} первое вхождение значения в порядке индексов массива.
     * Равенство элементов определяется методом {@code equals}.
     *
     * @param item значение для удаления
     * @return {@code true}, если элемент удалён, и {@code false}, если значение отсутствует или равно {@code null}
     */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (Objects.equals(item, elements[index])) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает текущий минимум за {@code O(1)} без удаления.
     *
     * @return минимальный элемент или {@code null}, если очередь пуста
     */
    public T peek() {
        return elementAt(0);
    }

    /**
     * Возвращает размер очереди за {@code O(1)}.
     *
     * @return число элементов
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет пустоту очереди за {@code O(1)}.
     *
     * @return {@code true}, если в очереди нет элементов
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void removeAt(int index) {
        int lastIndex = --size;
        elements[index] = elements[lastIndex];
        elements[lastIndex] = null;
        if (index == lastIndex) {
            return;
        }

        int parent = (index - 1) / 2;
        if (index > 0 && elementAt(index).compareTo(elementAt(parent)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (elementAt(index).compareTo(elementAt(parent)) >= 0) {
                return;
            }

            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        // Индексы от size / 2 до конца кучи принадлежат листьям.
        while (index < size / 2) {
            int left = 2 * index + 1;
            int right = left + 1;
            int smallerChild = left;
            if (right < size && elementAt(right).compareTo(elementAt(left)) < 0) {
                smallerChild = right;
            }
            if (elementAt(index).compareTo(elementAt(smallerChild)) <= 0) {
                return;
            }

            swap(index, smallerChild);
            index = smallerChild;
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
}
