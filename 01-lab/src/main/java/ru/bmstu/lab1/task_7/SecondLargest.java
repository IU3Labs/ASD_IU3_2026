package ru.bmstu.lab1.task_7;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 7. Второй максимум.
 * Вход: массив из n целых чисел. Все элементы различны.
 * Выход: второй по величине элемент массива.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 */

/**
 * Ищет второе по величине число.
 */
public class SecondLargest {

    /**
     * Возвращает второй максимум. В массиве все числа разные.
     */
    public static int findSecondLargest(int[] numbers) {
        // Сначала сравниваю первые два числа, потом смотрю остальные.
        int largest;
        int second;
        if (numbers[0] > numbers[1]) {
            largest = numbers[0];
            second = numbers[1];
        } else {
            largest = numbers[1];
            second = numbers[0];
        }

        for (int i = 2; i < numbers.length; i++) {
            if (numbers[i] > largest) {
                second = largest;
                largest = numbers[i];
            } else if (numbers[i] > second) {
                second = numbers[i];
            }
        }
        return second;
    }

    /**
     * Читает массив и печатает второй максимум.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(findSecondLargest(numbers));
    }
}
