package ru.bmstu.lab2.task_1;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Задачи 1A–1D. Очередь с приоритетом на основе двоичной кучи и динамического массива.
 * По умолчанию хранит минимум в корне; компаратор позволяет изменить порядок.
 *
 * @param <T> тип элементов очереди
 */
public final class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this.heap = new Object[INITIAL_CAPACITY];
        this.comparator = null;
    }

    /**
     * Создаёт очередь с заданным порядком элементов.
     *
     * @param comparator компаратор или null для естественного порядка
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.heap = new Object[INITIAL_CAPACITY];
        this.comparator = comparator;
    }

    /**
     * Строит минимальную кучу из массива за O(n), не изменяя исходный массив.
     *
     * @param array исходные элементы
     */
    public PriorityQueueCustom(T[] array) {
        Objects.requireNonNull(array, "Массив не должен быть null");
        this.comparator = null;
        this.size = array.length;
        this.heap = new Object[Math.max(INITIAL_CAPACITY, array.length)];
        for (int i = 0; i < array.length; i++) {
            heap[i] = Objects.requireNonNull(array[i], "Элемент не должен быть null");
        }
        // Просеивание всех внутренних узлов снизу вверх даёт суммарную сложность O(n).
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * Добавляет элемент в очередь.
     *
     * @param item добавляемый элемент, не null
     */
    public void add(T item) {
        Objects.requireNonNull(item, "Элемент не должен быть null");
        ensureCapacity();
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает элемент с наибольшим приоритетом.
     *
     * @return минимальный элемент при естественном порядке
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T result = elementAt(0);
        size--;
        Object last = heap[size];
        heap[size] = null;
        if (size > 0) {
            heap[0] = last;
            siftDown(0);
        }
        return result;
    }

    /**
     * Возвращает элемент с наибольшим приоритетом без удаления.
     *
     * @return элемент в корне либо null, если очередь пуста
     */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /**
     * Удаляет первое найденное в куче вхождение указанного значения за O(n).
     *
     * @param item удаляемый элемент
     * @return true, если элемент найден и удалён
     */
    public boolean remove(T item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(heap[i], item)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает число элементов очереди.
     *
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь.
     *
     * @return true, если элементов нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size < heap.length) {
            return;
        }
        Object[] bigger = new Object[heap.length * 2];
        System.arraycopy(heap, 0, bigger, 0, size);
        heap = bigger;
    }

    private void removeAt(int index) {
        size--;
        Object last = heap[size];
        heap[size] = null;
        if (index == size) {
            return;
        }
        heap[index] = last;
        // Замещающий элемент может оказаться меньше родителя или больше детей.
        if (index > 0 && compare(elementAt(index), elementAt((index - 1) / 2)) < 0) {
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
            if (compare(item, parentItem) >= 0) {
                break;
            }
            heap[index] = parentItem;
            index = parent;
        }
        heap[index] = item;
    }

    private void siftDown(int index) {
        T item = elementAt(index);
        int firstLeaf = size / 2;
        while (index < firstLeaf) {
            int child = index * 2 + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (compare(item, elementAt(child)) <= 0) {
                break;
            }
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = item;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) heap[index];
    }

    @SuppressWarnings("unchecked")
    private int compare(T left, T right) {
        if (comparator != null) {
            return comparator.compare(left, right);
        }
        return ((Comparable<? super T>) left).compareTo(right);
    }
}
