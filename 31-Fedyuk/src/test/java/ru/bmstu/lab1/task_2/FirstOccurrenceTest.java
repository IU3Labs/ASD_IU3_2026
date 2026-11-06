package ru.bmstu.lab1.task_2;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 2: каждый случай проверяет запуск программы и её полный вывод. */
class FirstOccurrenceTest {
    @Test
    void shouldReturnFirstRepeatedIndex() {
        assertResult("5 3 8 3 8 2 3", "0\n", "Введите x", FirstOccurrence::main);
    }

    @Test
    void shouldFindLastElement() {
        assertResult("3 1 2 3 3", "2\n", "Введите x", FirstOccurrence::main);
    }

    @Test
    void shouldReturnMinusOneWhenAbsent() {
        assertResult("2 1 2 9", "-1\n", "Введите x", FirstOccurrence::main);
    }

    @Test
    void shouldFindFirstMatchInMiddle() {
        assertResult("4 5 8 8 2 8", "1\n", "Введите x", FirstOccurrence::main);
    }

    @Test
    void shouldFindMinimumInt() {
        assertResult("2 -2147483648 0 -2147483648", "0\n", "Введите x", FirstOccurrence::main);
    }

    @Test
    void shouldReportMissingParameter() {
        assertOutput(
                "2 1 2 ",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                FirstOccurrence::main);
    }

    @Test
    void shouldReportNonNumericParameter() {
        assertOutput(
                "2 1 2 x",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                FirstOccurrence::main);
    }
}
