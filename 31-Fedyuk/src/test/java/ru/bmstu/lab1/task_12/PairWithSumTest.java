package ru.bmstu.lab1.task_12;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;
import static ru.bmstu.lab1.support.ConsoleAssertions.assertResult;

import org.junit.jupiter.api.Test;

/** Тесты задачи 12: каждый случай проверяет запуск программы и её полный вывод. */
class PairWithSumTest {
    @Test
    void shouldFindRegularPair() {
        assertResult("4 2 7 11 15 9", "0 1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldMinimizeFirstIndex() {
        assertResult("4 1 2 3 4 5", "0 3\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldMinimizeSecondIndex() {
        assertResult("4 2 3 3 2 5", "0 1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldUseTwoEqualElements() {
        assertResult("2 3 3 6", "0 1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldAvoidReusingSameElement() {
        assertResult("3 1 2 4 8", "-1 -1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldRejectTargetAbovePossibleSums() {
        assertResult("3 1 2 3 20", "-1 -1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldRejectTargetBelowPossibleSums() {
        assertResult("3 1 2 3 -20", "-1 -1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldChooseFirstZeroPair() {
        assertResult("3 0 0 0 0", "0 1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldFindPairStartingAfterZero() {
        assertResult("4 10 -5 4 1 -4", "1 3\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldHandleOppositeIntLimits() {
        assertResult("2 -2147483648 2147483647 -1", "0 1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldAvoidSumOverflow() {
        assertResult("2 2147483647 2147483647 -2", "-1 -1\n", "Введите сумму s", PairWithSum::main);
    }

    @Test
    void shouldAcceptSumBeyondInt() {
        assertResult(
                "2 2147483647 2147483647 4294967294",
                "0 1\n",
                "Введите сумму s",
                PairWithSum::main);
    }

    @Test
    void shouldReportMissingParameter() {
        assertOutput(
                "2 1 2 ",
                "Введите длину массива\nВведите массив\nВведите сумму s\nОшибка: должно быть целое число!\n",
                PairWithSum::main);
    }

    @Test
    void shouldReportNonNumericParameter() {
        assertOutput(
                "2 1 2 x",
                "Введите длину массива\nВведите массив\nВведите сумму s\nОшибка: должно быть целое число!\n",
                PairWithSum::main);
    }

    @Test
    void shouldRejectSumOutsideLongRange() {
        assertOutput(
                "2 1 2 9223372036854775808",
                "Введите длину массива\nВведите массив\nВведите сумму s\n"
                        + "Ошибка: должно быть целое число!\n",
                PairWithSum::main);
    }
}
