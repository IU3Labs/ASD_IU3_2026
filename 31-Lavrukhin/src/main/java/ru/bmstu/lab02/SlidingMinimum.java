package ru.bmstu.lab02;

import java.util.Comparator;

/** Находит минимумы окон с помощью очереди индексов. */
public final class SlidingMinimum {
    private SlidingMinimum() {}

    /** Возвращает минимумы окон размера k при 1 ≤ k ≤ arr.length. */
    public static int[] slidingMinimum(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Window size must be between 1 and array length");
        }
        PriorityQueueCustom<Integer> indices =
                new PriorityQueueCustom<>(Comparator.comparingInt(i -> arr[i]));
        int[] minima = new int[arr.length - k + 1];
        for (int i = 0; i < arr.length; i++) {
            indices.add(i);
            int start = i - k + 1;
            if (start >= 0) {
                while (indices.peek() < start) {
                    indices.poll();
                }
                minima[start] = arr[indices.peek()];
            }
        }
        return minima;
    }
}
