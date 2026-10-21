package ru.bmstu.lab1.task_37;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 37. Развернуть массив на месте. */
public class Task37 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        reverse(array);
        ArrayTools.printArray(array, n);
    }

    /** Меняет местами симметричные элементы исходного массива. */
    public static void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int last = array.length - 1 - i;
            int temp = array[i];
            array[i] = array[last];
            array[last] = temp;
        }
    }
}