package ru.bmstu.lab1.task_36;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 36. Построить массив сумм первых i + 1 элементов исходного массива. */
public final class Main {
    private Main() {
    }

    /** Читает массив и выводит накопленные суммы. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            ArrayIo.printArray(runningSums(numbers), System.out);
        }
    }

    /** Возвращает бегущие суммы за O(n) времени, используя O(n) памяти для результата. */
    public static long[] runningSums(int[] numbers) {
        long[] result = new long[numbers.length];
        long sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            result[i] = sum;
        }
        return result;
    }
}
