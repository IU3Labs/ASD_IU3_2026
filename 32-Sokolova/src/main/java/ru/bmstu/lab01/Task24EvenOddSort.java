package ru.bmstu.lab01;
import java.io.IOException;
import java.util.*;

public class Task24EvenOddSort {
    public static int[] sortEvenOdd(int[] arr) {
        List<Integer> evens = new ArrayList<>(), odds = new ArrayList<>();
        for (int v : arr) (v % 2 == 0 ? evens : odds).add(v);
        Collections.sort(evens);
        odds.sort(Collections.reverseOrder());
        int[] res = new int[arr.length];
        int idx = 0;
        for (int v : evens) res[idx++] = v;
        for (int v : odds) res[idx++] = v;
        return res;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        printArray(sortEvenOdd(StdIn.readArray(n)));
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}