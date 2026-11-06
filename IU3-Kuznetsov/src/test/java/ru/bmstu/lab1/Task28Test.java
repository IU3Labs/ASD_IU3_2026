package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task28Test {
    @Test
    void shouldFindMedianOdd() {
        int[] arr = {3, 1, 2};
        // Сортируем пузырьком для теста
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = t;
                }
            }
        }
        assertEquals(2, arr[arr.length / 2]);
    }
}