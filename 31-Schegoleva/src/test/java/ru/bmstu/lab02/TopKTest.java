package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopKTest {

    /** Эталон: сортировка по убыванию и взятие первых k. */
    private static <T extends Comparable<? super T>> List<T> reference(List<T> input, int k) {
        List<T> copy = new ArrayList<>(input);
        copy.sort(Collections.reverseOrder());
        return new ArrayList<>(copy.subList(0, Math.min(k, copy.size())));
    }

    // 1. Базовый случай
    @Test
    void shouldReturnTopKForBasicCase() {
        List<Integer> input = Arrays.asList(5, 1, 9, 3, 7, 2, 8);
        assertEquals(Arrays.asList(9, 8, 7), TopK.topK(input, 3));
    }

    // 2. k = 0 даёт пустой список
    @Test
    void shouldReturnEmptyListWhenKIsZero() {
        assertTrue(TopK.topK(Arrays.asList(1, 2, 3), 0).isEmpty());
    }

    // 3. Отрицательное k
    @Test
    void shouldThrowWhenKIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> TopK.topK(Arrays.asList(1, 2, 3), -1));
    }

    // 4. k больше размера списка: возвращаются все элементы по убыванию
    @Test
    void shouldReturnAllElementsDescendingWhenKExceedsSize() {
        List<Integer> input = Arrays.asList(4, 2, 9);
        assertEquals(Arrays.asList(9, 4, 2), TopK.topK(input, 100));
    }

    // 5. k равно размеру списка
    @Test
    void shouldReturnAllElementsDescendingWhenKEqualsSize() {
        List<Integer> input = Arrays.asList(3, 1, 2);
        assertEquals(Arrays.asList(3, 2, 1), TopK.topK(input, 3));
    }

    // 6. Пустой список
    @Test
    void shouldReturnEmptyListWhenInputIsEmpty() {
        assertTrue(TopK.topK(new ArrayList<Integer>(), 5).isEmpty());
    }

    // 7. null вместо списка
    @Test
    void shouldThrowWhenInputIsNull() {
        assertThrows(NullPointerException.class, () -> TopK.topK((List<Integer>) null, 3));
    }

    // 8. null внутри списка
    @Test
    void shouldThrowWhenInputContainsNull() {
        List<Integer> input = Arrays.asList(1, null, 3);
        assertThrows(NullPointerException.class, () -> TopK.topK(input, 2));
    }

    // 9. k = 1 даёт максимум
    @Test
    void shouldReturnMaximumWhenKIsOne() {
        assertEquals(Collections.singletonList(42), TopK.topK(Arrays.asList(10, 42, -5, 7), 1));
    }

    // 10. Дубликаты учитываются как отдельные элементы
    @Test
    void shouldCountDuplicatesSeparately() {
        List<Integer> input = Arrays.asList(5, 5, 5, 3, 3, 1);
        assertEquals(Arrays.asList(5, 5, 5, 3), TopK.topK(input, 4));
    }

    // 11. Все элементы одинаковые
    @Test
    void shouldHandleAllEqualElements() {
        List<Integer> input = Collections.nCopies(10, 7);
        assertEquals(Arrays.asList(7, 7, 7), TopK.topK(input, 3));
    }

    // 12. Отрицательные числа и экстремальные значения
    @Test
    void shouldHandleNegativeAndExtremeValues() {
        List<Integer> input = Arrays.asList(-1, Integer.MIN_VALUE, 0, Integer.MAX_VALUE, -100);
        assertEquals(Arrays.asList(Integer.MAX_VALUE, 0, -1), TopK.topK(input, 3));
    }

    // 13. Исходный список не изменяется
    @Test
    void shouldNotModifyInputList() {
        List<Integer> input = new ArrayList<>(Arrays.asList(4, 8, 1, 9, 3));
        List<Integer> copy = new ArrayList<>(input);

        TopK.topK(input, 2);

        assertEquals(copy, input);
    }

    // 14. Строки
    @Test
    void shouldWorkWithStrings() {
        List<String> input = Arrays.asList("pear", "apple", "orange", "banana", "kiwi");
        assertEquals(Arrays.asList("pear", "orange"), TopK.topK(input, 2));
    }

    // 15. Пользовательский Comparable
    private record Score(String player, int points) implements Comparable<Score> {
        @Override
        public int compareTo(Score other) {
            return Integer.compare(points, other.points);
        }
    }

    @Test
    void shouldWorkWithCustomComparable() {
        List<Score> input = Arrays.asList(
                new Score("alice", 50),
                new Score("bob", 90),
                new Score("carol", 70),
                new Score("dave", 10));

        List<Score> top = TopK.topK(input, 2);

        assertEquals(2, top.size());
        assertEquals("bob", top.get(0).player());
        assertEquals("carol", top.get(1).player());
    }

    // 16. Отсортированный по возрастанию и по убыванию вход
    @Test
    void shouldHandleSortedAndReverseSortedInput() {
        List<Integer> ascending = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ascending.add(i);
        }
        List<Integer> descending = new ArrayList<>(ascending);
        Collections.reverse(descending);

        List<Integer> expected = Arrays.asList(999, 998, 997, 996, 995);
        assertEquals(expected, TopK.topK(ascending, 5));
        assertEquals(expected, TopK.topK(descending, 5));
    }

    // 17. Случайные данные с дубликатами при разных k: сравнение с сортировкой
    @Test
    void shouldMatchSortingForVariousK() {
        Random random = new Random(42);
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 20_000; i++) {
            input.add(random.nextInt(5_000)); // много дубликатов
        }

        for (int k : new int[]{1, 2, 10, 100, 1_000, 19_999, 20_000, 25_000}) {
            assertEquals(reference(input, k), TopK.topK(input, k), "k = " + k);
        }
    }

    // 18. Результат всегда упорядочен по убыванию
    @Test
    void shouldReturnResultSortedDescending() {
        Random random = new Random(7);
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 5_000; i++) {
            input.add(random.nextInt());
        }

        List<Integer> top = TopK.topK(input, 500);

        assertEquals(500, top.size());
        for (int i = 1; i < top.size(); i++) {
            assertTrue(top.get(i - 1) >= top.get(i), "Order violated at index " + i);
        }
    }

    // 19. 1 000 000 элементов: корректность относительно сортировки
    @Test
    void shouldMatchSortingOnMillionElements() {
        Random random = new Random(2026);
        int n = 1_000_000;
        List<Integer> input = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            input.add(random.nextInt());
        }

        assertEquals(reference(input, 10), TopK.topK(input, 10));
        assertEquals(reference(input, 1_000), TopK.topK(input, 1_000));
    }
}
