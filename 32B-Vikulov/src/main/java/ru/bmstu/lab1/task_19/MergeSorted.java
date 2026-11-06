package ru.bmstu.lab1.task_19;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 19. Объединение двух отсортированных массивов.
 *
 * <p>Вход: {@code n}, {@code n} чисел, {@code m}, {@code m} чисел (оба массива по неубыванию).
 * Выход: отсортированный результат слияния. Два указателя: каждый шаг переносит один элемент,
 * поэтому O(n + m) по времени и O(n + m) памяти под результат.
 */
public final class MergeSorted {

    private MergeSorted() {
    }

    /**
     * Сливает два отсортированных массива.
     *
     * @param left  первый массив по неубыванию
     * @param right второй массив по неубыванию
     * @return новый массив длины {@code n + m} по неубыванию
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            // <= сохраняет устойчивость: при равенстве первым идёт элемент левого массива
            result[k++] = left[i] <= right[j] ? left[i++] : right[j++];
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        return result;
    }

    /**
     * Точка входа: читает два массива, печатает результат слияния.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int[] left = in.nextIntArray(in.nextInt());
            int[] right = in.nextIntArray(in.nextInt());
            out.println(ConsoleIo.join(merge(left, right)));
        });
    }
}
