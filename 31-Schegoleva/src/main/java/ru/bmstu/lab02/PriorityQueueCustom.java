package ru.bmstu.lab02;

import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом (min-heap) на динамическом массиве.
 * Элементы извлекаются в порядке неубывания (естественный порядок Comparable).
 *
 * Индексация: дети узла i — 2*i + 1 и 2*i + 2, родитель — (i - 1) / 2.
 *
 * Асимптотика:
 *  add     — O(log n) (амортизированно, с учётом расширения массива)
 *  poll    — O(log n)
 *  peek    — O(1)
 *  remove  — O(n) (линейный поиск по значению + O(log n) на восстановление кучи)
 *  size    — O(1)
 *  isEmpty — O(1)
 */
public class PriorityQueueCustom<T extends Comparable<? super T>> {

    private static final int DEFAULT_CAPACITY = 16;

    private Object[] heap;
    private int size;

    /** Создаёт пустую очередь с начальной ёмкостью по умолчанию. */
    public PriorityQueueCustom() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создаёт пустую очередь с заданной начальной ёмкостью.
     *
     * @throws IllegalArgumentException если ёмкость меньше 1
     */
    public PriorityQueueCustom(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be positive: " + initialCapacity);
        }
        this.heap = new Object[initialCapacity];
        this.size = 0;
    }

    /** Добавляет элемент. O(log n). */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("Null elements are not allowed");
        }
        if (size == heap.length) {
            grow();
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает и возвращает минимальный элемент. O(log n).
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T result = elementAt(0);
        size--;
        T last = elementAt(size);
        heap[size] = null; // не удерживаем ссылку (избегаем утечки памяти)
        if (size > 0) {
            heap[0] = last;
            siftDown(0);
        }
        return result;
    }

    /**
     * Возвращает минимальный элемент без удаления. O(1).
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return elementAt(0);
    }

    /**
     * Удаляет первое найденное вхождение элемента по значению (сравнение через equals).
     * Поиск линейный, O(n); восстановление свойства кучи — O(log n). Итого O(n).
     *
     * @return true, если элемент найден и удалён
     * @throws NullPointerException если item == null
     */
    public boolean remove(T item) {
        if (item == null) {
            throw new NullPointerException("Null elements are not allowed");
        }
        int index = indexOf(item);
        if (index < 0) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /** Количество элементов в очереди. O(1). */
    public int size() {
        return size;
    }

    /** Возвращает true, если очередь пуста. O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    // ------------------------------------------------------------------
    // Внутренние методы
    // ------------------------------------------------------------------

    /** Просеивание вверх: поднимаем элемент с индексом index, пока он меньше родителя. */
    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentItem = elementAt(parent);
            if (item.compareTo(parentItem) >= 0) {
                break;
            }
            heap[index] = parentItem;
            index = parent;
        }
        heap[index] = item;
    }

    /** Просеивание вниз: опускаем элемент с индексом index, пока он больше наименьшего из детей. */
    private void siftDown(int index) {
        T item = elementAt(index);
        int half = size / 2; // узлы с индексом >= half — листья
        while (index < half) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && elementAt(right).compareTo(elementAt(child)) < 0) {
                child = right;
            }
            T childItem = elementAt(child);
            if (item.compareTo(childItem) <= 0) {
                break;
            }
            heap[index] = childItem;
            index = child;
        }
        heap[index] = item;
    }

    /** Индекс первого элемента, равного item, в порядке расположения в массиве, либо -1. */
    private int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (item.equals(heap[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Удаляет элемент по индексу: на его место ставится последний элемент кучи, после чего свойство
     * кучи восстанавливается. Перенесённый элемент может оказаться и больше детей (тогда он опускается),
     * и меньше родителя (тогда он поднимается), поэтому вызываются оба просеивания; одно из них ничего не делает.
     */
    private void removeAt(int index) {
        size--;
        T moved = elementAt(size);
        heap[size] = null;
        if (index == size) {
            return; // удалён последний элемент, перестраивать нечего
        }
        heap[index] = moved;
        siftDown(index);
        siftUp(index);
    }

    /** Увеличивает массив в 2 раза. */
    private void grow() {
        Object[] newHeap = new Object[heap.length * 2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) heap[index];
    }
}
