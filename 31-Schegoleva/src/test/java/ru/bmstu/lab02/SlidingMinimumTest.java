package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlidingMinimumTest {

    /** Эталон: прямой перебор каждого окна, O(n * k). */
    private static int[] bruteForce(int[] arr, int k) {
        int[] result = new int[arr.length - k + 1];
        for (int i = 0; i < result.length; i++) {
            int min = arr[i];
            for (int j = i + 1; j < i + k; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                }
            }
            result[i] = min;
        }
        return result;
    }

    // 1. Классический пример
    @Test
    void shouldReturnWindowMinimumsForBasicCase() {
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        assertArrayEquals(new int[]{-1, -3, -3, -3, 3, 3}, SlidingMinimum.slidingMin(arr, 3));
    }

    // 2. Окно размера 1: результат равен исходному массиву
    @Test
    void shouldReturnSameValuesWhenWindowIsOne() {
        int[] arr = {4, 2, 7, 1};
        assertArrayEquals(arr, SlidingMinimum.slidingMin(arr, 1));
    }

    // 3. Окно равно длине массива: одно значение, минимум всего массива
    @Test
    void shouldReturnSingleMinimumWhenWindowEqualsArrayLength() {
        int[] arr = {5, 2, 9, 3};
        assertArrayEquals(new int[]{2}, SlidingMinimum.slidingMin(arr, 4));
    }

    // 4. Окно больше массива
    @Test
    void shouldThrowWhenWindowIsLargerThanArray() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[]{1, 2}, 3));
    }

    // 5. Окно меньше 1
    @Test
    void shouldThrowWhenWindowIsNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[]{1, 2}, 0));
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[]{1, 2}, -1));
    }

    // 6. Пустой массив
    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[0], 1));
    }

    // 7. null вместо массива
    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(NullPointerException.class, () -> SlidingMinimum.slidingMin(null, 2));
    }

    // 8. Дубликаты
    @Test
    void shouldHandleDuplicates() {
        int[] arr = {2, 2, 2, 1, 1, 3};
        assertArrayEquals(new int[]{2, 2, 1, 1, 1}, SlidingMinimum.slidingMin(arr, 2));
    }

    // 9. Возрастающий массив: минимум всегда левый край окна
    @Test
    void shouldHandleIncreasingArray() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        assertArrayEquals(new int[]{1, 2, 3, 4}, SlidingMinimum.slidingMin(arr, 3));
    }

    // 10. Убывающий массив: минимум всегда правый край окна
    @Test
    void shouldHandleDecreasingArray() {
        int[] arr = {6, 5, 4, 3, 2, 1};
        assertArrayEquals(new int[]{4, 3, 2, 1}, SlidingMinimum.slidingMin(arr, 3));
    }

    // 11. Отрицательные и экстремальные значения
    @Test
    void shouldHandleNegativeAndExtremeValues() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -1, Integer.MAX_VALUE};
        assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, -1, -1},
                SlidingMinimum.slidingMin(arr, 2));
    }

    // 12. Исходный массив не изменяется
    @Test
    void shouldNotModifyInputArray() {
        int[] arr = {5, 1, 4, 2, 3};
        int[] copy = arr.clone();

        SlidingMinimum.slidingMin(arr, 2);

        assertArrayEquals(copy, arr);
    }

    // 13. Случайные данные при разных размерах окна: сверка с перебором
    @Test
    void shouldMatchBruteForceOnRandomData() {
        Random random = new Random(5);
        int[] arr = new int[500];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }

        for (int k : new int[]{1, 2, 7, 50, 499, 500}) {
            assertArrayEquals(bruteForce(arr, k), SlidingMinimum.slidingMin(arr, k), "k = " + k);
        }
    }

    // 14. Требуемый сценарий: 200 000 элементов, окно 1 000
    @Test
    void shouldHandleLargeArrayWithLargeWindow() {
        Random random = new Random(2026);
        int[] arr = new int[200_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt();
        }

        assertArrayEquals(bruteForce(arr, 1_000), SlidingMinimum.slidingMin(arr, 1_000));
    }
}
