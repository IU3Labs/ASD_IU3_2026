package ru.bmstu.lab2.priorityqueue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на собственной двоичной куче: задания 1A–1D.
 * Порядок задаётся компаратором либо естественным сравнением элементов.
 * null не допускается; класс не предназначен для одновременного доступа из разных потоков.
 *
 * @param <T> тип элементов; без компаратора должен реализовывать Comparable
 */
public class PriorityQueueCustom<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private final Comparator<? super T> comparator;
    private Object[] elements;
    private int size;

    /** Создаёт пустую min-heap с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Создаёт пустую кучу с заданным порядком.
     *
     * @param comparator порядок приоритетов; null означает естественный порядок
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Строит min-heap из копии массива за O(n), не изменяя исходный массив.
     *
     * @param array исходные элементы
     * @throws NullPointerException если массив или его элемент равен null
     * @throws ClassCastException если элементы не поддерживают естественный порядок
     */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /**
     * Строит кучу снизу вверх за O(n) с заданным порядком элементов.
     *
     * @param array исходные элементы
     * @param comparator порядок приоритетов; null означает естественный порядок
     * @throws NullPointerException если массив или его элемент равен null
     * @throws ClassCastException если без компаратора элементы не поддерживают сравнение
     */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "Массив не должен быть null");
        this.comparator = comparator;
        elements = new Object[Math.max(DEFAULT_CAPACITY, array.length)];
        for (T item : array) {
            validateItem(item);
            elements[size++] = item;
        }
        // Листья уже являются кучами. Обрабатываем только узлы, у которых есть дети.
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /**
     * Добавляет элемент с восстановлением свойства кучи.
     * O(log n) амортизированно; отдельное расширение массива требует O(n).
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если item равен null
     * @throws ClassCastException если элемент нельзя сравнить с элементами очереди
     */
    public void add(T item) {
        validateItem(item);
        ensureCapacity();
        elements[size] = item;
        size++;
        siftUp(size - 1);
    }

    /**
     * Возвращает элемент наивысшего приоритета без удаления за O(1).
     *
     * @return корень кучи
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        checkNotEmpty();
        return elementAt(0);
    }

    /**
     * Извлекает элемент наивысшего приоритета за O(log n).
     *
     * @return удалённый корень; в min-heap это минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        checkNotEmpty();
        T result = elementAt(0);
        removeAt(0);
        return result;
    }

    /**
     * Удаляет одно вхождение по equals за O(n).
     * Первым считается совпадение при просмотре внутреннего массива слева направо.
     *
     * @param item искомое значение; для null возвращается false
     * @return true, если элемент найден и удалён
     */
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        for (int index = 0; index < size; index++) {
            if (item.equals(elements[index])) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает число элементов за O(1).
     *
     * @return текущий размер
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет отсутствие элементов за O(1).
     *
     * @return true, если очередь пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void validateItem(T item) {
        Objects.requireNonNull(item, "Элемент не должен быть null");
        if (comparator == null && !(item instanceof Comparable<?>)) {
            throw new ClassCastException("Без компаратора требуется Comparable");
        }
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
    }

    private void ensureCapacity() {
        if (size < elements.length) {
            return;
        }
        Object[] expanded = new Object[Math.multiplyExact(elements.length, GROWTH_FACTOR)];
        System.arraycopy(elements, 0, expanded, 0, size);
        elements = expanded;
    }

    private void removeAt(int index) {
        size--;
        if (index == size) {
            elements[size] = null;
            return;
        }
        elements[index] = elements[size];
        // Освобождаем ссылку, чтобы извлечённый объект мог быть собран сборщиком мусора.
        elements[size] = null;
        // После замены внутреннего узла нарушение может находиться с любой стороны.
        if (index > 0 && compare(elementAt(index), elementAt((index - 1) / 2)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(elementAt(index), elementAt(parent)) >= 0) {
                return;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        // У узлов с индексом >= size / 2 детей нет; это также исключает переполнение 2*i+1.
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (compare(elementAt(index), elementAt(child)) <= 0) {
                return;
            }
            swap(index, child);
            index = child;
        }
    }

    private void swap(int first, int second) {
        Object temporary = elements[first];
        elements[first] = elements[second];
        elements[second] = temporary;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        // Только элементы типа T попадают в закрытый массив через конструкторы и add.
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        }
        return ((Comparable<? super T>) first).compareTo(second);
    }
}
