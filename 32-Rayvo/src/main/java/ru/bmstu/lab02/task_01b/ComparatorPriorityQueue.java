package ru.bmstu.lab02.task_01b;

import java.util.Comparator;

import ru.bmstu.lab02.task_01a.PriorityQueueCustom;


/**
 * Очередь задачи 1B: min-heap или max-heap в зависимости от компаратора.
 *
 * <p>Наследует массив и операции задачи 1A; изменяется только способ сравнения элементов.
 *
 * @param <T> тип элемента, не обязательно реализующий Comparable при наличии компаратора
 */
public class ComparatorPriorityQueue<T> extends PriorityQueueCustom<T> {
    private final Comparator<? super T> comparator;

    /** Создаёт пустую очередь с естественным порядком элементов. */
    public ComparatorPriorityQueue() {
        this(null);
    }

    /**
     * Создаёт очередь с заданным порядком; reverseOrder задаёт max-heap.
     *
     * @param comparator порядок элементов либо null для естественного порядка
     */
    public ComparatorPriorityQueue(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    /** {@inheritDoc} */
    @Override
    protected int compare(T first, T second) {
        return comparator == null ? super.compare(first, second) : comparator.compare(first, second);
    }
}
