package ru.bmstu.lab01.common;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Сортировка массива {@code int} по произвольному компаратору.
 *
 * <p>{@code Arrays.sort(int[])} не принимает компаратор, поэтому элементы упаковываются в {@code Integer}
 * и сортируются стабильной сортировкой объектов (TimSort) за O(n log n) с O(n) дополнительной памяти.
 */
public final class ComparatorSort {

    private ComparatorSort() {
    }

    /**
     * Возвращает отсортированную копию массива; исходный массив не меняется.
     *
     * @param array      исходный массив
     * @param comparator порядок элементов
     * @return новый отсортированный массив
     */
    public static int[] sorted(int[] array, Comparator<Integer> comparator) {
        Integer[] boxed = Arrays.stream(array).boxed().toArray(Integer[]::new);
        Arrays.sort(boxed, comparator);
        return Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();
    }
}
