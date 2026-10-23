// задача 19 — объединение двух отсортированных массивов
package ru.bmstu.lab1.task_19;

import java.io.PrintWriter;
import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/** слияние двух отсортированных массивов двумя указателями */
public final class Main {
    private Main() {
    }

    /** сливает два неубывающих массива, сохраняя повторы и не изменяя входы */
    public static int[] merge(int[] first, int[] second) {
        int[] merged = new int[first.length + second.length];
        int firstIndex = 0;
        int secondIndex = 0;
        int resultIndex = 0;
        while (firstIndex < first.length && secondIndex < second.length) {
            if (first[firstIndex] <= second[secondIndex]) {
                merged[resultIndex++] = first[firstIndex++];
            } else {
                merged[resultIndex++] = second[secondIndex++];
            }
        }
        while (firstIndex < first.length) {
            merged[resultIndex++] = first[firstIndex++];
        }
        while (secondIndex < second.length) {
            merged[resultIndex++] = second[secondIndex++];
        }
        return merged;
    }

    /** читает входные данные и выводит ответ */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        int[] first = ArrayIo.readArray(input);
        int[] second = ArrayIo.readArray(input);
        ArrayIo.printArray(output, merge(first, second));
    }
}
