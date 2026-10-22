package ru.bmstu.lab1.task_23;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputReader;

/**
 * Задача 23. Проверка анаграммы массивов.
 *
 * <p>Вход: два массива одинаковой длины {@code n}.
 * Выход: {@code YES}, если один массив является перестановкой другого; {@code NO} иначе.
 *
 * <p>Идея: два массива — перестановки друг друга тогда и только тогда, когда после сортировки
 * они совпадают поэлементно. Сортируются копии, чтобы не портить входные данные.
 * Сортировка выбрана вместо хеш-таблицы: она не зависит от распределения значений
 * и гарантирует O(n log n) в худшем случае.
 */
public final class PermutationChecker {

    private PermutationChecker() {
    }

    /**
     * Проверяет, является ли второй массив перестановкой первого.
     *
     * @param first  первый массив
     * @param second второй массив
     * @return {@code true}, если массивы состоят из одних и тех же элементов с теми же кратностями
     * @throws IllegalArgumentException если какой-либо из массивов {@code null}
     */
    public static boolean isPermutation(int[] first, int[] second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Массивы не должны быть null");
        }
        if (first.length != second.length) {
            return false;
        }
        int[] sortedFirst = first.clone();
        int[] sortedSecond = second.clone();
        Arrays.sort(sortedFirst);
        Arrays.sort(sortedSecond);
        return Arrays.equals(sortedFirst, sortedSecond);
    }

    /**
     * Читает {@code n}, затем два массива по {@code n} элементов и печатает {@code YES} или {@code NO}.
     *
     * @param in  источник данных
     * @param out поток вывода
     */
    public static void run(Scanner in, PrintStream out) {
        int[] first = InputReader.readArray(in);
        int[] second = InputReader.readElements(in, first.length);
        out.println(isPermutation(first, second) ? "YES" : "NO");
    }

    /**
     * Точка входа: ввод с клавиатуры, вывод в консоль.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
