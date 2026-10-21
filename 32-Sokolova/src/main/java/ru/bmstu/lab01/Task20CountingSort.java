package ru.bmstu.lab01;
import java.io.IOException;

public class Task20CountingSort {
    public static int[] sort(int[] arr) {
        if (arr.length == 0) return arr;
        int min = arr[0], max = arr[0];
        for (int v : arr) { if (v < min) min = v; if (v > max) max = v; }
        int range = max - min + 1;
        int[] count = new int[range];
        for (int v : arr) count[v - min]++;
        int idx = 0;
        for (int i = 0; i < range; i++) {
            while (count[i] > 0) { arr[idx++] = i + min; count[i]--; }
        }
        return arr;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        printArray(sort(StdIn.readArray(n)));
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}