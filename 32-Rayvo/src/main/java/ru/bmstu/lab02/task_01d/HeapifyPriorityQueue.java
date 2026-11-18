package ru.bmstu.lab02.task_01d;

import java.util.Comparator;

import ru.bmstu.lab02.task_01c.RemovablePriorityQueue;


/**
 * Очередь задачи 1D с построением двоичной кучи из копии массива за O(n).
 *
 * <p>Сохраняет операции задач 1A–1C и поддерживает оба направления приоритетов.
 *
 * @param <T> тип элемента
 */
public class HeapifyPriorityQueue<T> extends RemovablePriorityQueue<T> {
    /** Создаёт пустую очередь с естественным порядком элементов. */
    public HeapifyPriorityQueue() {
        super();
    }

    /**
     * Создаёт пустую очередь с заданным порядком.
     *
     * @param comparator порядок элементов либо null для естественного порядка
     */
    public HeapifyPriorityQueue(Comparator<? super T> comparator) {
        super(comparator);
    }

    /**
     * Строит min-heap методом Флойда за O(n).
     *
     * @param array исходный массив; его содержимое не изменяется
     * @throws NullPointerException если массив или один из элементов равен null
     */
    public HeapifyPriorityQueue(T[] array) {
        this(array, null);
    }

    /**
     * Строит кучу с заданным порядком методом Флойда за O(n).
     *
     * @param array исходный массив; его содержимое не изменяется
     * @param comparator порядок элементов либо null для естественного порядка
     * @throws NullPointerException если массив или один из элементов равен null
     */
    public HeapifyPriorityQueue(T[] array, Comparator<? super T> comparator) {
        super(comparator);
        buildHeap(array);
    }
}
