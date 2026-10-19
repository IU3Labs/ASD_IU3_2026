package ru.bmstu.lab01.task_34;

import java.io.InputStream;
import java.io.PrintStream;
import ru.bmstu.lab01.common.TokenReader;

/**
 * Задача 34. Максимальная сумма подмассива.
 *
 * Вход: число n, затем массив из n целых чисел (могут быть отрицательные).
 * Выход: максимальная сумма непрерывного подмассива. Требуется O(n) (алгоритм Кадане).
 *
 * Идея: пусть current - наибольшая сумма подмассива, который заканчивается на текущем элементе.
 * Такой подмассив либо продолжает предыдущий (current + a_i), либо начинается заново с a_i.
 * Если накопленная сумма стала отрицательной, она только мешает, и выгоднее начать с нуля.
 * Ответ - наибольшее current по всем позициям.
 *
 * Подмассив считается непустым, поэтому для массива из одних отрицательных чисел ответом
 * будет наибольший (наименьший по модулю) элемент, а не 0.
 * Сумма не превышает ~2.2 * 10^14 по модулю, поэтому хранится в long.
 */
public final class Task34 {

    private Task34() {
    }

    public static long maxSubarraySum(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        long current = array[0];
        long best = array[0];
        for (int i = 1; i < array.length; i++) {
            current = Math.max(array[i], current + array[i]);
            best = Math.max(best, current);
        }
        return best;
    }

    public static void run(InputStream in, PrintStream out) {
        out.println(maxSubarraySum(new TokenReader(in).nextSizedIntArray()));
    }

    public static void main(String[] args) {
        run(System.in, System.out);
    }
}
