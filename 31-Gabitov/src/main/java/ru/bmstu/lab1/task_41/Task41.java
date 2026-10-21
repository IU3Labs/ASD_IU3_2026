package ru.bmstu.lab1.task_41;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 41. Удалить все вхождения заданного числа из массива. */
public class Task41 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        int x = scanner.nextInt();
        int size = remove(array, x);
        ArrayTools.printArray(array, size);
    }

    /** Сохраняет остальные элементы в начале массива и возвращает новую длину. */
    public static int remove(int[] array, int x) {
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != x) {
                array[size] = array[i];
                size++;
            }
        }
        return size;
    }
}