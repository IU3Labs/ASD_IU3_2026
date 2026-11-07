package ru.bmstu.lab1.task_12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Проверки задачи 12: обычные данные и граничные случаи. */
class MainTest {

    private static String run(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.solve(new Scanner(input), output);
        return buffer.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldFindSinglePair() {
        assertEquals("1 3", run("5\n1 2 9 7 4\n9"));
    }

    @Test
    void shouldReturnMinusOnesWhenNoPair() {
        assertEquals("-1 -1", run("4\n1 2 3 4\n10"));
    }

    @Test
    void shouldFindPairOfEqualValuesAtDifferentIndices() {
        assertEquals("0 1", run("2\n3 3\n6"));
    }

    @Test
    void shouldNotPairElementWithItself() {
        // 6 получается только как 3 + 3, то есть сложением элемента с самим собой
        assertEquals("-1 -1", run("2\n3 5\n6"));
    }

    @Test
    void shouldChooseSmallestFirstIndexAmongSeveralPairs() {
        // пары (0,5) и (1,2): побеждает пара с меньшим i, хотя её j больше
        assertEquals("0 5", run("6\n1 2 3 0 0 4\n5"));
    }

    @Test
    void shouldChooseSmallestSecondIndexWhenFirstIndexIsSame() {
        // для i = 0 подходят j = 2, 3, 4: нужен наименьший
        assertEquals("0 2", run("5\n2 1 3 3 3\n5"));
    }

    @Test
    void shouldFindPairWhenItIsAtTheEnd() {
        assertEquals("3 4", run("5\n1 1 1 5 5\n10"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("0 2", run("4\n-3 4 -7 1\n-10"));
    }

    @Test
    void shouldHandleZeroSum() {
        assertEquals("1 3", run("4\n5 -2 7 2\n0"));
    }

    @Test
    void shouldHandlePairOfZeros() {
        assertEquals("1 3", run("4\n4 0 5 0\n0"));
    }

    @Test
    void shouldNotOverflowWhenSumExceedsIntRange() {
        // 2147483647 + 2147483647 не помещается в int, s задана как long
        assertEquals("0 1", run("2\n2147483647 2147483647\n4294967294"));
        // сумма 2147483647 + 1 = 2147483648, а не -2147483648 (так было бы при переполнении int)
        assertEquals("-1 -1", run("2\n2147483647 1\n-2147483648"));
    }

    @Test
    void shouldAcceptNumbersSplitAcrossLines() {
        assertEquals("1 3", run("5\n1\n2\n9\n7\n4\n9"));
    }

    @Test
    void shouldHandleMaximumInputSizeWithoutPair() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        for (int i = 0; i < size; i++) {
            input.append("1 ");
        }
        // худший случай для перебора пар за O(n^2): все единицы, сумма 3 недостижима
        assertEquals("-1 -1", run(input.append("\n3").toString()));
    }

    @Test
    void shouldHandleMaximumInputSizeWithPairAtTheEnd() {
        int size = 99_999;
        StringBuilder input = new StringBuilder().append(size).append('\n');
        for (int i = 0; i < size - 2; i++) {
            input.append("1 ");
        }
        input.append("5 5\n10");
        assertEquals((size - 2) + " " + (size - 1), run(input.toString()));
    }
}
