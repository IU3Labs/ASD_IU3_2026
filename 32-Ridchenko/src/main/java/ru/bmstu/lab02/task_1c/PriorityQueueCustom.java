package ru.bmstu.lab02.task_1c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Задача 1C. Удаление по значению.
 * Добавлена операция boolean remove(T item) - удаление первого вхождения за O(n).
 * После удаления свойство кучи восстанавливается.
 */
@SuppressWarnings("unchecked")
public class PriorityQueueCustom<T> {
    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    public PriorityQueueCustom() {
        this(null);
    }

    public PriorityQueueCustom(Comparator<? super T> comparator) {
        this.comparator = comparator != null ? comparator : (Comparator<? super T>) Comparator.naturalOrder();
        heap = new Object[DEFAULT_CAPACITY];
        size = 0;
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

    //задача 1C: удаление по значению
    public boolean remove(T item) {
        if (item == null) throw new NullPointerException("item is null");
        for (int i = 0; i < size; i++) {
            if (comparator.compare((T) heap[i], item) == 0) {
                //нашли элемент - заменяем его последним
                heap[i] = heap[size - 1];
                heap[size - 1] = null;
                size--;
                if (i < size) {
                    //восстанавливаем кучу (проверяем и вверх, и вниз)
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
        int m = Integer.parseInt(line.trim());
        
        String type = reader.readLine().trim();
        Comparator<Integer> comp = type.equals("max") ? Comparator.reverseOrder() : Comparator.naturalOrder();
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(comp);

        for (int i = 0; i < m; i++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            String cmd = parts[0];
            switch (cmd) {
                case "add":
                    pq.add(Integer.parseInt(parts[1]));
                    break;
                case "poll":
                    System.out.println(pq.poll());
                    break;
                case "peek":
                    System.out.println(pq.peek());
                    break;
                case "remove":
                    System.out.println(pq.remove(Integer.parseInt(parts[1])));
                    break;
                case "size":
                    System.out.println(pq.size());
                    break;
                case "isEmpty":
                    System.out.println(pq.isEmpty());
                    break;
                default:
                    break;
            }
        }
    }
}