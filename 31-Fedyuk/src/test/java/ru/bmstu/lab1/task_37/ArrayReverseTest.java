package ru.bmstu.lab1.task_37;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 37: каждый случай проверяет запуск программы и её полный вывод. */
class ArrayReverseTest {
    @Test
    void shouldReverseOddLength() {
        assertResult("5 1 2 3 4 5", "5 4 3 2 1\n", ArrayReverse::main);
    }

    @Test
    void shouldReverseEvenLength() {
        assertResult("4 -1 0 2 -3", "-3 2 0 -1\n", ArrayReverse::main);
    }

    @Test
    void shouldPreserveEqualValues() {
        assertResult("2 7 7", "7 7\n", ArrayReverse::main);
    }

    @Test
    void shouldExchangeIntLimits() {
        assertResult("2 -2147483648 2147483647", "2147483647 -2147483648\n", ArrayReverse::main);
    }
}
