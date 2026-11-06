package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class Task1Test {
    
    @Test
    void shouldReturnMinMaxForMixedNumbers() {
        int[] array = {-10, 0, 5, -20, 30};
        int[] expected = {-20, 30};
        assertArrayEquals(expected, Task1.findMinMax(array));
    }

    @Test
    void shouldReturnMinMaxForAlternatingSequence() {
        int[] array = new int[99_999];
        for (int i = 0; i < array.length; i++) {
            array[i] = (i % 2 == 0) ? -(i + 1) : (i + 1);
        }
        int[] expected = {-99_999, 99_998};
        assertArrayEquals(expected, Task1.findMinMax(array));
}
}