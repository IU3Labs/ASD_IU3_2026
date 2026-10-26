package ru.bmstu.lab01.task_58;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Задача 58 - Заполнение матрицы змейкой */
public final class Task58 {
    private Task58() {
    }

    /** Заполнение матрицы по строкам */
    public static int[][] solve(int n, int m) {
        if (n <= 0 || m <= 0 || (long) n * m > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Неправильный размер");
        }
        int[][] a = new int[n][m];
        int num = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int col = i % 2 == 0 ? j : m - j - 1;
                a[i][col] = num++;
            }
        }
        return a;
    }

    /** Чтение размеров и вывод матрицы */
    public static void run(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(in);
        int[][] a = solve(sc.nextInt(), sc.nextInt());
        for (int[] row : a) {
            for (int j = 0; j < row.length; j++) {
                if (j > 0) out.print(' ');
                out.print(row[j]);
            }
            out.println();
        }
    }

    /** Запуск задачи */
    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
