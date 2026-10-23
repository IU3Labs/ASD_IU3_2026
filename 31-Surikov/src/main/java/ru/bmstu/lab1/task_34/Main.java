// задача 34 — максимальная сумма непрерывного подмассива
package ru.bmstu.lab1.task_34;

import java.io.PrintWriter;
import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/** максимальная сумма непустого подмассива по алгоритму Кадане */
public final class Main {
    private Main() {
    }

    /** находит максимальную сумму; вход должен быть непустым массивом */
    public static long maximumSubarraySum(int[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Подмассив должен быть непустым");
        }
        // первый элемент вместо нуля нужен для полностью отрицательного массива
        long endingHere = values[0];
        long maximum = endingHere;
        for (int index = 1; index < values.length; index++) {
            endingHere = Math.max((long) values[index], endingHere + values[index]);
            maximum = Math.max(maximum, endingHere);
        }
        return maximum;
    }

    /** читает входные данные и выводит ответ */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        int[] values = ArrayIo.readArray(input);
        output.println(maximumSubarraySum(values));
        output.flush();
    }
}
