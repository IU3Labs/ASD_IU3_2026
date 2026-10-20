package ru.bmstu.lab1;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MatrixTasksTest {
    @Test
    void shouldSumRowsOfRectangularMatrix() {
        int[][] a = {{1, 2, 3}, {-4, 0, 7}};
        assertArrayEquals(new long[]{6, 3}, ru.bmstu.lab1.task_49.Main.sumRows(a));
    }

    @Test
    void shouldSumColumnsOfRectangularMatrix() {
        int[][] a = {{1, 2, 3}, {-4, 0, 7}};
        assertArrayEquals(new long[]{-3, 2, 10}, ru.bmstu.lab1.task_50.Main.sumColumns(a));
    }

    @Test
    void shouldHandleSingleRowAndColumn() {
        int[][] row = {{-3, 0, 5}};
        int[][] col = {{-3}, {0}, {5}};
        assertArrayEquals(new long[]{2}, ru.bmstu.lab1.task_49.Main.sumRows(row));
        assertArrayEquals(new long[]{-3, 0, 5}, ru.bmstu.lab1.task_49.Main.sumRows(col));
        assertArrayEquals(new long[]{-3, 0, 5}, ru.bmstu.lab1.task_50.Main.sumColumns(row));
        assertArrayEquals(new long[]{2}, ru.bmstu.lab1.task_50.Main.sumColumns(col));
    }

    @Test
    void shouldKeepSumsOutsideIntRange() {
        int[][] a = {{Integer.MAX_VALUE, Integer.MAX_VALUE},
                     {Integer.MIN_VALUE, Integer.MIN_VALUE}};
        assertArrayEquals(new long[]{4294967294L, -4294967296L},
                ru.bmstu.lab1.task_49.Main.sumRows(a));
        assertArrayEquals(new long[]{4294967294L},
                ru.bmstu.lab1.task_50.Main.sumColumns(new int[][]{{Integer.MAX_VALUE}, {Integer.MAX_VALUE}}));
    }

    @Test
    void shouldSumLargeMatrix() {
        int[][] a = new int[200][500];
        long[] rows = new long[200];
        long[] cols = new long[500];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = 1;
            }
            rows[i] = 500;
        }
        java.util.Arrays.fill(cols, 200);
        assertArrayEquals(rows, ru.bmstu.lab1.task_49.Main.sumRows(a));
        assertArrayEquals(cols, ru.bmstu.lab1.task_50.Main.sumColumns(a));
    }

    @Test
    void shouldMultiplyRectangularMatrices() {
        int[][] a = {{1, 2, 3}, {4, 5, 6}};
        int[][] b = {{7, 8}, {9, 10}, {11, 12}};
        long[][] c = ru.bmstu.lab1.task_56.Main.multiply(a, b);
        assertArrayEquals(new long[]{58, 64}, c[0]);
        assertArrayEquals(new long[]{139, 154}, c[1]);
    }

    @Test
    void shouldMultiplyNegativeAndZeroValues() {
        int[][] a = {{-2, 3}, {0, -1}};
        int[][] b = {{4, 0}, {-5, 2}};
        long[][] c = ru.bmstu.lab1.task_56.Main.multiply(a, b);
        assertArrayEquals(new long[]{-23, 6}, c[0]);
        assertArrayEquals(new long[]{5, -2}, c[1]);
    }

    @Test
    void shouldKeepProductsOutsideIntRange() {
        int[][] a = {{Integer.MIN_VALUE, Integer.MAX_VALUE}};
        int[][] b = {{Integer.MIN_VALUE}, {Integer.MAX_VALUE}};
        long[][] c = ru.bmstu.lab1.task_56.Main.multiply(a, b);
        assertArrayEquals(new long[]{9223372032559808513L}, c[0]);
    }

    @Test
    void shouldMultiplyByIdentityAndZero() {
        int[][] a = {{-5, 8}, {2, 0}, {9, -3}};
        int[][] unit = {{1, 0}, {0, 1}};
        int[][] zero = new int[2][2];
        long[][] c = ru.bmstu.lab1.task_56.Main.multiply(a, unit);
        long[][] d = ru.bmstu.lab1.task_56.Main.multiply(a, zero);
        for (int i = 0; i < a.length; i++) {
            assertArrayEquals(new long[]{a[i][0], a[i][1]}, c[i]);
            assertArrayEquals(new long[]{0, 0}, d[i]);
        }
    }

    @Test
    void shouldMultiplySingleRowAndColumn() {
        long[][] c = ru.bmstu.lab1.task_56.Main.multiply(new int[][]{{2}, {-3}}, new int[][]{{4, 5, 0}});
        assertArrayEquals(new long[]{8, 10, 0}, c[0]);
        assertArrayEquals(new long[]{-12, -15, 0}, c[1]);
        assertArrayEquals(new long[]{-6},
                ru.bmstu.lab1.task_56.Main.multiply(new int[][]{{2}}, new int[][]{{-3}})[0]);
    }

    @Test
    void shouldTraverseSquareWithCenter() {
        int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertArrayEquals(new long[]{1, 2, 3, 6, 9, 8, 7, 4, 5}, ru.bmstu.lab1.task_57.Main.spiral(a));
    }

    @Test
    void shouldTraverseWideAndTallMatrices() {
        assertArrayEquals(new long[]{1, 2, 3, 4, 8, 7, 6, 5},
                ru.bmstu.lab1.task_57.Main.spiral(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}}));
        assertArrayEquals(new long[]{1, 2, 4, 6, 8, 7, 5, 3},
                ru.bmstu.lab1.task_57.Main.spiral(new int[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}));
    }

    @Test
    void shouldTraverseSingleRowColumnAndCell() {
        assertArrayEquals(new long[]{-1, 0, -1}, ru.bmstu.lab1.task_57.Main.spiral(new int[][]{{-1, 0, -1}}));
        assertArrayEquals(new long[]{-1, 0, -1}, ru.bmstu.lab1.task_57.Main.spiral(new int[][]{{-1}, {0}, {-1}}));
        assertArrayEquals(new long[]{42}, ru.bmstu.lab1.task_57.Main.spiral(new int[][]{{42}}));
    }

    @Test
    void shouldMatchIndependentSpiralWalk() {
        Random random = new Random(57);
        for (int n = 1; n <= 10; n++) {
            for (int m = 1; m <= 10; m++) {
                int[][] a = new int[n][m];
                for (int[] row : a) {
                    for (int j = 0; j < m; j++) {
                        row[j] = random.nextInt();
                    }
                }
                assertArrayEquals(walk(a), ru.bmstu.lab1.task_57.Main.spiral(a));
            }
        }
    }

    private long[] walk(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        boolean[][] seen = new boolean[n][m];
        int[] di = {0, 1, 0, -1};
        int[] dj = {1, 0, -1, 0};
        int i = 0;
        int j = 0;
        int dir = 0;
        long[] result = new long[n * m];
        for (int pos = 0; pos < result.length; pos++) {
            result[pos] = a[i][j];
            seen[i][j] = true;
            int ni = i + di[dir];
            int nj = j + dj[dir];
            if (ni < 0 || ni >= n || nj < 0 || nj >= m || seen[ni][nj]) {
                dir = (dir + 1) % 4;
            }
            i += di[dir];
            j += dj[dir];
        }
        return result;
    }
}
