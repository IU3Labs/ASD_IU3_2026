// Задача 12. Пара с заданной суммой.
// Вход: массив из n целых чисел и число s. Выход: индексы i < j, a[i] + a[j] = s,
// с наименьшим i, а при равенстве с наименьшим j. Если пары нет - "-1 -1".
package ru.bmstu.lab01.task_12;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 12: пара с суммой s за O(n) через HashMap.
 */
public class Task12 {

    /**
     * Ищет пару индексов с суммой s.
     *
     * @param a массив
     * @param s нужная сумма
     * @return {i, j} или {-1, -1}
     */
    public static int[] findPair(int[] a, long s) {
        // два вложенных цикла это O(n^2), для n = 10^5 долго.
        // храним значение -> индекс его первого появления
        Map<Long, Integer> first = new HashMap<>();
        int bestI = -1;
        int bestJ = -1;
        for (int j = 0; j < a.length; j++) {
            Integer i = first.get(s - a[j]);
            // j растёт, поэтому для одного i первым попадётся самый маленький j
            if (i != null && (bestI == -1 || i < bestI)) {
                bestI = i;
                bestJ = j;
            }
            first.putIfAbsent((long) a[j], j); // после проверки, чтобы не взять элемент с самим собой
        }
        return new int[] {bestI, bestJ};
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        long s = in.nextLong();
        int[] res = findPair(a, s);
        System.out.println(res[0] + " " + res[1]);
    }
}
