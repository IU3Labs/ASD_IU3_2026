/*
 * Задача 12. Пара с заданной суммой.
 * Вход: массив из n целых чисел и число s.
 * Выход: индексы i < j, для которых a_i + a_j = s. Если пар несколько — вывести пару с наименьшим i, а
 * при равенстве — с наименьшим j. Если пары нет — вывести -1 -1.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Сложность: средняя
 */
package ru.bmstu.lab1.task_12;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 12. Пара с заданной суммой: найти i < j, минимизируя сначала i, затем j. */
public final class PairWithSum {
    private PairWithSum() {}

    /**
     * Возвращает индексы требуемой пары или {-1, -1}, не изменяя входной массив.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @param targetSum требуемая сумма двух элементов
     * @return результат решения задачи
     */
    public static int[] solve(int[] values, long targetSum) {
        IndexedElement[] sorted = new IndexedElement[values.length];
        for (int index = 0; index < values.length; index++) {
            sorted[index] = new IndexedElement(values[index], index);
        }
        Arrays.sort(
                sorted,
                Comparator.comparingInt(IndexedElement::value)
                        .thenComparingInt(IndexedElement::index));
        for (int first = 0; first < values.length; first++) {
            long complement = targetSum - values[first];
            int position = lowerBound(sorted, complement, first + 1);
            if (position < sorted.length && sorted[position].value() == complement) {
                return new int[] {first, sorted[position].index()};
            }
        }
        return new int[] {-1, -1};
    }

    // Ищем первую пару (значение, индекс), не меньшую (complement, minimumIndex).
    // Внутри равных значений сортировка по индексу сразу даёт минимальный j > i.
    private static int lowerBound(IndexedElement[] sorted, long complement, int minimumIndex) {
        int left = 0;
        int right = sorted.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            IndexedElement element = sorted[middle];
            if (element.value() < complement
                    || (element.value() == complement && element.index() < minimumIndex)) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            System.out.println(
                    ConsoleIo.format(
                            solve(values, ConsoleIo.readLong(scanner, "Введите сумму s"))));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
