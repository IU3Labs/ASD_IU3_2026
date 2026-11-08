package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тесты общих проверок входных данных.
 */
class ValidationTest {

    @Test
    void shouldAcceptNonEmptyArray() {
        assertDoesNotThrow(() -> Validation.requireNonEmpty(new int[] {1}));
    }

    @Test
    void shouldRejectNullOrEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Validation.requireNonEmpty(null));
        assertThrows(IllegalArgumentException.class, () -> Validation.requireNonEmpty(new int[0]));
    }

    @Test
    void shouldAcceptRectangularMatrix() {
        assertDoesNotThrow(() -> Validation.requireRectangular(new int[][] {{1, 2}, {3, 4}}));
    }

    @Test
    void shouldRejectEmptyMatrix() {
        assertThrows(IllegalArgumentException.class, () -> Validation.requireRectangular(null));
        assertThrows(IllegalArgumentException.class, () -> Validation.requireRectangular(new int[0][]));
        assertThrows(IllegalArgumentException.class, () -> Validation.requireRectangular(new int[][] {null}));
        assertThrows(IllegalArgumentException.class, () -> Validation.requireRectangular(new int[][] {{}}));
    }

    @Test
    void shouldRejectRaggedMatrix() {
        assertThrows(IllegalArgumentException.class,
                () -> Validation.requireRectangular(new int[][] {{1, 2}, {3}}));
        assertThrows(IllegalArgumentException.class,
                () -> Validation.requireRectangular(new int[][] {{1, 2}, null}));
    }
}
