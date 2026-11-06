package ru.bmstu.lab1.task57;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 57. Спиральный обход матрицы.
 * Ввод: числа n и m, затем n строк по m целых чисел.
 * Вывод: элементы матрицы в порядке обхода по спирали через пробел.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает матрицу с клавиатуры и выводит её спиральный обход.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        System.out.println(ArrayIo.toLine(SpiralTraversal.traverse(matrix)));
    }
}
