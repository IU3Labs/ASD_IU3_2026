package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Task09CheckSortedTest {
    @Test
    void shouldReturnTrueForSorted() { assertTrue(Task09CheckSorted.isNonDecreasing(new int[]{1, 2, 2, 5})); }
    @Test
    void shouldReturnFalseForUnsorted() { assertFalse(Task09CheckSorted.isNonDecreasing(new int[]{1, 3, 2})); }
    @Test
    void shouldReturnTrueForSingle() { assertTrue(Task09CheckSorted.isNonDecreasing(new int[]{7})); }
}