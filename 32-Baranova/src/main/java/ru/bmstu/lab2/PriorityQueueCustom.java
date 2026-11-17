package ru.bmstu.lab2;
import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом на основе минимальной бинарной кучи.
 * Элементы хранятся в динамическом массиве.
 */
public class PriorityQueueCustom<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] heap;
    private int size;

    /**
     * Создаёт пустую очередь с приоритетом.
     */
    public PriorityQueueCustom() {
        heap = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Возвращает количество элементов в очереди.
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, является ли очередь пустой.
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /** Добавляет элемент в очередь. */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size == heap.length) {
            grow();
        }

        heap[size] = item;
        size++;
        siftUp(size - 1);
    }

    private void grow() {
        Object[] newHeap = new Object[heap.length * 2];

        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

    // Просеивание элемента вверх.
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (getElement(index).compareTo(getElement(parent)) >= 0) {
                break;
            }

            swap(index, parent);

            index = parent;
        }
    }

    @SuppressWarnings("unchecked")
    private T getElement(int index) {
        return (T) heap[index];
    }
    /** Возвращает минимальный элемент без удаления или null. */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return getElement(0);
    }

    /** Извлекает минимальный элемент. */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T result = getElement(0);
        heap[0] = heap[size - 1];
        size--;
        heap[size] = null;

        if (!isEmpty()) {
            siftDown(0);
        }

        return result;
    }

    // Просеивание элемента вниз.
    private void siftDown(int index) {
        while (index < size / 2) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = left;

            if (right < size &&
                    getElement(right).compareTo(getElement(left)) < 0) {
                smallest = right;
            }

            if (getElement(index).compareTo(getElement(smallest)) <= 0) {
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
}