package ru.bmstu.lab01;
import java.io.IOException;
import java.util.Arrays;

public class Task23CheckPermutation {
    public static boolean isPermutation(int[] a, int[] b) {
        if (a.length != b.length) return false;
        int[] sortedA = Arrays.copyOf(a, a.length);
        int[] sortedB = Arrays.copyOf(b, b.length);
        Arrays.sort(sortedA);
        Arrays.sort(sortedB);
        return Arrays.equals(sortedA, sortedB);
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] a = StdIn.readArray(n);
        int[] b = StdIn.readArray(n);
        System.out.println(isPermutation(a, b) ? "YES" : "NO");
    }
}