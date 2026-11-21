/*Задача 8. Фильтрация по знаку
Вход: массив из (n) целых чисел.
Выход: два массива — из положительных и из отрицательных элементов, в исходном порядке.
Нули не включать ни в один.
Сложность: базовая */

package ru.bmstu.lab1.task_8;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Решение задачи 8: разделение массива на положительные и отрицательные элементы.
 */
public class Filtration {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, печатает два списка: положительные и отрицательные элементы
     * в исходном порядке. Нули не попадают ни в один список.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> positive = new ArrayList<>();
        ArrayList<Integer> negative = new ArrayList<>();

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

        for (int i = 0; i < n; i++) {
            if (a[i] > 0) {
                positive.add(a[i]); 
            }
            if (a[i] < 0) {
                negative.add(a[i]);
            }
        }

        System.out.println(positive);
        System.out.println(negative);
        scanner.close();
    }
}