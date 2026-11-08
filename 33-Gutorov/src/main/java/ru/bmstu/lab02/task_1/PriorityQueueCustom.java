package ru.bmstu.lab02.task_1;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на двоичной мин-куче.
 *
 * @param <T> тип элементов с естественным порядком сравнения
 */
public class PriorityQueueCustom<T extends Comparable<? super T>> {

    private static final int INITIAL_CAPACITY = 8;

    private Object[] elements = new Object[INITIAL_CAPACITY];
    private int size;

    /** Добавляет элемент в очередь. */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }

        int index = size;
        elements[index] = item;
        size++;
        siftUp(index);
    }

    /** Извлекает минимум; для пустой очереди бросает NoSuchElementException. */
    public T poll() {
        T minimum = peek();
        int lastIndex = --size;
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;

        if (size > 0) {
            siftDown(0);
        }

        return minimum;
    }

    /** Возвращает минимум без удаления; для пустой очереди бросает исключение. */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return elementAt(0);
    }

    /** Возвращает количество элементов. */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли очередь. */
    public boolean isEmpty() {
        return size == 0;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (elementAt(parent).compareTo(elementAt(index)) <= 0) {
                return;
            }
            swap(parent, index);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int smallerChild = 2 * index + 1;
            int rightChild = smallerChild + 1;

            if (rightChild < size && elementAt(rightChild).compareTo(elementAt(smallerChild)) < 0) {
                smallerChild = rightChild;
            }
            if (elementAt(index).compareTo(elementAt(smallerChild)) <= 0) {
                return;
            }
            swap(index, smallerChild);
            index = smallerChild;
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
}