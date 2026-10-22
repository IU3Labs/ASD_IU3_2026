package ru.bmstu.lab1.task_60;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import ru.bmstu.lab1.common.InputReader;
import ru.bmstu.lab1.common.Validation;

/**
 * Задача 60. Острова из единиц.
 *
 * <p>Вход: бинарная матрица {@code n x m}, состоящая из 0 и 1.
 * Выход: количество «островов» — групп связанных единиц (связь по горизонтали и вертикали).
 * Требование: поиск в глубину (DFS) или в ширину (BFS).
 *
 * <p>Идея: проходим по всем клеткам. Встретив ещё не посещённую единицу, увеличиваем счётчик
 * островов и поиском в глубину помечаем весь остров как посещённый — тогда каждый остров
 * будет посчитан ровно один раз. DFS реализован через явный стек ({@link ArrayDeque}),
 * а не рекурсией: на большой матрице из одних единиц глубина рекурсии достигла бы {@code n * m}
 * и вызвала бы {@link StackOverflowError}.
 */
public final class IslandCounter {

    /** Смещения к четырём соседям: вверх, вниз, влево, вправо (диагонали не учитываются). */
    private static final int[][] NEIGHBOR_OFFSETS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private static final int LAND = 1;
    private static final int WATER = 0;

    private IslandCounter() {
    }

    /**
     * Считает количество островов в бинарной матрице. Исходная матрица не изменяется.
     *
     * @param grid прямоугольная матрица из 0 и 1
     * @return количество островов
     * @throws IllegalArgumentException если матрица пустая, непрямоугольная или содержит не 0/1
     */
    public static int countIslands(int[][] grid) {
        validateBinary(grid);
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int islands = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == LAND && !visited[row][col]) {
                    islands++;
                    markIsland(grid, visited, row, col);
                }
            }
        }
        return islands;
    }

    /**
     * Поиском в глубину помечает все клетки острова, содержащего клетку {@code (startRow, startCol)}.
     */
    private static void markIsland(int[][] grid, boolean[][] visited, int startRow, int startCol) {
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[] {startRow, startCol});
        visited[startRow][startCol] = true;
        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            for (int[] offset : NEIGHBOR_OFFSETS) {
                int row = cell[0] + offset[0];
                int col = cell[1] + offset[1];
                if (isUnvisitedLand(grid, visited, row, col)) {
                    // Помечаем при добавлении в стек, чтобы одна клетка не попала в него дважды.
                    visited[row][col] = true;
                    stack.push(new int[] {row, col});
                }
            }
        }
    }

    private static boolean isUnvisitedLand(int[][] grid, boolean[][] visited, int row, int col) {
        boolean inside = row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
        return inside && grid[row][col] == LAND && !visited[row][col];
    }

    private static void validateBinary(int[][] grid) {
        Validation.requireRectangular(grid);
        for (int[] row : grid) {
            for (int value : row) {
                if (value != LAND && value != WATER) {
                    throw new IllegalArgumentException("Матрица должна содержать только 0 и 1: " + value);
                }
            }
        }
    }

    /**
     * Читает бинарную матрицу и печатает количество островов.
     *
     * @param in  источник данных
     * @param out поток вывода
     */
    public static void run(Scanner in, PrintStream out) {
        out.println(countIslands(InputReader.readMatrix(in)));
    }

    /**
     * Точка входа: ввод с клавиатуры, вывод в консоль.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }
}
