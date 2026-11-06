package ru.bmstu.lab1;
import java.util.Scanner;
/**
 * Задача 49. Сумма строк матрицы.
 */
public class Task49 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < m; j++) {
                sum += matrix[i][j];
            }
            System.out.print(sum + " ");
        }
        System.out.println();
        sc.close();
    }
}