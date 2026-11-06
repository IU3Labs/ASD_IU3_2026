package ru.bmstu.lab1.task_1;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 1: каждый случай проверяет запуск программы и её полный вывод. */
class MinMaxTest {
    @Test
    void shouldFindMinimumAndMaximum() {
        assertResult("5 3 -1 9 0 4", "-1 9\n", MinMax::main);
    }

    @Test
    void shouldHandleEqualElements() {
        assertResult("2 7 7", "7 7\n", MinMax::main);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertResult("3 -3 -9 -5", "-9 -3\n", MinMax::main);
    }

    @Test
    void shouldHandleReversedIntLimits() {
        assertResult("2 2147483647 -2147483648", "-2147483648 2147483647\n", MinMax::main);
    }

    @Test
    void shouldHandleOrderedIntLimits() {
        assertResult("2 -2147483648 2147483647", "-2147483648 2147483647\n", MinMax::main);
    }
}
