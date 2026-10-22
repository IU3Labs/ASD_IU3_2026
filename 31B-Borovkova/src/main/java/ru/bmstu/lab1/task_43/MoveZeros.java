/*Задача 43. Перемещение нулей в конец
Вход: массив из (n) целых чисел.
Выход: тот же массив, в котором все нули перемещены в конец, порядок остальных элементов сохранён.
Требование: модификация на месте.
Сложность: средняя */

package ru.bmstu.lab1.task_43;

import java.util.Scanner;

/**
 * Решение задачи 43: перенос нулей в конец массива на месте с сохранением порядка ненулевых.
 */
public class MoveZeros {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, переносит все нули в конец на месте
     * (порядок ненулевых сохраняется) и печатает массив через пробел.
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

            // k — сколько ненулевых уже собрано в начале массива:
            // каждый ненулевой элемент переписываем на позицию k по порядку
            int k = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] != 0) {
                    a[k] = a[i];
                    k++;
                }
            }
            // Оставшиеся позиции заполняем нулями — их ровно столько, сколько было нулей
            for (int i = k; i < n; i++) {
                a[i] = 0;
            }

            // Печатаем массив через пробел
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
