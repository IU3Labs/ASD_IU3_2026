package ru.bmstu.lab1.task_25;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 25: каждый случай проверяет запуск программы и её полный вывод. */
class ArraySumTest {
    @Test
    void shouldSumMixedValues() {
        assertResult("5 1 -2 3 0 5", "7\n", ArraySum::main);
    }

    @Test
    void shouldSumZeros() {
        assertResult("2 0 0", "0\n", ArraySum::main);
    }

    @Test
    void shouldSumNegativeValues() {
        assertResult("3 -1 -2 -3", "-6\n", ArraySum::main);
    }

    @Test
    void shouldAvoidPositiveOverflow() {
        assertResult("2 2147483647 2147483647", "4294967294\n", ArraySum::main);
    }

    @Test
    void shouldAvoidNegativeOverflow() {
        assertResult("2 -2147483648 -2147483648", "-4294967296\n", ArraySum::main);
    }
}
