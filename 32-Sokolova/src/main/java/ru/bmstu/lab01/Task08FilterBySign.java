package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 8. Фильтрация по знаку.
 * Вход: массив.
 * Выход: два массива: положительные и отрицательные.
 */
public class Task08FilterBySign {
    /**
     * Разделяет массив на положительные и отрицательные.
     * @param arr исходный массив
     * @return [positive[], negative[]]
     */
    public static int[][] filterBySign(int[] arr) {
        int posCount = 0, negCount = 0;
        for (int val : arr) {
            if (val > 0) posCount++;
            else if (val < 0) negCount++;
        }
        int[] pos = new int[posCount];
        int[] neg = new int[negCount];
        int p = 0, n = 0;
        for (int val : arr) {
            if (val > 0) pos[p++] = val;
            else if (val < 0) neg[n++] = val;
        }
        return new int[][]{pos, neg};
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        int[][] res = filterBySign(arr);
        StringBuilder sb = new StringBuilder();
        for (int v : res[0]) sb.append(v).append(" ");
        System.out.println(sb.toString().trim());
        sb.setLength(0);
        for (int v : res[1]) sb.append(v).append(" ");
        System.out.println(sb.toString().trim());
    }
}