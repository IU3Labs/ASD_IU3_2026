package ru.bmstu.lab1.task_41;

import java.util.Scanner;

/**
 * Задача 41. Удаление элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: массив, из которого удалены все вхождения x.
 */
public final class Main {

    private Main() {
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        int target = scanner.nextInt();

        // Указатель записи для фильтрации на месте
        int writeIndex = 0;
        for (int i = 0; i < n; i++) {
            if (array[i] != target) {
                array[writeIndex] = array[i];
                writeIndex++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < writeIndex; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(array[i]);
        }
        System.out.println(sb);
    }
}