package ru.bmstu.lab1.task_37;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 37. Развернуть массив на месте без создания нового массива. */
public final class Main {
    private Main() {
    }

    /** Читает массив, разворачивает его и выводит результат. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            reverse(numbers);
            ArrayIo.printArray(numbers, System.out);
        }
    }

    /** Разворачивает исходный массив за O(n) времени и O(1) дополнительной памяти. */
    public static void reverse(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int temporary = numbers[left];
            numbers[left] = numbers[right];
            numbers[right] = temporary;
            left++;
            right--;
        }
    }
}
