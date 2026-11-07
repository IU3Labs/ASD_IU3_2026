package ru.bmstu.lab1.task_37;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 37. Разворот массива.
 * Вход: массив из n целых чисел.
 * Выход: массив, записанный в обратном порядке.
 * Разворот делается на месте, новый массив не создаётся.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * Вывод: числа через пробел.
 */

/**
 * Разворачивает массив на месте.
 */
public class ReverseArray {

    /**
     * Меняет местами элементы с краёв и двигается к середине.
     */
    public static int[] reverse(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int temporary = numbers[left];
            numbers[left] = numbers[right];
            numbers[right] = temporary;
            left++;
            right--;
        }
        return numbers;
    }

    /**
     * Читает массив, разворачивает его и печатает.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        reverse(numbers);
        ArrayIO.printNumbers(numbers);
    }
}
