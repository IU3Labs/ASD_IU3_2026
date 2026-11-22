package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом на двоичной куче, хранящейся в динамическом массиве (задачи 1A–1D ЛР2).
 *
 * <p>Корень кучи (индекс 0) — «наименьший» элемент по текущему порядку. Порядок задаётся компаратором:
 * без него используется естественный порядок ({@link Comparable}) и очередь работает как min-heap,
 * с {@code Comparator.reverseOrder()} — как max-heap (задача 1B).
 *
 * <p>Нумерация узлов: дети узла {@code i} — {@code 2 * i + 1} и {@code 2 * i + 2}, родитель — {@code (i - 1) / 2}.
 *
 * <p>Сложность операций:
 * <ul>
 *     <li>{@link #add} — O(log n) (амортизированно с учётом расширения массива);</li>
 *     <li>{@link #poll} — O(log n);</li>
 *     <li>{@link #peek}, {@link #size}, {@link #isEmpty} — O(1);</li>
 *     <li>{@link #remove} — O(n): линейный поиск значения + O(log n) на восстановление кучи;</li>
 *     <li>конструктор из массива — O(n) (построение кучи снизу вверх, задача 1D).</li>
 * </ul>
 *
 * <p>Значения {@code null} не допускаются: они не сравнимы и неотличимы от пустой ячейки массива.
 * Класс не потокобезопасен.
 *
 * @param <T> тип элементов
 */
public class PriorityQueueCustom<T> {

    /** Начальная ёмкость: небольшая, чтобы пустая очередь занимала мало памяти. */
    static final int DEFAULT_CAPACITY = 16;

    /**
     * Во сколько раз растёт массив при переполнении. Удвоение даёт амортизированную O(1) стоимость
     * копирования на одну вставку: суммарно копируется не более 2n элементов.
     */
    private static final int GROWTH_FACTOR = 2;

    private final Comparator<? super T> comparator;
    private Object[] heap;
    private int size;

    /** Пустая очередь с естественным порядком элементов (min-heap). */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Пустая очередь с заданным порядком (задача 1B).
     *
     * @param comparator порядок элементов; {@code null} — естественный порядок
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Очередь из элементов массива с естественным порядком, построенная за O(n) (задача 1D).
     *
     * @param array исходные элементы; массив копируется и не изменяется
     * @throws NullPointerException если массив или один из его элементов равен {@code null}
     * @throws ClassCastException   если элементы не реализуют {@link Comparable}
     */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /**
     * Очередь из элементов массива с заданным порядком, построенная за O(n) (задачи 1B + 1D).
     *
     * @param array      исходные элементы; массив копируется и не изменяется
     * @param comparator порядок элементов; {@code null} — естественный порядок
     * @throws NullPointerException если массив или один из его элементов равен {@code null}
     */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new NullPointerException("Source array must not be null");
        }
        this.comparator = comparator;
        this.heap = new Object[Math.max(DEFAULT_CAPACITY, array.length)];
        for (T item : array) {
            heap[size++] = checkElement(item);
        }
        heapify();
    }

    /**
     * Добавляет элемент в очередь. O(log n), амортизированно с учётом расширения массива.
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если {@code item == null}
     * @throws ClassCastException   если компаратор не задан, а элемент не реализует {@link Comparable}
     */
    public void add(T item) {
        checkElement(item);
        ensureCapacity();
        heap[size] = item;
        size++;
        siftUp(size - 1);
    }

    /**
     * Извлекает и удаляет минимальный (по текущему порядку) элемент. O(log n).
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T root = peek();
        removeAt(0);
        return root;
    }

    /**
     * Возвращает минимальный (по текущему порядку) элемент, не удаляя его. O(1).
     *
     * <p>На пустой очереди, как и {@link #poll()}, бросает исключение, а не возвращает {@code null}:
     * так ошибка обращения к пустой очереди не останется незамеченной.
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
     * Удаляет первое вхождение значения (по {@code equals}) с восстановлением свойства кучи (задача 1C).
     * O(n): поиск идёт линейно, потому что куча не упорядочивает элементы между разными ветвями.
     *
     * @param item удаляемое значение; {@code null} никогда не содержится в очереди
     * @return {@code true}, если элемент найден и удалён
     */
    public boolean remove(T item) {
        int index = indexOf(item);
        if (index < 0) {
            return false;
        }
        removeAt(index);
        return true;
    }

    /**
     * Количество элементов в очереди. O(1).
     *
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь. O(1).
     *
     * @return {@code true}, если элементов нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Текущая длина внутреннего массива; нужна тестам, проверяющим расширение. */
    int capacity() {
        return heap.length;
    }

    /**
     * Проверяет свойство кучи: каждый родитель не больше своих детей. O(n); используется в тестах.
     *
     * @return {@code true}, если свойство кучи выполняется для всех узлов
     */
    boolean isValidHeap() {
        for (int child = 1; child < size; child++) {
            if (compare(child, parentOf(child)) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Удаляет элемент по индексу: на его место ставится последний элемент кучи, который затем
     * «тонет» или «всплывает» — в зависимости от того, с какой стороны нарушено свойство кучи.
     */
    private void removeAt(int index) {
        int last = size - 1;
        heap[index] = heap[last];
        heap[last] = null; // не держим ссылку: иначе удалённый объект не соберёт GC
        size--;
        if (index == last) {
            return;
        }
        // Перенесённый элемент пришёл из другой ветви, поэтому он может оказаться и меньше
        // нового родителя (нужен siftUp), и больше детей (нужен siftDown). Сработает только одно из двух.
        int position = siftDown(index);
        if (position == index) {
            siftUp(index);
        }
    }

    /**
     * Поднимает элемент вверх, пока он меньше родителя. O(log n).
     *
     * @return итоговая позиция элемента
     */
    private int siftUp(int index) {
        int current = index;
        while (current > 0) {
            int parent = parentOf(current);
            if (compare(current, parent) >= 0) {
                break;
            }
            swap(current, parent);
            current = parent;
        }
        return current;
    }

    /**
     * Опускает элемент вниз, меняя его с меньшим из детей, пока он больше хотя бы одного ребёнка. O(log n).
     *
     * @return итоговая позиция элемента
     */
    private int siftDown(int index) {
        int current = index;
        while (true) {
            int smallest = smallestOf(current);
            if (smallest == current) {
                return current;
            }
            swap(current, smallest);
            current = smallest;
        }
    }

    /** Индекс наименьшего среди узла и его (существующих) детей. */
    private int smallestOf(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = left + 1;
        if (left < size && compare(left, smallest) < 0) {
            smallest = left;
        }
        if (right < size && compare(right, smallest) < 0) {
            smallest = right;
        }
        return smallest;
    }

    /**
     * Построение кучи за O(n) (алгоритм Флойда): siftDown от последнего внутреннего узла {@code size / 2 - 1}
     * к корню. Листья (вторая половина массива) уже являются кучами из одного элемента.
     * Сумма высот всех узлов полной кучи меньше n, поэтому общая работа — O(n), а не O(n log n).
     */
    private void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    private int indexOf(Object item) {
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

    private void ensureCapacity() {
        if (size < heap.length) {
            return;
        }
        Object[] extended = new Object[heap.length * GROWTH_FACTOR];
        System.arraycopy(heap, 0, extended, 0, size);
        heap = extended;
    }

    private static int parentOf(int index) {
        return (index - 1) / 2;
    }

    private void swap(int first, int second) {
        Object temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }

    /**
     * Сравнивает элементы по индексам. Без компаратора элементы приводятся к {@link Comparable};
     * если тип их не реализует — бросается {@link ClassCastException}, как в {@code java.util.PriorityQueue}.
     */
    @SuppressWarnings("unchecked")
    private int compare(int first, int second) {
        T a = elementAt(first);
        T b = elementAt(second);
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super T>) a).compareTo(b);
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) heap[index];
    }

    /**
     * Проверяет элемент до вставки: без этой проверки ошибка типа проявилась бы не сразу,
     * а только при первом сравнении (у единственного элемента сравнивать не с чем).
     */
    private T checkElement(T item) {
        if (item == null) {
            throw new NullPointerException("Priority queue does not accept null elements");
        }
        if (comparator == null && !(item instanceof Comparable)) {
            throw new ClassCastException(item.getClass().getName() + " is not Comparable and no comparator given");
        }
        return item;
    }
}
