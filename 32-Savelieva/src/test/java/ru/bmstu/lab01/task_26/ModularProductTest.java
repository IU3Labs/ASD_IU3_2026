package ru.bmstu.lab01.task_26;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ModularProductTest {

    private static final long MOD = 1_000_000_007L;

    @Test
    void shouldMultiplySmallNumbers() {
        assertEquals(24L, ModularProduct.product(new int[] {1, 2, 3, 4}));
    }

    @Test
    void shouldReturnZeroWhenZeroPresent() {
        assertEquals(0L, ModularProduct.product(new int[] {5, 0, 3}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(7L, ModularProduct.product(new int[] {7}));
    }

    @Test
    void shouldHandleNegativeValues() {
        // (-2) * 3 = -6, по модулю 10^9+7 это MOD - 6
        assertEquals(MOD - 6, ModularProduct.product(new int[] {-2, 3}));
    }

    @Test
    void shouldHandleDoubleNegative() {
        // (-2) * (-3) = 6
        assertEquals(6L, ModularProduct.product(new int[] {-2, -3}));
    }

    @Test
    void shouldReduceLargeResultByModulo() {
        // 10^6 * 10^6 = 10^12, по модулю 10^9+7 = 10^12 - 1000 * (10^9+7) = 999993000
        assertEquals(999_993_007L, ModularProduct.product(new int[] {1_000_000, 1_000_000}));
    }

    @Test
    void shouldHandleMaxIntValuesWithoutOverflow() {
        // MAX_VALUE * MAX_VALUE — в long помещается, но результат модуля корректен
        long expected = ((long) Integer.MAX_VALUE % MOD) * ((long) Integer.MAX_VALUE % MOD) % MOD;
        assertEquals(expected, ModularProduct.product(new int[] {
                Integer.MAX_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> ModularProduct.product(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> ModularProduct.product(new int[0]));
    }
}