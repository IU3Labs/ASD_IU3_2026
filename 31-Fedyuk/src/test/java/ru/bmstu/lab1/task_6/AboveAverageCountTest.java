package ru.bmstu.lab1.task_6;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 6: каждый случай проверяет запуск программы и её полный вывод. */
class AboveAverageCountTest {
    @Test
    void shouldExcludeValueEqualToAverage() {
        assertResult("5 1 2 3 4 5", "2\n", AboveAverageCount::main);
    }

    @Test
    void shouldHandleAllEqualValues() {
        assertResult("2 7 7", "0\n", AboveAverageCount::main);
    }

    @Test
    void shouldHandleNegativeAverage() {
        assertResult("3 -9 -6 -3", "1\n", AboveAverageCount::main);
    }

    @Test
    void shouldAvoidPositiveSumOverflow() {
        assertResult("3 2147483645 2147483646 2147483647", "1\n", AboveAverageCount::main);
    }

    @Test
    void shouldAvoidNegativeSumOverflow() {
        assertResult("3 -2147483648 -2147483647 -2147483646", "1\n", AboveAverageCount::main);
    }

    @Test
    void shouldHandleZeroAverage() {
        assertResult("3 -3 0 3", "1\n", AboveAverageCount::main);
    }
}
