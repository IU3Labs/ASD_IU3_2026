package ru.bmstu.lab2.task_1a_1b_1d;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Задачи 1A, 1B, 1D: бинарная куча в расширяемом массиве с настраиваемым порядком.
 * Null запрещён; без компаратора элементы должны реализовывать Comparable.
 *
 * @param <T> тип элемента
 */
public final class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] elements;
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт пустую min-heap с естественным порядком. */
    public PriorityQueueCustom() {
        comparator = null;
        elements = new Object[INITIAL_CAPACITY];
    }

    /** Создаёт очередь с заданным порядком; null означает естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[INITIAL_CAPACITY];
    }

    /** Строит min-heap за O(n), не изменяя исходный массив. */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /** Строит кучу за O(n) с заданным компаратором, копируя исходные элементы. */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new NullPointerException("array");
        }
        this.comparator = comparator;
        int capacity = INITIAL_CAPACITY;
        if (array.length > capacity) {
            capacity = array.length;
        }
        elements = new Object[capacity];
        size = array.length;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new NullPointerException("item");
            }
            elements[i] = array[i];
        }
        // листья просеивать не нужно
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /** Добавляет элемент за O(log n) амортизированно; расширение массива занимает O(n). */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("item");
        }
        if (size == elements.length) {
            grow();
        }
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /** Извлекает первый по порядку элемент за O(log n); пустая очередь вызывает исключение. */
    public T poll() {
        T result = peek();
        size--;
        elements[0] = elements[size];
        elements[size] = null;
        if (size > 0) {
            siftDown(0);
        }
        return result;
    }

    /** Возвращает первый элемент за O(1); при пустой очереди бросает NoSuchElementException. */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return elementAt(0);
    }

    /** Возвращает число элементов за O(1). */
    public int size() {
        return size;
    }

    /** Проверяет пустоту за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    private void grow() {
        Object[] bigger = new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = elements[i];
        }
        elements = bigger;
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

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        Comparable<? super T> value = (Comparable<? super T>) first;
        return value.compareTo(second);
    }
}
