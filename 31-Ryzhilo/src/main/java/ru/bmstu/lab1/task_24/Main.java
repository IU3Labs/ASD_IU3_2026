package ru.bmstu.lab1.task_24;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 24. Чётно-нечётная сортировка.
 * Сначала выводятся чётные числа по возрастанию,
 * затем нечётные числа по убыванию.
 * Для сортировки используется метод слияния.
 * Сложность по времени — O(n log n).
 * Память для исходного массива — O(n).
 * Дополнительная память сортировки — O(n).
 */
public class Main {

    /** Запускает решение через стандартные потоки ввода и вывода. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /** Читает массив и выводит его элементы в требуемом порядке. */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        //Все числа располагаются по возрастанию.
        int[] buffer = new int[n];
        sort(numbers, buffer, 0, n);

        // Перед первым числом пробел не нужен.
        boolean first = true;

        // При проходе слева направо чётные числа идут по возрастанию.
        // Ноль и отрицательные чётные числа тоже учитываются.
        for (int i = 0; i < n; i++) {
            if (numbers[i] % 2 == 0) {
                if (!first) {
                    output.print(' ');
                }
                output.print(numbers[i]);
                first = false;
            }
        }

        // Обратный проход даёт нечётные числа по убыванию.
        // Проверяем != 0: у отрицательного нечётного числа остаток равен -1.
        for (int i = n - 1; i >= 0; i--) {
            if (numbers[i] % 2 != 0) {
                if (!first) {
                    output.print(' ');
                }
                output.print(numbers[i]);
                first = false;
            }
        }

        output.println();
    }

    /**
     * Сортирует часть массива от left включительно до right исключительно.
     * Например, границы 0 и n означают весь массив.
     */
    private static void sort(int[] numbers, int[] buffer, int left, int right) {
        // Пустой участок и один элемент уже отсортированы.
        if (right - left < 2) {
            return;
        }

        int middle = left + (right - left) / 2;

        // Сортируем каждую половину, затем объединяем их.
        sort(numbers, buffer, left, middle);
        sort(numbers, buffer, middle, right);
        merge(numbers, buffer, left, middle, right);
    }

    /** Объединяет две отсортированные половины в один отсортированный участок. */
    private static void merge(int[] numbers, int[] buffer, int left, int middle, int right) {
        int i = left;       // Текущая позиция в левой половине.
        int j = middle;     // Текущая позиция в правой половине.
        int position = left;

        // Каждый раз выбираем меньшее из двух доступных чисел.
        while (i < middle && j < right) {
            if (numbers[i] <= numbers[j]) {
                buffer[position] = numbers[i];
                i++;
            } else {
                buffer[position] = numbers[j];
                j++;
            }
            position++;
        }

        // Одна половина закончилась. Копируем остаток другой.
        while (i < middle) {
            buffer[position] = numbers[i];
            i++;
            position++;
        }

        while (j < right) {
            buffer[position] = numbers[j];
            j++;
            position++;
        }

        // Возвращаем объединённый участок в исходный массив.
        for (int k = left; k < right; k++) {
            numbers[k] = buffer[k];
        }
    }
}
