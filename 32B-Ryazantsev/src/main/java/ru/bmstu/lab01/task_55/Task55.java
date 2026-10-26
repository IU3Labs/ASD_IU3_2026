package ru.bmstu.lab01.task_55;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Задача 55 - Сумма побочной диагонали */
public final class Task55 {
    private Task55() {
    }

    /** Вычисление ответа */
    public static long solve(int[][] a) {
        check(a);
        if (a.length != a[0].length) throw new IllegalArgumentException("Не квадратная матрица");
        long sum = 0;
        int n = a.length;
        for (int i = 0; i < n; i++) sum += a[i][n - i - 1];
        return sum;
    }

    /** Чтение данных и вывод ответа */
    public static void run(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(in);
        out.println(solve(read(sc)));
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

}
