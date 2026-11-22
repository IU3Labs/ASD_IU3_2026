/*
Задача 1C (удаление по значению). Добавьте операцию boolean remove(T item) — удаление первого вхождения элемента по значению за O(n). После удаления свойство кучи должно восстанавливаться. Тесты: удаление корня, удаление элемента из середины кучи, удаление несуществующего элемента, удаление из пустой очереди.
*/

package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Comparator;

public class LabaTask1C_PriorityQueue<T> {
    private Object[] elements;
    private int count;
    private final Comparator<? super T> comparator;

    public LabaTask1C_PriorityQueue() { this(null); }

    public LabaTask1C_PriorityQueue(Comparator<? super T> comparator) {
        this.elements = new Object[12];
        this.comparator = comparator;
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


    public boolean remove(T value) {
        for (int i = 0; i < count; i++) {
            if (Objects.equals(elements[i], value)) {
                elements[i] = elements[--count];
                elements[count] = null;
                if (i < count) {
                    if (i > 0 && compare(itemAt(i), itemAt((i - 1) / 2)) < 0) siftUp(i);
                    else siftDown(i);
                }
                return true;
            }
        }
        return false;
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
