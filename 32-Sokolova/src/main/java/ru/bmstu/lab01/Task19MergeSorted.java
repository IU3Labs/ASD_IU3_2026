package ru.bmstu.lab01;
import java.io.IOException;

public class Task19MergeSorted {
    public static int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) res[k++] = (a[i] <= b[j]) ? a[i++] : b[j++];
        while (i < a.length) res[k++] = a[i++];
        while (j < b.length) res[k++] = b[j++];
        return res;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] a = StdIn.readArray(n);
        int m = StdIn.nextInt();
        int[] b = StdIn.readArray(m);
        printArray(merge(a, b));
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}