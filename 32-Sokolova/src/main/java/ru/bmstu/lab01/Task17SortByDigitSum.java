package ru.bmstu.lab01;
import java.io.IOException;
import java.util.Arrays;

public class Task17SortByDigitSum {
    public static int[] sortByDigitSum(int[] arr) {
        Integer[] idx = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> {
            int sumA = digitSum(arr[a]);
            int sumB = digitSum(arr[b]);
            return sumA != sumB ? Integer.compare(sumA, sumB) : Integer.compare(a, b);
        });
        int[] res = new int[arr.length];
        for (int i = 0; i < idx.length; i++) res[i] = arr[idx[i]];
        return res;
    }

    private static int digitSum(int n) {
        int sum = 0;
        while (n > 0) { sum += n % 10; n /= 10; }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        printArray(sortByDigitSum(StdIn.readArray(n)));
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}