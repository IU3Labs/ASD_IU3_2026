package ru.bmstu.lab01.task_25;

import java.io.InputStream;
import java.io.PrintStream;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 25. Сумма элементов
 * Вход: число n, затем массив из n целых чисел. Выход: сумма всех элементов.
 * Сумма накапливается в long: при n до 10^5 она не превышает по модулю ~2.2 * 10^14.
 */

public final class Task25 {

    private Task25() {
    }

    public static long sum(int[] array) {
        long total = 0;
        for (int value : array) {
            total += value;
        }
        return total;
    }

    public static void run(InputStream in, PrintStream out) {
        TokenReader reader = new TokenReader(in);
        int n = reader.nextSize();
        out.println(sum(reader.nextIntArray(n)));
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
