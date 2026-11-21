/*Задача 16. Сортировка по модулю
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию модуля элементов. При равных модулях первым идёт
отрицательный элемент.
Сложность: средняя */

package ru.bmstu.lab1.task_16;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 16: сортировка по модулю через сортировку ключей вида 2·|x| + (x < 0 ? 0 : 1).
 */
public class AbsSort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, печатает их через пробел по неубыванию модуля;
     * при равных модулях отрицательный элемент идёт раньше положительного.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int n = scanner.nextInt();
        if (n < MIN_LENGTH || n > MAX_LENGTH) {
            System.out.println("Ошибка: некорректная длина массива");
            return;
        }

        // Читаем массив и сразу переводим каждое число в ключ k = 2·|x| + (x < 0 ? 0 : 1).
        // Порядок ключей совпадает с нужным порядком: сначала по модулю, при равном модуле
        // отрицательное (+0) раньше положительного (+1). long нужен, так как |-2^31| = 2^31.
        long[] k = new long[n];
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            long x = scanner.nextInt();
            if (x < 0) {
                k[i] = -x * 2;
            } else {
                k[i] = x * 2 + 1;
            }
        }

        Arrays.sort(k);

        // Восстанавливаем число по ключу: модуль — k / 2, знак — по чётности ключа
        for (int i = 0; i < n; i++) {
            long abs = k[i] / 2;
            if (k[i] % 2 == 0) {
                System.out.print(-abs);
            } else {
                System.out.print(abs);
            }
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}
