/*
Задача 1A (базовая реализация). Реализуйте PriorityQueueCustom<T> на динамическом массиве с операциями:

- void add(T item) — добавить элемент, O(log n);
- T poll() — извлечь и вернуть минимальный элемент, при пустой очереди бросать NoSuchElementException, O(log n);
- T peek() — вернуть минимальный элемент без удаления, O(1);
- int size(), boolean isEmpty() — O(1).

Используйте индексацию: дети узла i — 2*i + 1 и 2*i + 2, родитель — (i - 1) / 2. Реализуйте просеивание вверх и вниз отдельными приватными методами siftUp и siftDown. Обеспечьте автоматическое расширение массива. Требование: элементы извлекаются в порядке неубывания.
*/

package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LabaTask1A_PriorityQueue<T> {
    private Object[] elements;
    private int count;

    public LabaTask1A_PriorityQueue() { elements = new Object[12]; }

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
        return ((Comparable<? super T>) first).compareTo(second);
    }
}
