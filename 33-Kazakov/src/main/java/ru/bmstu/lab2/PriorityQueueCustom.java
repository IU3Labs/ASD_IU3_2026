package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Задачи 1A и 1C — очередь с приоритетом на двоичной мин-куче с удалением по значению
 *
 * @param <T> тип элементов с естественным порядком
 */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {

    private static final int INITIAL_CAPACITY = 16;
    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int size;

    /**
     * Добавляет элемент за амортизированное O(log n)
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если элемент равен null
     */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, Math.multiplyExact(elements.length, 2));
        }
        int index = size++;
        elements[index] = item;
        siftUp(index);
    }

    /**
     * Извлекает минимальный элемент за O(log n)
     *
     * @return извлечённый минимум
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T minimum = elementAt(0);
        removeAt(0);
        return minimum;
    }

    /**
     * Возвращает минимальный элемент без удаления за O(1)
     *
     * @return минимум или null, если очередь пуста
     */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /**
     * Удаляет первое равное по equals значение в порядке индексов кучи за O(n)
     *
     * @param item искомое значение
     * @return true, если элемент удалён
     */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (elements[index].equals(item)) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает количество элементов за O(1)
     *
     * @return размер очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь, за O(1)
     *
     * @return true, если очередь пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void removeAt(int index) {
        int last = --size;
        Object replacement = elements[last];
        elements[last] = null;
        if (index == last) {
            return;
        }

        elements[index] = replacement;
        if (index > 0 && elementAt(index).compareTo(elementAt(parentOf(index))) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = parentOf(index);
            if (elementAt(parent).compareTo(elementAt(index)) <= 0) {
                break;
            }
            swap(parent, index);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && elementAt(right).compareTo(elementAt(child)) < 0) {
                child = right;
            }
            if (elementAt(index).compareTo(elementAt(child)) <= 0) {
                break;
            }
            swap(index, child);
            index = child;
        }
    }

    private int parentOf(int index) {
        return (index - 1) / 2;
    }

    private void swap(int first, int second) {
        Object saved = elements[first];
        elements[first] = elements[second];
        elements[second] = saved;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }
}
