package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Задачи 1A, 1B, 1D: бинарная куча в расширяемом массиве с настраиваемым порядком.
 * Null запрещён; без компаратора элементы должны реализовывать Comparable.
 *
 * @param <T> тип элемента
 */
public final class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] elements;
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт пустую min-heap с естественным порядком. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /** Создаёт очередь с заданным порядком; null означает естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[INITIAL_CAPACITY];
    }

    /** Строит min-heap за O(n), не изменяя исходный массив. */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /** Строит кучу за O(n) с заданным компаратором, копируя исходные элементы. */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "array");
        this.comparator = comparator;
        elements = Arrays.copyOf(array, Math.max(INITIAL_CAPACITY, array.length), Object[].class);
        size = array.length;
        for (T item : array) {
            Objects.requireNonNull(item, "item");
        }
        // Листья уже являются кучами; восстанавливаем порядок снизу вверх.
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /** Добавляет элемент за O(log n) амортизированно; расширение массива занимает O(n). */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, Math.multiplyExact(elements.length, 2));
        }
        elements[size] = item;
        siftUp(size++);
    }

    /** Извлекает первый по порядку элемент за O(log n); пустая очередь вызывает исключение. */
    public T poll() {
        T result = peek();
        elements[0] = elements[--size];
        elements[size] = null;
        if (size > 0) {
            siftDown(0);
        }
        return result;
    }

    /** Возвращает первый элемент за O(1); при пустой очереди бросает NoSuchElementException. */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
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

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        return comparator == null
                ? ((Comparable<? super T>) first).compareTo(second)
                : comparator.compare(first, second);
    }
}
