package ru.bmstu.lab02;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Очередь с приоритетом на собственном динамическом массиве: задачи 1A–1D лабораторной № 2.
 *
 * <p>Двоичная куча хранит в корне наименьший элемент согласно компаратору. Без компаратора
 * используется естественный порядок; обратный компаратор превращает очередь в max-heap.
 * Значения {@code null} запрещены. Равноприоритетные элементы не сохраняют порядок вставки.
 *
 * <p>Память составляет O(C), где C — ёмкость массива. После удалений массив не уменьшается,
 * чтобы отдельные вызовы {@link #poll()} не требовали линейного копирования элементов.
 *
 * @param <T> тип элементов; без компаратора элементы должны иметь согласованный естественный порядок
 */
public final class PriorityQueueCustom<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private final Comparator<? super T> comparator;
    private Object[] elements;
    private int size;

    /**
     * Создаёт пустую min-heap с естественным порядком элементов за O(1).
     */
    public PriorityQueueCustom() {
        this((Comparator<? super T>) null);
    }

    /**
     * Создаёт пустую очередь с указанным порядком элементов за O(1).
     *
     * @param comparator порядок элементов; {@code null} означает естественный порядок
     */
    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator;
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Копирует массив и строит min-heap снизу вверх за O(n), используя O(n) памяти.
     *
     * @param array исходные элементы; сам массив не изменяется
     * @throws NullPointerException если массив или один из его элементов равен {@code null}
     * @throws ClassCastException если элементы не поддерживают согласованный естественный порядок
     */
    public PriorityQueueCustom(T[] array) {
        this(array, null);
    }

    /**
     * Копирует массив и строит кучу с заданным порядком за O(n), используя O(n) памяти.
     *
     * @param array исходные элементы; сам массив не изменяется
     * @param comparator порядок элементов; {@code null} означает естественный порядок
     * @throws NullPointerException если массив или один из его элементов равен {@code null}
     * @throws ClassCastException если естественный порядок неприменим к элементам
     */
    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        Objects.requireNonNull(array, "Массив не должен быть null");
        this.comparator = comparator;
        elements = new Object[Math.max(DEFAULT_CAPACITY, array.length)];
        for (int index = 0; index < array.length; index++) {
            requireValidItem(array[index]);
            elements[index] = array[index];
        }
        size = array.length;
        for (int index = size / 2 - 1; index >= 0; index--) {
            siftDown(index);
        }
    }

    /**
     * Добавляет элемент и восстанавливает кучу просеиванием вверх.
     *
     * <p>Время — O(log n) амортизированно; отдельное расширение массива требует O(n).
     * Без расширения используется O(1) дополнительной памяти, при расширении — O(n).
     *
     * @param item добавляемый элемент
     * @throws NullPointerException если элемент равен {@code null}
     * @throws ClassCastException если естественный порядок неприменим к элементам
     */
    public void add(T item) {
        requireValidItem(item);
        ensureCapacity();
        elements[size] = item;
        size++;
        siftUp(size - 1);
    }

    /**
     * Извлекает первый по заданному порядку элемент за O(log n), используя O(1) дополнительной памяти.
     *
     * @return извлечённый элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        T result = elementAt(0);
        removeAt(0);
        return result;
    }

    /**
     * Возвращает первый по заданному порядку элемент без удаления за O(1).
     *
     * @return первый элемент или {@code null}, если очередь пуста
     */
    public T peek() {
        return isEmpty() ? null : elementAt(0);
    }

    /**
     * Возвращает число элементов за O(1).
     *
     * @return число элементов в очереди
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет отсутствие элементов за O(1).
     *
     * @return {@code true}, если очередь пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Удаляет одно вхождение, сравнивая значения через {@code equals}, за O(n).
     *
     * <p>Выбирается первое совпадение при просмотре внутреннего массива слева направо.
     * Это не обязательно первое по времени добавления вхождение. Дополнительная память — O(1).
     *
     * @param item удаляемое значение; для {@code null} метод возвращает {@code false}
     * @return {@code true}, если найден и удалён один элемент
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

    private void requireValidItem(T item) {
        Objects.requireNonNull(item, "Элемент не должен быть null");
        if (comparator == null && !(item instanceof Comparable<?>)) {
            throw new ClassCastException("Без компаратора элемент должен реализовывать Comparable");
        }
    }

    private void ensureCapacity() {
        if (size < elements.length) {
            return;
        }
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity <= Integer.MAX_VALUE / GROWTH_FACTOR
                ? oldCapacity * GROWTH_FACTOR : Integer.MAX_VALUE;
        if (newCapacity == oldCapacity) {
            throw new OutOfMemoryError("Достигнута максимальная ёмкость очереди");
        }
        Object[] expanded = new Object[newCapacity];
        System.arraycopy(elements, 0, expanded, 0, size);
        elements = expanded;
    }

    private void removeAt(int index) {
        int lastIndex = --size;
        T moved = elementAt(lastIndex);
        elements[lastIndex] = null;
        if (index == lastIndex) {
            return;
        }
        elements[index] = moved;
        // Последний элемент может нарушить порядок как с родителем, так и с одним из детей.
        if (index > 0 && compare(moved, elementAt((index - 1) / 2)) < 0) {
            siftUp(index);
        } else {
            siftDown(index);
        }
    }

    private void siftUp(int index) {
        T item = elementAt(index);
        while (index > 0) {
            int parent = (index - 1) / 2;
            T parentItem = elementAt(parent);
            if (compare(item, parentItem) >= 0) {
                break;
            }
            elements[index] = parentItem;
            index = parent;
        }
        elements[index] = item;
    }

    private void siftDown(int index) {
        T item = elementAt(index);
        int firstLeaf = size / 2;
        // У листьев нет детей; эта граница также предотвращает переполнение индекса 2 * index + 1.
        while (index < firstLeaf) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && compare(elementAt(right), elementAt(child)) < 0) {
                child = right;
            }
            T childItem = elementAt(child);
            if (compare(item, childItem) <= 0) {
                break;
            }
            elements[index] = childItem;
            index = child;
        }
        elements[index] = item;
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