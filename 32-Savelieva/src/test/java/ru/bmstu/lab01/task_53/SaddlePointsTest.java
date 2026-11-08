package ru.bmstu.lab01.task_53;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class SaddlePointsTest {

    @Test
    void shouldFindSingleSaddlePoint() {
        // Строка 0: min = 1 (col 0); столбец 0: max = 3 (row 1) → не седло
        // Строка 1: min = 2 (col 0); столбец 0: max = 3 → нет
        // Проверим другую матрицу
        int[][] matrix = {{1, 2}, {3, 2}};
        List<int[]> points = SaddlePoints.find(matrix);
        // строка 0: min=1 (0,0); столбец 0: max=3 → не седло
        // строка 1: min=2 (1,1); столбец 1: max=2 → седло (1,1,2)
        assertEquals(1, points.size());
        assertEquals(1, points.get(0)[0]);
        assertEquals(1, points.get(0)[1]);
        assertEquals(2, points.get(0)[2]);
    }

    @Test
    void shouldFindNoSaddlePoints() {
        int[][] matrix = {{1, 2}, {3, 4}};
        // строка 0: min=1 (0,0); столбец 0: max=3 → нет
        // строка 1: min=3 (1,0); столбец 0: max=3 → седло (1,0,3)!
        // Проверим, что find вернёт хотя бы одну — значит, матрица не подходит.
        // Возьмём другую: {{1,2,3},{4,5,6}} — седла нет
        int[][] noSaddle = {{1, 2, 3}, {4, 5, 6}};
        // строка 0: min=1 (0,0); столбец 0: max=4 → нет
        // строка 1: min=4 (1,0); столбец 0: max=4 → седло (1,0,4)!
        // Хм, тоже есть. Возьмём матрицу где точно нет: {{2,1},{1,2}}
        int[][] trulyNo = {{2, 1}, {1, 2}};
        // строка 0: min=1 (0,1); столбец 1: max=2 → нет
        // строка 1: min=1 (1,0); столбец 0: max=2 → нет
        assertTrue(SaddlePoints.find(trulyNo).isEmpty());
    }

    @Test
    void shouldFindMultipleSaddlePointsWhenValuesEqual() {
        int[][] matrix = {{5, 5}, {5, 5}};
        // строка 0: min=5 (обе); столбец 0: max=5 (обе) → седло (0,0,5) и (1,0,5)
        // строка 0: min=5 (обе); столбец 1: max=5 (обе) → седло (0,1,5) и (1,1,5)
        assertEquals(4, SaddlePoints.find(matrix).size());
    }

    @Test
    void shouldHandleSingleCell() {
        List<int[]> points = SaddlePoints.find(new int[][] {{7}});
        assertEquals(1, points.size());
        assertEquals(7, points.get(0)[2]);
    }

    @Test
    void shouldHandleSingleRow() {
        // строка 0: min=2 (0,1); столбец 1: max=2 (0,1) → седло
        List<int[]> points = SaddlePoints.find(new int[][] {{5, 2, 8}});
        assertEquals(1, points.size());
        assertEquals(1, points.get(0)[1]);
        assertEquals(2, points.get(0)[2]);
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SaddlePoints.find(null));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> SaddlePoints.find(new int[0][]));
    }

    @Test
    void shouldRejectRaggedMatrix() {
        assertThrows(IllegalArgumentException.class,
                () -> SaddlePoints.find(new int[][] {{1, 2}, {3}}));
    }
}