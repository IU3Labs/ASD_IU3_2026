package ru.bmstu.lab1.task_43;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 43. Переместить нули в конец массива, сохранив порядок остальных элементов. */
public class Task43 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        moveZeros(array);
        ArrayTools.printArray(array, n);
    }

    /** Сначала записывает ненулевые элементы, затем заполняет остаток нулями. */
    public static void moveZeros(int[] array) {
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[size] = array[i];
                size++;
            }
        }
        for (int i = size; i < array.length; i++) {
            array[i] = 0;
        }
    }
}