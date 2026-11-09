package ru.bmstu.lab01.task_13;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Task13Test {
    @Test

    void shouldSortRandomArrayWithNegativesAndDuplicates() {
        int[] actual = {5, -2, 5, 0, 0, -10, 3};
        int[] expected = {-10, -2, 0, 0, 3, 5, 5};
        Task13.selectionSort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test

    void shouldHandleReverseSortedArray() {
        int[] actual = {9, 7, 5, 3, 1};
        int[] expected = {1, 3, 5, 7, 9};
        Task13.selectionSort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    
    void shouldReadInputAndPrintSortedArray() {
        String input = "4\n10 -5 0 3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task13.solve(in, new PrintStream(out));
        Assertions.assertEquals("-5 0 3 10", out.toString(StandardCharsets.UTF_8).trim());
    }
}