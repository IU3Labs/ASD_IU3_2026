package ru.bmstu.lab02.task_1d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Задача 1D. Построение кучи за O(n).
 * Добавлен конструктор PriorityQueueCustom(T[] array), который строит кучу 
 * методом просеивания вниз с последнего внутреннего узла к корню.
 */
@SuppressWarnings("unchecked")
public class PriorityQueueCustom<T> {
    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    public PriorityQueueCustom() {
        this((Comparator<? super T>) Comparator.naturalOrder());
    }

    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator != null ? comparator : (Comparator<? super T>) Comparator.naturalOrder();
        heap = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    //конструктор за O(n)
    public PriorityQueueCustom(T[] array) {
        this(array, (Comparator<? super T>) Comparator.naturalOrder());
    }

    public PriorityQueueCustom(T[] array, Comparator<? super T> comparator) {
        this.comparator = comparator != null ? comparator : (Comparator<? super T>) Comparator.naturalOrder();
        this.heap = new Object[Math.max(array.length, 1)];
        System.arraycopy(array, 0, heap, 0, array.length);
        this.size = array.length;
        //просеиваем с последнего внутреннего узла к корню
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public void add(T item) {
        if (item == null) throw new NullPointerException("item is null");
        if (size == heap.length) grow();
        heap[size] = item;
        siftUp(size);
        size++;
    }

    public T poll() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        T result = (T) heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) siftDown(0);
        return result;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return (T) heap[0];
    }

    public boolean remove(T item) {
        if (item == null) throw new NullPointerException("item is null");
        for (int i = 0; i < size; i++) {
            if (comparator.compare((T) heap[i], item) == 0) {
                heap[i] = heap[size - 1];
                heap[size - 1] = null;
                size--;
                if (i < size) {
                    siftDown(i);
                    siftUp(i);
                }
                return true;
            }
        }
        return false;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare((T) heap[index], (T) heap[parent]) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int best = index;

            if (left < size && comparator.compare((T) heap[left], (T) heap[best]) < 0) {
                best = left;
            }
            if (right < size && comparator.compare((T) heap[right], (T) heap[best]) < 0) {
                best = right;
            }
            if (best == index) break;
            swap(index, best);
            index = best;
        }
    }

    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private void grow() {
        Object[] newHeap = new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        if (line == null || line.trim().isEmpty()) return;
        
        //читаем массив
        String[] parts = line.trim().split("\\s+");
        Integer[] arr = new Integer[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }

        //строим кучу за O(n)
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        
        //выводим элементы в порядке возрастания
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " ");
        }
        System.out.println();
    }
}