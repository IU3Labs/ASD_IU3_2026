package ru.bmstu.lab1.task11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;

class ClosestToAverageTest {

    @Test
    void shouldReturnElementEqualToAverage() {
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals(3, ClosestToAverage.find(arr));
    }

    @Test
    void shouldWorkWithFractionalAverage() {
        // среднее 7 / 3 = 2.33..., ближе всех 2
        int[] arr = {1, 2, 4};
        assertEquals(2, ClosestToAverage.find(arr));
    }

    @Test
    void shouldReturnFirstElementWhenDistancesAreEqual() {
        // среднее 2, оба элемента на расстоянии 1
        int[] arr = {3, 1};
        assertEquals(3, ClosestToAverage.find(arr));
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        // среднее -10 / 4 = -2.5, ближе всех -3
        int[] arr = {-7, 5, -3, -5};
        assertEquals(-3, ClosestToAverage.find(arr));
    }

    @Test
    void shouldNotOverflowOnLargeNumbers() {
        // среднее чуть меньше MAX_VALUE, ближе всех MAX_VALUE
        int[] arr = {Integer.MAX_VALUE - 1, Integer.MAX_VALUE, Integer.MAX_VALUE};
        assertEquals(Integer.MAX_VALUE, ClosestToAverage.find(arr));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        int[] arr = {};
        assertThrows(IllegalArgumentException.class, () -> ClosestToAverage.find(arr));
    }

    @Test
    void shouldMatchSimpleSolutionOnRandomArrays() {
        Random random = new Random(11);
        for (int test = 0; test < 500; test++) {
            int[] arr = new int[2 + random.nextInt(20)];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(21) - 10;
            }
            assertEquals(simpleSolution(arr), ClosestToAverage.find(arr));
        }
    }

    /** Решение «в лоб» через дробное среднее, для сравнения на маленьких числах. */
    private static int simpleSolution(int[] arr) {
        double sum = 0;
        for (int value : arr) {
            sum += value;
        }
        double average = sum / arr.length;
        int best = arr[0];
        for (int value : arr) {
            if (Math.abs(value - average) < Math.abs(best - average)) {
                best = value;
            }
        }
        return best;
    }
}
