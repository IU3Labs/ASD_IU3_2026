package ru.bmstu.lab1.task_26;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 26. Произведение элементов.
 * Вход: массив из n целых чисел.
 * Выход: произведение всех элементов по модулю 10^9 + 7.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 */

/**
 * Считает произведение элементов по модулю 10^9 + 7.
 */
public class ArrayProduct {

    private static final long MODULO = 1000000007L;

    /**
     * Возвращает произведение по модулю. Остаток всегда неотрицательный.
     */
    public static long productModulo(int[] numbers) {
        long product = 1;
        for (int i = 0; i < numbers.length; i++) {
            long current = numbers[i] % MODULO;
            // У отрицательного числа остаток в Java тоже отрицательный.
            if (current < 0) {
                current = current + MODULO;
            }
            product = (product * current) % MODULO;
        }
        return product;
    }

    /**
     * Читает массив и печатает произведение по модулю.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(productModulo(numbers));
    }
}
