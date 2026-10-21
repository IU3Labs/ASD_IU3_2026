package ru.bmstu.lab01; // Убедись, что пакет совпадает с папкой

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Задача 16. Сортировка по модулю.
 * Вход: массив целых чисел.
 * Выход: массив, отсортированный по неубыванию модуля.
 * При равных модулях первым идёт отрицательный элемент.
 */
public class Task16SortByAbs {

    /**
     * Сортирует массив по модулю элементов.
     *
     * @param arr входной массив
     * @return отсортированный массив Integer
     */
    public static Integer[] sortByAbs(int[] arr) {
        Integer[] boxed = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        
        // Исправление: явное указание типа (Integer x) снимает неоднозначность Math.abs
        Comparator<Integer> comparator = Comparator.comparingInt((Integer x) -> Math.abs(x))
                                                   .thenComparingInt(x -> x);
        
        Arrays.sort(boxed, comparator);
        return boxed;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        Integer[] res = sortByAbs(StdIn.readArray(n));
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + (i == res.length - 1 ? "" : " "));
        }
        System.out.println();
    }
}