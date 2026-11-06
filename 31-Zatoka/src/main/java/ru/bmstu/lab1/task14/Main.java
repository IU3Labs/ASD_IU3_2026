package ru.bmstu.lab1.task14;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 14. Сортировка вставками.
 * Ввод: число n, затем n целых чисел.
 * Вывод: элементы массива по невозрастанию через пробел.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает массив с клавиатуры, сортирует его вставками и выводит.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = ArrayIo.readArray(scanner);
        InsertionSort.sort(arr);
        System.out.println(ArrayIo.toLine(arr));
    }
}
