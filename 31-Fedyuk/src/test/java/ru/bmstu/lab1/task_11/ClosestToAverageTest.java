package ru.bmstu.lab1.task_11;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 11: каждый случай проверяет запуск программы и её полный вывод. */
class ClosestToAverageTest {
    @Test
    void shouldChooseClosestValue() {
        assertResult("3 1 4 8", "4\n", ClosestToAverage::main);
    }

    @Test
    void shouldKeepFirstValueOnTie() {
        assertResult("2 0 1", "0\n", ClosestToAverage::main);
    }

    @Test
    void shouldPreserveInputOrderOnTie() {
        assertResult("2 1 0", "1\n", ClosestToAverage::main);
    }

    @Test
    void shouldHandleNegativeAverage() {
        assertResult("3 -9 -5 -1", "-5\n", ClosestToAverage::main);
    }

    @Test
    void shouldHandleEqualValues() {
        assertResult("3 7 7 7", "7\n", ClosestToAverage::main);
    }

    @Test
    void shouldHandleAscendingIntLimits() {
        assertResult("2 -2147483648 2147483647", "-2147483648\n", ClosestToAverage::main);
    }

    @Test
    void shouldHandleDescendingIntLimits() {
        assertResult("2 2147483647 -2147483648", "2147483647\n", ClosestToAverage::main);
    }

    @Test
    void shouldHandleFractionalAverage() {
        assertResult("3 0 100 101", "100\n", ClosestToAverage::main);
    }
}
