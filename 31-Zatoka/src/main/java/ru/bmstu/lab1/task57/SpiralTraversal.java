package ru.bmstu.lab1.task57;

/**
 * Задача 57. Спиральный обход матрицы.
 * Вход: матрица n x m из целых чисел.
 * Выход: элементы матрицы, обойдённые по спирали от левого верхнего угла
 * по часовой стрелке.
 *
 * <p>Идея: храним четыре границы ещё не пройденной части матрицы -
 * top, bottom, left, right. Проходим верхнюю строку слева направо,
 * правый столбец сверху вниз, нижнюю строку справа налево и левый столбец
 * снизу вверх, после каждой стороны сдвигая соответствующую границу внутрь.
 */
public class SpiralTraversal {

    private SpiralTraversal() {
    }

    /**
     * Возвращает элементы матрицы в порядке обхода по спирали.
     * Все строки матрицы должны быть одной длины.
     *
     * @param matrix матрица n x m
     * @return массив из n * m элементов в порядке спирали
     */
    public static int[] traverse(int[][] matrix) {
        int columns = matrix.length == 0 ? 0 : matrix[0].length;
        int[] result = new int[matrix.length * columns];
        int k = 0;
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = columns - 1;
        while (top <= bottom && left <= right) {
            k = copyRow(matrix, top, left, right - left + 1, 1, result, k);
            top++;
            k = copyColumn(matrix, right, top, bottom - top + 1, 1, result, k);
            right--;
            // проверки нужны, когда осталась одна строка или один столбец,
            // иначе эти элементы будут пройдены второй раз
            if (top <= bottom) {
                k = copyRow(matrix, bottom, right, right - left + 1, -1, result, k);
                bottom--;
            }
            if (left <= right) {
                k = copyColumn(matrix, left, bottom, bottom - top + 1, -1, result, k);
                left++;
            }
        }
        return result;
    }

    /**
     * Копирует count элементов строки row, начиная со столбца startColumn,
     * с шагом step (1 - вправо, -1 - влево). Возвращает новую позицию в result.
     */
    private static int copyRow(int[][] matrix, int row, int startColumn, int count, int step,
            int[] result, int k) {
        for (int t = 0; t < count; t++) {
            result[k++] = matrix[row][startColumn + t * step];
        }
        return k;
    }

    /**
     * Копирует count элементов столбца column, начиная со строки startRow,
     * с шагом step (1 - вниз, -1 - вверх). Возвращает новую позицию в result.
     */
    private static int copyColumn(int[][] matrix, int column, int startRow, int count, int step,
            int[] result, int k) {
        for (int t = 0; t < count; t++) {
            result[k++] = matrix[startRow + t * step][column];
        }
        return k;
    }
}
