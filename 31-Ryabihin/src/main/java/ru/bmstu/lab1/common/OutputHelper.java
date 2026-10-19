package ru.bmstu.lab1.common;

/** Перевод массива в строку для вывода. */
public final class OutputHelper {

    private OutputHelper() {}

    /**
     * Склеивает элементы массива через пробел.
     *
     * @param array массив
     * @return строка вида "1 2 3"
     */
    public static String join(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
}
