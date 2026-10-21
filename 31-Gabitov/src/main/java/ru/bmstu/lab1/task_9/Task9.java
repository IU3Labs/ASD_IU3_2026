package ru.bmstu.lab1.task_9;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 9. Проверить, отсортирован ли массив по неубыванию. */
public class Task9 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        System.out.println(isSorted(array) ? "YES" : "NO");
    }

    /** Возвращает true, если каждый следующий элемент не меньше предыдущего. */
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
}