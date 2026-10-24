package ru.bmstu.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Задача 8. Фильтрация по знаку
 * Вход: массив из n целых чисел.
 * Выход: сначала положительные элементы, затем отрицательные (нули исключаются).
 */
public class Task_8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        List<Integer> positives = new ArrayList<>();
        List<Integer> negatives = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int value = scanner.nextInt();
            if (value > 0) {
                positives.add(value);
            } else if (value < 0) {
                negatives.add(value);
            }
        }

        // Выводим положительные элементы через пробел
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < positives.size(); i++) {
            sb.append(positives.get(i));
            if (i < positives.size() - 1 || !negatives.isEmpty()) {
                sb.append(" ");
            }
        }

        // Выводим отрицательные элементы через пробел
        for (int i = 0; i < negatives.size(); i++) {
            sb.append(negatives.get(i));
            if (i < negatives.size() - 1) {
                sb.append(" ");
            }
        }

        System.out.println(sb.toString().trim());
        scanner.close();
    }
}