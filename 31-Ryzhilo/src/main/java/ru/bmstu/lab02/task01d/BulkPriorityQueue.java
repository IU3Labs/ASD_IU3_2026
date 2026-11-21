package ru.bmstu.lab02.task01d;

import ru.bmstu.lab02.task01a.PriorityQueueCustom;

/** Очередь с линейным построением из массива. */
public final class BulkPriorityQueue<T> extends PriorityQueueCustom<T> {
    /** Копирует массив и строит кучу за линейное время. */
    public BulkPriorityQueue(T[] array) {
        super(array);
    }
}
