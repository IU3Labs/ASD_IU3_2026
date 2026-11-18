package ru.bmstu.lab02.task_1a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

/**
 * Задача 1A. Базовая реализация PriorityQueueCustom на динамическом массиве.
 * Операции: add (O(log n)), poll (O(log n)), peek (O(1)), size, isEmpty.
 */
@SuppressWarnings("unchecked") //говорим компилятору, что приведение типов безопасно
public class PriorityQueueCustom<T extends Comparable<T>> {
    private Object[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public PriorityQueueCustom() {
        heap = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(T item) {
        if (item == null) throw new NullPointerException("item is null");
        if (size == heap.length) grow(); //массив заполнен - расширяем
        heap[size] = item;
        siftUp(size); //поднимаем элемент на нужное место
        size++;
    }

    public T poll() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        T result = (T) heap[0];
        heap[0] = heap[size - 1]; //на место корня - последний элемент
        heap[size - 1] = null; //чистим ссылку для сборщика мусора
        size--;
        if (size > 0) siftDown(0); //просеиваем новый корень вниз
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
            int parent = (index - 1) / 2; //формула родителя
            if (((T) heap[index]).compareTo((T) heap[parent]) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;  //левый ребенок
            int right = 2 * index + 2; //правый ребенок
            int smallest = index;

            if (left < size && ((T) heap[left]).compareTo((T) heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && ((T) heap[right]).compareTo((T) heap[smallest]) < 0) {
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
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();

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