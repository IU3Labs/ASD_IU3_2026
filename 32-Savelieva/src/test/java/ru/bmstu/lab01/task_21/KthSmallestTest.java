package ru.bmstu.lab01.task_21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class KthSmallestTest {

    @Test
    void shouldReturnFirstWhenKIsOne() {
        assertEquals(1, KthSmallest.findKthSmallest(new int[] {4, 2, 1, 3}, 1));
    }

    @Test
    void shouldReturnLastWhenKIsN() {
        assertEquals(4, KthSmallest.findKthSmallest(new int[] {4, 2, 1, 3}, 4));
    }

    @Test
    void shouldReturnMiddleElement() {
        assertEquals(3, KthSmallest.findKthSmallest(new int[] {5, 3, 1, 4, 2}, 3));
    }

    @Test
    void shouldCountDuplicates() {
        // отсортировано: 1, 2, 2, 3, 4. k=3 -> 2
        assertEquals(2, KthSmallest.findKthSmallest(new int[] {2, 4, 1, 3, 2}, 3));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals(-3, KthSmallest.findKthSmallest(new int[] {-1, -3, -2}, 1));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(7, KthSmallest.findKthSmallest(new int[] {7}, 1));
    }

    @Test
    void shouldNotModifySourceArray() {
        int[] source = {3, 1, 2};
        KthSmallest.findKthSmallest(source, 2);
        org.junit.jupiter.api.Assertions.assertArrayEquals(new int[] {3, 1, 2}, source);
    }

    @Test
    void shouldRejectZeroK() {
        assertThrows(IllegalArgumentException.class,
                () -> KthSmallest.findKthSmallest(new int[] {1, 2, 3}, 0));
    }

    @Test
    void shouldRejectKGreaterThanN() {
        assertThrows(IllegalArgumentException.class,
                () -> KthSmallest.findKthSmallest(new int[] {1, 2, 3}, 4));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> KthSmallest.findKthSmallest(null, 1));
    }

    @Test
    void shouldRejectEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> KthSmallest.findKthSmallest(new int[0], 1));
    }
}
