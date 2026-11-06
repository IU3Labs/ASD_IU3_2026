package ru.bmstu.lab1.task_01;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 1. Максимум и минимум.
 *
 * <p>Вход: {@code n}, затем {@code n} целых чисел. Выход: минимальный и максимальный элементы через пробел.
 * Один проход по массиву: O(n) по времени, O(1) доп. памяти.
 */
public final class MinMax {

    private MinMax() {
    }

    /**
     * Находит минимум и максимум массива.
     *
     * @param values непустой массив
     * @return пара {@code {min, max}}
     * @throws IllegalArgumentException если массив пуст
     */
    public static int[] solve(int[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        int min = values[0];
        int max = values[0];
        for (int value : values) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        return new int[] {min, max};
    }

    /**
     * Точка входа: читает массив из стандартного ввода и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> out.println(ConsoleIo.join(solve(in.nextIntArray(in.nextInt())))));
    }
}
