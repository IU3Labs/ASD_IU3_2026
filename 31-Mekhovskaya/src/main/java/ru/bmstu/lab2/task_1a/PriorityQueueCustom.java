package ru.bmstu.lab2.task_1a;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Задача 1A. Очередь с приоритетом на двоичной куче (min-heap).
 *
 * <p>Элементы хранятся в динамическом массиве {@code heap[0..size-1]}, который рассматривается как
 * полное двоичное дерево: у узла {@code i} дети — {@code 2*i + 1} и {@code 2*i + 2}, родитель —
 * {@code (i - 1) / 2}. Инвариант кучи: каждый узел не больше своих детей, поэтому минимум всегда
 * лежит в корне {@code heap[0]}. Высота такого дерева — {@code floor(log2 n)}, отсюда оценка
 * {@code O(log n)} для {@link #add} и {@link #poll}.
 *
 * <p>По умолчанию элементы сравниваются в естественном порядке ({@link Comparable}); можно передать
 * свой {@link Comparator} — тогда «минимальным» считается элемент, меньший по компаратору.
 * Элементы извлекаются в порядке неубывания. {@code null} хранить нельзя.
 *
 * @param <T> тип элементов
 */
public class PriorityQueueCustom<T> {

    /** Начальная ёмкость массива: небольшая, чтобы пустая очередь занимала мало памяти. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Во сколько раз растёт массив при заполнении: удвоение даёт амортизированное O(1) на вставку. */
    private static final int GROWTH_FACTOR = 2;

    private final Comparator<? super T> comparator;
    private Object[] heap;
    private int size;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this(null);
    }

    /**
     * Создаёт пустую очередь с заданным порядком элементов.
     *
     * @param comparator компаратор; {@code null} — естественный порядок ({@link Comparable})
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Добавляет элемент в очередь. Время: {@code O(log n)} (амортизированно, с учётом расширения массива).
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если {@code item == null}
     * @throws ClassCastException если компаратор не задан, а элемент не реализует {@link Comparable}
     */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("PriorityQueueCustom does not permit null elements");
        }
        if (comparator == null && !(item instanceof Comparable)) {
            // проверяем сразу, иначе ошибка проявилась бы только при добавлении второго элемента
            throw new ClassCastException(item.getClass().getName() + " is not Comparable");
        }
        if (size == heap.length) {
            grow();
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает и возвращает минимальный элемент. Время: {@code O(log n)}.
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T min = peek();
        size--;
        T last = elementAt(size);
        heap[size] = null; // не держим ссылку на извлечённый объект, чтобы его мог собрать GC
        if (size > 0) {
            heap[0] = last;
            siftDown(0);
        }
        return min;
    }

    /**
     * Возвращает минимальный элемент, не удаляя его. Время: {@code O(1)}.
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return elementAt(0);
    }

    /**
     * Возвращает количество элементов. Время: {@code O(1)}.
     *
     * @return число элементов в очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь. Время: {@code O(1)}.
     *
     * @return {@code true}, если элементов нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Просеивание вверх: элемент из позиции {@code index} поднимается, пока он меньше родителя.
     * Вместо обменов на каждом шаге родитель сдвигается вниз в «дырку», а сам элемент
     * записывается один раз в конце — это вдвое уменьшает число записей в массив.
     */
    private void siftUp(int index) {
        T item = elementAt(index);
        int current = index;
        while (current > 0) {
            int parent = (current - 1) / 2;
            T parentItem = elementAt(parent);
            if (compare(item, parentItem) >= 0) {
                break;
            }
            heap[current] = parentItem;
            current = parent;
        }
        heap[current] = item;
    }

    /**
     * Просеивание вниз: элемент из позиции {@code index} опускается, меняясь с меньшим из детей,
     * пока не станет не больше обоих детей. Узлы с индексом {@code >= size / 2} — листья.
     */
    private void siftDown(int index) {
        T item = elementAt(index);
        int current = index;
        int firstLeaf = size / 2;
        while (current < firstLeaf) {
            int child = 2 * current + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            T childItem = elementAt(child);
            if (compare(item, childItem) <= 0) {
                break;
            }
            heap[current] = childItem;
            current = child;
        }
        heap[current] = item;
    }

    /** Увеличивает ёмкость массива в {@link #GROWTH_FACTOR} раз: {@code O(n)}, но редко. */
    private void grow() {
        heap = Arrays.copyOf(heap, heap.length * GROWTH_FACTOR);
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
        return (T) heap[index];
    }
}
