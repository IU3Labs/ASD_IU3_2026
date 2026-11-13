package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Priority Queue implementation supporting removal by value in O(n) time.
 *
 * @param <T> type of elements held in this queue, must be comparable
 */
public class PriorityQueueWithRemove<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;
    /**
     * Constructs an empty priority queue with default initial capacity.
     */
    public PriorityQueueWithRemove() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the number of elements in this priority queue.
     * Time complexity: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this priority queue contains no elements.
     * Time complexity: O(1)
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Returns the minimum element without removing it.
     * Time complexity: O(1)
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