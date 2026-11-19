package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * ЛР2, задачи 1A–1D: двоичная куча на собственном динамическом массиве.
 * Компаратор определяет приоритет; без него используется естественный порядок.
 * null запрещён, порядок элементов с равным приоритетом не фиксирован.
 *
 * @param <T> тип элементов
 */
public final class PriorityQueueCustom<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;

    private final Comparator<? super T> comparator;
    private Object[] elements;
    private int size;

    /** Создаёт пустую min-heap с естественным порядком за O(1). */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /** Создаёт пустую кучу за O(1); null означает естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[DEFAULT_CAPACITY];
    }

    /** Копирует массив и строит min-heap за O(n), не изменяя исходный массив. */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /** Копирует массив и строит кучу по компаратору за O(n). null-элементы запрещены. */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "array");
        this.comparator = comparator;
        elements = new Object[Math.max(DEFAULT_CAPACITY, array.length)];
        for (int i = 0; i < array.length; i++) {
            elements[i] = Objects.requireNonNull(array[i], "array element");
        }
        size = array.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * Добавляет ненулевой элемент за O(log n) амортизированно.
     * Отдельное расширение массива требует O(n).
     */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        ensureCapacity();
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /** Извлекает первый по приоритету элемент за O(log n); пустая куча — исключение. */
    public T poll() {
        T result = peek();
        removeAt(0);
        return result;
    }

    /** Возвращает первый по приоритету элемент за O(1); пустая куча — исключение. */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elementAt(0);
    }

    /** Возвращает число элементов за O(1). */
    public int size() {
        return size;
    }

    /** Проверяет пустоту за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаляет первое по индексу массива вхождение, равное item через equals, за O(n).
     * Возвращает false, если элемента нет, в том числе для null.
     */
    public boolean remove(T item) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(item)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    private void removeAt(int index) {
        int lastIndex = --size;
        T replacement = elementAt(lastIndex);
        elements[lastIndex] = null;
        if (index == lastIndex) {
            return;
        }
        elements[index] = replacement;
        // Последний элемент может нарушить порядок как с родителем, так и с детьми.
        if (index > 0 && compare(replacement, elementAt((index - 1) / 2)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(item, elementAt(parent)) >= 0) {
                break;
            }
            elements[index] = elements[parent];
            index = parent;
        }
        elements[index] = item;
    }

    private void siftDown(int index) {
        T item = elementAt(index);
        // Условие до вычисления 2*i+1 исключает переполнение у листьев.
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (compare(item, elementAt(child)) <= 0) {
                break;
            }
            elements[index] = elements[child];
            index = child;
        }
        elements[index] = item;
    }

    private void ensureCapacity() {
        if (size < elements.length) {
            return;
        }
        if (size == MAX_CAPACITY) {
            throw new OutOfMemoryError("Queue is too large");
        }
        int newCapacity = (int) Math.min((long) elements.length * 2, MAX_CAPACITY);
        Object[] expanded = new Object[newCapacity];
        System.arraycopy(elements, 0, expanded, 0, size);
        elements = expanded;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    private int compare(T left, T right) {
        if (comparator != null) {
            return comparator.compare(left, right);
        }
        return ((Comparable<? super T>) left).compareTo(right);
    }
}
