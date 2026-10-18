package ru.bmstu.lab1.task_25;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 25. Вычислить сумму всех элементов массива. */
public final class Main {
    private Main() {
    }

    /** Читает условие задачи из System.in и выводит ответ в System.out. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            System.out.println(sum(numbers));
        }
    }

    /** Возвращает сумму за O(n) времени и O(1) дополнительной памяти. */
    public static long sum(int[] numbers) {
        long result = 0;
        for (int number : numbers) {
            result += number;
        }
        return result;
    }

}
