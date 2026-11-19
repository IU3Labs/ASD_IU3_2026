package ru.bmstu.lab02.task1a;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Очередь с приоритетом на двоичной куче, которая хранится в динамическом массиве (задача 1A).
 *
 * <p>Дети узла {@code i} лежат в ячейках {@code 2*i + 1} и {@code 2*i + 2}, родитель — в ячейке
 * {@code (i - 1) / 2}. Каждый элемент не больше своих детей, поэтому наименьший элемент всегда
 * в корне, и элементы извлекаются в порядке неубывания.
 *
 * <p>Порядок хранится в поле-компараторе. Публичный конструктор задаёт естественный порядок,
 * а защищённый позволяет подклассу (задача 1B) передать любой другой.
 *
 * @param <T> тип элементов очереди
 */
public class PriorityQueueCustom<T> {

    private static final int START_CAPACITY = 8;
    private static final int GROWTH_FACTOR = 2;

    private final Comparator<? super T> order;
    private Object[] items;
    private int count;

    /**
     * Создаёт пустую очередь с естественным порядком элементов (min-heap).
     */
    public PriorityQueueCustom() {
        this(null);
    }

    /**
     * Создаёт пустую очередь с заданным порядком.
     *
     * @param order порядок элементов; {@code null} означает естественный порядок
     */
    protected PriorityQueueCustom(Comparator<? super T> order) {
        this.order = order != null ? order : naturalOrder();
        this.items = new Object[START_CAPACITY];
        this.count = 0;
    }

    /**
     * Добавляет элемент за O(log n) амортизированно.
     *
     * @param item добавляемый элемент
     * @throws IllegalArgumentException если элемент равен {@code null}
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Нельзя добавить null в очередь");
        }
        if (count == items.length) {
            expand();
        }
        items[count] = item;
        count++;
        siftUp(count - 1);
    }

    /**
     * Извлекает и возвращает наименьший элемент за O(log n).
     *
     * @return наименьший элемент
     * @throws NoSuchElementException если очередь пуста
     */
    public T poll() {
        T top = peek();
        extractAt(0);
        return top;
    }

    /**
     * Возвращает наименьший элемент без удаления за O(1).
     *
     * @return наименьший элемент
     * @throws NoSuchElementException если очередь пуста
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (count == 0) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return (T) items[0];
    }

    /**
     * Возвращает количество элементов за O(1).
     *
     * @return размер очереди
     */
    public int size() {
        return count;
    }

    /**
     * Проверяет, пуста ли очередь, за O(1).
     *
     * @return {@code true}, если элементов нет
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Ищет позицию первого в массиве элемента, равного {@code item} по {@code equals}, за O(n).
     *
     * @param item искомый элемент
     * @return индекс в массиве кучи или {@code -1}, если элемента нет
     */
    protected int positionOf(T item) {
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < count; i++) {
            if (item.equals(items[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Удаляет элемент с заданной позиции и восстанавливает кучу за O(log n).
     *
     * @param position индекс занятой ячейки массива
     */
    protected void extractAt(int position) {
        count--;
        Object last = items[count];
        items[count] = null;
        if (position == count) {
            return;
        }
        items[position] = last;
        // Перенесённый элемент мог прийти из другой ветки: он может быть больше детей
        // (тогда опускаем) или меньше нового родителя (тогда поднимаем). Сработает только одно.
        siftDown(position);
        if (items[position] == last) {
            siftUp(position);
        }
    }

    /**
     * Поднимает элемент к корню, пока он меньше родителя.
     * Вместо обменов родители сдвигаются вниз на освободившееся место («дырку»),
     * а сам элемент записывается один раз в конце — одна запись в массив на уровень.
     */
    private void siftUp(int index) {
        Object moving = items[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(moving, items[parent]) >= 0) {
                break;
            }
            items[index] = items[parent];
            index = parent;
        }
        items[index] = moving;
    }

    /**
     * Опускает элемент к листьям, пока он больше меньшего из детей. Использует тот же приём с «дыркой».
     */
    private void siftDown(int index) {
        Object moving = items[index];
        while (2 * index + 1 < count) {
            int child = 2 * index + 1;
            int right = 2 * index + 2;
            if (right < count && compare(items[right], items[child]) < 0) {
                child = right;
            }
            if (compare(moving, items[child]) <= 0) {
                break;
            }
            items[index] = items[child];
            index = child;
        }
        items[index] = moving;
    }

    @SuppressWarnings("unchecked")
    private int compare(Object first, Object second) {
        return order.compare((T) first, (T) second);
    }

    private void expand() {
        Object[] bigger = new Object[items.length * GROWTH_FACTOR];
        for (int i = 0; i < count; i++) {
            bigger[i] = items[i];
        }
        items = bigger;
    }

    @SuppressWarnings("unchecked")
    private static <E> Comparator<E> naturalOrder() {
        return (first, second) -> ((Comparable<E>) first).compareTo(second);
    }
}
