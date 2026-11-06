package ru.bmstu.lab1.task_41;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 41: каждый случай проверяет запуск программы и её полный вывод. */
class RemoveElementTest {
    @Test
    void shouldRemoveAllScatteredMatches() {
        assertResult("5 2 1 2 3 2 2", "1 3\n", "Введите x", RemoveElement::main);
    }

    @Test
    void shouldPrintEmptyResult() {
        assertResult("2 7 7 7", "\n", "Введите x", RemoveElement::main);
    }

    @Test
    void shouldKeepValuesWhenAbsent() {
        assertResult("3 1 2 3 4", "1 2 3\n", "Введите x", RemoveElement::main);
    }

    @Test
    void shouldRemoveNegativeValues() {
        assertResult("4 -1 0 -1 2 -1", "0 2\n", "Введите x", RemoveElement::main);
    }

    @Test
    void shouldRemoveMinimumInt() {
        assertResult(
                "2 -2147483648 2147483647 -2147483648",
                "2147483647\n",
                "Введите x",
                RemoveElement::main);
    }

    @Test
    void shouldReportMissingParameter() {
        assertOutput(
                "2 1 2 ",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                RemoveElement::main);
    }

    @Test
    void shouldReportNonNumericParameter() {
        assertOutput(
                "2 1 2 x",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                RemoveElement::main);
    }
}
