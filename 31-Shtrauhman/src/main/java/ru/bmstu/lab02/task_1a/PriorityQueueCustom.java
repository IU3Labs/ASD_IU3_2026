package ru.bmstu.lab02.task_1a;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Задача 1A. Очередь с приоритетом на двоичной min-куче, хранящейся в динамическом массиве.
 *
 * <p>Для узла {@code i} дети лежат в {@code 2*i + 1} и {@code 2*i + 2}, родитель - в {@code (i - 1) / 2}.
 * Массив выбран вместо узлов-ссылок: куча всегда полное дерево, и индексная арифметика заменяет указатели.
 *
 * @param <T> тип элементов, сравниваемых по естественному порядку
 */
public class PriorityQueueCustom<T extends Comparable<? super T>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private Object[] heap;
    private int size;

    /**
     * Начальная ёмкость - 16 элементов.
     */
    public PriorityQueueCustom() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Ёмкость должна быть не меньше 1, иначе {@code IllegalArgumentException}.
     */
    public PriorityQueueCustom(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной: " + initialCapacity);
        }
        heap = new Object[initialCapacity];
    }

    /**
     * Время O(log n) амортизированно: при заполнении массив удваивается.
     *
     * @throws NullPointerException если элемент null - его нельзя сравнить
     */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("Очередь не хранит null");
        }
        ensureCapacity();
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает минимум за O(log n).
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T min = peek();
        size--;
        heap[0] = heap[size];
        // Без обнуления массив продолжал бы ссылаться на извлечённый объект и мешал бы сборщику мусора.
        heap[size] = null;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    /**
     * Минимум без удаления, O(1).
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return elementAt(0);
    }

    /**
     * Время O(1).
     */
    public int size() {
        return size;
    }

    /**
     * Время O(1).
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            // Рост в 2 раза даёт амортизированную O(1) на копирование: суммарно копий не больше 2n.
            heap = Arrays.copyOf(heap, heap.length * GROWTH_FACTOR);
        }
    }

    // Вместо обмена на каждом шаге элемент держится в переменной и записывается один раз: вдвое меньше записей.
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

    private void siftDown(int index) {
        T item = elementAt(index);
        int half = size / 2;
        // Узлы с индексом >= size / 2 - листья, у них нет детей.
        while (index < half) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && elementAt(right).compareTo(elementAt(child)) < 0) {
                child = right;
            }
            if (item.compareTo(elementAt(child)) <= 0) {
                break;
            }
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = item;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        // В массив попадают только объекты типа T через add, поэтому приведение безопасно.
        return (T) heap[index];
    }
}
