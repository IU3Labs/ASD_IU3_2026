package ru.bmstu.lab02.task_1b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Задача 1B. Очередь с приоритетом (min-heap и max-heap) через компаратор.
 * Конструктор принимает Comparator. Если не передан - используется естественный порядок.
 * Ввод: количество операций m, тип кучи (min или max), затем m строк команд:
 *   add X, poll, peek, size, isEmpty
 */
@SuppressWarnings("unchecked")
public class PriorityQueueCustom<T> {
    private Object[] heap;
    private int size;
    private final Comparator<? super T> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    //конструктор без компаратора - естественный порядок (min-heap)
    public PriorityQueueCustom() {
        this(null);
    }

    //конструктор с компаратором
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

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            //сравниваем через компаратор
            if (comparator.compare((T) heap[index], (T) heap[parent]) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && comparator.compare((T) heap[left], (T) heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && comparator.compare((T) heap[right], (T) heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
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

    //ввод-вывод через стандартные потоки
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        if (line == null || line.trim().isEmpty()) return;
        int m = Integer.parseInt(line.trim());
        
        //читаем тип кучи для демонстрации (min или max)
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