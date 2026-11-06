package ru.bmstu.lab1.task_57;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 57. Спиральный обход матрицы.
 *
 * <p>Вход: {@code n m}, затем матрица {@code n x m}. Выход: элементы по спирали от левого верхнего угла
 * по часовой стрелке.
 *
 * <p>Поддерживаются четыре границы непройденной области: {@code top, bottom, left, right}. За один виток
 * обходятся верхняя строка, правый столбец, нижняя строка и левый столбец, после чего границы сужаются.
 * Проверки перед нижней строкой и левым столбцом нужны, когда осталась одна строка или один столбец —
 * иначе элементы были бы выведены дважды. O(nm) по времени, O(1) доп. памяти (не считая ответа).
 */
public final class SpiralTraversal {

    private SpiralTraversal() {
    }

    /**
     * Возвращает элементы матрицы в порядке спирального обхода.
     *
     * @param matrix прямоугольная матрица
     * @return массив из {@code n * m} элементов
     */
    public static int[] traverse(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[0];
        }
        int[] result = new int[matrix.length * matrix[0].length];
        int k = 0;
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                result[k++] = matrix[top][j];
            }
            for (int i = top + 1; i <= bottom; i++) {
                result[k++] = matrix[i][right];
            }
            if (top < bottom) {
                for (int j = right - 1; j >= left; j--) {
                    result[k++] = matrix[bottom][j];
                }
            }
            if (left < right) {
                for (int i = bottom - 1; i > top; i--) {
                    result[k++] = matrix[i][left];
                }
            }
            top++;
            bottom--;
            left++;
            right--;
        }
        return result;
    }

    /**
     * Точка входа: читает матрицу, печатает спиральный обход.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int rows = in.nextInt();
            int columns = in.nextInt();
            out.println(ConsoleIo.join(traverse(in.nextMatrix(rows, columns))));
        });
    }
}
