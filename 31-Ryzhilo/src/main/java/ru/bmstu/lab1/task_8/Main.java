package ru.bmstu.lab1.task_8;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 8. Фильтрация по знаку: два массива - из положительных и из отрицательных элементов
 * в исходном порядке, нули не включаются ни в один. Сложность по времени - O(n)
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n и массив из n чисел, затем печатает в первой строке положительные элементы,
     * во второй - отрицательные (через пробел). Если элементов нет, печатается пустая строка.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];
        int positiveCount = 0;
        int negativeCount = 0;

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
            if (numbers[i] > 0) {
                positiveCount++;
            } else if (numbers[i] < 0) {
                negativeCount++;
            }
        }

        //размеры известны заранее, поэтому создаём массивы точной длины
        int[] positives = new int[positiveCount];
        int[] negatives = new int[negativeCount];
        int positiveIndex = 0;
        int negativeIndex = 0;

        //один проход слева направо сохраняет исходный порядок элементов
        for (int i = 0; i < n; i++) {
            if (numbers[i] > 0) {
                positives[positiveIndex++] = numbers[i];
            } else if (numbers[i] < 0) {
                negatives[negativeIndex++] = numbers[i];
            }
        }

        output.println(join(positives));
        output.println(join(negatives));
    }

    /**
     * Склеивает элементы массива через пробел; для пустого массива возвращает пустую строку.
     */
    private static String join(int[] values) {
        //StringBuilder вместо конкатенации в цикле, иначе вывод был бы O(n^2)
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            result.append(values[i]);
        }

        return result.toString();
    }
}
