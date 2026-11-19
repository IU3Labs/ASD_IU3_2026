package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Минимальная двоичная куча с естественным порядком элементов. */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 8;

    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int size;

    /** Добавляет ненулевой элемент в кучу. */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, Math.multiplyExact(elements.length, 2));
        }
        siftUp(size++, item);
    }

    /** Извлекает минимум. У пустой очереди бросает NoSuchElementException. */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T minimum = elementAt(0);
        T last = elementAt(--size);
        elements[size] = null;
        if (size > 0) {
            siftDown(0, last);
        }
        return minimum;
    }

    /** Возвращает минимум или null, если очередь пуста. */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /** Возвращает число элементов. */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли очередь. */
    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int index, T item) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentValue = elementAt(parent);
            if (item.compareTo(parentValue) >= 0) {
                break;
            }
            elements[index] = parentValue;
            index = parent;
        }
        elements[index] = item;
    }

    private void siftDown(int index, T item) {
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && elementAt(right).compareTo(elementAt(child)) < 0) {
                child = right;
            }
            T childValue = elementAt(child);
            if (item.compareTo(childValue) <= 0) {
                break;
            }
            elements[index] = childValue;
            index = child;
        }
        elements[index] = item;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }
}
