package ru.bmstu.lab2.queue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Задачи 1A, 1B: двоичная куча на расширяемом массиве. Null-элементы запрещены. */
public class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт min-heap с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this(null);
    }

    /** Создаёт кучу с заданным порядком; null означает естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    /** Добавляет элемент: O(log n) амортизированно с учётом расширения массива. */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        if (size == elements.length) {
            Object[] expanded = new Object[elements.length * 2];
            System.arraycopy(elements, 0, expanded, 0, size);
            elements = expanded;
        }
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /** Извлекает вершину за O(log n); в пустой очереди бросает NoSuchElementException. */
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

    /** Возвращает вершину за O(1); в пустой очереди бросает NoSuchElementException. */
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

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(elementAt(index), elementAt(parent)) >= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (compare(elementAt(index), elementAt(child)) <= 0) {
                break;
            }
            swap(index, child);
            index = child;
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
