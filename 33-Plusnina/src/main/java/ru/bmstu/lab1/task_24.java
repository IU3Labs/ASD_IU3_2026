package ru.bmstu.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Задача 24. Чётно-нечётная сортировка.
 * Вход: массив из n целых чисел.
 * Выход: массив, где чётные элементы по возрастанию идут первыми, а нечётные по убыванию — после.
 * Сложность: O(n log n).
 */
public final class task_24 {
    public static int[] sortEvenOdd(int[] array) {
        List<Integer> evens = new ArrayList<>();
        List<Integer> odds = new ArrayList<>();
        for (int v : array) {
            if (v % 2 == 0) evens.add(v);
            else odds.add(v);
        }
        Collections.sort(evens);
        odds.sort(Collections.reverseOrder());
        evens.addAll(odds);
        
        int[] res = new int[array.length];
        for (int i = 0; i < res.length; i++) res[i] = evens.get(i);
        return res;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            System.out.println(Arrays.toString(sortEvenOdd(a)));
        }
    }
}