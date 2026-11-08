package ru.bmstu.lab1.task_26;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 26. Вычислить произведение элементов по модулю 10^9 + 7. */
public final class Main {
    private static final long MODULUS = 1_000_000_007L;

    private Main() {
    }

    /** Читает массив и выводит произведение по модулю. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            System.out.println(productModulo(numbers));
        }
    }

    /** Возвращает неотрицательный остаток произведения за O(n) времени. */
    public static long productModulo(int[] numbers) {
        long result = 1;
        for (int number : numbers) {
            // Нормализация нужна и для отрицательных элементов массива.
            long normalized = Math.floorMod((long) number, MODULUS);
            result = result * normalized % MODULUS;
        }
        return result;
    }
}
