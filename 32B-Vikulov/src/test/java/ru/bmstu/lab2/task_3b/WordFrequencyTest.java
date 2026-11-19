package ru.bmstu.lab2.task_3b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab2.task_3b.WordFrequency.WordCount;

class WordFrequencyTest {

    @Test
    void shouldCountWordsIgnoringCase() {
        Map<String, Integer> counts = WordFrequency.countWords("Cat cat CAT dog");

        assertEquals(3, counts.get("cat"));
        assertEquals(1, counts.get("dog"));
        assertEquals(2, counts.size());
    }

    @Test
    void shouldDropPunctuation() {
        Map<String, Integer> counts = WordFrequency.countWords("Hello, world! Hello... (world)?");

        assertEquals(2, counts.get("hello"));
        assertEquals(2, counts.get("world"));
        assertEquals(2, counts.size());
    }

    @Test
    void shouldHandleCyrillicAndDigits() {
        Map<String, Integer> counts = WordFrequency.countWords("Ёжик ЁЖИК в 2026 году, 2026!");

        assertEquals(2, counts.get("ёжик"));
        assertEquals(2, counts.get("2026"));
        assertEquals(1, counts.get("году"));
    }

    @Test
    void shouldSplitOnHyphensApostrophesAndLineBreaks() {
        Map<String, Integer> counts = WordFrequency.countWords("don't\nwell-known\tdon't");

        assertEquals(2, counts.get("don"));
        assertEquals(2, counts.get("t"));
        assertEquals(1, counts.get("well"));
        assertNull(counts.get("don't"));
    }

    @Test
    void shouldReturnEmptyCountsForTextWithoutWords() {
        assertTrue(WordFrequency.countWords("").isEmpty());
        assertTrue(WordFrequency.countWords(" ,.!? \n ").isEmpty());
    }

    @Test
    void shouldCountWordAtVeryEndOfText() {
        assertFalse(WordFrequency.countWords("last").isEmpty());
        assertEquals(1, WordFrequency.countWords("last").get("last"));
    }

    @Test
    void shouldReturnTopWordsByFrequencyThenAlphabetically() {
        Map<String, Integer> counts = WordFrequency.countWords("b a c b a b d");

        List<WordCount> actual = WordFrequency.topWords(counts, 3);

        // у c и d по одному вхождению: при равной частоте первым идёт c (по алфавиту)
        assertEquals(List.of(new WordCount("b", 3), new WordCount("a", 2), new WordCount("c", 1)), actual);
    }

    @Test
    void shouldReturnAllWordsWhenLimitExceedsDistinctCount() {
        Map<String, Integer> counts = WordFrequency.countWords("x y");

        assertEquals(2, WordFrequency.topWords(counts, 10).size());
        assertTrue(WordFrequency.topWords(counts, 0).isEmpty());
    }

    @Test
    void shouldRejectNegativeLimit() {
        Map<String, Integer> counts = WordFrequency.countWords("x");

        assertThrows(IllegalArgumentException.class, () -> WordFrequency.topWords(counts, -1));
    }

    @Test
    void shouldCountSupplementaryUnicodeLetters() {
        assertEquals(Map.of("𐐨", 2), WordFrequency.countWords("𐐀, 𐐨"));
    }

    @Test
    void shouldMatchReferenceCountingOnRandomText() {
        String[] vocabulary = {"alpha", "Beta", "GAMMA", "delta", "эпсилон"};
        String[] separators = {" ", ", ", ".\n", "!  ", "-"};
        Random random = new Random(7);
        StringBuilder text = new StringBuilder();
        Map<String, Integer> expected = new HashMap<>();
        for (int i = 0; i < 20_000; i++) {
            String word = vocabulary[random.nextInt(vocabulary.length)];
            text.append(word).append(separators[random.nextInt(separators.length)]);
            expected.merge(word.toLowerCase(Locale.ROOT), 1, Integer::sum);
        }

        Map<String, Integer> actual = WordFrequency.countWords(text.toString());

        assertEquals(expected, actual);
        assertEquals(actual, expected);
        assertFalse(actual.containsKey(null));
        assertEquals(null, actual.get(42));
    }
}
