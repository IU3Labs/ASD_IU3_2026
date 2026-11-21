package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Очередь с приоритетом на основе двоичной min-кучи в динамическом массиве. */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {

    private static final int DEFAULT_CAPACITY = 16;

    private Object[] elements;
    private int size;

    /** Создаёт пустую очередь. */
    public PriorityQueueCustom() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Копирует массив и строит кучу снизу вверх за O(n).
     * @throws NullPointerException если массив или какой-либо его элемент равен null
     */
    public PriorityQueueCustom(T[] array) {
        elements = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, array.length), Object[].class);
        size = array.length;
        for (int index = 0; index < size; index++) {
            Objects.requireNonNull(elements[index], "Элемент очереди");
        }
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /**
     * Добавляет элемент за амортизированное O(log n).
     * @throws NullPointerException если элемент равен null
     */
    public void add(T item) {
        Objects.requireNonNull(item, "Элемент очереди");
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, size * 2);
        }
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает минимум за O(log n).
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T minimum = elementAt(0);
        size--;
        elements[0] = elements[size];
        elements[size] = null;
        siftDown(0);
        return minimum;
    }

    /** Возвращает минимум или null для пустой очереди за O(1). */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /** Возвращает количество элементов за O(1). */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли очередь, за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentItem = elementAt(parent);
            if (item.compareTo(parentItem) >= 0) {
                break;
            }
            elements[index] = parentItem;
            index = parent;
        }
        elements[index] = item;
    }

    private void siftDown(int index) {
        T item = elementAt(index);
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && elementAt(right).compareTo(elementAt(child)) < 0) {
                child = right;
            }
            T childItem = elementAt(child);
            if (item.compareTo(childItem) <= 0) {
                break;
            }
            elements[index] = childItem;
            index = child;
        }
        elements[index] = item;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }
}
