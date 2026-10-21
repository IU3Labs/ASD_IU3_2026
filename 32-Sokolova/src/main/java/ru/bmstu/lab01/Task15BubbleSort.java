package ru.bmstu.lab01;
import java.io.IOException;

public class Task15BubbleSort {
    public record Result(int[] sorted, int swaps) {}

    public static Result sortWithCount(int[] arr) {
        int swaps = 0;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return new Result(arr, swaps);
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        Result res = sortWithCount(arr);
        printArray(res.sorted());
        System.out.println(res.swaps());
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}