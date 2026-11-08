/**
 * Задача 12. Пара с заданной суммой.
 * Вход: массив из n целых чисел и число s.
 * Выход: индексы i < j, для которых a[i] + a[j] = s.
 * Если пар несколько — пара с наименьшим i, при равенстве — с наименьшим j.
 * Если пары нет — вывести "-1 -1".
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_12;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Поиск пары индексов с заданной суммой за O(n) в среднем.
 */
public final class PairWithSum {

    /** Минимально допустимое n: паре нужно хотя бы два элемента. */
    private static final int LOWER_BOUND = 2;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    /** Результат «пары нет». */
    private static final int[] NOT_FOUND = {-1, -1};

    private PairWithSum() {
    }

    /**
 * Ищет пару индексов {@code i < j} с суммой {@code target}.
 *
 * <p>Идём справа налево. В таблице держим самый левый индекс каждого значения
 * среди уже просмотренных (то есть минимальный {@code j > i}). Если для текущего
 * {@code i} в таблице есть {@code target - a[i]}, пара найдена. С каждой новой
 * парой {@code i} уменьшается, поэтому после цикла остаётся пара с наименьшим
 * {@code i}, а для неё — с наименьшим {@code j}.
 *
 * <p>Текущий элемент кладём в таблицу после проверки — иначе он мог бы
 * составить пару с самим собой (например, {@code 3 + 3 = 6} при единственной тройке).
 *
 * @param data   входной массив
 * @param target требуемая сумма
 * @return {@code [i, j]} или {@code [-1, -1]}, если пары нет
 * @throws IllegalArgumentException если массив {@code null} или его длина вне [2; 99 999]
 */

    public static int[] findPair(int[] data, int target) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        Map<Integer, Integer> nearestRight = new HashMap<>();
        int bestI = -1;
        int bestJ = -1;
        for (int i = data.length - 1; i >= 0; i--) {
            int complement = target - data[i];
            Integer j = nearestRight.get(complement);
            if (j != null) {
                bestI = i;
                bestJ = j;
            }
            nearestRight.put(data[i], i);
        }
        return bestI == -1 ? NOT_FOUND.clone() : new int[] {bestI, bestJ};
    }

    /**
     * Точка входа. Формат ввода: n, затем n чисел, затем s.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: ожидалось целое число");
                return;
            }
            int n = scanner.nextInt();
            if (n < LOWER_BOUND || n > UPPER_BOUND) {
                System.out.println("Ошибка: n вне допустимого диапазона");
                return;
            }
            int[] data = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: ожидалось целое число");
                    return;
                }
                data[i] = scanner.nextInt();
            }
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: ожидалось целое число");
                return;
            }
            int target = scanner.nextInt();
            int[] result = findPair(data, target);
            System.out.println(result[0] + " " + result[1]);
        }
    }
}