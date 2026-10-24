/**
 * Задача 8. Фильтрация по знаку.
 * Вход: массив из n целых чисел.
 * Выход: два массива — из положительных и из отрицательных элементов, в исходном порядке.
 * Нули не включаются ни в один.
 * Сложность: базовая.
 */
package ru.bmstu.lab01.task_8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Разделение массива на положительные и отрицательные элементы с сохранением порядка.
 */
public final class SignFilter {

    /** Минимально допустимое n. */
    private static final int LOWER_BOUND = 1;

    /** Максимально допустимое n. */
    private static final int UPPER_BOUND = 99_999;

    private SignFilter() {
    }

    /**
     * Разделяет массив на положительные и отрицательные элементы.
     * Нули отбрасываются. Порядок внутри каждой группы — как во входе.
     *
     * @param data входной массив
     * @return результат с двумя списками; индексы {@code 0} — положительные, {@code 1} — отрицательные
     * @throws IllegalArgumentException если массив {@code null} или его длина вне допустимых границ
     */
    public static Result split(int[] data) {
        if (data == null || data.length < LOWER_BOUND || data.length > UPPER_BOUND) {
            throw new IllegalArgumentException("некорректная длина массива");
        }
        List<Integer> positives = new ArrayList<>();
        List<Integer> negatives = new ArrayList<>();
        for (int value : data) {
            if (value > 0) {
                positives.add(value);
            } else if (value < 0) {
                negatives.add(value);
            }
        }
        return new Result(positives, negatives);
    }

    /**
     * Контейнер для двух списков — чтобы не возвращать пару из {@code main} через побочные эффекты.
     */
    public static final class Result {
        private final List<Integer> positives;
        private final List<Integer> negatives;

        public Result(List<Integer> positives, List<Integer> negatives) {
            this.positives = positives;
            this.negatives = negatives;
        }

        public List<Integer> getPositives() {
            return positives;
        }

        public List<Integer> getNegatives() {
            return negatives;
        }
    }

    /**
     * Точка входа. Формат ввода: n, затем n чисел.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: ожидалось целое число");
                return;
            }
            int n = scanner.nextInt();
            if (n < LOWER_BOUND || n > UPPER_BOUND) {
                System.out.println("Ошибка: n вне допустимого диапазона");
                return;
            }
            int[] data = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: ожидалось целое число");
                    return;
                }
                data[i] = scanner.nextInt();
            }

            Result result = split(data);
            printList(result.getPositives());
            printList(result.getNegatives());
        }
    }

    /** Печатает элементы через пробел в одну строку; для пустого списка — пустую строку. */
    private static void printList(List<Integer> values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(values.get(i));
        }
        System.out.println(sb);
    }
}