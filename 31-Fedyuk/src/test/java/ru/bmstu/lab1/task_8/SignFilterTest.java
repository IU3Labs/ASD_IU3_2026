package ru.bmstu.lab1.task_8;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 8: каждый случай проверяет запуск программы и её полный вывод. */
class SignFilterTest {
    @Test
    void shouldPreserveOrderAndSkipZeros() {
        assertResult("7 0 -2 4 -1 0 3 -7", "4 3\n-2 -1 -7\n", SignFilter::main);
    }

    @Test
    void shouldPrintTwoEmptyGroups() {
        assertResult("2 0 0", "\n\n", SignFilter::main);
    }

    @Test
    void shouldHandleOnlyPositiveValues() {
        assertResult("3 2 1 4", "2 1 4\n\n", SignFilter::main);
    }

    @Test
    void shouldHandleOnlyNegativeValues() {
        assertResult("3 -1 -3 -2", "\n-1 -3 -2\n", SignFilter::main);
    }

    @Test
    void shouldHandleIntLimits() {
        assertResult("2 -2147483648 2147483647", "2147483647\n-2147483648\n", SignFilter::main);
    }
}
