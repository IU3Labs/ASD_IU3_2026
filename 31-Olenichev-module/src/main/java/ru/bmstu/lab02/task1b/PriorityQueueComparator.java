package ru.bmstu.lab02.task1b;

import java.util.Comparator;

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

    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}
