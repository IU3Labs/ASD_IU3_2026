package ru.bmstu.lab1.task_58;

import java.io.PrintStream;
import java.util.Scanner;
import ru.bmstu.lab1.common.OutputWriter;

/**
 * Задача 58. Заполнение матрицы змейкой.
 *
 * <p>Вход: числа {@code n} и {@code m}.
 * Выход: матрица {@code n x m}, заполненная числами от 1 до {@code n * m} змейкой по строкам:
 * первая строка слева направо, вторая — справа налево и т. д.
 *
 * <p>Идея: идём по строкам и ведём счётчик значений. Для строк с чётным индексом (0, 2, …)
 * заполняем столбцы слева направо, для нечётных — справа налево.
 */
public final class SnakeMatrixFiller {

    private SnakeMatrixFiller() {
    }

    /**
     * Создаёт матрицу заданного размера, заполненную змейкой.
     *
     * @param rows количество строк
     * @param cols количество столбцов
     * @return заполненная матрица
     * @throws IllegalArgumentException если размеры не положительные
     */
    public static int[][] fill(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными");
        }
        int[][] matrix = new int[rows][cols];
        int value = 1;
        for (int row = 0; row < rows; row++) {
            boolean leftToRight = row % 2 == 0;
            for (int step = 0; step < cols; step++) {
                int col = leftToRight ? step : cols - 1 - step;
                matrix[row][col] = value++;
            }
        }
        return matrix;
    }

    /**
     * Читает {@code n} и {@code m}, печатает матрицу, заполненную змейкой.
     *
     * @param in  источник данных
     * @param out поток вывода
     */
    public static void run(Scanner in, PrintStream out) {
        int rows = in.nextInt();
        int cols = in.nextInt();
        OutputWriter.printMatrix(out, fill(rows, cols));
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
