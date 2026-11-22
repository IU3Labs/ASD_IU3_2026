package ru.bmstu.lab2.heap;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/** задачи 1A и 1D — минимальная двоичная куча на растущем массиве */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] values;
    private int size;

    /** создаёт пустую очередь */
    public PriorityQueueCustom() {
        values = new Object[INITIAL_CAPACITY];
    }

    /** строит кучу снизу вверх на собственной копии массива */
    public PriorityQueueCustom(T[] array) {
        values = Arrays.copyOf(array, Math.max(1, array.length), Object[].class);
        size = array.length;
        for (T item : array) {
            Objects.requireNonNull(item);
        }
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /** добавляет элемент согласно естественному порядку */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == values.length) {
            values = Arrays.copyOf(values, Math.multiplyExact(size, 2));
        }
        values[size] = item;
        siftUp(size++);
    }

    /** возвращает минимум или null для пустой очереди */
    public T peek() {
        return size == 0 ? null : at(0);
    }

    /** извлекает минимум или бросает NoSuchElementException для пустой очереди */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T minimum = at(0);
        values[0] = values[--size];
        values[size] = null;
        siftDown(0);
        return minimum;
    }

    /** возвращает число элементов */
    public int size() {
        return size;
    }

    /** проверяет, пуста ли очередь */
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private T at(int index) {
        return (T) values[index];
    }

    private void siftUp(int child) {
        while (child > 0) {
            int parent = (child - 1) / 2;
            if (at(child).compareTo(at(parent)) >= 0) {
                break;
            }
            swap(child, parent);
            child = parent;
        }
    }

    private void siftDown(int parent) {
        while (parent < size / 2) {
            int child = 2 * parent + 1;
            if (child + 1 < size && at(child + 1).compareTo(at(child)) < 0) {
                child++;
            }
            if (at(parent).compareTo(at(child)) <= 0) {
                break;
            }
            swap(parent, child);
            parent = child;
        }
    }

    private void swap(int first, int second) {
        Object saved = values[first];
        values[first] = values[second];
        values[second] = saved;
    }
}
