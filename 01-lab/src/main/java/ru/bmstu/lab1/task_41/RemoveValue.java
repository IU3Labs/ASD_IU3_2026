package ru.bmstu.lab1.task_41;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 41. Удаление элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: массив, из которого удалены все вхождения x.
 *
 * Ввод:
 * n x
 * a1 a2 ... an
 * Вывод: оставшиеся числа через пробел. Если ничего не осталось, строка пустая.
 */

/**
 * Убирает из массива все вхождения заданного числа.
 */
public class RemoveValue {

    /**
     * Возвращает новый массив без элементов, равных x. Порядок остальных сохраняется.
     */
    public static int[] removeValue(int[] numbers, int value) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != value) {
                count++;
            }
        }

        int[] result = new int[count];
        int position = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != value) {
                result[position] = numbers[i];
                position++;
            }
        }
        return result;
    }

    /**
     * Читает массив и число x, печатает массив без этого числа.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int value = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        ArrayIO.printNumbers(removeValue(numbers, value));
    }
}
