package ru.bmstu.lab02.task_1;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на двоичной куче в динамическом массиве
 * @param <T> - тип элементов очереди
 */
public class PriorityQueueCustom<T> {
    private static final int INITIAL_CAPACITY = 16;
    private final Comparator<? super T> comparator;
    private Object[] elements;
    private int size;

    /**
     * Создаёт пустую очередь с естественным порядком
     */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Создаёт пустую очередь с заданным порядком
     * @param comparator - компаратор или null для естественного порядка
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[INITIAL_CAPACITY];
    }

    /**
     * Строит кучу снизу вверх за линейное время
     * @param array - исходный массив без null, который не изменяется
     */
    public PriorityQueueCustom(T[] array) {
        comparator = null;
        size = array.length;
        elements = new Object[Math.max(size, INITIAL_CAPACITY)];
        for (int index = 0; index < size; index++) {
            elements[index] = checkedItem(array[index]);
        }
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /**
     * Добавляет элемент в очередь
     * @param item - добавляемый элемент, отличный от null
     */
    public void add(T item) {
        checkedItem(item);
        if (size == elements.length) {
            grow();
        }
        elements[size] = item;
        size++;
        siftUp(size - 1);
    }

    /**
     * Удаляет и возвращает первый по приоритету элемент
     * @return - удалённый элемент
     * @throws NoSuchElementException - если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T result = itemAt(0);
        removeAt(0);
        return result;
    }

    /**
     * Возвращает первый по приоритету элемент без удаления
     * @return - первый элемент либо null для пустой очереди
     */
    public T peek() {
        return isEmpty() ? null : itemAt(0);
    }

    /**
     * Возвращает число элементов очереди
     * @return - число элементов
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли очередь
     * @return - true, если элементов нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаляет первое совпадение по equals при обходе массива кучи
     * @param item - искомое значение
     * @return - true, если элемент найден и удалён
     */
    public boolean remove(T item) {
        for (int index = 0; index < size; index++) {
            if (Objects.equals(item, elements[index])) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    private void removeAt(int index) {
        int last = --size;
        Object replacement = elements[last];
        elements[last] = null;
        if (index == last) {
            return;
        }
        elements[index] = replacement;
        if (index > 0 && compare(itemAt(index), itemAt((index - 1) / 2)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(itemAt(index), itemAt(parent)) >= 0) {
                return;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(itemAt(right), itemAt(child)) < 0) {
                child = right;
            }
            if (compare(itemAt(index), itemAt(child)) <= 0) {
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

    private void grow() {
        long doubled = 2L * elements.length;
        int capacity = (int) Math.min(doubled, Integer.MAX_VALUE - 8L);
        if (capacity <= elements.length) {
            throw new OutOfMemoryError("Достигнута максимальная ёмкость массива");
        }
        Object[] expanded = new Object[capacity];
        System.arraycopy(elements, 0, expanded, 0, size);
        elements = expanded;
    }

    private T checkedItem(T item) {
        Objects.requireNonNull(item, "Элемент не может быть null");
        if (comparator == null && !(item instanceof Comparable<?>)) {
            throw new ClassCastException("Для элемента требуется Comparable или компаратор");
        }
        return item;
    }

    @SuppressWarnings("unchecked")
    private T itemAt(int index) {
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
