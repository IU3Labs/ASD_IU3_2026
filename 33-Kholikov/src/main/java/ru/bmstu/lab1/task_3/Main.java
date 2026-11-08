package ru.bmstu.lab1.task_3;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 3. Подсчитать количество элементов, равных заданному числу. */
public final class Main {
    private Main() {
    }

    /** Читает массив и искомое число, выводит количество совпадений. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            int target = scanner.nextInt();
            System.out.println(countOccurrences(numbers, target));
        }
    }

    /** Подсчитывает совпадения за O(n) времени и O(1) дополнительной памяти. */
    public static int countOccurrences(int[] numbers, int target) {
        int count = 0;
        for (int number : numbers) {
            if (number == target) {
                count++;
            }
        }
        return count;
    }
}
