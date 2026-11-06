package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class InputReaderTest {

    private static InputReader readerOf(String text) {
        return new InputReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void shouldReadNumbersAcrossLinesAndBlankLines() {
        InputReader reader = readerOf("3\n\n  1   2\n3\n");

        int size = reader.nextInt();
        int[] actual = reader.nextIntArray(size);

        assertArrayEquals(new int[] {1, 2, 3}, actual);
    }

    @Test
    void shouldReadMatrixRowByRow() {
        int[][] actual = readerOf("1 2 3\n4 5 6").nextMatrix(2, 3);

        assertArrayEquals(new int[] {1, 2, 3}, actual[0]);
        assertArrayEquals(new int[] {4, 5, 6}, actual[1]);
    }

    @Test
    void shouldThrowWhenInputEndsEarly() {
        InputReader reader = readerOf("1");
        reader.nextInt();

        assertThrows(IllegalStateException.class, reader::nextInt);
    }

    @Test
    void shouldJoinArrayWithSpaces() {
        assertEquals("1 -2 3", ConsoleIo.join(new int[] {1, -2, 3}));
        assertEquals("", ConsoleIo.join(new int[0]));
    }
}
