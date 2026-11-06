package ru.bmstu.lab01.task_08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Точка входа для запуска из командной строки.
 *
 * <p>Ожидает строку вида: {@code n a1 a2 ... an}.
 * Выводит две строки: положительные элементы через пробел, затем
 * отрицательные через пробел. Если соответствующий массив пуст,
 * выводится {@code -}.
 *
 */
public class Application {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] p = br.readLine().trim().split("\\s+");
            int n = Integer.parseInt(p[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(p[i + 1]);
            SignFilter.FilterResult r = SignFilter.filterBySign(array);
            System.out.println(formatArray(r.positive()));
            System.out.println(formatArray(r.negative()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Форматирует массив в строку через пробел; пустой массив — {@code -}.
     *
     * @param array массив для форматирования
     * @return строка с элементами через пробел или {@code -}
     */
    private static String formatArray(int[] array) {
        if (array.length == 0) return "-";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(array[i]);
        }
        return sb.toString();
    }
}