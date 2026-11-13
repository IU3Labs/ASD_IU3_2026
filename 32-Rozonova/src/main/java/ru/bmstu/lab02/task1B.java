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