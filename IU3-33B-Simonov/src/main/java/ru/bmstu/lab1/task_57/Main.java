package ru.bmstu.lab1.task_57;

import java.util.Scanner;
import ru.bmstu.lab1.MatrixIO;

public class Main {
    public static long[] spiral(int[][] a) {
        int top = 0;
        int bottom = a.length - 1;
        int left = 0;
        int right = a[0].length - 1;
        int pos = 0;
        long[] result = new long[a.length * a[0].length];
        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                result[pos++] = a[top][j];
            }
            top++;
            for (int i = top; i <= bottom; i++) {
                result[pos++] = a[i][right];
            }
            right--;
            for (int j = right; j >= left && top <= bottom; j--) {
                result[pos++] = a[bottom][j];
            }
            bottom--;
            for (int i = bottom; i >= top && left <= right; i--) {
                result[pos++] = a[i][left];
            }
            left++;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] a = MatrixIO.read(in, n, m);
        MatrixIO.print(spiral(a));
    }
}
