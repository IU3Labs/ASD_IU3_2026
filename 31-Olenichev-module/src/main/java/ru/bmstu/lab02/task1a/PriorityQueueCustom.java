package ru.bmstu.lab02.task1a;

import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом (min-heap) на динамическом массиве.
 * Элементы извлекаются в порядке неубывания.
 *
 * @param <T> тип хранимых элементов, поддерживающий естественное упорядочивание
 */
public class PriorityQueueCustom<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private Object[] heap;
    private int size;

    /**
     * Создаёт пустую очередь с приоритетом с начальной ёмкостью по умолчанию.
     */
    public PriorityQueueCustom() {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Добавляет элемент в очередь за O(log n).
     *
     * @param item добавляемый элемент (не null)
     * @throws IllegalArgumentException если передан null
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        if (size == heap.length) {
            grow();
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает и возвращает минимальный элемент за O(log n).
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T result = (T) heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return result;
    }

    /**
     * Возвращает минимальный элемент без удаления за O(1).
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return (T) heap[0];
    }

    /**
     * Возвращает текущее количество элементов за O(1).
     *
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь, за O(1).
     *
     * @return true, если очередь не содержит элементов
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (((T) heap[index]).compareTo((T) heap[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && ((T) heap[left]).compareTo((T) heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && ((T) heap[right]).compareTo((T) heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }

    private void grow() {
        Object[] newHeap = new Object[heap.length * GROWTH_FACTOR];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}
