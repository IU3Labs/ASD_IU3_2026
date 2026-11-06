package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task4Test {
    @Test
    void shouldFindAllIndexes() {
        int[] arr = {1, 2, 1};
        int count = 0;
        for (int i = 0; i < arr.length; i++) if (arr[i] == 1) count++;
        assertEquals(2, count); // Индексы 0 и 2
    }
}