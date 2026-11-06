package ru.bmstu.lab1.task_26;

import java.io.IOException;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 26. Найти произведение элементов по модулю 10^9 + 7. */
public final class Main {
    private static final long MODULUS = 1_000_000_007L;

    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static long solve(int[] values) {
        long product = 1;
        for (int number : values) {
            long normalizedValue = Math.floorMod((long) number, MODULUS);
            product = product * normalizedValue % MODULUS;
        }
        return product;
    }

    /** Читает n и n целых чисел, затем печатает результат. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values));
    }
}
