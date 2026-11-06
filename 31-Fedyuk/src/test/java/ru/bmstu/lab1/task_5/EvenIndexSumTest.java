package ru.bmstu.lab1.task_5;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 5: каждый случай проверяет запуск программы и её полный вывод. */
class EvenIndexSumTest {
    @Test
    void shouldSumEvenIndicesRatherThanEvenValues() {
        assertResult("5 1 100 2 100 3", "6\n", EvenIndexSum::main);
    }

    @Test
    void shouldIncludeIndexZeroInPair() {
        assertResult("2 -7 4", "-7\n", EvenIndexSum::main);
    }

    @Test
    void shouldHandleEvenLength() {
        assertResult("4 2 9 3 9", "5\n", EvenIndexSum::main);
    }

    @Test
    void shouldAvoidPositiveOverflow() {
        assertResult("3 2147483647 0 2147483647", "4294967294\n", EvenIndexSum::main);
    }

    @Test
    void shouldAvoidNegativeOverflow() {
        assertResult("3 -2147483648 0 -2147483648", "-4294967296\n", EvenIndexSum::main);
    }

    @Test
    void shouldSumZeros() {
        assertResult("2 0 0", "0\n", EvenIndexSum::main);
    }
}
