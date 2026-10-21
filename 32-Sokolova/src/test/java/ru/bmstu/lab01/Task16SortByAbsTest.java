package ru.bmstu.lab01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Тесты для задачи 16: Сортировка по модулю.
 */
class Task16SortByAbsTest {

    @Test
    void shouldSortByAbsWithNegFirst() {
        // У 1 и -1 одинаковый модуль.
        // -1 должен идти раньше, потому что -1 < 1.
        int[] input = {3, -1, -2, 1};
        Integer[] expected = {-1, 1, -2, 3};
        
        assertArrayEquals(expected, Task16SortByAbs.sortByAbs(input));
    }

    @Test
    void shouldHandleZeros() {
        // У 0 модуль 0, у -4 модуль 4.
        int[] input = {0, -4, 0};
        Integer[] expected = {0, 0, -4};
        
        assertArrayEquals(expected, Task16SortByAbs.sortByAbs(input));
    }

    @Test
    void shouldHandleAllPositive() {
        // Обычная сортировка по возрастанию.
        int[] input = {3, 1, 2};
        Integer[] expected = {1, 2, 3};
        
        assertArrayEquals(expected, Task16SortByAbs.sortByAbs(input));
    }
}