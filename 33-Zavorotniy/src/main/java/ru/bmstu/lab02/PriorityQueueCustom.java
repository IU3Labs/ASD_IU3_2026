package ru.bmstu.lab02;

import java.util.NoSuchElementException;
import java.util.Objects;

/** Двоичная min-куча на расширяемом массиве. Реализует задачи 1A и 1C. */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int size;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {}

    /** Добавляет ненулевой элемент за амортизированное O(log n). */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        if (size == elements.length) {
            grow();
        }
        elements[size] = item;
        size++;
        siftUp(size - 1);
    }

    /**
     * Извлекает минимальный элемент за O(log n).
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("The priority queue is empty");
        }
        T minimum = elementAt(0);
        removeAt(0);
        return minimum;
    }

    /** Возвращает минимум или null для пустой очереди за O(1). */
    public T peek() {
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

    /** Удаляет первое совпадение по equals в порядке обхода массива за O(n). */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (elements[index].equals(item)) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    private void grow() {
        Object[] larger = new Object[Math.multiplyExact(elements.length, 2)];
        System.arraycopy(elements, 0, larger, 0, size);
        elements = larger;
    }

    private void removeAt(int index) {
        int last = --size;
        T replacement = elementAt(last);
        elements[last] = null;
        if (index == last) {
            return;
        }
        elements[index] = replacement;
        // Замена пришла из другой ветви и может оказаться меньше нового родителя.
        if (index > 0 && replacement.compareTo(elementAt((index - 1) / 2)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentItem = elementAt(parent);
            if (item.compareTo(parentItem) >= 0) {
                break;
            }
            elements[index] = parentItem;
            index = parent;
        }
        elements[index] = item;
    }

    private void siftDown(int index) {
        T item = elementAt(index);
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && elementAt(right).compareTo(elementAt(child)) < 0) {
                child = right;
            }
            T childItem = elementAt(child);
            if (item.compareTo(childItem) <= 0) {
                break;
            }
            elements[index] = childItem;
            index = child;
        }
        elements[index] = item;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }
}
