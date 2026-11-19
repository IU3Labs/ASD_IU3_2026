package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на основе двоичной кучи в динамическом массиве.
 *
 * <p>Во главе очереди находится наименьший элемент относительно {@link Comparator}; если компаратор не задан,
 * используется естественный порядок. Чтобы получить max-heap, достаточно передать
 * {@code Comparator.reverseOrder()}.
 *
 * <p>Индексация: потомки узла {@code i} — {@code 2*i + 1} и {@code 2*i + 2}, родитель — {@code (i - 1) / 2}.
 * Элементы {@code null} не поддерживаются.
 *
 * @param <T> тип элементов
 */
public class PriorityQueueCustom<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Создаёт пустую очередь с заданным порядком.
     *
     * @param comparator порядок элементов; {@code null} означает естественный порядок
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Строит кучу из массива за O(n); исходный массив не изменяется.
     *
     * @param array исходные элементы
     */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /**
     * Строит кучу из массива за O(n) методом Флойда; исходный массив не изменяется.
     *
     * @param array      исходные элементы
     * @param comparator порядок элементов; {@code null} означает естественный порядок
     */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "array");
        this.comparator = comparator;
        this.heap = new Object[Math.max(DEFAULT_CAPACITY, array.length)];
        for (T item : array) {
            checkItem(item);
            heap[size++] = item;
        }
        // Листья (индексы >= size / 2) уже являются кучами из одного элемента, поэтому идём от последнего
        // внутреннего узла к корню: суммарная работа сходится к O(n), а не O(n log n).
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * Добавляет элемент за O(log n) (амортизированно, с учётом расширения массива).
     *
     * @param item добавляемый элемент, не {@code null}
     */
    public void add(T item) {
        checkItem(item);
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * GROWTH_FACTOR);
        }
        heap[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает и возвращает минимальный элемент за O(log n).
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (size == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T result = at(0);
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
     * Возвращает минимальный элемент без удаления за O(1).
     *
     * @return минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return at(0);
    }

    /**
     * Удаляет первое (в порядке расположения в массиве) вхождение элемента за O(n).
     *
     * @param item удаляемый элемент
     * @return {@code true}, если элемент был найден и удалён
     */
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        int index = indexOf(item);
        if (index < 0) {
            return false;
        }
        size--;
        Object last = heap[size];
        heap[size] = null;
        if (index == size) {
            return true;
        }
        heap[index] = last;
        // Последний элемент мог оказаться как меньше родителя нового места, так и больше его потомков:
        // сначала просеиваем вниз, и если он остался на месте — вверх.
        siftDown(index);
        if (heap[index] == last) {
            siftUp(index);
        }
        return true;
    }

    /** Возвращает число элементов за O(1). */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли очередь, за O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Проверяет инвариант кучи (только для тестов): ни один элемент не меньше своего родителя. */
    boolean isHeap() {
        for (int i = 1; i < size; i++) {
            if (compare(at(i), at((i - 1) / 2)) < 0) {
                return false;
            }
        }
        return true;
    }

    private int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (item.equals(heap[i])) {
                return i;
            }
        }
        return -1;
    }

    /** Поднимает элемент в позиции {@code index}, пока он меньше родителя. */
    private void siftUp(int index) {
        Object item = heap[index];
        int current = index;
        while (current > 0) {
            int parent = (current - 1) / 2;
            if (compare(cast(item), at(parent)) >= 0) {
                break;
            }
            heap[current] = heap[parent];
            current = parent;
        }
        heap[current] = item;
    }

    /** Опускает элемент в позиции {@code index}, пока он больше хотя бы одного из потомков. */
    private void siftDown(int index) {
        Object item = heap[index];
        int current = index;
        int firstLeaf = size / 2;
        while (current < firstLeaf) {
            int child = 2 * current + 1;
            int right = child + 1;
            if (right < size && compare(at(right), at(child)) < 0) {
                child = right;
            }
            if (compare(cast(item), at(child)) <= 0) {
                break;
            }
            heap[current] = heap[child];
            current = child;
        }
        heap[current] = item;
    }

    private void checkItem(T item) {
        Objects.requireNonNull(item, "Элементы null не поддерживаются");
        // Без компаратора ошибка типа проявилась бы только при втором add; сообщаем о ней сразу.
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
    private T at(int index) {
        return (T) heap[index];
    }

    @SuppressWarnings("unchecked")
    private T cast(Object item) {
        return (T) item;
    }
}
