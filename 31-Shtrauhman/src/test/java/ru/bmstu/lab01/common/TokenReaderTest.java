package ru.bmstu.lab01.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Тесты TokenReader: разбиение ввода на слова, числа, размеры и матрицы.
 */
class TokenReaderTest {

    private static TokenReader readerOf(String text) {
        return new TokenReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void shouldReadTokensSplitAcrossLinesAndExtraSpaces() {
        TokenReader reader = readerOf("  1   2\n\n3\t4  \n");

        int[] actual = reader.nextIntArray(4);

        assertArrayEquals(new int[] {1, 2, 3, 4}, actual);
    }

    @Test
    void shouldReadNegativeNumbers() {
        assertEquals(-7, readerOf("-7").nextInt());
    }

    @Test
    void shouldReadLengthFollowedByArray() {
        int[] actual = readerOf("3\n10 20 30").nextSizedIntArray();

        assertArrayEquals(new int[] {10, 20, 30}, actual);
    }

    @Test
    void shouldReadMatrixRowByRow() {
        int[][] actual = readerOf("1 2 3\n4 5 6").nextIntMatrix(2, 3);

        assertArrayEquals(new int[] {1, 2, 3}, actual[0]);
        assertArrayEquals(new int[] {4, 5, 6}, actual[1]);
    }

    @Test
    void shouldThrowWhenInputEndsEarly() {
        TokenReader reader = readerOf("1 2");

        assertThrows(NoSuchElementException.class, () -> reader.nextIntArray(3));
    }

    @Test
    void shouldThrowWhenTokenIsNotNumber() {
        assertThrows(NumberFormatException.class, () -> readerOf("abc").nextInt());
    }

    @Test
    void shouldRejectNonPositiveSize() {
        assertThrows(IllegalArgumentException.class, () -> readerOf("0").nextSize());
        assertThrows(IllegalArgumentException.class, () -> readerOf("-3").nextSize());
    }

    @Test
    void shouldAcceptPositiveSize() {
        assertEquals(5, readerOf("5").nextSize());
    }
}
