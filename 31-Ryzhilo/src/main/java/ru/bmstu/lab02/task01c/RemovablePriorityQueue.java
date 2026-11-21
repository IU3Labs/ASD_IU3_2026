package ru.bmstu.lab02.task01c;

import java.util.Objects;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;

/** Очередь с удалением первого вхождения по значению. */
public final class RemovablePriorityQueue<T> extends PriorityQueueCustom<T> {
    /** Удаляет первое вхождение элемента в порядке обхода массива. */
    public boolean remove(T item) {
        for (int index = 0; index < size(); index++) {
            if (Objects.equals(item, elementAt(index))) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }
}
