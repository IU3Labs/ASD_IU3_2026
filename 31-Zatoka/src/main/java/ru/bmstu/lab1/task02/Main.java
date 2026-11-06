package ru.bmstu.lab1.task02;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 2. Индекс первого вхождения.
 * Ввод: число n, затем n целых чисел, затем число x.
 * Вывод: индекс первого элемента, равного x, или -1.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив и число x с клавиатуры и выводит индекс первого вхождения x.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        int x = scanner.nextInt();
        System.out.println(FirstIndexFinder.findFirstIndex(arr, x));
    }
}
