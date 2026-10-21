package ru.bmstu.lab1.task_3;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 3. Подсчитать количество элементов, равных x. */
public class Task3 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        int x = scanner.nextInt();
        System.out.println(count(array, x));
    }

    /** Возвращает количество вхождений x в массив. */
    public static int count(int[] array, int x) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                count++;
            }
        }
        return count;
    }
}