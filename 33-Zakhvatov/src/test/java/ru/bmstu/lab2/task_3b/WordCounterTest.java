package ru.bmstu.lab2.task_3b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** проверки задачи 3B. */
class WordCounterTest {
    @Test
    void shouldCountWordsWithoutCaseOrPunctuation() {
        assertEquals(Map.of("hello", 3, "world", 2),
                WordCounter.countWords("Hello, world! HELLO; World... hello"));
    }

    @Test
    void shouldCountCyrillicAndUnicodeWords() {
        assertEquals(Map.of("ёж", 2, "мир", 2, "café", 1, "42", 1),
                WordCounter.countWords("ЁЖ ёж — Мир\nМИР café 42"));
    }

    @Test
    void shouldSeparateWordsAtPunctuationAndWhitespace() {
        assertEquals(Map.of("a", 2, "b", 2, "c", 2), WordCounter.countWords("a,b;c\ta-b\nc"));
    }

    @Test
    void shouldHandleEmptyAndPunctuationOnlyText() {
        assertTrue(WordCounter.countWords("").isEmpty());
        assertTrue(WordCounter.countWords(" ,.!? — \n\t").isEmpty());
    }

    @Test
    void shouldNormalizeCaseIndependentlyOfDefaultLocale() {
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr"));
            assertEquals(Map.of("i", 2), WordCounter.countWords("I i"));
        } finally {
            Locale.setDefault(previous);
        }
    }

    @Test
    void shouldRejectNullText() {
        assertThrows(NullPointerException.class, () -> WordCounter.countWords(null));
    }

    @Test
    void shouldCountManyUniqueWordsAfterResizing() {
        StringBuilder text = new StringBuilder();
        for (int index = 0; index < 1_000; index++) {
            text.append("word").append(index).append(' ');
        }
        Map<String, Integer> result = WordCounter.countWords(text + "word99 word99");
        assertEquals(1_000, result.size());
        assertEquals(3, result.get("word99"));
        assertEquals(1, result.get("word999"));
    }
}
