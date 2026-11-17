// задача 42 — вставка элемента в отсортированный массив
package ru.bmstu.lab1.task_42;

import java.io.PrintWriter;
import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/** вставка в отсортированный массив с бинарным поиском позиции */
public final class Main {
    private Main() {
    }

    /** возвращает новый неубывающий массив; исходный массив должен быть неубывающим */
    public static int[] insertSorted(int[] values, int value) {
        int position = lowerBound(values, value);
        int[] result = new int[values.length + 1];
        System.arraycopy(values, 0, result, 0, position);
        result[position] = value;
        System.arraycopy(values, position, result, position + 1, values.length - position);
        return result;
    }

    private static int lowerBound(int[] values, int value) {
        int left = 0;
        int right = values.length;

        while (left < right) {
            int middle = left + (right - left) / 2;
            if (values[middle] < value) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    /** читает входные данные и выводит ответ */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        int[] values = ArrayIo.readArray(input);
        int value = input.nextInt();
        ArrayIo.printArray(output, insertSorted(values, value));
    }
}
