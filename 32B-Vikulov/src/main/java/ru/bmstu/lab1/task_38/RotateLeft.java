package ru.bmstu.lab1.task_38;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 38. Циклический сдвиг влево.
 *
 * <p>Вход: {@code n}, {@code n} чисел, затем {@code k}. Выход: массив, сдвинутый влево на {@code k} позиций.
 *
 * <p>Тройной разворот: развернуть {@code [0, k)}, развернуть {@code [k, n)}, развернуть весь массив.
 * Пример для {@code k = 2}: {@code 1 2 3 4 5 → 2 1 5 4 3 → 3 4 5 1 2}.
 * Каждый элемент переставляется константное число раз: O(n) по времени, O(1) доп. памяти.
 */
public final class RotateLeft {

    private RotateLeft() {
    }

    /**
     * Сдвигает массив циклически влево на месте.
     *
     * @param values массив
     * @param shift  величина сдвига; может быть больше {@code n} или отрицательной (сдвиг вправо)
     */
    public static void rotate(int[] values, long shift) {
        int n = values.length;
        if (n == 0) {
            return;
        }
        // приводим сдвиг к [0, n): сдвиг на n не меняет массив, отрицательный — это сдвиг вправо
        int k = (int) (((shift % n) + n) % n);
        reverse(values, 0, k - 1);
        reverse(values, k, n - 1);
        reverse(values, 0, n - 1);
    }

    private static void reverse(int[] values, int from, int to) {
        while (from < to) {
            int tmp = values[from];
            values[from++] = values[to];
            values[to--] = tmp;
        }
    }

    /**
     * Точка входа: читает массив и {@code k}, печатает сдвинутый массив.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int[] values = in.nextIntArray(in.nextInt());
            rotate(values, in.nextInt());
            out.println(ConsoleIo.join(values));
        });
    }
}
