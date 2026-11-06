package ru.bmstu.lab01.task_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Точка входа для запуска из командной строки.
 *
 * <p>Ожидает строку вида: {@code n a1 a2 ... an x}.
 * Выводит индексы через пробел в порядке возрастания или {@code -1},
 * если вхождений нет.
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
            int[] idx = AllIndicesFinder.findAllIndices(array, x);
            if (idx.length == 0) {
                System.out.println(-1);
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < idx.length; i++) {
                    if (i > 0) sb.append(' ');
                    sb.append(idx[i]);
                }
                System.out.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}