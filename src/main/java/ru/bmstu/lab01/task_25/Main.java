
package ru.bmstu.lab01.task_25;

import java.util.Scanner;

// Task 25. Sum of array elements.
// Time complexity: O(n)
// Auxiliary space complexity: O(1)

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        long sum = 0;

        for (int i = 0; i < n; i++) {
            int number = scanner.nextInt();
            sum += number;
        }

        System.out.println(sum);

        scanner.close();
    }
}
