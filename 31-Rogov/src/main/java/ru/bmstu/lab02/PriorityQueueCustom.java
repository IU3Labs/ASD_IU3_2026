package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Очередь с приоритетом на динамическом массиве, организованном в двоичную кучу. */
public final class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;

    private final Comparator<? super T> comparator;
    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int size;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this(null);
    }

    /** Создаёт пустую очередь с заданным порядком. Допускает null для естественного порядка. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    /** Добавляет ненулевой элемент за амортизированное O(log n). */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        if (comparator == null && !(item instanceof Comparable<?>)) {
            throw new ClassCastException("Natural ordering requires Comparable elements");
        }
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, Math.multiplyExact(elements.length, 2));
        }
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /** Извлекает первый по порядку элемент за O(log n). Для пустой очереди бросает исключение. */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T first = elementAt(0);
        removeAt(0);
        return first;
    }

    /** Возвращает первый по порядку элемент без удаления либо null, если очередь пуста. */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /** Удаляет первое равное значение в массиве кучи за O(n). */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (elements[index].equals(item)) {
                removeAt(index);
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

    private void removeAt(int index) {
        int last = --size;
        T replacement = elementAt(last);
        elements[last] = null;
        if (index == last) {
            return;
        }
        elements[index] = replacement;
        if (index > 0 && compare(replacement, elementAt(parentOf(index))) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = parentOf(index);
            T parentItem = elementAt(parent);
            if (compare(item, parentItem) >= 0) {
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
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            T childItem = elementAt(child);
            if (compare(item, childItem) <= 0) {
                break;
            }
            elements[index] = childItem;
            index = child;
        }
        elements[index] = item;
    }

    private int parentOf(int index) {
        return (index - 1) / 2;
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }
}
