package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task41Test {

    @Test
    void shouldRemoveAllOccurrences() {
        int[] numbers = {1, 2, 3, 2, 4, 2};

        int[] result = Task41.removeAll(numbers, 2);

        assertArrayEquals(new int[]{1, 3, 4}, result);
    }

    @Test
    void shouldReturnEmptyArrayWhenAllElementsMatch() {
        int[] numbers = {5, 5, 5};

        int[] result = Task41.removeAll(numbers, 5);

        assertArrayEquals(new int[]{}, result);
    }

    @Test
    void shouldKeepArrayWhenElementIsAbsent() {
        int[] numbers = {1, 3, 4};

        int[] result = Task41.removeAll(numbers, 2);

        assertArrayEquals(new int[]{1, 3, 4}, result);
    }
}
