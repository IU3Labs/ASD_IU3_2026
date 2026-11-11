// Задача 35. Префиксные суммы
// Вход: массив из n целых чисел и q запросов вида [l, r].
// Выход: для каждого запроса — сумму элементов с индексами от l до r включительно.
// Требование: O(1) на запрос. Сложность: средняя.

import java.util.Scanner;

public class task_35 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + sc.nextLong();
        }
        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            System.out.println(prefix[r + 1] - prefix[l]);
        }
    }
}