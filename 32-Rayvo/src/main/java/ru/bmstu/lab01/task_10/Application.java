package ru.bmstu.lab01.task_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Точка входа для запуска из командной строки.
 *
 * <p>Ожидает строку вида: {@code n a1 a2 ... an k}.
 * Выводит индекс последнего элемента, кратного {@code k}, или
 * {@code -1}, если такого нет.
 *
 */
public class Application {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] p = br.readLine().trim().split("\\s+");
            int n = Integer.parseInt(p[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(p[i + 1]);
            int k = Integer.parseInt(p[n + 1]);
            System.out.println(LastMultipleFinder.findLastMultiple(array, k));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}