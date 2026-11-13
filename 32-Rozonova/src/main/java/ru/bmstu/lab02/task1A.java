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