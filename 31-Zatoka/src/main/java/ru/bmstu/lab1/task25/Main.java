package ru.bmstu.lab1.task25;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 25. Сумма элементов.
 * Ввод: число n, затем n целых чисел.
 * Вывод: сумма всех элементов.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив с клавиатуры и выводит сумму его элементов.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        System.out.println(ArraySum.sum(arr));
    }
}
