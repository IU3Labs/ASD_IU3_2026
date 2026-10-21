package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task08FilterBySignTest {
    @Test
    void shouldSeparatePosAndNeg() {
        int[][] res = Task08FilterBySign.filterBySign(new int[]{3, -1, 0, 5, -2});
        assertArrayEquals(new int[]{3, 5}, res[0]);
        assertArrayEquals(new int[]{-1, -2}, res[1]);
    }
    @Test
    void shouldReturnEmptyIfOnlyZeros() {
        int[][] res = Task08FilterBySign.filterBySign(new int[]{0, 0});
        assertArrayEquals(new int[]{}, res[0]);
        assertArrayEquals(new int[]{}, res[1]);
    }
}