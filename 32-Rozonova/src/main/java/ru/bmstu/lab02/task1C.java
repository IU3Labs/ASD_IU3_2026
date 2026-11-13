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

    /**
     * Retrieves and removes the minimum element from the priority queue.
     * Time complexity: O(log n)
     */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T min = (T) data[0];
        data[0] = data[size - 1];
        data[size - 1] = null;
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    /**
     * Removes the first occurrence of the specified element from this priority queue.
     * Time complexity: O(n) for linear search + O(log n) for heap restoration = O(n)
     *
     * @param item element to be removed
     * @return true if an element was removed, false otherwise
     */
    public boolean remove(T item) {
        if (item == null || isEmpty()) {
            return false;
        }

        int index = -1;
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T current = (T) data[i];
            if (item.equals(current)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false; // Element not found
        }

        // If it's the last element, simply remove it
        if (index == size - 1) {
            data[size - 1] = null;
            size--;
            return true;
        }

        // Replace with the last element
        @SuppressWarnings("unchecked")
        T lastElement = (T) data[size - 1];
        data[size - 1] = null;
        size--;

        data[index] = lastElement;

        // Restore heap property
        int parentIndex = (index - 1) / 2;
        if (index > 0 && lastElement.compareTo((T) data[parentIndex]) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }

        return true;
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

    private void siftDown(int index) {
        int half = size / 2;
        while (index < half) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = leftChild;

            @SuppressWarnings("unchecked")
            T smallestVal = (T) data[leftChild];

            if (rightChild < size) {
                @SuppressWarnings("unchecked")
                T rightVal = (T) data[rightChild];
                if (rightVal.compareTo(smallestVal) < 0) {
                    smallest = rightChild;
                    smallestVal = rightVal;
                }
            }

            @SuppressWarnings("unchecked")
            T currentVal = (T) data[index];
            if (currentVal.compareTo(smallestVal) > 0) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void ensureCapacity() {
        if (size >= data.length) {
            int newCapacity = data.length * 2;
            data = Arrays.copyOf(data, newCapacity);
        }
    }
}