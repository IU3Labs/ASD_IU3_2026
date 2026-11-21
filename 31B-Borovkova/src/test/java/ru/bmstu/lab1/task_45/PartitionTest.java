package ru.bmstu.lab1.task_45;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 45: программа запускается с подставленным вводом, проверяется её вывод.
 * Порядок внутри групп по условию не важен, поэтому проверяется не конкретный вывод,
 * а то, что он является перестановкой входа и разбит на группы &lt; p, = p, &gt; p.
 */
class PartitionTest {

    /**
     * Запускает Partition с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            Partition.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    /**
     * Запускает программу на массиве a и опорном p и проверяет, что вывод —
     * перестановка a, в которой идут сначала элементы &lt; p, затем = p, затем &gt; p.
     */
    private void assertPartitioned(int[] a, int p) {
        StringBuilder input = new StringBuilder();
        input.append(a.length).append('\n');
        for (int x : a) {
            input.append(x).append(' ');
        }
        input.append('\n').append(p).append('\n');

        int[] b = Arrays.stream(run(input.toString()).split(" ")).mapToInt(Integer::parseInt).toArray();

        int[] sortedA = a.clone();
        int[] sortedB = b.clone();
        Arrays.sort(sortedA);
        Arrays.sort(sortedB);
        assertArrayEquals(sortedA, sortedB, "вывод должен быть перестановкой входа");

        // Номер группы каждого элемента (0: < p, 1: = p, 2: > p) не должен убывать
        int prev = 0;
        for (int x : b) {
            int g = Integer.compare(x, p) + 1;
            assertTrue(g >= prev, "группы перемешаны: " + Arrays.toString(b));
            prev = g;
        }
    }

    @Test
    void shouldPartitionMixedArray() {
        assertPartitioned(new int[] {5, 1, 4, 3, 9, 3, 2, 8}, 3);
    }

    @Test
    void shouldPartitionWhenPivotIsAbsent() {
        assertPartitioned(new int[] {7, 1, 8, 2, 9}, 5);
    }

    @Test
    void shouldHandleAllElementsLessThanPivot() {
        assertPartitioned(new int[] {3, 1, 2}, 10);
    }

    @Test
    void shouldHandleAllElementsGreaterThanPivot() {
        assertPartitioned(new int[] {3, 1, 2}, 0);
    }

    @Test
    void shouldHandleAllElementsEqualToPivot() {
        assertEquals("4 4 4", run("3\n4 4 4\n4\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertPartitioned(new int[] {-1, 5, -7, 0, 0, 3, -2}, 0);
    }

    @Test
    void shouldHandleMinimalLength() {
        assertEquals("1 2", run("2\n2 1\n2\n"));
    }

    @Test
    void shouldHandleExtremeValues() {
        assertPartitioned(new int[] {2147483647, -2147483648, 0, 2147483647}, 0);
    }

    @Test
    void shouldReportErrorWhenPivotIsNotNumber() {
        assertTrue(run("3\n1 2 3\np\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooSmall() {
        assertTrue(run("1\n5\n5\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooLarge() {
        assertTrue(run("100000\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("3\n1 x 2\n").contains("должно быть целое число"));
    }
}
