
package ru.bmstu.lab01.task_49;

import java.util.Scanner;

// Task 49. Sum of matrix rows.
// Time complexity: O(n * m)
// Space complexity: O(1) auxiliary space

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            long sum = 0;

            for (int j = 0; j < m; j++) {
                sum += scanner.nextInt();
            }

            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(sum);
        }

        System.out.println();
        scanner.close();
    }
}
