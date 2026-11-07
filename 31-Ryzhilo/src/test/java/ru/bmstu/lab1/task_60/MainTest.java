package ru.bmstu.lab1.task_60;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Граничные и функциональные тесты задачи 60. */
class MainTest {

    @Test
    void shouldCountSeparateComponents() {
        assertEquals("2\n", run("2 3\n1 0 1\n1 0 1\n"));
    }

    @Test
    void shouldCountZeroForSingleWaterCell() {
        assertEquals("0\n", run("1 1\n0\n"));
    }

    @Test
    void shouldKeepDiagonalCellsSeparate() {
        assertEquals("2\n", run("2 2\n1 0\n0 1\n"));
    }

    @Test
    void shouldCountConnectedLandAsOneIsland() {
        assertEquals("1\n", run("2 2\n1 1\n1 1\n"));
    }

    @Test
    void shouldCountZeroForAllWater() {
        assertEquals("0\n", run("2 2\n0 0\n0 0\n"));
    }

    @Test
    void shouldCountSeparateIslandsInColumn() {
        assertEquals("2\n", run("3 1\n1\n0\n1\n"));
    }

    @Test
    void shouldHandleSingleLandCell() {
        assertEquals("1\n", run("1 1\n1\n"));
    }

    @Test
    void shouldHandleLargeConnectedIsland() {
        int size = 120;
        StringBuilder input = new StringBuilder();
        input.append(size).append(' ').append(size).append('\n');
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                input.append("1 ");
            }
            input.append('\n');
        }
        assertEquals("1\n", run(input.toString()));
    }

    private static String run(String inputText) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner input = new Scanner(inputText);
                PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            Main.solve(input, output);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
