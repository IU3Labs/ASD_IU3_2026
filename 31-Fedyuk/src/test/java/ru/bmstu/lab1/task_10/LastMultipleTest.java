package ru.bmstu.lab1.task_10;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 10: каждый случай проверяет запуск программы и её полный вывод. */
class LastMultipleTest {
    @Test
    void shouldFindLastMultiple() {
        assertResult("5 6 7 9 4 12 3", "4\n", "Введите k", LastMultiple::main);
    }

    @Test
    void shouldFindOnlyFirstMultiple() {
        assertResult("3 6 7 5 3", "0\n", "Введите k", LastMultiple::main);
    }

    @Test
    void shouldReturnMinusOneWhenAbsent() {
        assertResult("2 1 5 2", "-1\n", "Введите k", LastMultiple::main);
    }

    @Test
    void shouldHandleNegativeDivisor() {
        assertResult("4 -6 5 0 7 -3", "2\n", "Введите k", LastMultiple::main);
    }

    @Test
    void shouldTreatZeroAsMultiple() {
        assertResult("2 0 1 4", "0\n", "Введите k", LastMultiple::main);
    }

    @Test
    void shouldHandleMinimumIntAndMinusOne() {
        assertResult("2 1 -2147483648 -1", "1\n", "Введите k", LastMultiple::main);
    }

    @Test
    void shouldHandleMinimumIntDivisor() {
        assertResult("2 2 -2147483648 -2147483648", "1\n", "Введите k", LastMultiple::main);
    }

    @Test
    void shouldReportMissingParameter() {
        assertOutput(
                "2 1 2 ",
                "Введите длину массива\nВведите массив\nВведите k\nОшибка: должно быть целое число!\n",
                LastMultiple::main);
    }

    @Test
    void shouldReportNonNumericParameter() {
        assertOutput(
                "2 1 2 x",
                "Введите длину массива\nВведите массив\nВведите k\nОшибка: должно быть целое число!\n",
                LastMultiple::main);
    }

    @Test
    void shouldReportZeroDivisor() {
        assertOutput(
                "2 1 2 0",
                "Введите длину массива\nВведите массив\nВведите k\nОшибка: Делитель не должен быть равен нулю\n",
                LastMultiple::main);
    }
}
