/*Задачи 1A–1D. Очередь с приоритетом на двоичной куче
1A: add, poll, peek, size, isEmpty; siftUp / siftDown; автоматическое расширение массива.
1B: конструктор с Comparator<T> (без него — естественный порядок), работает как min- и max-куча.
1C: boolean remove(T item) — удаление первого вхождения по значению за O(n).
1D: конструктор PriorityQueueCustom(T[] array) — построение кучи за O(n). */

package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на двоичной куче, хранящейся в динамическом массиве.
 * Дети узла {@code i} — {@code 2*i + 1} и {@code 2*i + 2}, родитель — {@code (i - 1) / 2}.
 * На вершине всегда «наименьший» по компаратору элемент: при естественном порядке это min-куча,
 * при обратном компараторе — max-куча. Элементы {@code null} не допускаются.
 *
 * @param <T> тип элементов
 */
public class PriorityQueueCustom<T> {
    /** Начальная ёмкость массива, если размер заранее неизвестен. */
    private static final int DEFAULT_CAPACITY = 16;
    /** Во сколько раз увеличивается массив при заполнении. */
    private static final int GROW_FACTOR = 2;

    /** Массив кучи; занята часть [0, size). */
    private Object[] heap;
    /** Количество элементов в очереди. */
    private int size;
    /** Порядок элементов: на вершине минимальный по этому компаратору. */
    private final Comparator<? super T> comparator;

    /**
     * Создаёт пустую очередь с естественным порядком элементов (min-куча).
     * Элементы должны реализовывать {@link Comparable}.
     */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Создаёт пустую очередь с заданным порядком.
     *
     * @param comparator порядок элементов; {@code null} — естественный порядок
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator == null ? naturalOrder() : comparator;
    }

    /**
     * Строит кучу из массива за O(n) с естественным порядком элементов.
     * Исходный массив не изменяется.
     *
     * @param array исходные элементы
     * @throws NullPointerException если массив или один из его элементов равен {@code null}
     */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /**
     * Строит кучу из массива за O(n): просеивание вниз от последнего внутреннего узла к корню.
     * Исходный массив не изменяется.
     *
     * @param array      исходные элементы
     * @param comparator порядок элементов; {@code null} — естественный порядок
     * @throws NullPointerException если массив или один из его элементов равен {@code null}
     */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "array");
        this.comparator = comparator == null ? naturalOrder() : comparator;
        this.heap = Arrays.copyOf(array, Math.max(array.length, DEFAULT_CAPACITY), Object[].class);
        this.size = array.length;
        for (int i = 0; i < size; i++) {
            Objects.requireNonNull(heap[i], "элемент массива");
        }
        // Листья (индексы >= size / 2) уже являются кучами из одного элемента
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * Добавляет элемент в очередь, O(log n) (амортизированно с учётом расширения массива).
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если элемент равен {@code null}
     */
    public void add(T item) {
        Objects.requireNonNull(item, "item");
        if (size == heap.length) {
            grow();
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает и возвращает минимальный элемент, O(log n).
     *
     * @return минимальный по компаратору элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T top = elementAt(0);
        removeAt(0);
        return top;
    }

    /**
     * Возвращает минимальный элемент без удаления, O(1).
     *
     * @return минимальный по компаратору элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return elementAt(0);
    }

    /**
     * Удаляет первое (в порядке массива) вхождение элемента, равного {@code item} по {@code equals}, O(n).
     *
     * @param item удаляемый элемент
     * @return {@code true}, если элемент был найден и удалён
     */
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        // Линейный поиск O(n), затем восстановление кучи O(log n)
        for (int i = 0; i < size; i++) {
            if (item.equals(heap[i])) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает количество элементов, O(1).
     *
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь, O(1).
     *
     * @return {@code true}, если элементов нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаляет элемент по индексу: на его место ставится последний элемент,
     * который затем просеивается вниз или вверх.
     */
    private void removeAt(int index) {
        size--;
        T last = elementAt(size);
        heap[size] = null; // не держим ссылку, чтобы не мешать сборщику мусора
        if (index == size) {
            return; // удалили последний элемент — восстанавливать нечего
        }
        heap[index] = last;
        siftDown(index);
        // Если элемент не опустился, возможно, он меньше нового родителя (удаление из середины)
        if (heap[index] == last) {
            siftUp(index);
        }
    }

    /**
     * Просеивание вверх: поднимает элемент, пока он меньше родителя.
     */
    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentItem = elementAt(parent);
            if (comparator.compare(item, parentItem) >= 0) {
                break;
            }
            heap[index] = parentItem; // сдвигаем родителя вниз вместо полного обмена
            index = parent;
        }
        heap[index] = item;
    }

    /**
     * Просеивание вниз: опускает элемент, пока он больше меньшего из детей.
     */
    private void siftDown(int index) {
        T item = elementAt(index);
        int half = size / 2; // у узлов с индексом >= half нет детей
        while (index < half) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && comparator.compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (comparator.compare(item, elementAt(child)) <= 0) {
                break;
            }
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = item;
    }

    /**
     * Увеличивает массив в {@link #GROW_FACTOR} раз.
     */
    private void grow() {
        int newCapacity = Math.max(heap.length * GROW_FACTOR, DEFAULT_CAPACITY);
        heap = Arrays.copyOf(heap, newCapacity);
    }

    /**
     * Возвращает элемент массива с приведением к типу T.
     */
    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) heap[index];
    }

    /**
     * Компаратор естественного порядка: элементы сравниваются через {@link Comparable}.
     */
    @SuppressWarnings("unchecked")
    private static <T> Comparator<T> naturalOrder() {
        return (a, b) -> ((Comparable<? super T>) a).compareTo(b);
    }
}
