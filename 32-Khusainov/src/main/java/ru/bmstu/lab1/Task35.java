package ru.bmstu.lab1;

/**
 * Решение задачи 35: префиксные суммы.
 */
public class Task35 {

    /**
     * Предпосчитывает префиксные суммы и отвечает на запросы суммы на отрезке [l, r].
     *
     * @param array   входной массив целых чисел
     * @param queries массив запросов, где queries[i] = {l, r}
     * @return массив результатов (сумм) для каждого запроса
     */
    public static long[] rangeSumQueries(int[] array, int[][] queries) {
        long[] prefix = new long[array.length + 1];
        
        for (int i = 0; i < array.length; i++) {
            prefix[i + 1] = prefix[i] + array[i];
        }

        long[] result = new long[queries.length];
        
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            result[i] = prefix[r + 1] - prefix[l];
        }

        return result;
    }
}