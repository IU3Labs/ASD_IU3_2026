package ru.bmstu.lab01.task_27;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 27. Сумма квадратов.
 *
 * Вход: число n, затем массив из n целых чисел. Выход: сумма квадратов всех элементов.
 *
 * Квадрат одного int достигает ~4.6 * 10^18, а сумма 10^5 таких квадратов (~4.6 * 10^23)
 * уже не помещается в long (~9.2 * 10^18). Диапазон значений в условии не задан,
 * поэтому сумма накапливается в BigInteger.
 */

public final class Task27 {

    private Task27() {
    }

    public static BigInteger sumOfSquares(int[] array) {
        BigInteger total = BigInteger.ZERO;
        for (int value : array) {
            total = total.add(BigInteger.valueOf((long) value * value));
        }
        return total;
    }

    public static void run(InputStream in, PrintStream out) {
        out.println(sumOfSquares(new TokenReader(in).nextSizedIntArray()));
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
