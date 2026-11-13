package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Custom Binary Min-Heap Priority Queue implementation without using java.util.PriorityQueue.
 *
 * @param <T> type of elements held in this queue, must be comparable
 */
public class PriorityQueueCustom<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;

    /**
     * Constructs an empty priority queue with default initial capacity.
     */
    public PriorityQueueCustom() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Constructs an empty priority queue with specified initial capacity.
     *
     * @param initialCapacity initial capacity of the array
     */
    public PriorityQueueCustom(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative: " + initialCapacity);
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Returns the number of elements in this priority queue.
     * Time complexity: O(1)
     *
     * @return number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this priority queue contains no elements.
     * Time complexity: O(1)
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the minimum element without removing it.
     * Time complexity: O(1)
     *
     * @return the minimum element
     * @throws NoSuchElementException if the queue is empty
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
     * Time complexity: O(log n) amortized
     *
     * @-param item element to add
     * @throws IllegalArgumentException if item is null
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
     * Retrieves and removes the minimum element from the priority queue.
     * Time complexity: O(log n)
     *
     * @return the minimum element
     * @throws NoSuchElementException if the queue is empty
     */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T min = (T) data[0];
        data[0] = data[size - 1];
        data[size - 1] = null; // Prevent memory leak
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    /**
     * Sifts the element up to maintain the min-heap property.
     *
     * @param index index of the element to sift up
     */
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            @SuppressWarnings("unchecked")
            T current = (T) data[index];
            @SuppressWarnings("unchecked")
            T parent = (T) data[parentIndex];

            if (current.compareTo(parent) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }