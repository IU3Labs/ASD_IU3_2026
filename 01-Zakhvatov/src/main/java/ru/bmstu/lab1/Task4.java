package ru.bmstu.lab1;

/**
 * Задача 4. Все индексы элемента.
 */
public class Task4 {

    public static int[] findAll(int[] arr, int x) {
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
             if (arr[i] == x) cnt++;
        }
        int[] res = new int[cnt];
         
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                res[k] = i;
                k++;
            }
        }
        return res;
    }
}
