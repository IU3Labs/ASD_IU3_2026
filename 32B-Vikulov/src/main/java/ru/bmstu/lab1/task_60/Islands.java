package ru.bmstu.lab1.task_60;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 60. Острова из единиц.
 *
 * <p>Вход: {@code n m}, затем бинарная матрица {@code n x m}. Выход: количество групп единиц, связанных
 * по горизонтали и вертикали.
 *
 * <p>Перебираем клетки; встретив непосещённую единицу, увеличиваем счётчик и поиском в глубину помечаем
 * весь её остров. DFS сделан итеративным, с явным стеком: рекурсия на матрице из одних единиц
 * (до 10^5 клеток и больше) привела бы к {@link StackOverflowError}. Клетка помечается в момент
 * добавления в стек, поэтому попадает в него не более одного раза и стека размера {@code n * m} хватает.
 * O(nm) по времени и памяти. Входная матрица не изменяется.
 */
public final class Islands {

    private static final int[] ROW_STEPS = {-1, 1, 0, 0};
    private static final int[] COLUMN_STEPS = {0, 0, -1, 1};

    private Islands() {
    }

    /**
     * Считает количество островов.
     *
     * @param grid прямоугольная матрица из 0 и 1
     * @return число островов
     */
    public static int count(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int[] stack = new int[grid.length * grid[0].length];
        int islands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    islands++;
                    markIsland(grid, visited, stack, i, j);
                }
            }
        }
        return islands;
    }

    /** Помечает все клетки острова, содержащего {@code (startRow, startColumn)}. */
    private static void markIsland(int[][] grid, boolean[][] visited, int[] stack, int startRow, int startColumn) {
        int columns = grid[0].length;
        int size = 0;
        visited[startRow][startColumn] = true;
        // клетка (r, c) кодируется одним числом r * columns + c
        stack[size++] = startRow * columns + startColumn;
        while (size > 0) {
            int cell = stack[--size];
            int row = cell / columns;
            int column = cell % columns;
            for (int d = 0; d < ROW_STEPS.length; d++) {
                int nextRow = row + ROW_STEPS[d];
                int nextColumn = column + COLUMN_STEPS[d];
                if (isUnvisitedLand(grid, visited, nextRow, nextColumn)) {
                    visited[nextRow][nextColumn] = true;
                    stack[size++] = nextRow * columns + nextColumn;
                }
            }
        }
    }

    private static boolean isUnvisitedLand(int[][] grid, boolean[][] visited, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length
                && grid[row][column] == 1 && !visited[row][column];
    }

    /**
     * Точка входа: читает матрицу, печатает число островов.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int rows = in.nextInt();
            int columns = in.nextInt();
            out.println(count(in.nextMatrix(rows, columns)));
        });
    }
}
