/*Задача 3. Подсчёт вхождений
Вход: массив из (n) целых чисел и число (x).
Выход: количество элементов, равных (x).
Сложность: базовая */

package ru.bmstu.lab1.task_3;

import java.util.Scanner;


/**
 * Решение задачи 3: подсчет элементов равных x.
 */
public class NumberElements {

    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Начальное значение счетчика: пока не найдено ни одного элемента. */
    private static final int INITIAL_COUNT = 0;

    /**
     * Читает n, n целых чисел и x, печатает кол-во элементов массива, равных x.
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

        // Читаем сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
        }

        
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int x = scanner.nextInt();

       
        int k = INITIAL_COUNT; 
        
        for (int i = 0; i < n; i++) {
            if (a[i] == x) {
                k += 1; 
            }
        }

        
        System.out.println(k);
        
        scanner.close();
    }

}
