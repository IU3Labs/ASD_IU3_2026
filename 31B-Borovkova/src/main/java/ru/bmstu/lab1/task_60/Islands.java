/*Задача 60. Острова из единиц
Вход: бинарная матрица (n x m), состоящая из 0 и 1.
Выход: количество «островов» — групп связанных единиц
(связь по горизонтали и вертикали, не по диагонали).
Требование: поиск в глубину (DFS) или в ширину (BFS).
Сложность: высокая */

package ru.bmstu.lab1.task_60;

import java.util.Scanner;

/**
 * Решение задачи 60: подсчет островов из единиц в бинарной матрице обходом в ширину (BFS).
 */
public class Islands {
    /** Наименьший допустимый размер стороны матрицы. */
    private static final int MIN_SIZE = 1;
    /** Наибольший допустимый размер стороны матрицы (чтобы матрица помещалась в память). */
    private static final int MAX_SIZE = 1000;
    /** Начальное значение количества островов: пока не найдено ни одного. */
    private static final int COUNT = 0;
    /** Смещения по строкам для четырех соседей: вверх, вниз, влево, вправо. */
    private static final int[] DI = {-1, 1, 0, 0};
    /** Смещения по столбцам для четырех соседей: вверх, вниз, влево, вправо. */
    private static final int[] DJ = {0, 0, -1, 1};

    /**
     * Читает n, m и матрицу из 0 и 1, печатает количество островов.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int n = scanner.nextInt();
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int m = scanner.nextInt();
        if (n < MIN_SIZE || n > MAX_SIZE || m < MIN_SIZE || m > MAX_SIZE) {
            System.out.println("Ошибка: некорректный размер матрицы");
            return;
        }

        // Читаем саму матрицу
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i][j] = scanner.nextInt();
                if (a[i][j] != 0 && a[i][j] != 1) {
                    System.out.println("Ошибка: матрица должна состоять из 0 и 1");
                    return;
                }
            }
        }

        // Очередь для BFS создается один раз и переиспользуется для всех островов
        boolean[][] visited = new boolean[n][m];
        int[] queueI = new int[n * m];
        int[] queueJ = new int[n * m];
        int k = COUNT;

        // Каждая еще не посещенная единица — начало нового острова
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a[i][j] == 1 && !visited[i][j]) {
                    k += 1;
                    bfs(a, visited, queueI, queueJ, i, j);
                }
            }
        }

        System.out.println(k);

        scanner.close();
    }

    /**
     * Обход в ширину: отмечает посещенными все единицы острова, начиная с клетки (startI, startJ).
     * Каждая клетка попадает в очередь не больше одного раза, поэтому очереди длины n * m хватает.
     *
     * @param a       матрица из 0 и 1
     * @param visited отметки посещенных клеток
     * @param queueI  очередь: номера строк клеток
     * @param queueJ  очередь: номера столбцов клеток
     * @param startI  строка начальной клетки
     * @param startJ  столбец начальной клетки
     */
    private static void bfs(int[][] a, boolean[][] visited, int[] queueI, int[] queueJ,
                            int startI, int startJ) {
        int n = a.length;
        int m = a[0].length;
        int head = 0;
        int tail = 0;

        queueI[tail] = startI;
        queueJ[tail] = startJ;
        tail += 1;
        visited[startI][startJ] = true;

        while (head < tail) {
            int i = queueI[head];
            int j = queueJ[head];
            head += 1;
            for (int d = 0; d < DI.length; d++) {
                int ni = i + DI[d];
                int nj = j + DJ[d];
                if (ni >= 0 && ni < n && nj >= 0 && nj < m && a[ni][nj] == 1 && !visited[ni][nj]) {
                    visited[ni][nj] = true;
                    queueI[tail] = ni;
                    queueJ[tail] = nj;
                    tail += 1;
                }
            }
        }
    }
}