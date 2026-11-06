package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.bmstu.lab1.task_3.OccurrenceCount;

/** Проверки ограничений и общего ввода-вывода. */
class ConsoleIoTest {
    private static final int MAX_VALID_SIZE = 99_999;

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 100_000})
    void shouldRejectArraySizeOutsideTaskConstraints(int size) {
        try (Scanner scanner = new Scanner(Integer.toString(size))) {
            assertThrows(IllegalArgumentException.class, () -> ConsoleIo.readArray(scanner));
        }
    }

    @Test
    void shouldReadAcrossSpacesTabsAndNewlines() {
        try (Scanner scanner = new Scanner("3\n-1\t0   2\n")) {
            assertArrayEquals(new int[] {-1, 0, 2}, ConsoleIo.readArray(scanner));
        }
    }

    @Test
    void shouldFormatEmptyArrayWithoutExtraSpaces() {
        assertEquals("", ConsoleIo.format(new int[0]));
    }

    @Test
    void shouldReadLargestPermittedArrayThroughConsole() {
        String input = MAX_VALID_SIZE + "\n" + "7 ".repeat(MAX_VALID_SIZE) + "\n7\n";
        assertResult(input, MAX_VALID_SIZE + "\n", "Введите x", OccurrenceCount::main);
    }
}
