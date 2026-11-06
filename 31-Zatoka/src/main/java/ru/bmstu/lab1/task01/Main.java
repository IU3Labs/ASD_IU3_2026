package ru.bmstu.lab1.task01;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 1. Максимум и минимум.
 * Ввод: число n, затем n целых чисел.
 * Вывод: минимум и максимум через пробел.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив с клавиатуры и выводит его минимум и максимум.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        int[] result = MinMaxFinder.findMinMax(arr);
        System.out.println(result[0] + " " + result[1]);
    }
}
