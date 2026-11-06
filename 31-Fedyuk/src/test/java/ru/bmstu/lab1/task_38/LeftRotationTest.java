package ru.bmstu.lab1.task_38;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 38: каждый случай проверяет запуск программы и её полный вывод. */
class LeftRotationTest {
    @Test
    void shouldShiftLeft() {
        assertResult("5 1 2 3 4 5 2", "3 4 5 1 2\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldReduceLargeShift() {
        assertResult("5 1 2 3 4 5 7", "3 4 5 1 2\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldHandleZeroShift() {
        assertResult("3 4 5 6 0", "4 5 6\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldHandleWholeRotations() {
        assertResult("3 4 5 6 6", "4 5 6\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldSwapPair() {
        assertResult("2 1 2 1", "2 1\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldSupportNegativeShift() {
        assertResult("3 1 2 3 -1", "3 1 2\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldHandleMaximumIntShift() {
        assertResult("3 1 2 3 2147483647", "2 3 1\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldHandleMinimumIntShift() {
        assertResult("3 1 2 3 -2147483648", "2 3 1\n", "Введите k", LeftRotation::main);
    }

    @Test
    void shouldReportMissingParameter() {
        assertOutput(
                "2 1 2 ",
                "Введите длину массива\nВведите массив\nВведите k\nОшибка: должно быть целое число!\n",
                LeftRotation::main);
    }

    @Test
    void shouldReportNonNumericParameter() {
        assertOutput(
                "2 1 2 x",
                "Введите длину массива\nВведите массив\nВведите k\nОшибка: должно быть целое число!\n",
                LeftRotation::main);
    }
}
