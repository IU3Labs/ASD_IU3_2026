/*
Задача 1D (построение кучи за O(n)). Добавьте конструктор PriorityQueueCustom(T[] array), строящий кучу за O(n) методом просеивания вниз с последнего внутреннего узла к корню. Проведите замер: построение кучи из 100 000 элементов «последовательными add» против «за O(n)». Результаты оформите таблицей в benchmarks.md с кратким выводом.
*/

package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Comparator;

public class LabaTask1D_PriorityQueue<T> {
    private Object[] elements;
    private int count;
    private final Comparator<? super T> comparator;

    public LabaTask1D_PriorityQueue() { this((Comparator<? super T>) null); }

    public LabaTask1D_PriorityQueue(Comparator<? super T> comparator) {
        this.elements = new Object[12];
        this.comparator = comparator;
    }


    public LabaTask1D_PriorityQueue(T[] source) { this(source, null); }

    public LabaTask1D_PriorityQueue(T[] source, Comparator<? super T> comparator) {
        Objects.requireNonNull(source, "array cannot be null");
        this.comparator = comparator;
        this.elements = Arrays.copyOf(source, Math.max(12, source.length), Object[].class);
        this.count = source.length;
        for (int i = 0; i < count; i++) Objects.requireNonNull(source[i], "null element");
        for (int i = count / 2 - 1; i >= 0; i--) siftDown(i);
    }

    public void add(T value) {
        Objects.requireNonNull(value, "null elements are not supported");
        if (count == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[count] = value;
        siftUp(count);
        count++;
    }

    public T peek() {
        return count == 0 ? null : itemAt(0);
    }

    public T poll() {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        T minimum = itemAt(0);
        elements[0] = elements[--count];
        elements[count] = null;
        if (count != 0) siftDown(0);
        return minimum;
    }

    public int size() { return count; }

    public boolean isEmpty() { return count == 0; }

    private void siftUp(int position) {
        T current = itemAt(position);
        while (position > 0) {
            int parent = (position - 1) / 2;
            if (compare(current, itemAt(parent)) >= 0) break;
            elements[position] = elements[parent];
            position = parent;
        }
        elements[position] = current;
    }

    private void siftDown(int position) {
        T current = itemAt(position);
        while (2L * position + 1 < count) {
            int left = position * 2 + 1;
            int best = left;
            if (left + 1 < count && compare(itemAt(left + 1), itemAt(left)) < 0) best++;
            if (compare(current, itemAt(best)) <= 0) break;
            elements[position] = elements[best];
            position = best;
        }
        elements[position] = current;
    }

    @SuppressWarnings("unchecked")
    private T itemAt(int position) { return (T) elements[position]; }

    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        return comparator != null ? comparator.compare(first, second)
                : ((Comparable<? super T>) first).compareTo(second);
    }
}
