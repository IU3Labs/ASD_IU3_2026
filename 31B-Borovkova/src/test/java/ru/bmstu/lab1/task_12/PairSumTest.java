package ru.bmstu.lab1.task_12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 12: программа запускается с подставленным вводом, проверяется её вывод.
 */
class PairSumTest {

    /** Наибольшая длина массива по условию — для проверки скорости. */
    private static final int LARGE_LENGTH = 99_999;
    /** Ограничение по времени для большого теста: O(n^2) в него не укладывается. */
    private static final Duration TIME_LIMIT = Duration.ofSeconds(2);

    /**
     * Запускает PairSum с заданным вводом и возвращает всё, что программа напечатала.
     */
    private String run(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            PairSum.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void shouldFindPairWithSmallestI() {
        assertEquals("0 1", run("5\n1 4 2 3 4\n5\n"));
    }

    @Test
    void shouldChooseSmallestJForSameI() {
        assertEquals("0 2", run("4\n1 3 4 4\n5\n"));
    }

    @Test
    void shouldPreferSmallerIOverSmallerJ() {
        assertEquals("0 3", run("4\n5 2 3 0\n5\n"));
    }

    @Test
    void shouldFindPairOfEqualValues() {
        assertEquals("0 2", run("3\n3 1 3\n6\n"));
    }

    @Test
    void shouldNotPairElementWithItself() {
        assertEquals("-1 -1", run("3\n3 1 2\n6\n"));
    }

    @Test
    void shouldReturnMinusOnesWhenThereIsNoPair() {
        assertEquals("-1 -1", run("3\n1 2 3\n10\n"));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals("2 3", run("4\n-3 7 1 -1\n0\n"));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        assertEquals("0 1", run("2\n2147483647 2147483647\n4294967294\n"));
    }

    @Test
    void shouldWorkFastOnLargeArrayWithoutPair() {
        StringBuilder input = new StringBuilder();
        input.append(LARGE_LENGTH).append('\n');
        for (int i = 0; i < LARGE_LENGTH; i++) {
            input.append("0 ");
        }
        input.append("\n1\n");
        String text = input.toString();
        assertTimeout(TIME_LIMIT, () -> assertEquals("-1 -1", run(text)));
    }

    @Test
    void shouldReportErrorWhenLengthIsNotNumber() {
        assertTrue(run("abc\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenLengthIsNegative() {
        assertTrue(run("-3\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenLengthIsTooLarge() {
        assertTrue(run("100000\n").contains("некорректная длина"));
    }

    @Test
    void shouldReportErrorWhenElementIsNotNumber() {
        assertTrue(run("3\n1 x\n").contains("должно быть целое число"));
    }

    @Test
    void shouldReportErrorWhenSumIsNotNumber() {
        assertTrue(run("2\n1 2\ny\n").contains("должно быть целое число"));
    }
}
