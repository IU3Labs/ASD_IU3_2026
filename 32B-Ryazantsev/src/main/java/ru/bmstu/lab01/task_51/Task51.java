package ru.bmstu.lab01.task_51;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Задача 51 - Максимумы строк матрицы */
public final class Task51 {
    private Task51() {
    }

    /** Вычисление ответа */
    public static int[] solve(int[][] a) {
        check(a);
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            int max = a[i][0];
            for (int x : a[i]) if (x > max) max = x;
            res[i] = max;
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
