package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class task_19_test {
    @Test
    void shouldMergeArrays() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, 
            task_19.merge(new int[]{1, 3, 5}, new int[]{2, 4, 6}));
    }
}