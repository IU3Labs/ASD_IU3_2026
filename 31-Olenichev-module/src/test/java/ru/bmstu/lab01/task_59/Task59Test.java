package ru.bmstu.lab01.task_59;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Task59Test {
    private final Task59 solver = new Task59();

    @Test
    public void shouldCalculateDeterminantForIdentityMatrix() {
        int[][] matrix = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        int expected = 1;
        
        int actual = solver.calculateDeterminant(matrix);
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnZeroForNullMatrix() {
        int expected = 0;
        
        int actual = solver.calculateDeterminant(null);
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldCalculateDeterminantForRandomMatrix() {
        // Произвольная матрица
        int[][] matrix = {
            {2, 5, 3}, 
            {1, 8, 4}, 
            {7, 6, 9}
        };
        int expected = 41; // Определитель = 41
        
        int actual = solver.calculateDeterminant(matrix);
        
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnZeroForEmptyMatrix() {
        int[][] matrix = {};
        int expected = 0;
        
        int actual = solver.calculateDeterminant(matrix);
        
        Assertions.assertEquals(expected, actual);
    }
}