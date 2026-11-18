package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Двоичная куча на растущем массиве. Первым выходит элемент с наивысшим приоритетом. */
public class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 8;
    private final Comparator<? super T> order;
    private Object[] heap;
    private int size;

    /** Создаёт очередь с естественным порядком. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /** Задаёт порядок элементов. При null используется естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        order = comparator;
        heap = new Object[INITIAL_CAPACITY];
    }

    /** Строит min-heap снизу вверх, исходный массив не меняется. */
    public PriorityQueueCustom(T[] array) {
        order = null;
        size = array.length;
        heap = Arrays.copyOf(array, Math.max(INITIAL_CAPACITY, size), Object[].class);
        for (T item : array) {
            Objects.requireNonNull(item);
        }
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /** Вставляет ненулевой элемент. */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, size * 2);
        }
        heap[size++] = item;
        siftUp(size - 1);
    }

    /** Возвращает вершину или null для пустой очереди. */
    public T peek() {
        return size == 0 ? null : at(0);
    }

    /** Снимает вершину. Для пустой очереди бросает NoSuchElementException. */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T result = at(0);
        removeAt(0);
        return result;
    }

    /** Удаляет первое совпадение по equals в порядке обхода массива. */
    public boolean remove(T item) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(item)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /** Возвращает текущее число элементов. */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли очередь. */
    public boolean isEmpty() {
        return size == 0;
    }

    private void removeAt(int i) {
        heap[i] = heap[--size];
        heap[size] = null;
        if (i == size) {
            return;
        }
        // Последний элемент на новом месте может нарушить порядок с любой стороны.
        if (i > 0 && compare(i, (i - 1) / 2) < 0) {
            siftUp(i);
        } else {
            siftDown(i);
        }
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (compare(i, parent) >= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i) {
        while (i < size / 2) {
            int child = 2 * i + 1;
            if (child + 1 < size && compare(child + 1, child) < 0) {
                child++;
            }
            if (compare(i, child) <= 0) {
                break;
            }
            swap(i, child);
            i = child;
        }
    }

    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private T at(int i) {
        return (T) heap[i];
    }

    @SuppressWarnings("unchecked")
    private int compare(int i, int j) {
        if (order != null) {
            return order.compare(at(i), at(j));
        }
        return ((Comparable<? super T>) at(i)).compareTo(at(j));
    }
}
