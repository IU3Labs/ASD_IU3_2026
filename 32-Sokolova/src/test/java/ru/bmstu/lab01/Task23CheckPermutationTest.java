package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task23CheckPermutationTest {
    @Test void shouldReturnYesForPermutation() { assertTrue(Task23CheckPermutation.isPermutation(new int[]{1, 2, 3}, new int[]{3, 1, 2})); }
    @Test void shouldReturnNoForDifferent() { assertFalse(Task23CheckPermutation.isPermutation(new int[]{1, 2}, new int[]{1, 3})); }
    @Test void shouldReturnNoForDifferentLengths() { assertFalse(Task23CheckPermutation.isPermutation(new int[]{1}, new int[]{1, 1})); }
}