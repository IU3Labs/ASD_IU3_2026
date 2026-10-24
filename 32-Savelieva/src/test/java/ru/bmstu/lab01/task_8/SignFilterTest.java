package ru.bmstu.lab01.task_8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class SignFilterTest {

    @Test
    void shouldSplitMixedArrayKeepingOrder() {
        SignFilter.Result r = SignFilter.split(new int[] {-1, 2, 0, -3, 4});
        assertEquals(List.of(2, 4), r.getPositives());
        assertEquals(List.of(-1, -3), r.getNegatives());
    }

    @Test
    void shouldReturnEmptyNegativesWhenOnlyPositives() {
        SignFilter.Result r = SignFilter.split(new int[] {1, 2, 3});
        assertEquals(List.of(1, 2, 3), r.getPositives());
        assertEquals(List.of(), r.getNegatives());
    }

    @Test
    void shouldReturnEmptyPositivesWhenOnlyNegatives() {
        SignFilter.Result r = SignFilter.split(new int[] {-1, -2, -3});
        assertEquals(List.of(), r.getPositives());
        assertEquals(List.of(-1, -2, -3), r.getNegatives());
    }

    @Test
    void shouldIgnoreAllZeros() {
        SignFilter.Result r = SignFilter.split(new int[] {0, 0, 0});
        assertEquals(List.of(), r.getPositives());
        assertEquals(List.of(), r.getNegatives());
    }

    @Test
    void shouldHandleSinglePositive() {
        SignFilter.Result r = SignFilter.split(new int[] {5});
        assertEquals(List.of(5), r.getPositives());
        assertEquals(List.of(), r.getNegatives());
    }

    @Test
    void shouldHandleSingleNegative() {
        SignFilter.Result r = SignFilter.split(new int[] {-5});
        assertEquals(List.of(), r.getPositives());
        assertEquals(List.of(-5), r.getNegatives());
    }

    @Test
    void shouldKeepOriginalOrderInEachGroup() {
        SignFilter.Result r = SignFilter.split(new int[] {9, -1, 7, -2, 5, -3});
        assertEquals(List.of(9, 7, 5), r.getPositives());
        assertEquals(List.of(-1, -2, -3), r.getNegatives());
    }

    @Test
    void shouldHandleExtremeIntValues() {
        SignFilter.Result r = SignFilter.split(
                new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE});
        assertEquals(List.of(Integer.MAX_VALUE), r.getPositives());
        assertEquals(List.of(Integer.MIN_VALUE), r.getNegatives());
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SignFilter.split(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> SignFilter.split(new int[0]));
    }
}