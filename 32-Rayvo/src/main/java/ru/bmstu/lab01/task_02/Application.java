package ru.bmstu.lab01.task_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Точка входа для запуска из командной строки.
 *
 * <p>Ожидает строку вида: {@code n a1 a2 ... an x}.
 * Выводит индекс первого вхождения {@code x} или {@code -1}.
 *
 */
public class Application {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] p = br.readLine().trim().split("\\s+");
            int n = Integer.parseInt(p[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(p[i + 1]);
            int x = Integer.parseInt(p[n + 1]);
            System.out.println(FirstOccurrenceFinder.findFirstOccurrence(array, x));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}