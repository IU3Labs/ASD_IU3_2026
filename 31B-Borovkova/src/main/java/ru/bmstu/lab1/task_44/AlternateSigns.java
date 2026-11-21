/*Задача 44. Чередование знаков
Вход: массив из (n) целых чисел, содержащий равное число положительных и отрицательных элементов
(нулей нет).
Выход: массив, в котором положительные и отрицательные элементы чередуются, начиная с положительного.
Относительный порядок внутри положительных и отрицательных сохранить.
Сложность: средняя */

package ru.bmstu.lab1.task_44;

import java.util.Scanner;

/**
 * Решение задачи 44: чередование положительных и отрицательных с сохранением их порядка.
 */
public class AlternateSigns {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n и n ненулевых целых чисел (положительных и отрицательных поровну),
     * печатает через пробел массив, где они чередуются, начиная с положительного.
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

            // Читаем массив, проверяем отсутствие нулей и считаем положительные
            int[] a = new int[n];
            int k = 0;
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i] = scanner.nextInt();
                if (a[i] == 0) {
                    System.out.println("Ошибка: в массиве не должно быть нулей");
                    return;
                }
                if (a[i] > 0) {
                    k++;
                }
            }
            if (2 * k != n) {
                System.out.println("Ошибка: положительных и отрицательных должно быть поровну");
                return;
            }

            // Раскладываем по знаку в исходном порядке
            int[] pos = new int[k];
            int[] neg = new int[k];
            int pi = 0;
            int ni = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] > 0) {
                    pos[pi] = a[i];
                    pi++;
                } else {
                    neg[ni] = a[i];
                    ni++;
                }
            }

            // Чётные позиции — положительные, нечётные — отрицательные
            for (int i = 0; i < k; i++) {
                a[2 * i] = pos[i];
                a[2 * i + 1] = neg[i];
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
