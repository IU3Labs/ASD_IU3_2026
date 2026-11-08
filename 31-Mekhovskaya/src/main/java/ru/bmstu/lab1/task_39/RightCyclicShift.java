package ru.bmstu.lab1.task_39;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputReader;
import ru.bmstu.lab1.common.OutputWriter;

/**
 * Задача 39. Циклический сдвиг вправо.
 *
 * <p>Вход: массив из {@code n} целых чисел и число {@code k}.
 * Выход: массив, циклически сдвинутый вправо на {@code k} позиций.
 * Требование: учесть, что {@code k} может быть больше {@code n}.
 *
 * <p>Идея: сдвиг на {@code k} равен сдвигу на {@code k mod n}, поэтому большие {@code k} сводятся
 * к диапазону {@code [0, n)}. Сам сдвиг выполняется на месте тройным разворотом:
 * развернуть весь массив, затем отдельно первые {@code shift} элементов и оставшиеся.
 * Пример для {@code [1, 2, 3, 4, 5]}, {@code shift = 2}:
 * {@code [5, 4, 3, 2, 1] -> [4, 5, 3, 2, 1] -> [4, 5, 1, 2, 3]}.
 */
public final class RightCyclicShift {

    private RightCyclicShift() {
    }

    /**
     * Сдвигает массив вправо на {@code k} позиций на месте.
     * Отрицательное {@code k} означает сдвиг влево на {@code |k|}.
     *
     * @param array массив, который будет изменён
     * @param k     величина сдвига, может быть больше длины массива
     * @throws IllegalArgumentException если массив {@code null}
     */
    public static void shiftRight(int[] array, long k) {
        if (array == null) {
            throw new IllegalArgumentException("Массив не должен быть null");
        }
        int length = array.length;
        if (length == 0) {
            return;
        }
        // Двойной остаток приводит и отрицательные k к диапазону [0, length).
        int shift = (int) (((k % length) + length) % length);
        if (shift == 0) {
            return;
        }
        reverse(array, 0, length - 1);
        reverse(array, 0, shift - 1);
        reverse(array, shift, length - 1);
    }

    /**
     * Разворачивает участок массива {@code [from, to]} включительно.
     */
    private static void reverse(int[] array, int from, int to) {
        int left = from;
        int right = to;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Читает массив и число {@code k}, печатает сдвинутый массив.
     *
     * @param in  источник данных
     * @param out поток вывода
     */
    public static void run(Scanner in, PrintStream out) {
        int[] array = InputReader.readArray(in);
        long k = in.nextLong();
        shiftRight(array, k);
        OutputWriter.printArray(out, array);
    }

    /**
     * Точка входа: ввод с клавиатуры, вывод в консоль.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
