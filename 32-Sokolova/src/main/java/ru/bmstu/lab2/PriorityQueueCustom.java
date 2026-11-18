
package ru.bmstu.lab2;

import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом на основе двоичной min-кучи.
 *
 * @param <T> тип элементов, поддерживающий сравнение
 */
public class PriorityQueueCustom<T extends Comparable<? super T>> {

    private static final int INITIAL_CAPACITY = 10;

    private Object[] heap;
    private int size;

    /**
     * Создаёт пустую очередь с приоритетом.
     */
    public PriorityQueueCustom() {
        heap = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Добавляет элемент в очередь за O(log n).
     *
     * @param item добавляемый элемент
     */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("Элемент не может быть null");
        }

        ensureCapacity();
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает минимальный элемент за O(log n).
     *
     * @return минимальный элемент
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }

        T result = elementAt(0);
        size--;

        if (size == 0) {
            heap[0] = null;
            return result;
        }

        heap[0] = heap[size];
        heap[size] = null;
        siftDown(0);

        return result;
    }

    /**
     * Возвращает минимальный элемент без удаления за O(1).
     *
     * @return минимальный элемент
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }

        return elementAt(0);
    }

    /**
     * Возвращает количество элементов за O(1).
     *
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь, за O(1).
     *
     * @return true, если очередь пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size < heap.length) {
            return;
        }

        Object[] newHeap = new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (elementAt(index).compareTo(elementAt(parent)) >= 0) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (2 * index + 1 < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = left;

            if (right < size
                    && elementAt(right).compareTo(elementAt(left)) < 0) {
                smallest = right;
            }

            if (elementAt(index).compareTo(elementAt(smallest)) <= 0) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int first, int second) {
        Object temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) heap[index];
    }

    /**
     * Заменяет минимальный элемент новым за O(log n).
     *
     * @param item новый элемент
     * @return прежний минимальный элемент
     */
    public T replaceMin(T item) {
        if (item == null) {
            throw new NullPointerException("Элемент не может быть null");
        }

        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }

        T previous = elementAt(0);
        heap[0] = item;
        siftDown(0);

        return previous;
    }

}
