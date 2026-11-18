package ru.bmstu.lab02.task_01a;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с минимальным приоритетом на двоичной куче и динамическом массиве.
 *
 * <p>Естественный порядок требует взаимно сравнимых элементов. Готовые коллекции не используются.
 * Защищённые методы позволяют задачам 1B–1D расширять реализацию без копирования алгоритмов.
 *
 * @param <T> тип элемента
 */
public class PriorityQueueCustom<T> {
    private static final int DEFAULT_CAPACITY = 16;

    private Object[] elements;
    private int size;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public PriorityQueueCustom() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Добавляет элемент за O(log n) амортизированно; расширение массива отдельно занимает O(n).
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если элемент равен null
     * @throws ClassCastException если элементы нельзя сравнить в выбранном порядке
     */
    public void add(T item) {
        Objects.requireNonNull(item, "Элемент не должен быть null");
        ensureCapacity();
        elements[size] = item;
        siftUp(size);
        size++;
    }

    /**
     * Извлекает минимальный элемент за O(log n).
     *
     * @return извлечённый элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return removeAt(0);
    }

    /**
     * Возвращает минимальный элемент без удаления за O(1).
     *
     * @return минимальный элемент либо null, если очередь пуста
     */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /**
     * Возвращает число элементов за O(1).
     *
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет пустоту за O(1).
     *
     * @return true, если элементов нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Сравнивает элементы в естественном порядке; задача 1B заменяет стратегию сравнения.
     *
     * @param first первый элемент
     * @param second второй элемент
     * @return знак результата сравнения
     */
    @SuppressWarnings("unchecked")
    protected int compare(T first, T second) {
        return ((Comparable<? super T>) first).compareTo(second);
    }

    /**
     * Читает элемент массива за O(1). Индекс должен относиться к занятой части массива.
     *
     * @param index индекс элемента
     * @return элемент по индексу
     */
    @SuppressWarnings("unchecked")
    protected final T elementAt(int index) {
        return (T) elements[index];
    }

    /**
     * Удаляет элемент по известному занятому индексу и восстанавливает кучу за O(log n).
     *
     * @param index индекс удаляемого элемента
     * @return удалённый элемент
     */
    protected final T removeAt(int index) {
        T removed = elementAt(index);
        int lastIndex = --size;
        elements[index] = elements[lastIndex];
        elements[lastIndex] = null;
        if (index < size) {
            // Последний элемент может нарушить порядок как с родителем, так и с детьми.
            if (index > 0 && compare(elementAt(index), elementAt((index - 1) / 2)) < 0) {
                siftUp(index);
            } else {
                siftDown(index);
            }
        }
        return removed;
    }

    /**
     * Копирует массив и строит кучу снизу вверх за O(n), сохраняя входной массив.
     *
     * @param array исходные элементы
     * @throws NullPointerException если массив или один из элементов равен null
     */
    protected final void buildHeap(T[] array) {
        Objects.requireNonNull(array, "Массив не должен быть null");
        for (T item : array) {
            Objects.requireNonNull(item, "Элемент не должен быть null");
        }
        elements = new Object[Math.max(DEFAULT_CAPACITY, array.length)];
        System.arraycopy(array, 0, elements, 0, array.length);
        size = array.length;
        // Листья уже являются кучами; обрабатываем только внутренние узлы.
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] expanded = new Object[elements.length * 2];
            System.arraycopy(elements, 0, expanded, 0, size);
            elements = expanded;
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
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = 2 * index + 2;
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
}
