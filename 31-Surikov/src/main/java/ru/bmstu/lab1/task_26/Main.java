// задача 26 — произведение элементов по модулю 10^9 + 7
package ru.bmstu.lab1.task_26;

import java.io.PrintWriter;
import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/** произведение элементов по модулю с поддержкой отрицательных чисел */
public final class Main {
    private Main() {
    }

    private static final long MODULUS = 1_000_000_007L;

    /** возвращает неотрицательный остаток произведения; для пустого массива — 1 */
    public static long productModulo(int[] values) {
        long product = 1;
        for (int value : values) {

            long normalized = Math.floorMod((long) value, MODULUS);
            product = product * normalized % MODULUS;
        }
        return product;
    }

    /** читает входные данные и выводит ответ */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        int[] values = ArrayIo.readArray(input);
        output.println(productModulo(values));
        output.flush();
    }
}
