package ru.bmstu.lab1.task_41;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/** Проверяет решение задачи №41, включая граничные случаи. */
class Task41Test {
    @Test
    void shouldRemoveEveryMatch() {
        assertArrayEquals(new int[]{1, 3, 4},
                        Task41.removeAll(new int[]{1, 2, 2, 3, 2, 4}, 2));
    }

    @Test
    void shouldPreserveValuesWhenAbsent() {
        assertArrayEquals(new int[]{1, 2, 3}, Task41.removeAll(new int[]{1, 2, 3}, 8));
    }

    @Test
    void shouldAllowEmptyResult() {
        assertArrayEquals(new int[]{}, Task41.removeAll(new int[]{0, 0, 0}, 0));
    }

    @Test
    void shouldPreserveOriginalArray() {
        int[] original = {1, 2, 3};
                    Task41.removeAll(original, 2);
                    assertArrayEquals(new int[]{1, 2, 3}, original);
    }
}
