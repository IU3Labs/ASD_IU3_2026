package ru.bmstu.lab01;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task18SortEvensInPlace {
    public static int[] sortEvensOnly(int[] arr) {
        List<Integer> evens = new ArrayList<>();
        for (int v : arr) if (v % 2 == 0) evens.add(v);
        Collections.sort(evens);
        int idx = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 == 0) arr[i] = evens.get(idx++);
        }
        return arr;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        printArray(sortEvensOnly(StdIn.readArray(n)));
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}