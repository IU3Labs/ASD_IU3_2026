package ru.bmstu.lab1.task_27;

import java.io.IOException;
import java.math.BigInteger;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 27. Найти точную сумму квадратов всех элементов массива. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static BigInteger solve(int[] values) {
        BigInteger sumOfSquares = BigInteger.ZERO;
        for (int number : values) {
            long square = (long) number * number;
            sumOfSquares = sumOfSquares.add(BigInteger.valueOf(square));
        }
        return sumOfSquares;
    }

    /** Читает n и n целых чисел, затем печатает результат. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(solve(values));
    }
}
