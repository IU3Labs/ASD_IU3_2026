package ru.bmstu.lab01.task_59;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/** Задача 59 - Определитель матрицы 3 на 3 */
public final class Task59 {
    private Task59() {
    }

    /** Определитель по правилу Саррюса */
    public static BigInteger solve(int[][] a) {
        if (a == null || a.length != 3) throw new IllegalArgumentException("Размер должен быть 3 на 3");
        for (int[] row : a) {
            if (row == null || row.length != 3) throw new IllegalArgumentException("Размер должен быть 3 на 3");
        }
        BigInteger p = BigInteger.ZERO;
        BigInteger q = BigInteger.ZERO;
        for (int i = 0; i < 3; i++) {
            BigInteger x = BigInteger.ONE;
            BigInteger y = BigInteger.ONE;
            for (int j = 0; j < 3; j++) {
                x = x.multiply(BigInteger.valueOf(a[j][(i + j) % 3]));
                y = y.multiply(BigInteger.valueOf(a[j][(i - j + 3) % 3]));
            }
            p = p.add(x);
            q = q.add(y);
        }
        return p.subtract(q);
    }

    /** Чтение матрицы и вывод определителя */
    public static void run(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        if (n != 3 || m != 3) throw new IllegalArgumentException("Размер должен быть 3 на 3");
        int[][] a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) a[i][j] = sc.nextInt();
        }
        out.println(solve(a));
    }

    /** Запуск задачи */
    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
