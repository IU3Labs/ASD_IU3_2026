package ru.bmstu.lab1.task_12;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Задача 12. Пара с заданной суммой: индексы i < j, для которых a[i] + a[j] = s. Из нескольких пар
 * выбирается пара с наименьшим i, а при равенстве - с наименьшим j. Если пары нет - выводится "-1 -1".
 * Сложность по времени - O(n) в среднем (хеш-таблица)
 */
public class Main {

    private static final int NOT_FOUND = -1;

    /**
     * Запускает программу с вводом и выводом через консоль.
     */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n, массив из n чисел и число s, затем печатает индексы искомой пары через пробел.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        //s читаем как long: сумма двух int может не поместиться в int
        long sum = input.nextLong();
        int[] pair = findPair(numbers, sum);

        output.println(pair[0] + " " + pair[1]);
    }

    /**
     * Ищет пару индексов i < j с a[i] + a[j] = sum: минимальный i, затем минимальный j.
     *
     * @return массив из двух индексов или {-1, -1}, если пары нет
     */
    static int[] findPair(int[] numbers, long sum) {
        //значение -> наименьший индекс справа от текущей позиции.
        //Идём справа налево: к моменту обработки i в таблице лежат только индексы j > i,
        //а повторная запись одного значения заменяет индекс на меньший
        Map<Long, Integer> firstIndexOf = new HashMap<>();
        int[] best = {NOT_FOUND, NOT_FOUND};

        for (int i = numbers.length - 1; i >= 0; i--) {
            Integer j = firstIndexOf.get(sum - numbers[i]);
            //каждая найденная пара имеет меньший i, чем предыдущая, поэтому перезаписываем результат:
            //в конце цикла останется пара с наименьшим i
            if (j != null) {
                best[0] = i;
                best[1] = j;
            }
            firstIndexOf.put((long) numbers[i], i);
        }

        return best;
    }
}
