package ru.bmstu.lab1.task_10;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 10. Поиск элемента с условием.
 * Вход: массив из n целых чисел и число k (k не равно 0).
 * Выход: индекс последнего элемента, кратного k. Если такого нет — -1.
 *
 * Ввод:
 * n k
 * a1 a2 ... an
 */

/**
 * Ищет последнее число, которое делится на k.
 */
public class LastDivisibleIndex {

    /**
     * Возвращает индекс последнего элемента, кратного k, или -1.
     */
    public static int findLastDivisibleIndex(int[] numbers, int divisor) {
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] % divisor == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Читает массив и делитель, печатает индекс.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int divisor = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(findLastDivisibleIndex(numbers, divisor));
    }
}
