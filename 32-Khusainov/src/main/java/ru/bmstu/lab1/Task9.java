package ru.bmstu.lab1;

/**
 * Решение задачи 9: проверка массива на неубывание.
 */
public class Task9 {

    /**
     * Проверяет, является ли массив неубывающим.
     *
     * @param array входной массив целых чисел
     * @return "YES", если массив неубывающий, иначе "NO"
     */
    public static String checkNonDecreasing(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return "NO";
            }
        }

        return "YES";
    }
}