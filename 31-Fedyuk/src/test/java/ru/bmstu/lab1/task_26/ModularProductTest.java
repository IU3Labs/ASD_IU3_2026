package ru.bmstu.lab1.task_26;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 26: каждый случай проверяет запуск программы и её полный вывод. */
class ModularProductTest {
    @Test
    void shouldMultiplyPositiveValues() {
        assertResult("4 2 3 4 5", "120\n", ModularProduct::main);
    }

    @Test
    void shouldHandleZeroFactor() {
        assertResult("3 2 0 -4", "0\n", ModularProduct::main);
    }

    @Test
    void shouldNormalizeNegativeProduct() {
        assertResult("2 -2 3", "1000000001\n", ModularProduct::main);
    }

    @Test
    void shouldMultiplyTwoNegatives() {
        assertResult("2 -2 -3", "6\n", ModularProduct::main);
    }

    @Test
    void shouldHandleFactorEqualToModulus() {
        assertResult("2 1000000007 3", "0\n", ModularProduct::main);
    }

    @Test
    void shouldHandleNegativeModulusFactor() {
        assertResult("2 -1000000007 -1", "0\n", ModularProduct::main);
    }

    @Test
    void shouldHandleIntLimits() {
        assertResult("2 2147483647 -2147483648", "1897632\n", ModularProduct::main);
    }
}
