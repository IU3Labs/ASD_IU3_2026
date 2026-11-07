package ru.bmstu.lab1;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayIOTest {

    @Test
    void shouldReadArrayOfGivenLength() {
        int[] numbers = ArrayIO.readArray(new Scanner("3 -1 7 0"), 3);

        assertArrayEquals(new int[] {3, -1, 7}, numbers);
    }

    @Test
    void shouldReadMatrixRowByRow() {
        int[][] matrix = ArrayIO.readMatrix(new Scanner("1 2 3\n4 5 6\n"), 2, 3);

        assertArrayEquals(new int[] {1, 2, 3}, matrix[0]);
        assertArrayEquals(new int[] {4, 5, 6}, matrix[1]);
    }

    @Test
    void shouldPrintIntNumbersSeparatedBySpaces() {
        String output = ProgramOutput.read(args -> ArrayIO.printNumbers(new int[] {1, -2, 3}), "");

        assertEquals("1 -2 3", output);
    }

    @Test
    void shouldPrintLongNumbersSeparatedBySpaces() {
        String output = ProgramOutput.read(args -> ArrayIO.printNumbers(new long[] {3000000000L, 5L}), "");

        assertEquals("3000000000 5", output);
    }

    @Test
    void shouldPrintEmptyLineForEmptyArray() {
        String output = ProgramOutput.read(args -> ArrayIO.printNumbers(new int[0]), "");

        assertEquals("", output);
    }
}
