package ru.bmstu.lab02.task1b;

import java.util.Comparator;

/**
 * Очередь с приоритетом, поддерживающая режимы min-heap и max-heap через компаратор.
 * При отсутствии компаратора используется естественный порядок элементов.
 *
 * @param <T> тип хранимых элементов
 */
public class PriorityQueueComparator<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private final Comparator<? super T> comparator;
    private Object[] heap;
    private int size;

    /**
     * Создаёт пустую очередь с естественным порядком сортировки (min-heap).
     */
    public PriorityQueueComparator() {
        this(null);
    }

    /**
     * Создаёт пустую очередь с заданным компаратором.
     *
     * @param comparator компаратор для определения приоритета (null для естественного порядка)
     */
    public PriorityQueueComparator(Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }
}
