package ru.bmstu.lab1.task_36;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 36: каждый случай проверяет запуск программы и её полный вывод. */
class RunningSumTest {
    @Test
    void shouldBuildPositiveRunningSums() {
        assertResult("5 1 2 3 4 5", "1 3 6 10 15\n", RunningSum::main);
    }

    @Test
    void shouldHandleMixedSigns() {
        assertResult("3 -2 5 -7", "-2 3 -4\n", RunningSum::main);
    }

    @Test
    void shouldHandleZeros() {
        assertResult("2 0 0", "0 0\n", RunningSum::main);
    }

    @Test
    void shouldAvoidPositiveOverflow() {
        assertResult("2 2147483647 2147483647", "2147483647 4294967294\n", RunningSum::main);
    }

    @Test
    void shouldAvoidNegativeOverflow() {
        assertResult("2 -2147483648 -2147483648", "-2147483648 -4294967296\n", RunningSum::main);
    }
}
