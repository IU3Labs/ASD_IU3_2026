package ru.bmstu.lab1.task_40;
import java.util.Scanner;
import ru.bmstu.lab1.ArrayTools;

/** Задача 40. Удалить дубликаты из отсортированного массива на месте. */
public class Task40 {
    /** Читает входные данные и выводит результат. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = ArrayTools.readArray(scanner, n);
        int size = removeDuplicates(array);
        ArrayTools.printArray(array, size);
    }

    /** Записывает уникальные элементы в начало и возвращает их количество. */
    public static int removeDuplicates(int[] array) {
        int size = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] != array[size - 1]) {
                array[size] = array[i];
                size++;
            }
        }
        return size;
    }
}