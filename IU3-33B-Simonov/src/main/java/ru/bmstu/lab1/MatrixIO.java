package ru.bmstu.lab1;

import java.util.Scanner;

public class MatrixIO {
    private MatrixIO() {
    }

    public static int[][] read(Scanner in, int n, int m) {
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = in.nextInt();
            }
        }
        return a;
    }

    public static void print(long[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(a[i]);
        }
        System.out.println();
    }
}
