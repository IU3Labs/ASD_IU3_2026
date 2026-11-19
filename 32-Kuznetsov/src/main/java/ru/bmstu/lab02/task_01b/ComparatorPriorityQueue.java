package ru.bmstu.lab02.task_01b;

import ru.bmstu.lab02.task_01a.PriorityQueueCustom;

import java.util.Comparator;

public class ComparatorPriorityQueue<T> extends PriorityQueueCustom<T> {

    private final Comparator<? super T> order;

    public ComparatorPriorityQueue() {
        this(null);
    }

    public ComparatorPriorityQueue(Comparator<? super T> order) {
        super();
        this.order = order;
    }

    public static <E extends Comparable<? super E>> ComparatorPriorityQueue<E> minHeap() {
        return new ComparatorPriorityQueue<>(Comparator.naturalOrder());
    }

    public static <E extends Comparable<? super E>> ComparatorPriorityQueue<E> maxHeap() {
        return new ComparatorPriorityQueue<>(Comparator.reverseOrder());
    }

    @Override
    protected int compare(T first, T second) {
        if (order == null) {
            return super.compare(first, second);
        }
        return order.compare(first, second);
    }
}
