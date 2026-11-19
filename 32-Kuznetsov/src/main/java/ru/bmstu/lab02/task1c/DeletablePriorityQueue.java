package ru.bmstu.lab02.task1c;

import ru.bmstu.lab02.task1b.OrderedPriorityQueue;

import java.util.Comparator;

/**
 * Очередь с приоритетом с удалением элемента по значению (задача 1C).
 *
 * <p>Поддерживает и min-, и max-режим из задачи 1B. Удаление состоит из линейного поиска
 * и восстановления кучи; куча упорядочена только «по вертикали», поэтому быстрее O(n)
 * найти произвольный элемент нельзя.
 *
 * @param <T> тип элементов очереди
 */
public class DeletablePriorityQueue<T> extends OrderedPriorityQueue<T> {

    /**
     * Создаёт пустую очередь с естественным порядком элементов.
     */
    public DeletablePriorityQueue() {
        super();
    }

    /**
     * Создаёт пустую очередь с заданным порядком.
     *
     * @param order компаратор; {@code null} означает естественный порядок
     */
    public DeletablePriorityQueue(Comparator<? super T> order) {
        super(order);
    }

    /**
     * Удаляет первое вхождение элемента (по {@code equals}) за O(n) и восстанавливает свойство кучи.
     *
     * @param item удаляемый элемент
     * @return {@code true}, если элемент был найден и удалён; {@code false} для отсутствующего элемента,
     *     пустой очереди или {@code null}
     */
    public boolean remove(T item) {
        int position = positionOf(item);
        if (position < 0) {
            return false;
        }
        extractAt(position);
        return true;
    }
}
