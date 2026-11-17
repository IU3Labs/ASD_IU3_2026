// задача 38 — циклический сдвиг массива влево
package ru.bmstu.lab1.task_38;

import java.io.PrintWriter;
import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;
import ru.bmstu.lab1.common.ArrayOperations;

/** циклический сдвиг влево тройным разворотом на месте */
public final class Main {
    private Main() {
    }

    /** сдвигает массив на месте; отрицательный сдвиг означает движение вправо */
    public static void rotateLeft(int[] values, int shift) {
        if (values.length == 0) {
            return;
        }
        int distance = Math.floorMod(shift, values.length);
        if (distance == 0) {
            return;
        }

        ArrayOperations.reverse(values, 0, distance);
        ArrayOperations.reverse(values, distance, values.length);
        ArrayOperations.reverse(values, 0, values.length);
    }

    /** читает входные данные и выводит ответ */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        int[] values = ArrayIo.readArray(input);
        int shift = input.nextInt();
        rotateLeft(values, shift);
        ArrayIo.printArray(output, values);
    }
}
