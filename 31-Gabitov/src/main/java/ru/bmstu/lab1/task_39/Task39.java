package ru.bmstu.lab1.task_39;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 39. Циклически сдвинуть массив вправо на k позиций. */
public class Task39 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        int k = scanner.nextInt();
        int[] result = shift(array, k);
        ArrayTools.printArray(result, n);
    }

    /** Возвращает массив после сдвига вправо; k может быть больше длины. */
    public static int[] shift(int[] array, int k) {
        int n = array.length;
        k %= n;
        if (k < 0) {
            k += n;
        }
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[(i + k) % n] = array[i];
        }
        return result;
    }
}