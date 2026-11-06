package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** Сквозные проверки стандартного ввода-вывода всех программ лабораторной. */
class ConsoleTest {
    static Stream<Arguments> examples() {
        return Stream.of(
                Arguments.of(1, "5\n3 -8 0 12 3\n", "-8 12\n"),
                Arguments.of(1, "2\n2147483647 -2147483648\n", "-2147483648 2147483647\n"),
                Arguments.of(1, "3\n4 4 4", "4 4\n"),
                Arguments.of(2, "5 4 7 4 9 4 4", "0\n"),
                Arguments.of(2, "2 4 7 7", "1\n"),
                Arguments.of(2, "3 1 2 3 8", "-1\n"),
                Arguments.of(3, "5 4 7 4 9 4 4", "3\n"),
                Arguments.of(3, "2 4 7 8", "0\n"),
                Arguments.of(3, "3 2 2 2 2", "3\n"),
                Arguments.of(4, "5 4 7 4 9 4 4", "0 2 4\n"),
                Arguments.of(4, "2 4 7 8", "\n"),
                Arguments.of(4, "2 1 1 1", "0 1\n"),
                Arguments.of(5, "5 1 100 3 100 5", "9\n"),
                Arguments.of(5, "3 2147483647 0 2147483647", "4294967294\n"),
                Arguments.of(5, "2 -2147483648 0", "-2147483648\n"),
                Arguments.of(6, "4 1 3 5 7", "2\n"),
                Arguments.of(6, "3 -2 -2 -2", "0\n"),
                Arguments.of(6, "2 -4 -2", "1\n"),
                Arguments.of(6, "2 2147483647 2147483647", "0\n"),
                Arguments.of(7, "4 3 9 5 7", "7\n"),
                Arguments.of(7, "2 -2147483648 2147483647", "-2147483648\n"),
                Arguments.of(7, "5 1 2 3 4 5", "4\n"),
                Arguments.of(7, "4 -1 -2 -3 -4", "-2\n"),
                Arguments.of(8, "7 0 -2 3 -4 0 5 -6", "3 5\n-2 -4 -6\n"),
                Arguments.of(8, "2 0 0", "\n\n"),
                Arguments.of(8, "2 1 2", "1 2\n\n"),
                Arguments.of(8, "2 -1 -2", "\n-1 -2\n"),
                Arguments.of(9, "4 -2 -2 0 8", "YES\n"),
                Arguments.of(9, "2 2 1", "NO\n"),
                Arguments.of(9, "3 1 3 2", "NO\n"),
                Arguments.of(10, "5 6 1 9 4 12 3", "4\n"),
                Arguments.of(10, "3 3 1 2 -3", "0\n"),
                Arguments.of(10, "2 1 5 2", "-1\n"),
                Arguments.of(10, "3 7 0 8 -3", "1\n"),
                Arguments.of(11, "4 1 3 5 7", "3\n"),
                Arguments.of(11, "2 7 1", "7\n"),
                Arguments.of(11, "3 -5 -1 0", "-1\n"),
                Arguments.of(11, "2 -2147483648 2147483647", "-2147483648\n"),
                Arguments.of(11, "3 5 5 5", "5\n"),
                Arguments.of(12, "5 1 2 3 4 5 6", "0 4\n"),
                Arguments.of(12, "4 3 3 3 3 6", "0 1\n"),
                Arguments.of(12, "3 1 2 4 9", "-1 -1\n"),
                Arguments.of(12, "2 3 4 6", "-1 -1\n"),
                Arguments.of(12, "4 0 5 5 0 5", "0 1\n"),
                Arguments.of(12, "2 2147483647 2147483647 4294967294", "0 1\n"),
                Arguments.of(12, "2 -2147483648 -2147483648 -4294967296", "0 1\n"),
                Arguments.of(12, "2 -2147483648 -2147483648 0", "-1 -1\n"),
                Arguments.of(25, "5 1 -2 0 3 4", "6\n"),
                Arguments.of(25, "2 2147483647 2147483647", "4294967294\n"),
                Arguments.of(25, "2 -2147483648 -2147483648", "-4294967296\n"),
                Arguments.of(26, "2 -2 3", "1000000001\n"),
                Arguments.of(26, "2 -2 -3", "6\n"),
                Arguments.of(26, "3 5 0 -7", "0\n"),
                Arguments.of(26, "2 1000000007 4", "0\n"),
                Arguments.of(26, "2 -2147483648 2147483647", "1897632\n"),
                Arguments.of(27, "3 1 -2 3", "14\n"),
                Arguments.of(27, "2 0 0", "0\n"),
                Arguments.of(27, "2 -2147483648 -2147483648", "9223372036854775808\n"),
                Arguments.of(27, "3 2147483647 2147483647 2147483647", "13835058042397261827\n")
        );
    }

    @ParameterizedTest(name = "task_{0}: input={1}")
    @MethodSource("examples")
    void shouldPrintExpectedAnswer(int task, String input, String expected) throws Exception {
        assertEquals(expected, run(task, input));
    }

    @Test
    void shouldAcceptBlankLinesAndTabs() throws Exception {
        assertEquals("-5 8\n", run(1, "\n\t2\n\n-5\t8\n"));
    }

    @Test
    void shouldReportMissingInput() {
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class, () -> run(1, "3 1 2"));
        assertEquals(IOException.class, exception.getCause().getClass());
    }

    private String run(int task, String input) throws Exception {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(capture);
            Class<?> program = Class.forName("ru.bmstu.lab1.task_" + task + ".Main");
            program.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
            return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}
