package ru.bmstu.lab1.task_4;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 4: каждый случай проверяет запуск программы и её полный вывод. */
class AllIndicesTest {
    @Test
    void shouldReturnIncreasingIndices() {
        assertResult("5 2 7 2 3 2 2", "0 2 4\n", "Введите x", AllIndices::main);
    }

    @Test
    void shouldReturnAllIndices() {
        assertResult("2 7 7 7", "0 1\n", "Введите x", AllIndices::main);
    }

    @Test
    void shouldPrintEmptyLineWhenAbsent() {
        assertResult("2 1 2 9", "\n", "Введите x", AllIndices::main);
    }

    @Test
    void shouldFindOnlyLastMatch() {
        assertResult("3 0 1 2 2", "2\n", "Введите x", AllIndices::main);
    }

    @Test
    void shouldFindNegativeMatches() {
        assertResult("3 -1 -1 2 -1", "0 1\n", "Введите x", AllIndices::main);
    }

    @Test
    void shouldReportMissingParameter() {
        assertOutput(
                "2 1 2 ",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                AllIndices::main);
    }

    @Test
    void shouldReportNonNumericParameter() {
        assertOutput(
                "2 1 2 x",
                "Введите длину массива\nВведите массив\nВведите x\nОшибка: должно быть целое число!\n",
                AllIndices::main);
    }
}
