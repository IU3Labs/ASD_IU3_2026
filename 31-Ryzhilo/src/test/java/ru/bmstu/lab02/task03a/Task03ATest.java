package ru.bmstu.lab02.task03a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task03a.Task03A;

/** Проверяет решение задачи 03A. */
class Task03ATest {
    @Test
    @DisplayName("Результат topK совпадает с результатом сортировки")
    void shouldMatchSortingForTopK() {
        List<Integer> input = new LinkedList<>(new Random(42).ints(5_000, -500, 500).boxed().toList());
        List<Integer> expected = input.stream().sorted(Comparator.reverseOrder()).limit(150).toList();
        assertEquals(expected, Task03A.topK(input, 150));
    }

    @Test
    @DisplayName("Поддерживаются нулевое и превышающее размер входа k")
    void shouldHandleZeroAndOversizedK() {
        List<Integer> input = List.of(2, 1, 2);
        assertEquals(List.of(), Task03A.topK(input, 0));
        assertEquals(List.of(2, 2, 1), Task03A.topK(input, 9));
        assertThrows(IllegalArgumentException.class, () -> Task03A.topK(input, -1));
    }
}
