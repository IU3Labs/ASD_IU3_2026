package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом на основе двоичной кучи в динамическом массиве.
 * <p>
 * Индексация: дети узла i — 2*i + 1 и 2*i + 2, родитель — (i - 1) / 2.
 * По умолчанию это min-heap (естественный порядок); с компаратором порядок задаётся им,
 * поэтому {@code Comparator.reverseOrder()} превращает очередь в max-heap (задача 1B).
 * <p>
 * Асимптотика: add, poll — O(log n); peek, size, isEmpty — O(1);
 * remove(T) — O(n); конструктор из массива — O(n).
 * Элементы {@code null} не допускаются.
 */
public class PriorityQueueCustom<T> {

    private static final int DEFAULT_CAPACITY = 16;

    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator; // null => естественный порядок

    /** Min-heap с естественным порядком. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /** Очередь с заданным компаратором; {@code null} — естественный порядок. */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new Object[DEFAULT_CAPACITY];
    }

    /** Очередь с заданной начальной ёмкостью. */
    public PriorityQueueCustom(int initialCapacity, Comparator<? super T> comparator) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("initialCapacity < 1: " + initialCapacity);
        }
        this.comparator = comparator;
        this.heap = new Object[initialCapacity];
    }

    /** Построение кучи из массива за O(n) (задача 1D), естественный порядок. */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /** Построение кучи из массива за O(n) с компаратором. Исходный массив не изменяется. */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new NullPointerException("array is null");
        }
        this.comparator = comparator;
        this.heap = new Object[Math.max(DEFAULT_CAPACITY, array.length)];
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new NullPointerException("null element at index " + i);
            }
            heap[i] = array[i];
        }
        this.size = array.length;
        // просеивание вниз от последнего внутреннего узла (size/2 - 1) к корню
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /** Добавляет элемент, O(log n) амортизированно (с учётом расширения массива). */
    public void add(T item) {
        if (item == null) {
            throw new NullPointerException("null elements are not allowed");
        }
        if (size == heap.length) {
            grow();
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /** Извлекает минимальный элемент (по компаратору), O(log n). */
    @SuppressWarnings("unchecked")
    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("priority queue is empty");
        }
        T result = (T) heap[0];
        removeAt(0);
        return result;
    }

    /** Возвращает минимальный элемент без удаления, O(1). Для пустой очереди — NoSuchElementException. */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("priority queue is empty");
        }
        return (T) heap[0];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаляет первое (по порядку в массиве кучи) вхождение элемента по значению (equals), O(n).
     *
     * @return true, если элемент найден и удалён
     */
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (item.equals(heap[i])) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /** Очищает очередь. */
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    /** Удаление элемента по индексу и восстановление свойства кучи, O(log n). */
    private void removeAt(int index) {
        int last = --size;
        Object moved = heap[last];
        heap[last] = null;
        if (index == last) {
            return; // удалили последний лист
        }
        heap[index] = moved;
        siftDown(index);
        if (heap[index] == moved) { // элемент не спустился — возможно, его надо поднять
            siftUp(index);
        }
    }

    /** Просеивание вверх: поднимает элемент в позиции index, пока он меньше родителя. */
    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        Object item = heap[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            Object p = heap[parent];
            if (compare((T) item, (T) p) >= 0) {
                break;
            }
            heap[index] = p;
            index = parent;
        }
        heap[index] = item;
    }

    /** Просеивание вниз: опускает элемент в позиции index, пока он больше наименьшего из детей. */
    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        Object item = heap[index];
        int half = size >>> 1; // узлы с индексом >= half — листья
        while (index < half) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare((T) heap[right], (T) heap[child]) < 0) {
                child = right;
            }
            if (compare((T) item, (T) heap[child]) <= 0) {
                break;
            }
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = item;
    }

    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super T>) a).compareTo(b);
    }

    private void grow() {
        int newCapacity = heap.length < 64 ? heap.length * 2 : heap.length + (heap.length >> 1);
        Object[] newHeap = new Object[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    /** Проверка инварианта кучи (используется в тестах). */
    @SuppressWarnings("unchecked")
    boolean isValidHeap() {
        for (int i = 1; i < size; i++) {
            if (compare((T) heap[i], (T) heap[(i - 1) / 2]) < 0) {
                return false;
            }
        }
        return true;
    }

    /** Текущая ёмкость внутреннего массива (используется в тестах). */
    int capacity() {
        return heap.length;
    }

    // ================================================================ задачи группы 3
    // ---------------------------------------------------------------- 3A: Top-K


    /**
     * Возвращает k наибольших элементов списка по убыванию. Если k больше размера списка,
     * вернётся весь список (отсортированный по убыванию). Исходный список не изменяется.
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("k < 0: " + k);
        }
        if (k == 0 || input.isEmpty()) {
            return new ArrayList<>();
        }
        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>(Math.min(k, input.size()) + 1, null);
        for (T item : input) {
            if (heap.size() < k) {
                heap.add(item);                       // O(log k)
            } else if (item.compareTo(heap.peek()) > 0) {
                heap.poll();                          // выкидываем наименьший из k лучших
                heap.add(item);
            }
        }
        // в куче k наибольших; извлекаем по возрастанию и заполняем результат с конца
        int n = heap.size();
        List<T> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            result.add(null);
        }
        for (int i = n - 1; i >= 0; i--) {
            result.set(i, heap.poll());
        }
        return result;
    }

    // ---------------------------------------------------------------- 3D: скользящий минимум


    private record Item(int value, int index) {
    }

    private static final Comparator<Item> SLIDING_BY_VALUE =
            Comparator.comparingInt(Item::value).thenComparingInt(Item::index);

    /**
     * Результат имеет длину n - k + 1; элемент j — минимум arr[j..j+k-1].
     * Устаревшие элементы (вышедшие за левую границу окна) удаляются «лениво»: только когда
     * оказываются на вершине кучи. Каждый элемент добавляется и извлекается не более одного раза.
     */
    public static int[] slidingMin(int[] arr, int k) {
        int n = arr.length;
        if (k < 1) {
            throw new IllegalArgumentException("k < 1: " + k);
        }
        if (k > n) {
            throw new IllegalArgumentException("k > n: " + k + " > " + n);
        }
        int[] result = new int[n - k + 1];
        PriorityQueueCustom<Item> heap = new PriorityQueueCustom<>(SLIDING_BY_VALUE);
        for (int i = 0; i < n; i++) {
            heap.add(new Item(arr[i], i));
            if (i >= k - 1) {
                int left = i - k + 1;
                while (heap.peek().index() < left) {
                    heap.poll();
                }
                result[left] = heap.peek().value();
            }
        }
        return result;
    }
}
