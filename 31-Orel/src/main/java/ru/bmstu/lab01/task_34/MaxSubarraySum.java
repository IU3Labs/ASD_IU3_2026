/*
 * Задача 34. Максимальная сумма подмассива.
 * Вход: массив из n целых чисел (могут быть отрицательные).
 * Выход: максимальная сумма непрерывного подмассива.
 * Требование: O(n) (алгоритм Кадане).
 */
package ru.bmstu.lab01.task_34;

import java.io.InputStream;
import java.io.PrintStream;

import ru.bmstu.lab01.common.InputReader;

/**
 * Максимальная сумма непустого непрерывного подмассива алгоритмом Кадане.
 *
 * <p>Формат ввода: {@code n}, затем {@code n} чисел. Вывод: одно число.
 * Время O(n), дополнительная память O(1).
 */
public final class MaxSubarraySum {

    private MaxSubarraySum() {
    }

    /**
     * Точка входа: читает массив из стандартного ввода и печатает максимальную сумму.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    static void run(InputStream input, PrintStream output) {
        int[] array = new InputReader(input).nextArray();
        output.println(maxSum(array));
    }

    /**
     * Находит максимальную сумму непустого непрерывного подмассива.
     *
     * @param array исходный массив, не пустой
     * @return максимальная сумма ({@code long}: сумма 10^5 чисел типа int выходит за int)
     * @throws IllegalArgumentException если массив пуст
     */
    public static long maxSum(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        // bestEndingHere — лучшая сумма подмассива, заканчивающегося в текущей позиции.
        // Если она стала меньше самого элемента, выгоднее начать подмассив заново с него.
        // Старт с array[0] (а не с 0) корректно обрабатывает массив из одних отрицательных чисел.
        long bestEndingHere = array[0];
        long best = array[0];
        for (int i = 1; i < array.length; i++) {
            bestEndingHere = Math.max(array[i], bestEndingHere + array[i]);
            best = Math.max(best, bestEndingHere);
        }
        return best;
    }
}
