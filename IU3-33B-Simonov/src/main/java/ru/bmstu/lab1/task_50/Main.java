package ru.bmstu.lab1.task_50;

import java.util.Scanner;
import ru.bmstu.lab1.MatrixIO;

public class Main {
    public static long[] sumColumns(int[][] a) {
        long[] sums = new long[a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                sums[j] += a[i][j];
            }
        }
        return sums;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] a = MatrixIO.read(in, n, m);
        MatrixIO.print(sumColumns(a));
    }
}
