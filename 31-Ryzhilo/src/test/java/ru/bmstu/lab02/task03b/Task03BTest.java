package ru.bmstu.lab02.task03b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task03b.Task03B;

/** Проверяет решение задачи 03B. */
class Task03BTest {
    @Test
    @DisplayName("Подсчёт слов игнорирует регистр и отделяет пунктуацию")
    void shouldCountWordsWithoutCaseOrPunctuation() {
        Map<String, Integer> counts = Task03B.countWords("Hello, HELLO! мир; МИР. 42");
        assertEquals(Map.of("hello", 2, "мир", 2, "42", 1), counts);
        assertEquals(3, counts.entrySet().size());
        assertTrue(counts.containsKey("мир"));
        assertFalse(counts.containsKey(42));
        assertNull(counts.get("absent"));
    }

    @Test
    @DisplayName("Строка из знаков препинания не содержит слов")
    void shouldReturnEmptyCountsForPunctuationOnly() {
        assertTrue(Task03B.countWords("... -- !").isEmpty());
    }
}
