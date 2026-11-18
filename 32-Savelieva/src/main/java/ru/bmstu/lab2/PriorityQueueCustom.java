package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Бинарная min-куча на динамическом массиве.
 * Дети i: 2*i+1, 2*i+2. Родитель: (i-1)/2.
 */
public class PriorityQueueCustom<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] heap;
    private int size;

    public PriorityQueueCustom() {
        this.heap = new Object[DEFAULT_CAPACITY];
    }

    public PriorityQueueCustom(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("initialCapacity must be > 0");
        }
        this.heap = new Object[initialCapacity];
    }

    /** O(log n) */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("item must not be null");
        }
        if (size == heap.length) {
            grow();
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /** O(log n), бросает NoSuchElementException при пустой очереди */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        T result = (T) heap[0];
        size--;
        if (size > 0) {
            heap[0] = heap[size];
            heap[size] = null;
            siftDown(0);
        } else {
            heap[0] = null;
        }
        return result;
    }

    /** O(1) */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        return (T) heap[0];
    }

    /** O(1) */
    public int size() {
        return size;
    }

    /** O(1) */
    public boolean isEmpty() {
        return size == 0;
    }

    // ---------- внутреннее ----------

    private void grow() {
        int newCapacity = heap.length * 2;
        heap = Arrays.copyOf(heap, newCapacity);
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        T item = (T) heap[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentItem = (T) heap[parent];
            if (item.compareTo(parentItem) >= 0) {
                break;
            }
            heap[index] = parentItem;
            index = parent;
        }
        heap[index] = item;
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        T item = (T) heap[index];
        int half = size / 2;
        while (index < half) {
            int left = 2 * index + 1;
            int right = left + 1;
            int smallest = left;

            T leftItem = (T) heap[left];
            T rightItem = right < size ? (T) heap[right] : null;

            if (rightItem != null && rightItem.compareTo(leftItem) < 0) {
                smallest = right;
            }
            T smallestItem = (T) heap[smallest];
            if (item.compareTo(smallestItem) <= 0) {
                break;
            }
            heap[index] = smallestItem;
            index = smallest;
        }
        heap[index] = item;
    }
}