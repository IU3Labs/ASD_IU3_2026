package ru.bmstu.lab02.task03c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task03c.Task03C;

/** Проверяет решение задачи 03C. */
class Task03CTest {
    @Test
    @DisplayName("Сохраняются первые вхождения, включая null")
    void shouldKeepFirstOccurrenceIncludingNull() {
        List<Integer> items = Arrays.asList(8, null, 1, 8, 2, null, 1);
        assertEquals(Arrays.asList(8, null, 1, 2), Task03C.distinct(items));
        assertTrue(Task03C.distinct(List.of()).isEmpty());
    }
}
