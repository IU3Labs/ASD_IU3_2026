package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Очередь с приоритетом на двоичной куче с динамическим массивом. */
public class PriorityQueueCustom<T> {
    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /** Задаёт порядок приоритетов. При null используется естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        heap = new Object[16];
    }

    /** Строит кучу из копии массива за линейное время. */
    public PriorityQueueCustom(T[] array) {
        comparator = null;
        size = array.length;
        heap = Arrays.copyOf(array, size, Object[].class);
        for (T item : array) {
            Objects.requireNonNull(item);
        }
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /** Добавляет ненулевой элемент с восстановлением порядка кучи. */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, Math.max(1, Math.multiplyExact(size, 2)));
        }
        heap[size] = item;
        size++;
        siftUp(size - 1);
    }

    /** Возвращает вершину. Для пустой очереди выбрасывает NoSuchElementException. */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elementAt(0);
    }

    /** Извлекает вершину. Для пустой очереди выбрасывает NoSuchElementException. */
    public T poll() {
        T first = peek();
        removeAt(0);
        return first;
    }

    /** Удаляет первое совпадение по equals и сообщает, найден ли элемент. */
    public boolean remove(T item) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(item)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /** Возвращает число элементов. */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли очередь. */
    public boolean isEmpty() {
        return size == 0;
    }

    private void removeAt(int i) {
        size--;
        heap[i] = heap[size];
        heap[size] = null;
        if (i == size) {
            return;
        }
        if (i > 0 && compare(i, (i - 1) / 2) < 0) {
            siftUp(i);
        } else {
            siftDown(i);
        }
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (compare(i, parent) >= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i) {
        while (i < size / 2) {
            int child = 2 * i + 1;
            if (child + 1 < size && compare(child + 1, child) < 0) {
                child++;
            }
            if (compare(i, child) <= 0) {
                break;
            }
            swap(i, child);
            i = child;
        }
    }

    private void swap(int i, int j) {
        Object value = heap[i];
        heap[i] = heap[j];
        heap[j] = value;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int i) {
        return (T) heap[i];
    }

    @SuppressWarnings("unchecked")
    private int compare(int i, int j) {
        T first = elementAt(i);
        T second = elementAt(j);
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
    }
}
