/*
 * Задача 60. Острова из единиц.
 * Вход: бинарная матрица n x m, состоящая из 0 и 1.
 * Выход: количество «островов» — групп связанных единиц (связь по горизонтали и вертикали,
 * не по диагонали).
 * Требование: поиск в глубину (DFS) или в ширину (BFS).
 */
package ru.bmstu.lab01.task_60;

import java.io.InputStream;
import java.io.PrintStream;

import ru.bmstu.lab01.common.InputReader;
import ru.bmstu.lab01.common.Matrices;

/**
 * Подсчёт островов в бинарной матрице поиском в ширину (BFS).
 *
 * <p>Формат ввода: {@code n m}, затем матрица из 0 и 1 построчно. Вывод: одно число — количество островов.
 * Время O(n * m), дополнительная память O(n * m).
 */
public final class IslandCounter {

    private static final int LAND = 1;
    private static final int WATER = 0;

    /** Смещения к соседям по вертикали и горизонтали: вверх, вниз, влево, вправо. */
    private static final int[] ROW_OFFSETS = {-1, 1, 0, 0};
    private static final int[] COL_OFFSETS = {0, 0, -1, 1};

    private IslandCounter() {
    }

    /**
     * Точка входа: читает матрицу из стандартного ввода и печатает количество островов.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    static void run(InputStream input, PrintStream output) {
        int[][] grid = new InputReader(input).nextMatrix();
        output.println(count(grid));
    }

    /**
     * Считает количество островов; исходная матрица не меняется.
     *
     * @param grid прямоугольная матрица из 0 и 1
     * @return количество островов
     * @throws IllegalArgumentException если в матрице есть значения кроме 0 и 1
     */
    public static int count(int[][] grid) {
        int cols = Matrices.requireRectangular(grid);
        requireBinary(grid);
        boolean[][] visited = new boolean[grid.length][cols];
        // Очередь BFS создаётся один раз: каждая клетка попадает в неё не более одного раза за весь подсчёт.
        int[] queue = new int[grid.length * cols];
        int islands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == LAND && !visited[i][j]) {
                    markIsland(grid, visited, queue, i, j);
                    islands++;
                }
            }
        }
        return islands;
    }

    /**
     * Помечает посещёнными все клетки острова, содержащего (startRow, startCol).
     * BFS выбран вместо рекурсивного DFS: на матрице 1000 x 1000 из единиц глубина рекурсии
     * достигла бы 10^6 и вызвала бы StackOverflowError. Клетка кодируется числом row * cols + col.
     */
    private static void markIsland(int[][] grid, boolean[][] visited, int[] queue, int startRow, int startCol) {
        int cols = grid[0].length;
        int head = 0;
        int tail = 0;
        visited[startRow][startCol] = true;
        queue[tail++] = startRow * cols + startCol;
        while (head < tail) {
            int cell = queue[head++];
            int row = cell / cols;
            int col = cell % cols;
            for (int d = 0; d < ROW_OFFSETS.length; d++) {
                int nextRow = row + ROW_OFFSETS[d];
                int nextCol = col + COL_OFFSETS[d];
                if (isUnvisitedLand(grid, visited, nextRow, nextCol)) {
                    visited[nextRow][nextCol] = true;
                    queue[tail++] = nextRow * cols + nextCol;
                }
            }
        }
    }

    private static boolean isUnvisitedLand(int[][] grid, boolean[][] visited, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length
                && grid[row][col] == LAND && !visited[row][col];
    }

    private static void requireBinary(int[][] grid) {
        for (int[] row : grid) {
            for (int value : row) {
                if (value != LAND && value != WATER) {
                    throw new IllegalArgumentException("Grid must contain only 0 and 1, found: " + value);
                }
            }
        }
    }
}
