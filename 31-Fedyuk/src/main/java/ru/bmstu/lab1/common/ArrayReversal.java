package ru.bmstu.lab1.common;

/** Разворот участка массива на месте; используется в задачах 37 и 38. */
public final class ArrayReversal {
    private ArrayReversal() {}

    /** Разворачивает допустимый диапазон [fromInclusive, toExclusive) без нового массива. */
    public static void reverse(int[] values, int fromInclusive, int toExclusive) {
        int left = fromInclusive;
        int right = toExclusive - 1;
        while (left < right) {
            int temporary = values[left];
            values[left] = values[right];
            values[right] = temporary;
            left++;
            right--;
        }
    }
}
