package ru.bmstu.lab02.task01a;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Двоичная куча на массиве. В корне хранится первый элемент по заданному порядку. */
public class PriorityQueueCustom<T> {
    private static final int DEFAULT_CAPACITY = 16;

    private final Comparator<? super T> comparator;
    private Object[] elements;
    private int size;

    /** Создаёт пустую кучу с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this(PriorityQueueCustom::compareNaturally);
    }

    /** Создаёт пустую кучу с заданным компаратором. */
    protected PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
        elements = new Object[DEFAULT_CAPACITY];
    }

    /** Копирует элементы для последующего построения кучи. */
    protected PriorityQueueCustom(T[] array) {
        comparator = PriorityQueueCustom::compareNaturally;
        elements = Arrays.copyOf(array, Math.max(DEFAULT_CAPACITY, array.length), Object[].class);
        size = array.length;
        for (T item : array) {
            Objects.requireNonNull(item);
        }
    }

    /** Просеивает внутренние узлы снизу вверх. */
    protected final void buildHeap() {
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /** Добавляет элемент в кучу. Значение null недопустимо. */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, Math.multiplyExact(elements.length, 2));
        }
        elements[size] = item;
        size++;
        siftUp(size - 1);
    }

    /** Извлекает корень. Для пустой кучи выбрасывает NoSuchElementException. */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь с приоритетом пуста");
        }
        T first = elementAt(0);
        removeAt(0);
        return first;
    }

    /** Возвращает корень или null, если куча пуста. */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /** Возвращает число элементов. */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли куча. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Удаляет элемент по индексу и восстанавливает порядок. */
    protected final void removeAt(int index) {
        int last = --size;
        elements[index] = elements[last];
        elements[last] = null;
        if (index == last) {
            return;
        }
        // Новый элемент может нарушить порядок с родителем или с одним из детей.
        if (index > 0 && compare(index, (index - 1) / 2) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(index, parent) >= 0) {
                return;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(right, child) < 0) {
                child = right;
            }
            if (compare(index, child) <= 0) {
                return;
            }
            swap(index, child);
            index = child;
        }
    }

    private void swap(int first, int second) {
        Object saved = elements[first];
        elements[first] = elements[second];
        elements[second] = saved;
    }

    private int compare(int first, int second) {
        return comparator.compare(elementAt(first), elementAt(second));
    }

    /** Возвращает элемент по индексу в массиве кучи. */
    @SuppressWarnings("unchecked")
    protected final T elementAt(int index) {
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    private static <T> int compareNaturally(T first, T second) {
        return ((Comparable<? super T>) first).compareTo(second);
    }
}
