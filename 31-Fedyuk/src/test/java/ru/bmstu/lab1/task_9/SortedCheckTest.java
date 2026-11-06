package ru.bmstu.lab1.task_9;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 9: каждый случай проверяет запуск программы и её полный вывод. */
class SortedCheckTest {
    @Test
    void shouldAcceptRepeatedNeighbours() {
        assertResult("5 -2 -2 0 3 3", "YES\n", SortedCheck::main);
    }

    @Test
    void shouldRejectDecreasingPair() {
        assertResult("2 3 2", "NO\n", SortedCheck::main);
    }

    @Test
    void shouldDetectViolationAtEnd() {
        assertResult("3 1 3 2", "NO\n", SortedCheck::main);
    }

    @Test
    void shouldAcceptEqualPair() {
        assertResult("2 5 5", "YES\n", SortedCheck::main);
    }

    @Test
    void shouldAcceptOrderedIntLimits() {
        assertResult("2 -2147483648 2147483647", "YES\n", SortedCheck::main);
    }

    @Test
    void shouldDetectViolationAtBeginning() {
        assertResult("3 3 1 2", "NO\n", SortedCheck::main);
    }
}
