package ru.bmstu.lab2;

import java.util.Comparator;
import java.util.NoSuchElementException;

/** Задачи 1A, 1B, 1C. Очередь с приоритетом на двоичной куче в массиве. */
public class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 8;
    private Object[] data = new Object[INITIAL_CAPACITY];
    private int size;
    private Comparator<? super T> comparator;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
    }

    /** Создаёт очередь с заданным порядком; null означает естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    /** Добавляет элемент; O(log n) амортизированно с учётом расширения массива. */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("Элемент не должен быть null");
        }
        growIfNeeded();
        data[size] = item;
        size++;
        siftUp(size - 1);
    }

    /** Извлекает первый по приоритету элемент за O(log n); пустая очередь — исключение. */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T result = elementAt(0);
        size--;
        data[0] = data[size];
        data[size] = null;
        if (size > 0) {
            siftDown(0);
        }
        return result;
    }

    /** Возвращает первый по приоритету элемент за O(1); пустая очередь — исключение. */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return elementAt(0);
    }

    /** Возвращает количество элементов за O(1). */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли очередь, за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Удаляет первое совпадение в массиве кучи за O(n); null или отсутствие дают false. */
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (item.equals(data[i])) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    private void removeAt(int index) {
        size--;
        data[index] = data[size];
        data[size] = null;
        if (index == size) {
            return;
        }
        int parent = (index - 1) / 2;
        if (index > 0 && compare(elementAt(index), elementAt(parent)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void growIfNeeded() {
        if (size < data.length) {
            return;
        }
        Object[] bigger = new Object[data.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = data[i];
        }
        data = bigger;
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
        // У узлов с индексом size / 2 и дальше детей нет.
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (compare(elementAt(index), elementAt(child)) <= 0) {
                break;
            }
            swap(index, child);
            index = child;
        }
    }

    private void swap(int first, int second) {
        Object temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    // Object[] нужен, потому что Java не разрешает new T[].
    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) data[index];
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
    }
}
