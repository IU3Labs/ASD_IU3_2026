package ru.bmstu.lab1.task19;

/**
 * Задача 19. Объединение двух отсортированных массивов.
 * Вход: два массива, каждый отсортирован по неубыванию; размеры n и m.
 * Выход: один отсортированный массив - результат слияния.
 * Требование: O(n + m).
 *
 * <p>Идея: держим по указателю на начало каждого массива. На каждом шаге
 * берём меньший из двух текущих элементов, кладём его в результат
 * и сдвигаем указатель того массива, откуда взяли.
 */
public class SortedMerger {

    private SortedMerger() {
    }

    /**
     * Сливает два отсортированных по неубыванию массива в один.
     *
     * @param first первый отсортированный массив
     * @param second второй отсортированный массив
     * @return новый массив длины n + m, отсортированный по неубыванию
     */
    public static int[] merge(int[] first, int[] second) {
        int[] result = new int[first.length + second.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < first.length && j < second.length) {
            // при равенстве берём из первого массива, порядок равных сохраняется
            if (first[i] <= second[j]) {
                result[k++] = first[i++];
            } else {
                result[k++] = second[j++];
            }
        }
        // один из массивов закончился, остаток другого уже отсортирован
        while (i < first.length) {
            result[k++] = first[i++];
        }
        while (j < second.length) {
            result[k++] = second[j++];
        }
        return result;
    }
}
