
package ru.bmstu.lab01.task_36;

import java.util.Scanner;

// Task 36. Running sum.
// Time complexity: O(n)
// Space complexity: O(n)

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        long[] result = new long[n];

        long sum = 0;

        for (int i = 0; i < n; i++) {
            int number = scanner.nextInt();
            sum += number;
            result[i] = sum;
        }

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(result[i]);
        }

        System.out.println();
        scanner.close();
    }
}
