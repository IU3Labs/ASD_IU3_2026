package ru.bmstu.lab1.task13;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 13. Сортировка выбором.
 * Ввод: число n, затем n целых чисел.
 * Вывод: элементы массива по неубыванию через пробел.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив с клавиатуры, сортирует его выбором и выводит.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        SelectionSort.sort(arr);
        System.out.println(ArrayIo.toLine(arr));
    }
}
