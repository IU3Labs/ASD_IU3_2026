package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 4. Все индексы элемента.
 * Вход: массив и x.
 * Выход: индексы x по возрастанию.
 */
public class Task04AllIndices {
    /**
     * Возвращает массив индексов вхождения x.
     * @param arr массив
     * @param x искомое значение
     * @return массив индексов
     */
    public static int[] findAllIndices(int[] arr, int x) {
        int count = 0;
        for (int val : arr) if (val == x) count++;
        int[] indices = new int[count];
        int pos = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) indices[pos++] = i;
        }
        return indices;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        int x = StdIn.nextInt();
        int[] res = findAllIndices(arr, x);
        StringBuilder sb = new StringBuilder();
        for (int idx : res) sb.append(idx).append(" ");
        System.out.println(sb.toString().trim());
    }
}