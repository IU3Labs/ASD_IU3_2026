package ru.bmstu.lab1.task32;

import java.math.BigInteger;

/**
 * Задача 32. Сумма произведений пар.
 * Вход: массив из n целых чисел.
 * Выход: сумма произведений всех пар элементов a[i] * a[j] для {@code i < j}.
 * Требование: O(n).
 *
 * <p>Идея: каждый элемент a[j] входит в пары со всеми элементами левее него,
 * поэтому его вклад равен a[j] * (a[0] + a[1] + ... + a[j - 1]).
 * Сумму левой части (префикс) обновляем на ходу, и всё считается за один проход.
 */
public class PairProductSum {

    private PairProductSum() {
    }

    /**
     * Считает сумму a[i] * a[j] по всем парам {@code i < j} за один проход.
     * Ответ может быть порядка 10^28 и не влезает даже в long,
     * поэтому он хранится в BigInteger. Префиксная сумма не больше 2 * 10^14
     * и спокойно хранится в long.
     *
     * @param arr массив чисел
     * @return сумма произведений всех пар (0, если пар нет)
     */
    public static BigInteger sum(int[] arr) {
        BigInteger result = BigInteger.ZERO;
        long prefix = 0;
        for (int j = 0; j < arr.length; j++) {
            BigInteger contribution = BigInteger.valueOf(arr[j]).multiply(BigInteger.valueOf(prefix));
            result = result.add(contribution);
            prefix += arr[j];
        }
        return result;
    }
}
