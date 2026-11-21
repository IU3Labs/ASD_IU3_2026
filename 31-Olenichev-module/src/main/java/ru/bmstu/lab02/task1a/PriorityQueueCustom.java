package ru.bmstu.lab02.task1a;

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

    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

}
