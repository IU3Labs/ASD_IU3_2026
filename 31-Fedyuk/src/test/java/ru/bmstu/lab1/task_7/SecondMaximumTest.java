package ru.bmstu.lab1.task_7;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 7: каждый случай проверяет запуск программы и её полный вывод. */
class SecondMaximumTest {
    @Test
    void shouldFindSecondMaximumInMiddle() {
        assertResult("5 3 9 1 7 5", "7\n", SecondMaximum::main);
    }

    @Test
    void shouldHandleIncreasingPair() {
        assertResult("2 4 9", "4\n", SecondMaximum::main);
    }

    @Test
    void shouldHandleDecreasingPair() {
        assertResult("2 9 4", "4\n", SecondMaximum::main);
    }

    @Test
    void shouldHandleNegativeValues() {
        assertResult("4 -8 -3 -5 -10", "-5\n", SecondMaximum::main);
    }

    @Test
    void shouldHandleIntLimits() {
        assertResult("2 -2147483648 2147483647", "-2147483648\n", SecondMaximum::main);
    }

    @Test
    void shouldHandleIncreasingArray() {
        assertResult("5 1 2 3 4 5", "4\n", SecondMaximum::main);
    }

    @Test
    void shouldHandleDecreasingArray() {
        assertResult("5 5 4 3 2 1", "4\n", SecondMaximum::main);
    }
}
