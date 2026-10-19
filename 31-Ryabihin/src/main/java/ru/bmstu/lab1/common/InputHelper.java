package ru.bmstu.lab1.common;

import java.util.Scanner;

/** Чтение массивов из входных данных. Нужно во всех задачах, поэтому вынесено сюда. */
public final class InputHelper {

    private InputHelper() {}

    /**
     * Читает сначала размер n, потом n чисел.
     *
     * @param scanner откуда читать
     * @return прочитанный массив
     */
    public static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }
}
