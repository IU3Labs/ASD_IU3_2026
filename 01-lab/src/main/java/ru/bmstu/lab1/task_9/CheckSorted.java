package ru.bmstu.lab1.task_9;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 9. Проверка отсортированности.
 * Вход: массив из n целых чисел.
 * Выход: YES, если массив неубывающий, иначе NO.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 */

/**
 * Проверяет, идут ли числа по неубыванию.
 */
public class CheckSorted {

    /**
     * Возвращает true, если каждый следующий элемент не меньше предыдущего.
     */
    public static boolean isNonDecreasing(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < numbers[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Читает массив и печатает YES или NO.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        if (isNonDecreasing(numbers)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
