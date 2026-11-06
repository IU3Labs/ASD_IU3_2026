package ru.bmstu.lab1.task_02;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 2. Индекс первого вхождения.
 *
 * <p>Вход: {@code n}, затем {@code n} целых чисел, затем {@code x}. Выход: индекс первого элемента,
 * равного {@code x}, или {@code -1}. Линейный поиск: O(n) по времени, O(1) доп. памяти.
 */
public final class FirstOccurrence {

    /** Ответ, когда элемент не найден. */
    public static final int NOT_FOUND = -1;

    private FirstOccurrence() {
    }

    /**
     * Ищет первое вхождение элемента.
     *
     * @param values массив
     * @param target искомое значение
     * @return индекс первого вхождения или {@link #NOT_FOUND}
     */
    public static int solve(int[] values, int target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Точка входа: читает массив и {@code x}, печатает индекс.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int[] values = in.nextIntArray(in.nextInt());
            out.println(solve(values, in.nextInt()));
        });
    }
}
