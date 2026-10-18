package ru.bmstu.lab1.task_2;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 2. Найти индекс первого вхождения числа или вывести -1. */
public final class Main {
    private Main() {
    }

    /** Читает массив и искомое число, выводит индекс первого совпадения. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            int target = scanner.nextInt();
            System.out.println(firstIndex(numbers, target));
        }
    }

    /** Ищет первое совпадение за O(n) времени и O(1) дополнительной памяти. */
    public static int firstIndex(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
