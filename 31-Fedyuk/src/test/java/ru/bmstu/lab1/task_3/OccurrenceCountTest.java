package ru.bmstu.lab1.task_3;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 3: каждый случай проверяет запуск программы и её полный вывод. */
class OccurrenceCountTest {
    @Test
    void shouldCountSeparatedMatches() {
        assertResult("5 1 2 1 3 1 1", "3\n", "Введите x", OccurrenceCount::main);
    }

    @Test
    void shouldCountEveryElement() {
        assertResult("2 4 4 4", "2\n", "Введите x", OccurrenceCount::main);
    }

    @Test
    void shouldReturnZeroWhenAbsent() {
        assertResult("2 1 2 7", "0\n", "Введите x", OccurrenceCount::main);
    }

    @Test
    void shouldCountNegativeValues() {
        assertResult("3 -1 0 -1 -1", "2\n", "Введите x", OccurrenceCount::main);
    }

    @Test
    void shouldCountMinimumInt() {
        assertResult(
                "2 2147483647 -2147483648 -2147483648", "1\n", "Введите x", OccurrenceCount::main);
    }

    @Test
    void shouldReportMissingParameter() {
        assertOutput(
                "2 1 2 ",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                OccurrenceCount::main);
    }

    @Test
    void shouldReportNonNumericParameter() {
        assertOutput(
                "2 1 2 x",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                OccurrenceCount::main);
    }
}
