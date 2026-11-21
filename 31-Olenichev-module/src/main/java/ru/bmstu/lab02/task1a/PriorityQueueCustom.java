package ru.bmstu.lab02.task1a;

/**
 * Очередь с приоритетом (min-heap) на динамическом массиве.
 * Элементы извлекаются в порядке неубывания.
 *
 * @param <T> тип хранимых элементов, поддерживающий естественное упорядочивание
 */
public class PriorityQueueCustom<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private Object[] heap;
    private int size;

    /**
     * Создаёт пустую очередь с приоритетом с начальной ёмкостью по умолчанию.
     */
    public PriorityQueueCustom() {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }
}
