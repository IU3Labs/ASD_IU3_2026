/*Задача 7. Второй максимум
Вход: массив из (n) целых чисел. Все элементы различны.
Выход: второй по величине элемент массива.
Сложность: базовая */

package ru.bmstu.lab1.task_7;

import java.util.Scanner;

/**
 * Решение задачи 7: вывод второго по величине элемента массива за один проход.
 */
public class SecondMax {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, печатает второй по величине элемент.
     * Массив не хранится: первый и второй максимумы обновляются прямо при чтении.
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

        // Первое число сразу становится максимумом, второго максимума пока нет
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int one = scanner.nextInt();
        int two = Integer.MIN_VALUE;

        for (int i = 1; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int current = scanner.nextInt();
            
            if (current > one) {
                // Новый максимум: старый максимум становится вторым
                two = one;
                one = current;
            } else if (current > two) {
                // Меньше максимума, но больше второго — новый второй
                two = current;
            }
        }
        System.out.println(two);
        scanner.close();
    }
}