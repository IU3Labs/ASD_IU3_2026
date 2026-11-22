package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Очередь с приоритетом на основе двоичной кучи. */
public final class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;

    private final Comparator<? super T> comparator;
    private Object[] elements;
    private int size;

    /** Создаёт пустую очередь. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /** Создаёт пустую очередь с заданным порядком. При null действует естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[INITIAL_CAPACITY];
    }

    /**
     * Строит кучу снизу вверх за O(n), копируя входной массив.
     *
     * @throws NullPointerException если массив или один из его элементов равен null
     */
    public PriorityQueueCustom(T[] array) {
        comparator = null;
        elements = Arrays.copyOf(array, Math.max(INITIAL_CAPACITY, array.length), Object[].class);
        size = array.length;
        for (int index = 0; index < size; index++) {
            validateItem(elementAt(index));
        }
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /**
     * Добавляет элемент за амортизированное O(log n).
     *
     * @throws NullPointerException если элемент равен null
     */
    public void add(T item) {
        validateItem(item);
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, Math.multiplyExact(elements.length, 2));
        }
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает первый по заданному порядку элемент за O(log n).
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T minimum = peek();
        removeAt(0);
        return minimum;
    }

    /**
     * Возвращает первый по заданному порядку элемент за O(1).
     *
     * @throws NoSuchElementException если очередь пуста
     */
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

    /** Проверяет, пуста ли очередь, за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаляет первое равное значение в порядке обхода массива кучи за O(n).
     * Возвращает true, если элемент найден.
     */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (Objects.equals(item, elementAt(index))) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    private void removeAt(int index) {
        size--;
        elements[index] = elements[size];
        elements[size] = null;
        if (index == size) {
            return;
        }
        int parent = (index - 1) / 2;
        if (index > 0 && compare(elementAt(index), elementAt(parent)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void validateItem(T item) {
        Objects.requireNonNull(item);
        if (comparator == null && !(item instanceof Comparable<?>)) {
            throw new ClassCastException("Для естественного порядка элемент должен реализовать Comparable");
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(T left, T right) {
        if (comparator != null) {
            return comparator.compare(left, right);
        }
        return ((Comparable<? super T>) left).compareTo(right);
    }

    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(item, elementAt(parent)) >= 0) {
                break;
            }
            elements[index] = elements[parent];
            index = parent;
        }
        elements[index] = item;
    }

    private void siftDown(int index) {
        T item = elementAt(index);
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (compare(item, elementAt(child)) <= 0) {
                break;
            }
            elements[index] = elements[child];
            index = child;
        }
        elements[index] = item;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }
}
