package ru.bmstu.lab02.task1b;

import java.util.Comparator;
import java.util.NoSuchElementException;

/*
 * ============================================================================
 * Лабораторная работа №2. Задача 1B (2 балла).
 *
 * Условие:
 *   Расширить очередь с приоритетом из 1A так, чтобы конструктор принимал
 *   Comparator<T> (при отсутствии — естественный порядок Comparable).
 *   Класс должен корректно работать как min-heap, так и max-heap.
 *   Продемонстрировать оба режима в тестах.
 *   Дополнительно: сравнить время работы min- и max-варианта на одном и том же
 *   наборе из 100 000 случайных элементов, объяснить результат.
 * ============================================================================
 */

/**
 * Очередь с приоритетом, поддерживающая режимы min-heap и max-heap через компаратор.
 * При отсутствии компаратора используется естественный порядок элементов.
 *
 * @param <T> тип хранимых элементов
 */
public class PriorityQueueComparator<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private final Comparator<? super T> comparator;
    private Object[] heap;
    private int size;

    /**
     * Создаёт пустую очередь с естественным порядком сортировки (min-heap).
     */
    public PriorityQueueComparator() {
        this(null);
    }

    /**
     * Создаёт пустую очередь с заданным компаратором.
     *
     * @param comparator компаратор для определения приоритета (null для естественного порядка)
     */
    public PriorityQueueComparator(Comparator<? super T> comparator) {
        this.comparator = comparator;
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
     * Извлекает и возвращает корневой элемент кучи за O(log n).
     *
     * @return элемент с наивысшим приоритетом
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
     * Возвращает корневой элемент без удаления за O(1).
     *
     * @return элемент с наивысшим приоритетом
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
     * Возвращает количество элементов в очереди за O(1).
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

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(index, parent) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int highestPriority = index;

            if (left < size && compare(left, highestPriority) < 0) {
                highestPriority = left;
            }
            if (right < size && compare(right, highestPriority) < 0) {
                highestPriority = right;
            }
            if (highestPriority == index) {
                break;
            }
            swap(index, highestPriority);
            index = highestPriority;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(int firstIndex, int secondIndex) {
        T first = (T) heap[firstIndex];
        T second = (T) heap[secondIndex];
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
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
