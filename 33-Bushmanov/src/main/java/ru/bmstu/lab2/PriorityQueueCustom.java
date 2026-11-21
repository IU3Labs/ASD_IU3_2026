package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на двоичной куче. Корень хранит наименьший элемент по выбранному порядку.
 *
 * @param <T> тип элементов
 */
public class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int size;
    private final Comparator<? super T> comparator;

    /** Создаёт очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
        this(null);
    }

    /**
     * Создаёт очередь с заданным порядком.
     *
     * @param comparator правило сравнения; null означает естественный порядок
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Добавляет элемент за O(log n) амортизированно; расширение массива занимает O(n).
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если item равен null
     */
    public void add(T item) {
        Objects.requireNonNull(item, "Элемент не должен быть null");
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает корень кучи за O(log n).
     *
     * @return первый элемент по выбранному порядку
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T result = peek();
        size--;
        elements[0] = elements[size];
        elements[size] = null; // Не удерживаем ссылку на удалённый элемент.
        if (size > 0) {
            siftDown(0);
        }
        return result;
    }

    /**
     * Возвращает корень без удаления за O(1).
     *
     * @return первый элемент по выбранному порядку
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return elementAt(0);
    }

    /**
     * Возвращает размер очереди за O(1).
     *
     * @return количество элементов
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет пустоту очереди за O(1).
     *
     * @return true, если очередь пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(elementAt(index), elementAt(parent)) >= 0) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            // Меняемся с меньшим ребёнком, иначе свойство кучи не восстановится.
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            if (compare(elementAt(index), elementAt(child)) <= 0) {
                break;
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
