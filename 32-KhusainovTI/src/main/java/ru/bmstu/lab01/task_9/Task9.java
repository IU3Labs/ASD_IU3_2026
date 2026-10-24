/*
 * Задача 9. Проверка отсортированности.
 * Вход: массив из n целых чисел.
 * Выход: YES, если массив неубывающий, иначе NO.
 */

package ru.bmstu.lab01.task_9;

import java.util.Scanner;

/** Решение задачи 9. */
public class Task9 {

    /**
     * Считывает массив и выводит результат проверки его отсортированности.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numberOfElements = input.nextInt();
        int[] numbers = new int[numberOfElements];
        for (int index = 0; index < numberOfElements; index++) {
            numbers[index] = input.nextInt();
        }
        if (isSorted(numbers)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    /**
     * Проверяет, расположен ли массив по неубыванию.
     *
     * @param numbers массив целых чисел
     * @return true, если каждый следующий элемент не меньше предыдущего
     */
    public static boolean isSorted(int[] numbers) {
        for (int index = 1; index < numbers.length; index++) {
            if (numbers[index] < numbers[index - 1]) {
                return false;
            }
        }
        return true;
    }
}
