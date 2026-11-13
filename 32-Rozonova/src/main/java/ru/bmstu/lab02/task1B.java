package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Priority Queue implementation supporting both Min-Heap and Max-Heap via Comparator.
 *
 * @param <T> type of elements held in this queue
 */
public class PriorityQueueCustomComparator<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;
    private final Comparator<? super T> comparator;

    /**
     * Constructs an empty priority queue with natural ordering and default capacity.
     */
    @SuppressWarnings("unchecked")
    public PriorityQueueCustomComparator() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = (a, b) -> ((Comparable<T>) a).compareTo(b);
    }

    /**
     * Constructs an empty priority queue with specified comparator.
     *
     * @param comparator custom comparator for element ordering
     */
    @SuppressWarnings("unchecked")
    public PriorityQueueCustomComparator(Comparator<? super T> comparator) {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator != null ? comparator : (a, b) -> ((Comparable<T>) a).compareTo(b);
    }

    /**
     * Returns the number of elements in this priority queue.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this priority queue contains no elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the top priority element without removing it.
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return (T) data[0];
    }

    /**
     * Adds an element to the priority queue.
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not allowed");
        }
        ensureCapacity();
        data[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Retrieves and removes the top priority element.
     */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T top = (T) data[0];
        data[0] = data[size - 1];
        data[size - 1] = null;
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return top;
    }

    /**
     * Helper method to compare elements using the provided comparator.
     */
    private boolean compare(T a, T b) {
        return comparator.compare(a, b) < 0;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            @SuppressWarnings("unchecked")
            T current = (T) data[index];
            @SuppressWarnings("unchecked")
            T parent = (T) data[parentIndex];

            if (compare(current, parent)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }