package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Минимальная двоичная куча с естественным порядком элементов.
 *
 * @param <T> тип элементов с естественным порядком
 */
public final class PriorityQueueCustom<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 8;
    private Object[] heap = new Object[INITIAL_CAPACITY];
    private int size;

    /**
     * Добавляет ненулевой элемент и восстанавливает порядок снизу вверх.
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если элемент равен {@code null}
     */
    public void add(T item) {
        Objects.requireNonNull(item);
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, Math.multiplyExact(size, 2));
        }
        heap[size] = item;
        siftUp(size++);
    }

    /**
     * Возвращает минимум. Для пустой очереди бросает NoSuchElementException.
     *
     * @return минимальный элемент очереди
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return at(0);
    }

    /**
     * Извлекает минимум. Для пустой очереди бросает NoSuchElementException.
     *
     * @return извлечённый минимальный элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T result = peek();
        heap[0] = heap[--size];
        heap[size] = null;
        if (size > 1) {
            siftDown(0);
        }
        return result;
    }

    /**
     * Возвращает число элементов.
     *
     * @return текущее число элементов
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли куча.
     *
     * @return {@code true}, если очередь пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private T at(int index) {
        return (T) heap[index];
    }

    private void siftUp(int index) {
        T item = at(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (item.compareTo(at(parent)) >= 0) {
                break;
            }
            heap[index] = heap[parent];
            index = parent;
        }
        heap[index] = item;
    }

    private void siftDown(int index) {
        T item = at(index);
        while (index < size / 2) {
            int child = 2 * index + 1;
            if (child + 1 < size && at(child + 1).compareTo(at(child)) < 0) {
                child++;
            }
            if (item.compareTo(at(child)) <= 0) {
                break;
            }
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = item;
    }
}
