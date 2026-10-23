// Задача 49 — Сумма строк матрицы
package ru.bmstu.lab01.task_49;

import java.io.InputStream;
import java.io.PrintStream;
import ru.bmstu.lab01.common.Mat;
import ru.bmstu.lab01.common.MatIO;

public final class Task49 {
    private Task49() {
    }

    /** Решение задачи для готовой матрицы */
    public static long[] solve(int[][] a) {
        Mat.cols(a);
        long[] res = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            long sum = 0;
            for (int x : a[i]) sum += x;
            res[i] = sum;
        }
        return res;
    }

    /** Запуск с вводом из потока */
    public static void run(InputStream in, PrintStream out) {
        MatIO.print(solve(MatIO.read(in)), out);
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
