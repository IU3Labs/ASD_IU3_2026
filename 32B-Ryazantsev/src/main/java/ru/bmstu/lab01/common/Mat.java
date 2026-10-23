// Проверка размеров матрицы
package ru.bmstu.lab01.common;

public final class Mat {
    private Mat() {
    }

    /** Возвращает число столбцов и проверяет матрицу */
    public static int cols(int[][] a) {
        if (a == null || a.length == 0 || a[0] == null || a[0].length == 0) {
            throw new IllegalArgumentException("Пустая матрица");
        }
        int m = a[0].length;
        for (int[] row : a) {
            if (row == null || row.length != m) {
                throw new IllegalArgumentException("Неправильный размер матрицы");
            }
        }
        return m;
    }
}
