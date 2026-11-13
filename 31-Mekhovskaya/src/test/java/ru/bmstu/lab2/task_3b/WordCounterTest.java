package ru.bmstu.lab2.task_3b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/**
 * Тесты задачи 3B — {@link WordCounter} и {@link WordTokenizer}.
 */
class WordCounterTest {

    private static final int TOP_COUNT = 10;
    private static final long SEED = 2026L;

    @Test
    void shouldReturnEmptyMapForEmptyText() {
        HashMapCustom<String, Integer> counts = WordCounter.countWords("");

        assertTrue(counts.isEmpty());
    }

    @Test
    void shouldReturnEmptyMapForPunctuationOnly() {
        HashMapCustom<String, Integer> counts = WordCounter.countWords("... !!! — , ; : ?? - '");

        assertTrue(counts.isEmpty());
    }

    @Test
    void shouldCountSingleWord() {
        HashMapCustom<String, Integer> counts = WordCounter.countWords("слово");

        assertEquals(1, counts.size());
        assertEquals(1, counts.get("слово"));
    }

    @Test
    void shouldIgnoreCase() {
        HashMapCustom<String, Integer> counts = WordCounter.countWords("Мир мир МИР World world");

        assertEquals(3, counts.get("мир"));
        assertEquals(2, counts.get("world"));
        assertNull(counts.get("Мир"));
    }

    @Test
    void shouldDropPunctuation() {
        HashMapCustom<String, Integer> counts = WordCounter.countWords("Привет, мир! Привет... (мир) «привет»?");

        assertEquals(2, counts.size());
        assertEquals(3, counts.get("привет"));
        assertEquals(2, counts.get("мир"));
    }

    @Test
    void shouldKeepInnerHyphenAndApostrophe() {
        List<String> words = tokenize("Кто-то сказал: don't — и ушёл; it’s a well-known fact.");

        assertEquals(List.of("кто-то", "сказал", "don't", "и", "ушёл", "it's", "a", "well-known", "fact"), words);
    }

    @Test
    void shouldTreatHyphenAtWordEdgeAsSeparator() {
        List<String> words = tokenize("-начало конец- двойной--дефис");

        assertEquals(List.of("начало", "конец", "двойной", "дефис"), words);
    }

    @Test
    void shouldSplitOnWhitespaceOfAnyKind() {
        List<String> words = tokenize("  один\tдва\nтри\r\n четыре  ");

        assertEquals(List.of("один", "два", "три", "четыре"), words);
    }

    @Test
    void shouldTreatDigitsAsPartOfWords() {
        HashMapCustom<String, Integer> counts = WordCounter.countWords("ИУ3 2026, иу3 — 2026.");

        assertEquals(2, counts.get("иу3"));
        assertEquals(2, counts.get("2026"));
    }

    @Test
    void shouldReturnTopWordsByFrequencyThenAlphabetically() {
        String text = "b a c b a b d d";

        List<WordCount> top = WordCounter.topWords(text, 3);

        assertEquals(List.of(new WordCount("b", 3), new WordCount("a", 2), new WordCount("d", 2)), top);
    }

    @Test
    void shouldReturnAllWordsWhenFewerThanK() {
        List<WordCount> top = WordCounter.topWords("кот пёс кот", TOP_COUNT);

        assertEquals(List.of(new WordCount("кот", 2), new WordCount("пёс", 1)), top);
    }

    @Test
    void shouldReturnEmptyListWhenKIsZero() {
        assertTrue(WordCounter.topWords("a b c", 0).isEmpty());
    }

    @Test
    void shouldRejectNegativeKAndNullText() {
        assertThrows(IllegalArgumentException.class, () -> WordCounter.topWords("a", -1));
        assertThrows(NullPointerException.class, () -> WordCounter.countWords(null));
    }

    @Test
    void shouldMatchReferenceCountingOnOneMegabyteText() {
        String text = SampleTextGenerator.generate(SampleTextGenerator.ONE_MEGABYTE, SEED);
        assertTrue(text.getBytes(StandardCharsets.UTF_8).length >= SampleTextGenerator.ONE_MEGABYTE);
        Map<String, Integer> expectedCounts = new HashMap<>();
        WordTokenizer.forEachWord(text, word -> expectedCounts.merge(word, 1, Integer::sum));

        HashMapCustom<String, Integer> actualCounts = WordCounter.countWords(text);
        List<WordCount> actualTop = WordCounter.topWords(actualCounts, TOP_COUNT);

        assertEquals(expectedCounts.size(), actualCounts.size());
        expectedCounts.forEach((word, count) -> assertEquals(count, actualCounts.get(word), word));
        assertEquals(referenceTop(expectedCounts), actualTop);
    }

    private static List<String> tokenize(String text) {
        List<String> words = new ArrayList<>();
        WordTokenizer.forEachWord(text, words::add);
        return words;
    }

    /** Эталон топа: полная сортировка всех слов стандартной библиотекой. */
    private static List<WordCount> referenceTop(Map<String, Integer> counts) {
        List<WordCount> all = new ArrayList<>();
        counts.forEach((word, count) -> all.add(new WordCount(word, count)));
        all.sort(Comparator.comparingInt(WordCount::count).reversed().thenComparing(WordCount::word));
        return all.subList(0, Math.min(TOP_COUNT, all.size()));
    }
}
