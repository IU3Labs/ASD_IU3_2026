/*
 * Задача 19. Объединение двух отсортированных массивов.
 * Вход: два массива, отсортированных по неубыванию, размеры n и m.
 * Выход: один отсортированный массив. Требование: O(n + m).
 */
package ru.bmstu.lab1.task_19;

/** Слияние двух отсортированных массивов методом двух указателей. */
public final class MergeSorted {

    private MergeSorted() {}

    /**
     * Сливает два отсортированных массива в один отсортированный.
     *
     * @param a первый отсортированный массив
     * @param b второй отсортированный массив
     * @return отсортированный массив из всех элементов a и b
     */
    public static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        // каждый раз берём меньший из двух текущих элементов
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        // один массив закончился, дописываем остаток другого
        while (i < a.length) {
            result[k++] = a[i++];
        }
        while (j < b.length) {
            result[k++] = b[j++];
        }
        return result;
    }
}
