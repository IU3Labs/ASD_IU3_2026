package ru.bmstu.lab1.task_57;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputReader;
import ru.bmstu.lab1.common.OutputWriter;
import ru.bmstu.lab1.common.Validation;

/**
 * Задача 57. Спиральный обход матрицы.
 *
 * <p>Вход: матрица {@code n x m} из целых чисел.
 * Выход: элементы матрицы, обойдённые по спирали от левого верхнего угла по часовой стрелке.
 *
 * <p>Идея: храним четыре границы ещё не обойдённой области ({@code top}, {@code bottom},
 * {@code left}, {@code right}). За один виток проходим верхнюю строку, правый столбец,
 * нижнюю строку и левый столбец, после каждой стороны сдвигая соответствующую границу внутрь.
 */
public final class SpiralTraversal {

    private SpiralTraversal() {
    }

    /**
     * Возвращает элементы матрицы в порядке спирального обхода.
     *
     * @param matrix прямоугольная непустая матрица
     * @return массив из {@code n * m} элементов в порядке обхода
     * @throws IllegalArgumentException если матрица пустая или непрямоугольная
     */
    public static int[] spiralOrder(int[][] matrix) {
        Validation.requireRectangular(matrix);
        int[] result = new int[matrix.length * matrix[0].length];
        collectSpiral(matrix, result);
        return result;
    }

    /**
     * Записывает элементы матрицы в {@code result} в порядке спирального обхода.
     *
     * <p>Проверки {@code top <= bottom} и {@code left <= right} перед нижней строкой и левым столбцом
     * нужны, когда осталась одна строка или один столбец: без них этот ряд был бы пройден
     * повторно в обратную сторону.
     */
    private static void collectSpiral(int[][] matrix, int[] result) {
        int index = 0;
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                result[index++] = matrix[top][col];
            }
            top++;
            for (int row = top; row <= bottom; row++) {
                result[index++] = matrix[row][right];
            }
            right--;
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result[index++] = matrix[bottom][col];
                }
                bottom--;
            }
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result[index++] = matrix[row][left];
                }
                left++;
            }
        }
    }

    /**
     * Читает матрицу и печатает её элементы в порядке спирального обхода.
     *
     * @param in  источник данных
     * @param out поток вывода
     */
    public static void run(Scanner in, PrintStream out) {
        OutputWriter.printArray(out, spiralOrder(InputReader.readMatrix(in)));
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
