package ru.bmstu.lab1.task40;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 40. Удаление дубликатов.
 * Ввод: число n, затем n целых чисел, отсортированных по неубыванию.
 * Вывод: числа без повторов через пробел.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает отсортированный массив, удаляет повторы на месте и выводит результат.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        int count = DuplicateRemover.removeDuplicates(arr);
        System.out.println(ArrayIo.toLine(arr, count));
    }
}
