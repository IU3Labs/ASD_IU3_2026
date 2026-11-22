/*Задача 1B (min-heap и max-heap через компаратор).
Реализуйте очередь с приоритетом, конструктор которой принимает
Comparator<T> (при отсутствии — естественный порядок). Класс должен
корректно работать и как min-heap, и как max-heap. В тестах продемонстрируйте
оба режима. Дополнительно: сравните время работы min- и max-варианта на одном
и том же наборе из 100 000 случайных элементов и объясните результат.*/

package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class Task1B_PriorityQueue<T> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;

    public Task1B_PriorityQueue() {
        this((Comparator<? super T>) null);
    }

    public Task1B_PriorityQueue(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new Object[INITIAL_CAPACITY];
    }

    public void add(T item) {
        Objects.requireNonNull(item, "item must not be null");
        if (size == heap.length) {
            Object[] next = new Object[heap.length * 2];
            System.arraycopy(heap, 0, next, 0, size);
            heap = next;
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        T result = elementAt(0);
        removeAt(0);
        return result;
    }

    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        return elementAt(0);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void removeAt(int index) {
        size--;
        if (index == size) {
            heap[size] = null;
            return;
        }
        heap[index] = heap[size];
        heap[size] = null;
        if (index > 0 && compare(elementAt(index), elementAt((index - 1) / 2)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(elementAt(index), elementAt(parent)) >= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (2L * index + 1 < size) {
            int child = 2 * index + 1;
            if (child + 1 < size && compare(elementAt(child + 1), elementAt(child)) < 0) {
                child++;
            }
            if (compare(elementAt(index), elementAt(child)) <= 0) {
                break;
            }
            swap(index, child);
            index = child;
        }
    }

    private void swap(int a, int b) {
        Object temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) heap[index];
    }

    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super T>) a).compareTo(b);
    }
}
