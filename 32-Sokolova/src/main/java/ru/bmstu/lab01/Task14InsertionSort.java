package ru.bmstu.lab01;
import java.io.IOException;

public class Task14InsertionSort {
    public static int[] sortDescending(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] < key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        return arr;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        printArray(sortDescending(StdIn.readArray(n)));
    }
    private static void printArray(int[] a) { for(int i=0;i<a.length;i++) System.out.print(a[i]+(i==a.length-1?"":" ")); System.out.println(); }
}