/*
 * Задача 29. Мода массива.
 * Вход: массив из n целых чисел.
 * Выход: наиболее часто встречающийся элемент. Если несколько — вывести наименьший по значению.
 * Ограничения: 1 <= n <= 10^4.
 */
package ru.bmstu.lab01.task_29;

import java.util.Arrays;
import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Поиск моды массива.
 * Массив сортируется, после чего одинаковые числа стоят рядом, и остаётся найти самую длинную
 * группу одинаковых чисел.
 */
public class Task29 {

    /**
     * Читает массив (n, затем n чисел) и печатает его моду.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = ArrayUtils.readArray(scanner);
        System.out.println(findMode(array));
    }

    /**
     * Находит моду массива. Исходный массив не меняется.
     *
     * @param array непустой массив
     * @return самый частый элемент, при равной частоте — наименьший
     * @throws IllegalArgumentException если массив пустой
     */
    public static int findMode(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив пустой");
        }
        int[] sorted = Arrays.copyOf(array, array.length);
        Arrays.sort(sorted);

        int mode = sorted[0];
        int bestCount = 0;
        int i = 0;
        while (i < sorted.length) {
            int j = i;
            while (j < sorted.length && sorted[j] == sorted[i]) {
                j++;
            }
            // группа одинаковых чисел занимает позиции от i до j-1
            int count = j - i;
            // строгое ">" : массив отсортирован по возрастанию, поэтому при равной частоте
            // остаётся число, которое встретилось раньше, то есть меньшее
            if (count > bestCount) {
                bestCount = count;
                mode = sorted[i];
            }
            i = j;
        }
        return mode;
    }
}
