package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на основе двоичной кучи, которая хранится в массиве.
 *
 * <p>Наверху всегда лежит наименьший элемент. Порядок можно поменять компаратором:
 * с {@code Comparator.reverseOrder()} получится max-heap. Элементы {@code null} не поддерживаются.
 *
 * <p>Для узла с номером {@code i}: дети - {@code 2*i + 1} и {@code 2*i + 2}, родитель - {@code (i - 1) / 2}.
 *
 * @param <T> тип элементов
 */
public class PriorityQueueCustom<T> {

    private static final int START_CAPACITY = 16;

    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;

    /** Пустая очередь, элементы сравниваются через {@code compareTo}. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Пустая очередь с заданным порядком.
     *
     * @param comparator порядок элементов, {@code null} - естественный порядок
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new Object[START_CAPACITY];
    }

    /**
     * Строит кучу из массива за O(n). Сам массив не меняется.
     *
     * @param array исходные элементы
     */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /**
     * Строит кучу из массива за O(n). Сам массив не меняется.
     *
     * @param array      исходные элементы
     * @param comparator порядок элементов, {@code null} - естественный порядок
     */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "array");
        this.comparator = comparator;
        this.heap = new Object[Math.max(START_CAPACITY, array.length)];
        for (T item : array) {
            checkItem(item);
            heap[size] = item;
            size++;
        }
        // Листья уже являются кучами из одного элемента, поэтому идём от последнего узла с детьми к корню.
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * Добавляет элемент, O(log n).
     *
     * @param item элемент, не {@code null}
     */
    public void add(T item) {
        checkItem(item);
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = item;
        size++;
        siftUp(size - 1);
    }

    /**
     * Достаёт наименьший элемент, O(log n).
     *
     * @return наименьший элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T min = peek();
        swap(0, size - 1);
        heap[size - 1] = null;
        size--;
        siftDown(0);
        return min;
    }

    /**
     * Смотрит на наименьший элемент, не удаляя его, O(1).
     *
     * @return наименьший элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return get(0);
    }

    /**
     * Удаляет первое найденное вхождение элемента, O(n).
     *
     * @param item удаляемый элемент
     * @return {@code true}, если элемент нашёлся и был удалён
     */
    public boolean remove(T item) {
        int index = indexOf(item);
        if (index == -1) {
            return false;
        }
        // Ставим на место удаляемого последний элемент, а потом чиним кучу в обе стороны:
        // он может оказаться и меньше родителя, и больше детей.
        swap(index, size - 1);
        heap[size - 1] = null;
        size--;
        if (index < size) {
            siftDown(index);
            siftUp(index);
        }
        return true;
    }

    /** Сколько элементов в очереди, O(1). */
    public int size() {
        return size;
    }

    /** Пуста ли очередь, O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Проверка для тестов: каждый элемент не меньше своего родителя. */
    boolean isHeap() {
        for (int i = 1; i < size; i++) {
            if (compare(get(i), get((i - 1) / 2)) < 0) {
                return false;
            }
        }
        return true;
    }

    private int indexOf(T item) {
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (item.equals(heap[i])) {
                return i;
            }
        }
        return -1;
    }

    /** Пока элемент меньше родителя, меняем их местами. */
    private void siftUp(int index) {
        int current = index;
        while (current > 0) {
            int parent = (current - 1) / 2;
            if (compare(get(current), get(parent)) >= 0) {
                return;
            }
            swap(current, parent);
            current = parent;
        }
    }

    /** Пока элемент больше одного из детей, меняем его с меньшим ребёнком. */
    private void siftDown(int index) {
        int current = index;
        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int smallest = current;
            if (left < size && compare(get(left), get(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && compare(get(right), get(smallest)) < 0) {
                smallest = right;
            }
            if (smallest == current) {
                return;
            }
            swap(current, smallest);
            current = smallest;
        }
    }

    private void swap(int first, int second) {
        Object temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }

    private void checkItem(T item) {
        Objects.requireNonNull(item, "Элементы null не поддерживаются");
        if (comparator == null && !(item instanceof Comparable)) {
            throw new ClassCastException(item.getClass().getName() + " не реализует Comparable");
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
    }

    @SuppressWarnings("unchecked")
    private T get(int index) {
        return (T) heap[index];
    }
}
