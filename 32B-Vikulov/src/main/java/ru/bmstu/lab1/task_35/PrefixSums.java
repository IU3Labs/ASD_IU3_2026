package ru.bmstu.lab1.task_35;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 35. Префиксные суммы.
 *
 * <p>Вход: {@code n}, {@code n} чисел, {@code q}, затем {@code q} пар {@code l r} (индексы с нуля, включительно).
 * Выход: для каждого запроса сумма {@code a[l..r]} на отдельной строке.
 *
 * <p>{@code prefix[i]} — сумма первых {@code i} элементов ({@code prefix[0] = 0}), тогда
 * {@code sum(l, r) = prefix[r + 1] - prefix[l]}. Предпосчёт O(n), запрос O(1), память O(n).
 */
public final class PrefixSums {

    private final long[] prefix;

    /**
     * Строит префиксные суммы массива.
     *
     * @param values исходный массив
     */
    public PrefixSums(int[] values) {
        prefix = new long[values.length + 1];
        for (int i = 0; i < values.length; i++) {
            prefix[i + 1] = prefix[i] + values[i];
        }
    }

    /**
     * Возвращает сумму элементов на отрезке.
     *
     * @param left  левая граница (включительно)
     * @param right правая граница (включительно)
     * @return сумма {@code a[left..right]}
     * @throws IndexOutOfBoundsException если отрезок некорректен
     */
    public long sum(int left, int right) {
        if (left < 0 || right >= prefix.length - 1 || left > right) {
            throw new IndexOutOfBoundsException("Invalid range [" + left + ", " + right + "]");
        }
        return prefix[right + 1] - prefix[left];
    }

    /**
     * Точка входа: читает массив и запросы, печатает ответы.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            PrefixSums sums = new PrefixSums(in.nextIntArray(in.nextInt()));
            int queries = in.nextInt();
            for (int i = 0; i < queries; i++) {
                int left = in.nextInt();
                out.println(sums.sum(left, in.nextInt()));
            }
        });
    }
}
