package ru.bmstu.lab02.task_01a;

import java.util.NoSuchElementException;

public class PriorityQueueCustom<T> {

    private static final int START_CAPACITY = 8;
    private static final int GROWTH_FACTOR = 2;

    private Object[] items;
    private int count;

    public PriorityQueueCustom() {
        items = new Object[START_CAPACITY];
        count = 0;
    }

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Нельзя добавить null в очередь");
        }
        if (count == items.length) {
            expand();
        }
        items[count] = item;
        count++;
        siftUp(count - 1);
    }

    public T poll() {
        T top = peek();
        deleteAt(0);
        return top;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (count == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return (T) items[0];
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    @SuppressWarnings("unchecked")
    protected int compare(T first, T second) {
        return ((Comparable<T>) first).compareTo(second);
    }

    protected Object itemAt(int index) {
        return items[index];
    }

    protected void deleteAt(int index) {
        count--;
        Object last = items[count];
        items[count] = null;
        if (index == count) {
            return;
        }
        items[index] = last;
        siftDown(index);
        if (items[index] == last) {
            siftUp(index);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        T moving = (T) items[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(moving, (T) items[parent]) >= 0) {
                break;
            }
            items[index] = items[parent];
            index = parent;
        }
        items[index] = moving;
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        T moving = (T) items[index];
        while (2 * index + 1 < count) {
            int child = 2 * index + 1;
            int right = 2 * index + 2;
            if (right < count && compare((T) items[right], (T) items[child]) < 0) {
                child = right;
            }
            if (compare(moving, (T) items[child]) <= 0) {
                break;
            }
            items[index] = items[child];
            index = child;
        }
        items[index] = moving;
    }

    private void expand() {
        Object[] bigger = new Object[items.length * GROWTH_FACTOR];
        for (int i = 0; i < count; i++) {
            bigger[i] = items[i];
        }
        items = bigger;
    }
}
