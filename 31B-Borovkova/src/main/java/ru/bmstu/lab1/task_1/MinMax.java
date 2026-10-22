/*
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 * Ограничения: 1 < n < 10^5.
 * Сложность: базовая.
 */
package ru.bmstu.lab1.task_1;

import java.util.Scanner;

/**
 * Решение задачи 1: поиск минимума и максимума за один проход без хранения массива.
 */
public class MinMax {

    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;

    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n и n целых чисел, печатает минимум и максимум.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите длину массива");
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int n = scanner.nextInt();
        if (n < MIN_LENGTH || n > MAX_LENGTH) {
            System.out.println("Ошибка: некорректная длина массива");
            return;
        }

        System.out.println("Введите массив");
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int min = scanner.nextInt();
        int max = min;

        for (int i = 1; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int current = scanner.nextInt();
            if (current < min) {
                min = current;
            }
            if (current > max) {
                max = current;
            }
        }
        System.out.println(min + " " + max);
        scanner.close();
    }
}