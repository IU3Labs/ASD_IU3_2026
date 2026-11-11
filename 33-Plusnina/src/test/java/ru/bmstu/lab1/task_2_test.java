package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class task_2_test {
    @Test
    void shouldReturnFirstOccurrence() {
        assertEquals(1, task_2.firstIndexOf(new int[]{5, 7, 7, 3}, 7));
    }
    @Test
    void shouldReturnMinusOneWhenAbsent() {
        assertEquals(-1, task_2.firstIndexOf(new int[]{1, 2, 3}, 42));
    }
}