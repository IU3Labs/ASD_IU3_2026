package ru.bmstu.lab1.task_12;

import java.util.HashMap;
import java.util.Map;
import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 12. Пара с заданной суммой.
 *
 * <p>Вход: {@code n}, затем {@code n} целых чисел, затем {@code s}. Выход: индексы {@code i < j}, для которых
 * {@code a[i] + a[j] = s}; среди всех пар — с наименьшим {@code i}, затем с наименьшим {@code j}.
 * Если пары нет — {@code -1 -1}.
 *
 * <p>Идея (O(n) в среднем вместо перебора O(n^2)): хеш-таблица «значение → последний индекс».
 * Пара с данным {@code i} существует тогда и только тогда, когда последний индекс значения {@code s - a[i]}
 * больше {@code i}. Первое такое {@code i} — наименьшее; для него {@code j} ищется линейным проходом вправо.
 */
public final class PairWithSum {

    private static final int[] NO_PAIR = {-1, -1};

    private PairWithSum() {
    }

    /**
     * Находит лексикографически наименьшую пару индексов с заданной суммой.
     *
     * @param values массив
     * @param sum    требуемая сумма
     * @return {@code {i, j}} или {@code {-1, -1}}
     */
    public static int[] solve(int[] values, long sum) {
        Map<Long, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            lastIndex.put((long) values[i], i);
        }
        for (int i = 0; i < values.length; i++) {
            long complement = sum - values[i];
            Integer last = lastIndex.get(complement);
            if (last != null && last > i) {
                return new int[] {i, firstIndexAfter(values, i, complement)};
            }
        }
        return NO_PAIR.clone();
    }

    private static int firstIndexAfter(int[] values, int from, long target) {
        int j = from + 1;
        while (values[j] != target) {
            j++;
        }
        return j;
    }

    /**
     * Точка входа: читает массив и {@code s}, печатает пару индексов.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int[] values = in.nextIntArray(in.nextInt());
            out.println(ConsoleIo.join(solve(values, in.nextInt())));
        });
    }
}
