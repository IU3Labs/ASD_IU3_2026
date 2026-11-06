package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class Task43Test {

    @Test
    void shouldMoveZeroesToEndPreservingOrder() {
        int[] array = {0, 1, 0, 3, 12};
        int[] expected = {1, 3, 12, 0, 0};
        
        Task43.moveZeroes(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void shouldNotChangeArrayWithoutZeroes() {
        int[] array = {1, 2, 3};
        int[] expected = {1, 2, 3};
        
        Task43.moveZeroes(array);
        
        assertArrayEquals(expected, array);
    }

    @Test
    void shouldWorkWithMaximumAllowedLength() {
        int[] array = new int[99_999];
        int[] expected = new int[99_999];
        
        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 0) {
                array[i] = 0;
            } else {
                array[i] = i;
                expected[i / 2] = i;
            }
        }
        
        Task43.moveZeroes(array);
        
        assertArrayEquals(expected, array);
    }
}
