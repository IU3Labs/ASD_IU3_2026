package ru.bmstu.lab01.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class InputReaderTest {

    private static InputReader readerOf(String text) {
        return new InputReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void shouldReadNumbersSeparatedByAnyWhitespace() {
        InputReader reader = readerOf("  1\t-2\n\n3  ");

        assertEquals(1, reader.nextInt());
        assertEquals(-2, reader.nextInt());
        assertEquals(3, reader.nextInt());
    }

    @Test
    void shouldReadArrayPrefixedWithLength() {
        int[] expected = {5, 6, 7};

        assertArrayEquals(expected, readerOf("3\n5 6 7").nextArray());
    }

    @Test
    void shouldReadMatrixPrefixedWithDimensions() {
        int[][] expected = {{1, 2, 3}, {4, 5, 6}};

        assertArrayEquals(expected, readerOf("2 3\n1 2 3\n4 5 6").nextMatrix());
    }

    @Test
    void shouldThrowWhenInputEndsEarly() {
        InputReader reader = readerOf("3\n1 2");

        assertThrows(NoSuchElementException.class, reader::nextArray);
    }

    @Test
    void shouldThrowWhenSizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> readerOf("-1").nextArray());
    }

    @Test
    void shouldThrowWhenTokenIsNotNumber() {
        assertThrows(NumberFormatException.class, () -> readerOf("abc").nextInt());
    }

    @Test
    void shouldWrapIoErrorIntoUncheckedException() {
        InputStream failing = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("disk failure");
            }
        };

        assertThrows(UncheckedIOException.class, () -> new InputReader(failing).nextInt());
    }
}
