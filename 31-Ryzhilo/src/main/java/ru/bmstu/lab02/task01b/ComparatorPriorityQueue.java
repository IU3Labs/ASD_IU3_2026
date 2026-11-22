package ru.bmstu.lab02.task01b;

import java.util.Comparator;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;

/** Очередь с порядком, заданным компаратором. */
public final class ComparatorPriorityQueue<T> extends PriorityQueueCustom<T> {
    /** Создаёт очередь с естественным порядком. */
    public ComparatorPriorityQueue() {
        super();
    }

    /** Создаёт очередь с заданным компаратором. */
    public ComparatorPriorityQueue(Comparator<? super T> comparator) {
        super(comparator);
    }
}
