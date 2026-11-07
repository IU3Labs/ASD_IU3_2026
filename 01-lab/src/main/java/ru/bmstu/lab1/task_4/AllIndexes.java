package ru.bmstu.lab1.task_4;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 4. Все индексы элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: все индексы элементов, равных x, в порядке возрастания.
 *
 * Ввод:
 * n x
 * a1 a2 ... an
 * Вывод: индексы через пробел. Если числа нет, строка пустая.
 */

/**
 * Собирает все места, где встречается заданное число.
 */
public class AllIndexes {

    /**
     * Возвращает индексы всех вхождений x от меньшего к большему.
     */
    public static int[] findAllIndexes(int[] numbers, int target) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                count++;
            }
        }

        int[] indexes = new int[count];
        int position = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                indexes[position] = i;
                position++;
            }
        }
        return indexes;
    }

    /**
     * Читает массив и печатает найденные индексы.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int target = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        ArrayIO.printNumbers(findAllIndexes(numbers, target));
    }
}
