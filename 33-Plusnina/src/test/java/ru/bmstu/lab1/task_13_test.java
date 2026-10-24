package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class task_13_test {
    @Test
    void shouldSortArray() {
        int[] a = {5, 3, 8, 1};
        task_13.sort(a);
        assertArrayEquals(new int[]{1, 3, 5, 8}, a);
    }
}