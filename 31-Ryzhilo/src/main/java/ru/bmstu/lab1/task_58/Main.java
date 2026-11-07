package ru.bmstu.lab1.task_58;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 58. Заполнение матрицы змейкой.
 * Выход: матрица n x m, заполненная числами от 1 до n x m змейкой по строкам: первая строка слева направо,
 * вторая — справа налево и т. д.
 * Сложность по времени — O(nm), где n и m - размеры матрицы
 * Память — O(1).
 */
public class Main {

    /** Запускает решение через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает данные и печатает результат. */
    static void solve(Scanner input, PrintStream output) {
        int rows = input.nextInt();
        int columns = input.nextInt();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                long value = (long) row * columns + (row % 2 == 0 ? column + 1 : columns - column);
                if (column > 0) {
                    output.print(' ');
                }
                output.print(value);
            }
            output.println();
        }
    }

}
