package ru.bmstu.lab01.task_52;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Задача 52 - Минимумы столбцов матрицы */
public final class Task52 {
    private Task52() {
    }

    /** Вычисление ответа */
    public static int[] solve(int[][] a) {
        check(a);
        int[] res = a[0].clone();
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < res.length; j++) {
                if (a[i][j] < res[j]) res[j] = a[i][j];
            }
        }
        return res;
    }

    /** Чтение данных и вывод ответа */
    public static void run(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(in);
        print(solve(read(sc)), out);
    }

    /** Запуск задачи */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    private static void check(int[][] a) {
        if (a == null || a.length == 0 || a[0] == null || a[0].length == 0) {
            throw new IllegalArgumentException("Пустая матрица");
        }
        int m = a[0].length;
        for (int[] row : a) {
            if (row == null || row.length != m) {
                throw new IllegalArgumentException("Неровная матрица");
            }
        }
    }

    private static int[][] read(Scanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Неправильный размер");
        }
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        return a;
    }

    private static void print(int[] a, PrintStream out) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) out.print(' ');
            out.print(a[i]);
        }
        out.println();
    }

}
