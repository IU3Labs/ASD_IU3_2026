package ru.bmstu.lab01.task_32;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 32. Сумма произведений пар.
 *
 * Вход: число n, затем массив из n целых чисел. Выход: сумма a_i * a_j по всем парам
 * при i меньше j. Требуется O(n).
 *
 * Идея: (sum a_i)^2 = sum(a_i^2) + 2 * sum(a_i * a_j), поэтому искомая сумма равна
 * ((sum a_i)^2 - sum(a_i^2)) / 2. Достаточно одного прохода.
 *
 * Диапазон значений в условии не задан, поэтому итог считается в BigInteger:
 * сумма квадратов 10^5 значений типа int не помещается в long.
 */
public final class Task32 {

    private Task32() {
    }

    public static BigInteger pairProductsSum(int[] array) {
        long sum = 0;
        BigInteger squares = BigInteger.ZERO;
        for (int value : array) {
            sum += value;
            squares = squares.add(BigInteger.valueOf((long) value * value));
        }
        return BigInteger.valueOf(sum).pow(2).subtract(squares).divide(BigInteger.TWO);
    }

    public static void run(InputStream in, PrintStream out) {
        out.println(pairProductsSum(new TokenReader(in).nextSizedIntArray()));
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
