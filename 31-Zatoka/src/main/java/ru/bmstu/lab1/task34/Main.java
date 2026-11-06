package ru.bmstu.lab1.task34;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 34. Максимальная сумма подмассива.
 * Ввод: число n, затем n целых чисел.
 * Вывод: максимальная сумма непрерывного подмассива.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив с клавиатуры и выводит максимальную сумму подмассива.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        System.out.println(MaxSubarraySum.find(arr));
    }
}
