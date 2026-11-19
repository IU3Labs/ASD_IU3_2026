package ru.bmstu.lab02.task1b;

import ru.bmstu.lab02.task1a.PriorityQueueCustom;

import java.util.Comparator;

/**
 * Очередь с приоритетом, порядок которой задаётся компаратором (задача 1B).
 *
 * <p>Куча всегда держит в корне элемент, наименьший по компаратору. С обычным порядком это минимум
 * (min-heap), с {@link Comparator#reverseOrder()} — максимум (max-heap). Алгоритм кучи берётся
 * из {@link PriorityQueueCustom} без изменений, класс только передаёт ему компаратор.
 *
 * @param <T> тип элементов очереди
 */
public class OrderedPriorityQueue<T> extends PriorityQueueCustom<T> {

    /**
     * Создаёт пустую очередь с естественным порядком элементов.
     */
    public OrderedPriorityQueue() {
        super(null);
    }

    /**
     * Создаёт пустую очередь с заданным порядком.
     *
     * @param order компаратор; {@code null} означает естественный порядок
     */
    public OrderedPriorityQueue(Comparator<? super T> order) {
        super(order);
    }

    /**
     * Создаёт min-heap: первым извлекается наименьший элемент.
     *
     * @param <E> тип элементов с естественным порядком
     * @return пустая min-куча
     */
    public static <E extends Comparable<? super E>> OrderedPriorityQueue<E> minHeap() {
        return new OrderedPriorityQueue<>(Comparator.naturalOrder());
    }

    /**
     * Создаёт max-heap: первым извлекается наибольший элемент.
     *
     * @param <E> тип элементов с естественным порядком
     * @return пустая max-куча
     */
    public static <E extends Comparable<? super E>> OrderedPriorityQueue<E> maxHeap() {
        return new OrderedPriorityQueue<>(Comparator.reverseOrder());
    }
}
