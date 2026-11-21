package ru.bmstu.lab02;

import java.util.Objects;

/** Calculates minimums over consecutive windows using the custom min-heap. */
public final class SlidingMinimum {

    private SlidingMinimum() {
    }

    /**
     * Returns the minimum for each window of length k, from left to right.
     *
     * @throws NullPointerException if arr is null
     * @throws IllegalArgumentException if k is outside the interval [1, arr.length]
     */
    public static int[] minimums(int[] arr, int k) {
        Objects.requireNonNull(arr);
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Window size must be between 1 and the array length");
        }
        int[] result = new int[arr.length - k + 1];
        PriorityQueueCustom<WindowItem> queue = new PriorityQueueCustom<>();
        for (int index = 0; index < arr.length; index++) {
            queue.add(new WindowItem(arr[index], index));
            int start = index - k + 1;
            if (start >= 0) {
                // An expired item only matters when it reaches the root.
                while (queue.peek().index() < start) {
                    queue.poll();
                }
                result[start] = queue.peek().value();
            }
        }
        return result;
    }

    private record WindowItem(int value, int index) implements Comparable<WindowItem> {

        /** Orders by value, with the earlier index first on ties. */
        @Override
        public int compareTo(WindowItem other) {
            int byValue = Integer.compare(value, other.value);
            return byValue == 0 ? Integer.compare(index, other.index) : byValue;
        }
    }
}
