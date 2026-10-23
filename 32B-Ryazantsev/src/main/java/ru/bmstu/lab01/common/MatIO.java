// Ввод матрицы и вывод ответа
package ru.bmstu.lab01.common;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public final class MatIO {
    private MatIO() {
    }

    /** Сначала вводятся n и m, потом элементы матрицы */
    public static int[][] read(InputStream in) {
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Размеры должны быть больше нуля");
        }
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = sc.nextInt();
            }
        }
        return a;
    }

    /** Вывод массива целых чисел */
    public static void print(int[] a, PrintStream out) {
        for (int i = 0; i < a.length; i++) {
            if (i != 0) out.print(' ');
            out.print(a[i]);
        }
        out.println();
    }

    /** Вывод массива сумм типа long */
    public static void print(long[] a, PrintStream out) {
        for (int i = 0; i < a.length; i++) {
            if (i != 0) out.print(' ');
            out.print(a[i]);
        }
        out.println();
    }
}
