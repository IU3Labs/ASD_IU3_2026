package ru.bmstu.lab1.task_34;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 34. Найти максимальную сумму непустого непрерывного подмассива алгоритмом Кадане. */
public final class Main {
    private Main() {
    }

    /** Читает массив и выводит максимальную сумму подмассива. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            System.out.println(maxSubarraySum(numbers));
        }
    }

    /** Находит максимальную сумму за O(n) времени и O(1) дополнительной памяти. */
    public static long maxSubarraySum(int[] numbers) {
        // Начало с первого элемента сохраняет правильный ответ для всех отрицательных чисел.
        long currentSum = numbers[0];
        long bestSum = currentSum;
        for (int i = 1; i < numbers.length; i++) {
            currentSum = Math.max((long) numbers[i], currentSum + numbers[i]);
            bestSum = Math.max(bestSum, currentSum);
        }
        return bestSum;
    }
}
