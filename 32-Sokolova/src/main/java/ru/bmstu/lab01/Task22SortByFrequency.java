package ru.bmstu.lab01;
import java.io.IOException;
import java.util.*;

public class Task22SortByFrequency {
    public static int[] sortByFreq(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int v : arr) freq.put(v, freq.getOrDefault(v, 0) + 1);
        List<Integer> list = new ArrayList<>(freq.keySet());
        list.sort((a, b) -> {
            int fComp = freq.get(b).compareTo(freq.get(a));
            return fComp != 0 ? fComp : a.compareTo(b);
        });
        int[] res = new int[arr.length];
        int idx = 0;
        for (int val : list) for (int i = 0; i < freq.get(val); i++) res[idx++] = val;
        return res;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        printArray(sortByFreq(StdIn.readArray(n)));
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}