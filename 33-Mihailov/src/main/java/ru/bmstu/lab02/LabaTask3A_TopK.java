/*
Задача 3A (Top-K элементов). Используя собственную очередь с приоритетом, реализуйте List<T> topK(List<T> input, int k) — k наибольших элементов. Требуемая асимптотика: O(n log k). Проверьте корректность сравнением с сортировкой и измерьте время на 1 000 000 элементов.
*/

package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LabaTask3A_TopK {
    private LabaTask3A_TopK() { }

    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        Objects.requireNonNull(input, "input cannot be null");
        if (k < 0 || k > input.size()) throw new IllegalArgumentException("invalid k");
        List<T> answer = new ArrayList<>(k);
        if (k == 0) return answer;

        LabaTask1B_PriorityQueue<T> smallestFirst = new LabaTask1B_PriorityQueue<>();
        for (T element : input) {
            Objects.requireNonNull(element, "null elements are not supported");
            if (smallestFirst.size() < k) smallestFirst.add(element);
            else if (element.compareTo(smallestFirst.peek()) > 0) {
                smallestFirst.poll();
                smallestFirst.add(element);
            }
        }
        while (!smallestFirst.isEmpty()) answer.add(smallestFirst.poll());
        return answer;
    }
}
