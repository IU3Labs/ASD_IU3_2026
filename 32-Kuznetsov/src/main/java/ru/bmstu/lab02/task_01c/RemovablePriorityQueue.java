package ru.bmstu.lab02.task_01c;

import ru.bmstu.lab02.task_01b.ComparatorPriorityQueue;

import java.util.Comparator;

public class RemovablePriorityQueue<T> extends ComparatorPriorityQueue<T> {

    public RemovablePriorityQueue() {
        super();
    }

    public RemovablePriorityQueue(Comparator<? super T> order) {
        super(order);
    }

    public boolean remove(T item) {
        int position = find(item);
        if (position < 0) {
            return false;
        }
        deleteAt(position);
        return true;
    }

    private int find(T item) {
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < size(); i++) {
            if (item.equals(itemAt(i))) {
                return i;
            }
        }
        return -1;
    }
}
