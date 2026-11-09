// Задача 4. Все индексы элемента.
// Вход: массив из n целых чисел и число x. Выход: все индексы элементов, равных x, по возрастанию.
package ru.bmstu.lab01.task_04;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayIO;

/**
 * Задача 4: все индексы x.
 */
public class Task04 {

    /**
     * Собирает все индексы, где стоит x.
     *
     * @param a массив
     * @param x что ищем
     * @return список индексов (пустой, если x нет)
     */
    public static List<Integer> allIndices(int[] a, int x) {
        // заранее не знаем сколько будет совпадений, поэтому список
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * Читает входные данные и печатает ответ.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] a = ArrayIO.readArray(in);
        int x = in.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int idx : allIndices(a, x)) {
            sb.append(idx).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}
