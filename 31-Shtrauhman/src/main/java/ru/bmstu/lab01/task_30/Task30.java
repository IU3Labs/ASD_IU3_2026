package ru.bmstu.lab01.task_30;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 30. Дисперсия.
 *
 * Вход: число n, затем массив из n целых чисел. Выход: дисперсия
 * D = (1 / n) * sum((a_i - avg)^2), где avg - среднее арифметическое.
 *
 * Алгоритм двухпроходный: сначала среднее, затем сумма квадратов отклонений. Он точнее формулы E[x^2] - E[x]^2, которая теряет точность при больших значениях.
 */

public final class Task30 {

    private static final String OUTPUT_FORMAT = "%.6f";

    private Task30() {
    }

    public static double variance(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        double avg = (double) sum / array.length;
        double squares = 0;
        for (int value : array) {
            double deviation = value - avg;
            squares += deviation * deviation;
        }
        return squares / array.length;
    }

    public static void run(InputStream in, PrintStream out) {
        double result = variance(new TokenReader(in).nextSizedIntArray());
        out.println(String.format(Locale.ROOT, OUTPUT_FORMAT, result));
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
