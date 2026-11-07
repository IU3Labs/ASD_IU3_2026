package ru.bmstu.lab1.task_20;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 20. Сортировка целых чисел по неубыванию методом подсчёта.
 * Небольшой диапазон сортируется напрямую, широкий — подсчётом по байтам.
 * Прямой вариант: время O(n + R), память O(n + R), где R = max - min + 1.
 * Поразрядный вариант: время O(4 * (n + 256)), память O(n + 256).
 */
public class Main {

    // До этого размера массив счётчиков занимает не более 4 МБ.
    // Это порог выбора алгоритма, а не ограничение входных данных.
    private static final int DIRECT_RANGE_LIMIT = 1_000_000;
    private static final int BYTE_VALUES = 256;
    private static final int BYTE_MASK = BYTE_VALUES - 1;

    /** Запускает программу с вводом и выводом через консоль. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает размер и элементы массива, затем выводит их по неубыванию. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        int[] sorted = countingSort(numbers);
        for (int i = 0; i < sorted.length; i++) {
            if (i > 0) {
                output.print(' ');
            }
            output.print(sorted[i]);
        }
        output.println();
    }

    /** Возвращает новый отсортированный массив, сохраняя исходный. */
    static int[] countingSort(int[] numbers) {
        if (numbers.length == 0) {
            return new int[0];
        }

        int min = numbers[0];
        int max = numbers[0];
        for (int value : numbers) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        // Разность крайних int может не поместиться в int.
        long range = (long) max - min + 1;
        if (range <= DIRECT_RANGE_LIMIT) {
            return sortByCounting(numbers, min, (int) range);
        }

        return sortByBytes(numbers);
    }

    /** Подсчитывает частоты значений в небольшом непрерывном диапазоне. */
    private static int[] sortByCounting(int[] numbers, int min, int range) {
        int[] counts = new int[range];
        for (int value : numbers) {
            // Сдвиг на min делает индексы счётчиков неотрицательными.
            counts[value - min]++;
        }

        int[] sorted = new int[numbers.length];
        int position = 0;
        for (int offset = 0; offset < counts.length; offset++) {
            for (int repeat = 0; repeat < counts[offset]; repeat++) {
                sorted[position++] = min + offset;
            }
        }
        return sorted;
    }

    /** Выполняет четыре стабильных сортировки подсчётом: по одному байту int. */
    private static int[] sortByBytes(int[] numbers) {
        int[] sorted = numbers.clone();
        int[] buffer = new int[numbers.length];

        for (int shift = 0; shift < Integer.SIZE; shift += Byte.SIZE) {
            int[] counts = new int[BYTE_VALUES];
            for (int value : sorted) {
                counts[getByte(value, shift)]++;
            }

            // После накопления counts[b] — конец группы байта b в новом массиве.
            for (int i = 1; i < counts.length; i++) {
                counts[i] += counts[i - 1];
            }

            // Обратный обход сохраняет порядок равных байтов от прошлого прохода.
            for (int i = sorted.length - 1; i >= 0; i--) {
                int currentByte = getByte(sorted[i], shift);
                buffer[--counts[currentByte]] = sorted[i];
            }

            // Результат становится входом следующего прохода; память используем повторно.
            int[] temporary = sorted;
            sorted = buffer;
            buffer = temporary;
        }
        return sorted;
    }

    /** Извлекает байт ключа, в котором отрицательные числа предшествуют неотрицательным. */
    private static int getByte(int value, int shift) {
        // Инвертируем знаковый бит: MIN_VALUE получает ключ 0, MAX_VALUE — 2^32 - 1.
        return ((value ^ Integer.MIN_VALUE) >>> shift) & BYTE_MASK;
    }
}
