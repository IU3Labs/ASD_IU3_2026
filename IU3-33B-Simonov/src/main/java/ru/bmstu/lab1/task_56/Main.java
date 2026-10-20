package ru.bmstu.lab1.task_56;

import java.util.Scanner;
import ru.bmstu.lab1.MatrixIO;

public class Main {
    public static long[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int m = b.length;
        int k = b[0].length;
        long[][] c = new long[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                for (int r = 0; r < m; r++) {
                    c[i][j] += (long) a[i][r] * b[r][j];
                }
            }
        }
        return c;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int[][] a = MatrixIO.read(in, n, m);
        int[][] b = MatrixIO.read(in, m, k);
        for (long[] row : multiply(a, b)) {
            MatrixIO.print(row);
        }
    }
}
