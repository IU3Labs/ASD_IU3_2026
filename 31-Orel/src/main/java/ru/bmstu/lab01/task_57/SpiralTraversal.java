/*
 * Задача 57. Спиральный обход матрицы.
 * Вход: матрица n x m из целых чисел.
 * Выход: элементы матрицы, обойдённые по спирали от левого верхнего угла по часовой стрелке.
 */
package ru.bmstu.lab01.task_57;

import java.io.InputStream;
import java.io.PrintStream;

import ru.bmstu.lab01.common.InputReader;
import ru.bmstu.lab01.common.Matrices;
import ru.bmstu.lab01.common.OutputFormatter;

/**
 * Спиральный обход матрицы по часовой стрелке.
 *
 * <p>Формат ввода: {@code n m}, затем матрица построчно. Вывод: элементы в порядке обхода в одну строку.
 * Время O(n * m), дополнительная память O(n * m) под результат (O(1) сверх него).
 */
public final class SpiralTraversal {

    private SpiralTraversal() {
    }

    /**
     * Точка входа: читает матрицу из стандартного ввода и печатает спиральный обход.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    static void run(InputStream input, PrintStream output) {
        int[][] matrix = new InputReader(input).nextMatrix();
        output.println(OutputFormatter.join(traverse(matrix)));
    }

    /**
     * Обходит матрицу по спирали от левого верхнего угла по часовой стрелке.
     *
     * @param matrix прямоугольная матрица
     * @return элементы в порядке обхода
     */
    public static int[] traverse(int[][] matrix) {
        int cols = Matrices.requireRectangular(matrix);
        int[] result = new int[matrix.length * cols];
        // Границы ещё не пройденной части матрицы; после каждого витка они сжимаются внутрь.
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = cols - 1;
        int index = 0;
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                result[index++] = matrix[top][j];
            }
            for (int i = top + 1; i <= bottom; i++) {
                result[index++] = matrix[i][right];
            }
            // Если осталась одна строка или один столбец, обратный проход повторил бы уже взятые элементы.
            if (top < bottom && left < right) {
                for (int j = right - 1; j >= left; j--) {
                    result[index++] = matrix[bottom][j];
                }
                for (int i = bottom - 1; i > top; i--) {
                    result[index++] = matrix[i][left];
                }
            }
            top++;
            bottom--;
            left++;
            right--;
        }
        return result;
    }
}
