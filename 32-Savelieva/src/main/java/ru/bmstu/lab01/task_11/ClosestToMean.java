/**
 * Задача 11. Ближайший к среднему.
 * Вход: массив из n целых чисел.
 * Выход: элемент массива, ближайший к среднему арифметическому.
 * При равенстве — первый по индексу.
 * Сложность: средняя.
 */
package ru.bmstu.lab01.task_11;

import java.util.Scanner;

/**
 * Поиск элемента, ближайшего к среднему арифметическому массива.
 */
public final class ClosestToMean {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private ClosestToMean() {
    }

    /**
     * Возвращает элемент, ближайший к среднему. Сравнение ведётся через
     * {@code |a[i] * n - sum|}, чтобы избежать погрешности {@code double}
     * и одновременно не переполнить {@code int}: произведение копится в {@code long}.
     *
     * @param data непустой массив
     * @return элемент, ближайший к среднему; при равенстве — первый по индексу
     * @throws IllegalArgumentException если массив {@code null} или длина вне границ
     */
    public static int findClosest(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        long sum = 0L;
        for (int value : data) {
            sum += value;
        }
        long n = data.length;
        int bestIndex = 0;
        long bestDistance = Math.abs((long) data[0] * n - sum);
        for (int i = 1; i < data.length; i++) {
            long distance = Math.abs((long) data[i] * n - sum);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestIndex = i;
            }
        }
        return data[bestIndex];
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
            System.out.println(findClosest(data));
        }
    }
}