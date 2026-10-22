package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Тесты вывода массивов и матриц.
 */
class OutputWriterTest {

    @Test
    void shouldPrintArraySeparatedBySpaces() {
        List<String> actual = ConsoleRunner.execute(
                (in, out) -> OutputWriter.printArray(out, new int[] {1, -2, 3}), "");

        assertEquals(List.of("1 -2 3"), actual);
    }

    @Test
    void shouldPrintEachMatrixRowOnSeparateLine() {
        List<String> actual = ConsoleRunner.execute(
                (in, out) -> OutputWriter.printMatrix(out, new int[][] {{1, 2}, {3, 4}}), "");

        assertEquals(List.of("1 2", "3 4"), actual);
    }
}
