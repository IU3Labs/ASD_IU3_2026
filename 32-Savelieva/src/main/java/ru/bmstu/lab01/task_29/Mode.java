/**
 * Задача 29. Мода массива.
 * Вход: массив из n целых чисел (1 <= n <= 10^4).
 * Выход: наиболее часто встречающийся элемент; при равенстве — наименьший по значению.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_29;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Поиск моды массива.
 */
public final class Mode {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n (по условию). */
    private static final int UPPER_BOUND = 10_000;

    private Mode() {
    }

    /**
     * Возвращает моду — самое частое значение. При равенстве частот — наименьшее значение.
     *
     * <p>{@link HashMap} для подсчёта частот даёт O(1) в среднем на вставку.
     * Сортировка массива дала бы O(n log n), но тогда пришлось бы отдельно
     * искать первое вхождение каждой серии. Хеш-таблица проще и быстрее.
     *
     * @param data входной массив
     * @return мода
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static int mode(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int value : data) {
            frequency.merge(value, 1, Integer::sum);
        }
        int best = data[0];
        int bestCount = -1;
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            int value = entry.getKey();
            int count = entry.getValue();
            if (count > bestCount || (count == bestCount && value < best)) {
                best = value;
                bestCount = count;
            }
        }
        return best;
    }

    /**
     * Точка входа. Формат ввода: n, затем n чисел.
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
            System.out.println(mode(data));
        }
    }
}