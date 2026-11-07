package ru.bmstu.lab1.task_19;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 19. Объединение двух отсортированных массивов: слияние двух массивов, отсортированных
 * по неубыванию, в один отсортированный массив. Сложность по времени - O(n + m)
 */
public class Main {

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n, первый массив, m, второй массив и печатает результат слияния через пробел.
     */
    static void solve(Scanner input, PrintStream output) {
        int[] first = readArray(input);
        int[] second = readArray(input);

        int[] merged = merge(first, second);

        //StringBuilder вместо конкатенации в цикле, иначе вывод был бы O(n^2)
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < merged.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            result.append(merged[i]);
        }

        output.println(result);
    }

    /**
     * Сливает два массива, отсортированных по неубыванию, в новый отсортированный массив.
     * Исходные массивы не изменяются.
     */
    static int[] merge(int[] first, int[] second) {
        int[] merged = new int[first.length + second.length];
        int i = 0; //текущая позиция в первом массиве
        int j = 0; //текущая позиция во втором массиве
        int k = 0; //следующая свободная позиция в результате

        //на каждом шаге берём меньший из двух текущих элементов: каждый элемент попадает в результат один раз
        while (i < first.length && j < second.length) {
            if (first[i] <= second[j]) {
                merged[k++] = first[i++];
            } else {
                merged[k++] = second[j++];
            }
        }

        //один из массивов закончился, остаток другого уже отсортирован и переносится как есть
        while (i < first.length) {
            merged[k++] = first[i++];
        }
        while (j < second.length) {
            merged[k++] = second[j++];
        }

        return merged;
    }

    /**
     * Читает размер массива, а затем столько же чисел.
     */
    private static int[] readArray(Scanner input) {
        int size = input.nextInt();
        int[] numbers = new int[size];

        for (int i = 0; i < size; i++) {
            numbers[i] = input.nextInt();
        }

        return numbers;
    }
}
