package ru.bmstu.lab01.task_12;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PairWithSumTest {

    @Test
    void shouldReturnPairWithSmallestFirstIndex() {
        // пары с суммой 5: (0,3) и (1,2). Наименьший i = 0.
        assertArrayEquals(new int[] {0, 3},
                PairWithSum.findPair(new int[] {1, 2, 3, 4, 5}, 5));
    }

    @Test
    void shouldReturnMinusOneWhenNoPair() {
        assertArrayEquals(new int[] {-1, -1},
                PairWithSum.findPair(new int[] {1, 2, 3, 4, 5}, 10));
    }

    @Test
    void shouldNotPairElementWithItself() {
        // 3 + 3 = 6, но второй тройки нет — пары быть не должно.
        assertArrayEquals(new int[] {-1, -1},
                PairWithSum.findPair(new int[] {3, 1, 2}, 6));
    }

    @Test
    void shouldFindPairWithEqualValuesAtDifferentIndexes() {
        // 3 + 3 = 6: две тройки на индексах 0 и 2.
        assertArrayEquals(new int[] {0, 2},
                PairWithSum.findPair(new int[] {3, 5, 3}, 6));
    }

    @Test
    void shouldPreferSmallestFirstThenSmallestSecond() {
        // пары: (0,1), (0,3), (1,2), (2,3). Наименьший i = 0, для него наименьший j = 1.
        assertArrayEquals(new int[] {0, 1},
                PairWithSum.findPair(new int[] {2, 3, 2, 3}, 5));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        // -5 + 10 = 5.
        assertArrayEquals(new int[] {0, 1},
                PairWithSum.findPair(new int[] {-5, 10, 5}, 5));
    }

    @Test
    void shouldFindPairInMinimalArray() {
        assertArrayEquals(new int[] {0, 1},
                PairWithSum.findPair(new int[] {7, 2}, 9));
    }

    @Test
    void shouldPickSmallestJAmongDuplicatesOfFirstElement() {
        // пары с суммой 5: (0,3) и (3,4). Наименьший i = 0, единственный j = 3.
        assertArrayEquals(new int[] {0, 3},
                PairWithSum.findPair(new int[] {1, 9, 9, 4, 1}, 5));
    }

    @Test
    void shouldHandleNegativeTarget() {
        // -3 + (-2) = -5.
        assertArrayEquals(new int[] {0, 1},
                PairWithSum.findPair(new int[] {-3, -2, 5}, -5));
    }

    @Test
    void shouldPreferEarlierSecondIndexWhenFirstIsTied() {
        // пары с суммой 10: (0,1) 2+8, (0,3) 2+8, (2,3) 7+3. i=0, min j = 1.
        assertArrayEquals(new int[] {0, 1},
                PairWithSum.findPair(new int[] {2, 8, 7, 3, 3}, 10));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> PairWithSum.findPair(null, 5));
    }

    @Test
    void shouldRejectTooShortArray() {
        assertThrows(IllegalArgumentException.class,
                () -> PairWithSum.findPair(new int[] {1}, 5));
    }
}