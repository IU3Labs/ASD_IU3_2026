/*Задача 46. Сжатие диапазона
Вход: массив из (n) целых чисел.
Выход: массив той же длины, где каждый элемент заменён его рангом — номером в отсортированном
порядке без дубликатов (начиная с 0).
Пример: [100, 50, 100, 300] -> [1, 0, 1, 2].
Сложность: высокая */

package ru.bmstu.lab1.task_46;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 46: сжатие координат — сортировка, удаление повторов и бинарный поиск ранга.
 */
public class RankCompression {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает через пробел ранг каждого элемента —
     * его номер среди различных значений массива, упорядоченных по возрастанию.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int n = scanner.nextInt();
            if (n < MIN_LENGTH || n > MAX_LENGTH) {
                System.out.println("Ошибка: некорректная длина массива");
                return;
            }

            // Читаем сам массив
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i] = scanner.nextInt();
            }

            // Копия массива, отсортированная и без повторов: b[0..k-1] — различные значения
            int[] b = a.clone();
            Arrays.sort(b);
            int k = 1;
            for (int i = 1; i < n; i++) {
                if (b[i] != b[k - 1]) {
                    b[k] = b[i];
                    k++;
                }
            }

            // Ранг элемента — его индекс в b[0..k-1]; ищем бинарным поиском
            for (int i = 0; i < n; i++) {
                a[i] = Arrays.binarySearch(b, 0, k, a[i]);
            }

            // Печатаем ранги через пробел
            for (int i = 0; i < n; i++) {
                System.out.print(a[i]);
                if (i < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
