package ru.bmstu.lab1.task_23;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

/**
 * Тесты задачи 23 «Проверка анаграммы массивов».
 */
class PermutationCheckerTest {

    @Test
    void shouldReturnTrueWhenArraysArePermutations() {
        assertTrue(PermutationChecker.isPermutation(new int[] {3, 1, 2, 1}, new int[] {1, 2, 1, 3}));
    }

    @Test
    void shouldReturnFalseWhenMultiplicitiesDiffer() {
        // Одинаковый набор значений {1, 2}, но разное количество повторов.
        assertFalse(PermutationChecker.isPermutation(new int[] {1, 1, 2}, new int[] {1, 2, 2}));
    }

    @Test
    void shouldReturnFalseWhenLengthsDiffer() {
        assertFalse(PermutationChecker.isPermutation(new int[] {1, 2}, new int[] {1, 2, 3}));
    }

    @Test
    void shouldNotModifyInputArrays() {
        int[] first = {3, 2, 1};
        int[] second = {1, 3, 2};

        PermutationChecker.isPermutation(first, second);

        assertArrayEquals(new int[] {3, 2, 1}, first);
        assertArrayEquals(new int[] {1, 3, 2}, second);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> PermutationChecker.isPermutation(null, new int[] {1}));
        assertThrows(IllegalArgumentException.class, () -> PermutationChecker.isPermutation(new int[] {1}, null));
    }

    @Test
    void shouldPrintYesOrNoFromConsoleInput() {
        assertEquals(List.of("YES"), ConsoleRunner.execute(PermutationChecker::run, "3\n1 2 3\n3 2 1"));
        assertEquals(List.of("NO"), ConsoleRunner.execute(PermutationChecker::run, "3\n1 2 3\n1 2 4"));
    }
}
