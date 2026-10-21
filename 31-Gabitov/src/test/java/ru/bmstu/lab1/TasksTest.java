package ru.bmstu.lab1;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.task_2.Task2;
import ru.bmstu.lab1.task_3.Task3;
import ru.bmstu.lab1.task_9.Task9;
import ru.bmstu.lab1.task_37.Task37;
import ru.bmstu.lab1.task_39.Task39;
import ru.bmstu.lab1.task_40.Task40;
import ru.bmstu.lab1.task_41.Task41;
import ru.bmstu.lab1.task_43.Task43;
import ru.bmstu.lab1.task_48.Task48;

/** Проверки решений задач лабораторной работы №1. */
class TasksTest {
    // По условию длина массива меньше 100000.
    private static final int LARGE_SIZE = 99999;
    private InputStream oldInput;
    private PrintStream oldOutput;
    private ByteArrayOutputStream output;

    @BeforeEach
    void prepareOutput() {
        oldInput = System.in;
        oldOutput = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreInputAndOutput() {
        System.setIn(oldInput);
        System.setOut(oldOutput);
    }

    // Вместо клавиатуры программа получает текст из теста.
    private void setInput(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        System.setIn(new ByteArrayInputStream(bytes));
    }

    private String getOutput() {
        String text = output.toString(StandardCharsets.UTF_8);
        return text.replace("\r\n", "\n").strip();
    }

    private int[] createLargeArray() {
        int[] array = new int[LARGE_SIZE];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        return array;
    }

    // После удаления важны новая длина и элементы до этой длины.
    private void assertPrefix(int[] array, int size, int[] expected) {
        assertEquals(expected.length, size);
        for (int i = 0; i < size; i++) {
            assertEquals(expected[i], array[i]);
        }
    }

    @Test
    void shouldFindFirstMatch() {
        int[] array = {7, 2, 7, 2, 9};
        assertEquals(1, Task2.firstIndex(array, 2));
    }

    @Test
    void shouldReturnMinusOneWhenMissing() {
        int[] array = {-1, 0, 5};
        assertEquals(-1, Task2.firstIndex(array, 8));
    }

    @Test
    void shouldFindExtremeValue() {
        int[] array = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        assertEquals(0, Task2.firstIndex(array, Integer.MIN_VALUE));
    }

    @Test
    void shouldCountSeveralMatches() {
        int[] array = {Integer.MIN_VALUE, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertEquals(3, Task3.count(array, Integer.MIN_VALUE));
    }

    @Test
    void shouldCountZeroWhenMissing() {
        int[] array = {-1, 0, 5};
        assertEquals(0, Task3.count(array, 8));
    }

    @Test
    void shouldCountAllElements() {
        int[] array = {0, 0};
        assertEquals(2, Task3.count(array, 0));
    }

    @Test
    void shouldAllowEqualNeighbors() {
        int[] array = {-2, -2, 0, Integer.MAX_VALUE};
        assertTrue(Task9.isSorted(array));
    }

    @Test
    void shouldRejectDescendingArray() {
        int[] array = {3, 2, 1};
        assertFalse(Task9.isSorted(array));
    }

    @Test
    void shouldFindDecreaseAtEnd() {
        int[] array = {-2, 0, 5, 4};
        assertFalse(Task9.isSorted(array));
    }

    @Test
    void shouldReverseOddArray() {
        setInput("5 -1 0 2 -3 4");
        Task37.main(new String[0]);
        String expected = "4 -3 2 0 -1";
        assertEquals(expected, getOutput());
    }

    @Test
    void shouldReverseEvenArray() {
        int[] array = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        Task37.reverse(array);
        int[] expected = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertArrayEquals(expected, array);
    }

    @Test
    void shouldNormalizeLargeShift() {
        setInput("3 10 20 30 2147483647");
        Task39.main(new String[0]);
        String expected = "30 10 20";
        assertEquals(expected, getOutput());
    }

    @Test
    void shouldKeepZeroShift() {
        int[] array = {-1, 8};
        int[] actual = Task39.shift(array, 0);
        assertArrayEquals(array, actual);
    }

    @Test
    void shouldShiftLeftForNegativeK() {
        int[] array = {10, 20, 30};
        int[] actual = Task39.shift(array, -1);
        int[] expected = {20, 30, 10};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldRemoveRepeatedValues() {
        setInput("6 -2 -2 0 3 3 3");
        Task40.main(new String[0]);
        String expected = "-2 0 3";
        assertEquals(expected, getOutput());
    }

    @Test
    void shouldKeepDistinctValues() {
        int[] array = {-1, 0, 2};
        int size = Task40.removeDuplicates(array);
        int[] expected = {-1, 0, 2};
        assertPrefix(array, size, expected);
    }

    @Test
    void shouldLeaveOneEqualValue() {
        int[] array = {7, 7, 7, 7};
        int size = Task40.removeDuplicates(array);
        int[] expected = {7};
        assertPrefix(array, size, expected);
    }

    @Test
    void shouldRemoveMultipleMatches() {
        setInput("5 2 1 2 3 2 2");
        Task41.main(new String[0]);
        String expected = "1 3";
        assertEquals(expected, getOutput());
    }

    @Test
    void shouldRemoveEveryElement() {
        int[] array = {7, 7, 7};
        int size = Task41.remove(array, 7);
        assertEquals(0, size);
    }

    @Test
    void shouldKeepArrayWithoutMatches() {
        int[] array = {-1, 0, 2};
        int size = Task41.remove(array, 8);
        int[] expected = {-1, 0, 2};
        assertPrefix(array, size, expected);
    }

    @Test
    void shouldMoveMixedZeros() {
        setInput("6 0 1 0 -2 3 0");
        Task43.main(new String[0]);
        String expected = "1 -2 3 0 0 0";
        assertEquals(expected, getOutput());
    }

    @Test
    void shouldKeepAllZeros() {
        int[] array = {0, 0, 0};
        Task43.moveZeros(array);
        int[] expected = {0, 0, 0};
        assertArrayEquals(expected, array);
    }

    @Test
    void shouldKeepNonZeroArray() {
        int[] array = {-1, 2, 3};
        Task43.moveZeros(array);
        int[] expected = {-1, 2, 3};
        assertArrayEquals(expected, array);
    }

    @Test
    void shouldTransposeThreeByThree() {
        setInput("3 1 2 3 4 5 6 7 8 9");
        Task48.main(new String[0]);
        String expected = "1 4 7\n2 5 8\n3 6 9";
        assertEquals(expected, getOutput());
    }

    @Test
    void shouldFindLastValueInLargeArray() {
        int[] array = createLargeArray();
        assertEquals(LARGE_SIZE - 1, Task2.firstIndex(array, LARGE_SIZE - 1));
    }

    @Test
    void shouldCountLargeArray() {
        int[] array = new int[LARGE_SIZE];
        assertEquals(LARGE_SIZE, Task3.count(array, 0));
    }

    @Test
    void shouldCheckLargeArrayOrder() {
        int[] array = createLargeArray();
        assertTrue(Task9.isSorted(array));
        array[LARGE_SIZE - 1] = -1;
        assertFalse(Task9.isSorted(array));
    }

    @Test
    void shouldReverseLargeArrayInPlace() {
        int[] array = createLargeArray();
        Task37.reverse(array);
        for (int i = 0; i < array.length; i++) {
            assertEquals(LARGE_SIZE - 1 - i, array[i]);
        }
    }

    @Test
    void shouldShiftLargeArray() {
        int[] array = createLargeArray();
        int[] actual = Task39.shift(array, LARGE_SIZE + 1);
        assertEquals(LARGE_SIZE, actual.length);
        assertEquals(LARGE_SIZE - 1, actual[0]);
        for (int i = 1; i < actual.length; i++) {
            assertEquals(i - 1, actual[i]);
        }
    }

    @Test
    void shouldRemoveDuplicatesFromLargeArray() {
        int[] array = createLargeArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = i / 2;
        }
        int size = Task40.removeDuplicates(array);
        assertEquals(50000, size);
        for (int i = 0; i < size; i++) {
            assertEquals(i, array[i]);
        }
    }

    @Test
    void shouldRemoveFromMiddleOfLargeArray() {
        int[] array = createLargeArray();
        int x = LARGE_SIZE / 2;
        int size = Task41.remove(array, x);
        assertEquals(LARGE_SIZE - 1, size);
        for (int i = 0; i < size; i++) {
            int expected = i;
            if (i >= x) {
                expected++;
            }
            assertEquals(expected, array[i]);
        }
    }

    @Test
    void shouldMoveZerosInLargeArray() {
        int[] array = createLargeArray();
        Task43.moveZeros(array);
        for (int i = 0; i < array.length - 1; i++) {
            assertEquals(i + 1, array[i]);
        }
        assertEquals(0, array[LARGE_SIZE - 1]);
    }

    @Test
    void shouldTransposeInOriginalRows() {
        int[][] matrix = {{1, 2}, {3, 4}};
        int[] firstRow = matrix[0];
        Task48.transpose(matrix);
        assertArrayEquals(new int[] {1, 3}, firstRow);
        assertArrayEquals(new int[] {2, 4}, matrix[1]);
        Task48.transpose(matrix);
        assertArrayEquals(new int[] {1, 2}, matrix[0]);
    }
}