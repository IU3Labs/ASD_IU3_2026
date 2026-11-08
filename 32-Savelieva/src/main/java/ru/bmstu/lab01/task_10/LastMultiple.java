/**
 * Задача 10. Поиск элемента с условием.
 * Вход: массив из n целых чисел и число k (k != 0).
 * Выход: индекс последнего элемента, кратного k, или -1, если такого нет.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_10;

import java.util.Scanner;

/**
 * Поиск последнего индекса элемента, кратного заданному числу.
 */
public final class LastMultiple {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    /** Значение, сигнализирующее об отсутствии подходящего элемента. */
    private static final int NOT_FOUND = -1;

    private LastMultiple() {
    }

    /**
     * Возвращает индекс последнего элемента, кратного {@code divisor}.
     * Идём с конца — так первый найденный и есть последний в массиве,
     * ранний выход экономит проход.
     *
     * @param data    входной массив
     * @param divisor ненулевое число, на кратность которому проверяем
     * @return индекс последнего кратного или {@code -1}
     * @throws IllegalArgumentException если массив {@code null}, длина вне границ или {@code divisor == 0}
     */
    public static int findLastMultipleIndex(int[] data, int divisor) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        if (divisor == 0) {
            throw new IllegalArgumentException("k не может быть нулём");
        }
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i] % divisor == 0) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Точка входа. Формат ввода: n, затем n чисел, затем k.
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
            int divisor = scanner.nextInt();
            if (divisor == 0) {
                System.out.println("Ошибка: k не может быть нулём");
                return;
            }
            System.out.println(findLastMultipleIndex(data, divisor));
        }
    }
}