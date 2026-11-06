package ru.bmstu.lab1.task11;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 11. Ближайший к среднему.
 * Ввод: число n, затем n целых чисел.
 * Вывод: элемент массива, ближайший к среднему арифметическому.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив с клавиатуры и выводит элемент, ближайший к среднему.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        System.out.println(ClosestToAverage.find(arr));
    }
}
