package ru.bmstu.lab1.task_2;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 2. Найти индекс первого вхождения числа x или вернуть -1. */
public class Task2 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        int x = scanner.nextInt();
        System.out.println(firstIndex(array, x));
    }

    /** Возвращает индекс первого совпадения или -1, если совпадений нет. */
    public static int firstIndex(int[] array, int x) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                return i;
            }
        }
        return -1;
    }
}