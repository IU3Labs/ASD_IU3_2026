package ru.bmstu.lab01;
import java.io.IOException;
import java.util.Arrays;

public class Task21KthSmallest {
    public static int findKth(int[] arr, int k) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);
        return copy[k - 1];
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        int k = StdIn.nextInt();
        System.out.println(findKth(arr, k));
    }
}