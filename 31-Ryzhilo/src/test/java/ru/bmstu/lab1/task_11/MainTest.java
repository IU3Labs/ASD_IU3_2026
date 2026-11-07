package ru.bmstu.lab1.task_11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 11: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldReturnElementEqualToIntegerAverage() {
        assertEquals("3", run("5\n1 2 3 4 5"));
    }

    @Test
    void shouldReturnClosestElementWhenAverageIsFractional() {
        // среднее 7/3 = 2.33...
        assertEquals("2", run("3\n1 2 4"));
    }

    @Test
    void shouldReturnFirstByIndexWhenDistancesAreEqual() {
        // среднее 2: числа 1 и 3 равноудалены
        assertEquals("1", run("2\n1 3"));
        assertEquals("3", run("2\n3 1"));
    }

    @Test
    void shouldReturnFirstByIndexAmongEqualDistancesInMiddle() {
        // среднее 5: числа 3 и 7 равноудалены, а 5 в массиве нет
        assertEquals("7", run("4\n0 7 3 10"));
    }

    @Test
    void shouldReturnElementWhenAllElementsAreEqual() {
        assertEquals("4", run("3\n4 4 4"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        // среднее -2
        assertEquals("-2", run("3\n-5 -2 1"));
    }

    @Test
    void shouldHandleNegativeFractionalAverage() {
        // среднее -5/3 = -1.66...
        assertEquals("-2", run("3\n-10 -2 7"));
    }

    @Test
    void shouldHandleZeroAverage() {
        assertEquals("0", run("5\n-4 -1 0 1 4"));
    }

    @Test
    void shouldNotPickOutlierThatPullsAverageUp() {
        // среднее 25: единицы (расстояние 24) ближе, чем выброс 97 (расстояние 72)
        assertEquals("1", run("4\n1 1 1 97"));
    }

    @Test
    void shouldNotOverflowOnIntegerExtremes() {
        // среднее -0.5: MAX_VALUE и MIN_VALUE равноудалены, берётся первый
        assertEquals("2147483647", run("2\n2147483647 -2147483648"));
        assertEquals("-2147483648", run("2\n-2147483648 2147483647"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("3", run("5\n1\n2\n3\n4\n5"));
    }

    @Test
    void shouldHandleMaximumInputSize() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        for (int i = 1; i <= size; i++) {
            input.append(i).append(' ');
        }
        // среднее чисел 1..99999 равно 50000
        assertEquals("50000", run(input.toString()));
    }
}
