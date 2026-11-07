package ru.bmstu.lab1.task_20;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверяет оба варианта подсчёта, границы int и консольный ввод-вывод. */
class MainTest {

    @Test
    void shouldSortUnorderedValues() {
        assertEquals(
                "1 2 2 3 4 4\n",
                run("6\n4 2 4 1 2 3\n")
        );
    }

    @Test
    void shouldSortNegativeValues() {
        assertEquals(
                "-5 -5 -2 -1\n",
                run("4\n-2 -5 -1 -5\n")
        );
    }

    @Test
    void shouldSortMixedSigns() {
        assertEquals(
                "-2 -1 0 0 3\n",
                run("5\n-2 0 3 -1 0\n")
        );
    }

    @Test
    void shouldKeepAllRepeatedValues() {
        assertEquals(
                "7 7 7 7\n",
                run("4\n7 7 7 7\n")
        );
    }

    @Test
    void shouldKeepSortedOrder() {
        assertEquals(
                "-3 -1 0 2 4\n",
                run("5\n-3 -1 0 2 4\n")
        );
    }

    @Test
    void shouldSortReverseOrder() {
        assertEquals(
                "1 2 3 4 5\n",
                run("5\n5 4 3 2 1\n")
        );
    }

    @Test
    void shouldHandleOnlyZeros() {
        assertEquals(
                "0 0 0\n",
                run("3\n0 0 0\n")
        );
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals(
                "2 9\n",
                run("2\n9 2\n")
        );
    }

    @Test
    void shouldHandleCompactRangeNearMinimum() {
        assertEquals(
                "-2147483648 -2147483647 -2147483646\n",
                run("3\n-2147483648 -2147483646 -2147483647\n")
        );
    }

    @Test
    void shouldHandleCompactRangeNearMaximum() {
        assertEquals(
                "2147483645 2147483646 2147483647\n",
                run("3\n2147483647 2147483645 2147483646\n")
        );
    }

    @Test
    void shouldHandleFullIntegerRange() {
        assertEquals(
                "-2147483648 -1 0 1 2147483647\n",
                run("5\n2147483647 -1 0 -2147483648 1\n")
        );
    }

    @Test
    void shouldHandleWideRangeWithDuplicates() {
        assertEquals(
                "-2147483648 -2147483648 0 2147483647 2147483647\n",
                run("5\n2147483647 -2147483648 2147483647 -2147483648 0\n")
        );
    }

    @Test
    void shouldHandleWideAlreadySortedArray() {
        assertEquals(
                "-2147483648 -10000000 0 10000000 2147483647\n",
                run("5\n-2147483648 -10000000 0 10000000 2147483647\n")
        );
    }

    @Test
    void shouldHandleLastDirectRange() {
        assertEquals(
                "-500000 -500000 0 499999\n",
                run("4\n-500000 499999 0 -500000\n")
        );
    }

    @Test
    void shouldHandleFirstByteRange() {
        assertEquals(
                "-500000 -500000 0 500000\n",
                run("4\n-500000 500000 0 -500000\n")
        );
    }

    @Test
    void shouldHandleEmptyArrayInSortingMethod() {
        // Дополнительная проверка метода: по условию консольный n больше 1.
        assertArrayEquals(new int[0], Main.countingSort(new int[0]));
    }

    @Test
    void shouldPreserveInputForDirectCounting() {
        int[] original = {3, -1, 2, 3};
        int[] saved = original.clone();
        int[] sorted = Main.countingSort(original);

        assertArrayEquals(new int[]{-1, 2, 3, 3}, sorted);
        assertArrayEquals(saved, original);
        assertNotSame(original, sorted);
    }

    @Test
    void shouldPreserveInputForByteCounting() {
        int[] original = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        int[] saved = original.clone();
        int[] sorted = Main.countingSort(original);

        assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, sorted);
        assertArrayEquals(saved, original);
        assertNotSame(original, sorted);
    }

    @Test
    void shouldMatchIndependentSortForRandomArrays() {
        // Фиксированный seed делает проверку воспроизводимой.
        Random random = new Random(20261022L);
        for (int attempt = 0; attempt < 100; attempt++) {
            int[] numbers = new int[2 + random.nextInt(200)];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = attempt % 2 == 0 ? random.nextInt(101) - 50 : random.nextInt();
            }
            int[] expected = numbers.clone();
            // Готовая сортировка используется только как независимый эталон в тесте.
            Arrays.sort(expected);
            assertArrayEquals(expected, Main.countingSort(numbers), "attempt=" + attempt);
        }
    }

    @Test
    void shouldHandleMaximumAllowedSize() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i < size; i++) {
            input.append(size - i).append(' ');
            if (i > 0) {
                expected.append(' ');
            }
            expected.append(i + 1);
        }
        expected.append('\n');
        assertEquals(expected.toString(), run(input.toString()));
    }

    @Test
    void shouldHandleMaximumSizeAndWideRange() {
        int[] numbers = new int[99_999];
        Random random = new Random(20L);
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt();
        }
        numbers[0] = Integer.MIN_VALUE;
        numbers[1] = Integer.MAX_VALUE;
        int[] expected = numbers.clone();
        Arrays.sort(expected);
        assertArrayEquals(expected, Main.countingSort(numbers));
    }

    @Test
    void shouldRunMainWithStandardStreams() {
        InputStream savedInput = System.in;
        PrintStream savedOutput = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream("3\n3 1 2\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            Main.main(new String[0]);
        } finally {
            // Возвращаем потоки даже при ошибке, чтобы не повлиять на другие тесты.
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }

        assertEquals("1 2 3\n", buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
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
