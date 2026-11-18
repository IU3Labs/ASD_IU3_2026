package ru.bmstu.lab02.task_01c;

import java.util.Comparator;

import ru.bmstu.lab02.task_01b.ComparatorPriorityQueue;


/**
 * Очередь задачи 1C с удалением первого найденного по equals вхождения за O(n).
 *
 * <p>Поиск идёт по внутреннему массиву, после чего общий алгоритм восстанавливает кучу.
 *
 * @param <T> тип элемента
 */
public class RemovablePriorityQueue<T> extends ComparatorPriorityQueue<T> {
    /** Создаёт пустую очередь с естественным порядком элементов. */
    public RemovablePriorityQueue() {
        super();
    }

    /**
     * Создаёт пустую очередь с заданным порядком.
     *
     * @param comparator порядок элементов либо null для естественного порядка
     */
    public RemovablePriorityQueue(Comparator<? super T> comparator) {
        super(comparator);
    }

    /**
     * Удаляет одно вхождение по equals за O(n), независимо от компаратора приоритетов.
     *
     * @param item удаляемое значение; для null возвращается false
     * @return true, если элемент найден и удалён
     */
    public boolean remove(T item) {
        if (item == null) {
            return false;
        }
        for (int index = 0; index < size(); index++) {
            if (item.equals(elementAt(index))) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }
}
