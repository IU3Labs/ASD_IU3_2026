package ru.bmstu.lab1.task32;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 32. Сумма произведений пар.
 * Ввод: число n, затем n целых чисел.
 * Вывод: сумма a[i] * a[j] по всем парам {@code i < j}.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив с клавиатуры и выводит сумму произведений всех пар.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        System.out.println(PairProductSum.sum(arr));
    }
}
