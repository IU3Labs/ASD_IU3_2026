package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/** A min-heap backed by a resizable array, ordered by the elements' natural order. */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {

    private static final int INITIAL_CAPACITY = 16;

    private Object[] heap;
    private int size;

    /** Creates an empty queue. */
    public PriorityQueueCustom() {
        heap = new Object[INITIAL_CAPACITY];
    }

    /**
     * Builds a heap from a copy of the given array in O(n) time.
     *
     * @throws NullPointerException if the array or any element is null
     */
    public PriorityQueueCustom(T[] array) {
        size = array.length;
        heap = Arrays.copyOf(array, Math.max(INITIAL_CAPACITY, size), Object[].class);
        for (T item : array) {
            Objects.requireNonNull(item);
        }
        for (int parent = size / 2 - 1; parent >= 0; parent--) {
            siftDown(parent);
        }
    }

    /**
     * Adds an item in amortized O(log n) time.
     *
     * @throws NullPointerException if item is null
     */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, Math.multiplyExact(heap.length, 2));
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Removes and returns the minimum in O(log n) time.
     *
     * @throws NoSuchElementException if the queue is empty
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T minimum = itemAt(0);
        size--;
        heap[0] = heap[size];
        heap[size] = null;
        siftDown(0);
        return minimum;
    }

    /** Returns the minimum without removing it, or null when the queue is empty. */
    public T peek() {
        return isEmpty() ? null : itemAt(0);
    }

    /** Returns the number of elements. */
    public int size() {
        return size;
    }

    /** Returns whether the queue has no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int index) {
        T item = itemAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (item.compareTo(itemAt(parent)) >= 0) {
                break;
            }
            heap[index] = heap[parent];
            index = parent;
        }
        heap[index] = item;
    }

    private void siftDown(int index) {
        T item = itemAt(index);
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && itemAt(right).compareTo(itemAt(child)) < 0) {
                child = right;
            }
            if (item.compareTo(itemAt(child)) <= 0) {
                break;
            }
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = item;
    }

    @SuppressWarnings("unchecked")
    private T itemAt(int index) {
        return (T) heap[index];
    }
}
