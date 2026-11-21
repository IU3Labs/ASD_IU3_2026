package ru.bmstu.lab02.task03d;

import ru.bmstu.lab02.task01a.PriorityQueueCustom;
import ru.bmstu.lab02.task01b.ComparatorPriorityQueue;

/** Поиск минимума в скользящем окне с помощью двоичной кучи. */
public final class Task03D {
    private Task03D() {
    }

    /** Возвращает минимум для каждого окна заданного размера. */
    public static int[] slidingMinimum(int[] arr, int k) {
        if (k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Размер окна должен быть от 1 до длины массива");
        }
        PriorityQueueCustom<Integer> indices = new ComparatorPriorityQueue<>(
                (first, second) -> Integer.compare(arr[first], arr[second]));
        int[] result = new int[arr.length - k + 1];
        for (int right = 0; right < arr.length; right++) {
            indices.add(right);
            int left = right - k + 1;
            if (left >= 0) {
                // Индекс вне окна можно оставить в куче до его подъёма в корень.
                while (indices.peek() < left) {
                    indices.poll();
                }
                result[left] = arr[indices.peek()];
            }
        }
        return result;
    }
}
