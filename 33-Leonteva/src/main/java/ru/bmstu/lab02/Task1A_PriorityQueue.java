/*Задача 1A (базовая реализация). Реализуйте PriorityQueueCustom<T> на динамическом массиве с операциями:

void add(T item) — добавить элемент, (O(\log n));
T poll() — извлечь и вернуть минимальный элемент, при пустой очереди бросать NoSuchElementException, (O(\log n));
T peek() — вернуть минимальный элемент без удаления, (O(1));
int size(), boolean isEmpty() — (O(1)).
Используйте индексацию: дети узла i — 2*i + 1 и 2*i + 2, родитель — (i - 1) / 2. Реализуйте просеивание вверх и вниз
отдельными приватными методами siftUp и siftDown. Обеспечьте автоматическое расширение массива. Требование: элементы
извлекаются в порядке неубывания.*/

package ru.bmstu.lab02;

import java.util.NoSuchElementException;
import java.util.Objects;

public final class Task1A_PriorityQueue<T> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] heap;
    private int size;

    public Task1A_PriorityQueue() {
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
        return ((Comparable<? super T>) a).compareTo(b);
    }
}
