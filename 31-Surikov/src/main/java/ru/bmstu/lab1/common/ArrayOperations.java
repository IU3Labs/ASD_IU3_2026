package ru.bmstu.lab1.common;

/** операции на месте без выделения вспомогательных массивов */
public final class ArrayOperations {
    private ArrayOperations() {
    }

    /** меняет местами элементы по двум допустимым индексам */
    public static void swap(int[] values, int first, int second) {
        int temporary = values[first];
        values[first] = values[second];
        values[second] = temporary;
    }

    /** разворачивает полуинтервал [from, to); границы должны принадлежать [0, length] */
    public static void reverse(int[] values, int from, int to) {
        int left = from;
        int right = to - 1;
        while (left < right) {
            swap(values, left++, right--);
        }
    }
}
