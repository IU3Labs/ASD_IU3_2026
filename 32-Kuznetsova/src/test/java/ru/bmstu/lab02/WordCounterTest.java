package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class WordCounterTest {

    @Test
    void shouldCountWordsIgnoringCase() {
        Map<String, Integer> counts = WordCounter.countWords("Cat cat CAT dog");

        assertEquals(2, counts.size());
        assertEquals(3, counts.get("cat"));
        assertEquals(1, counts.get("dog"));
    }

    @Test
    void shouldDropPunctuation() {
        Map<String, Integer> counts = WordCounter.countWords("Hello, world! Hello... (world); \"hello\"?");

        assertEquals(3, counts.get("hello"));
        assertEquals(2, counts.get("world"));
        assertEquals(2, counts.size());
    }

    @Test
    void shouldReturnEmptyMapForTextWithoutWords() {
        assertTrue(WordCounter.countWords("").isEmpty());
        assertTrue(WordCounter.countWords(" ,.;!? \n\t").isEmpty());
    }

    @Test
    void shouldHandleCyrillicAndDigits() {
        Map<String, Integer> counts = WordCounter.countWords("Привет привет ПРИВЕТ 42 42");

        assertEquals(3, counts.get("привет"));
        assertEquals(2, counts.get("42"));
    }

    @Test
    void shouldTreatNewlinesAndTabsAsSeparators() {
        Map<String, Integer> counts = WordCounter.countWords("a\nb\tc a");

        assertEquals(2, counts.get("a"));
        assertEquals(3, counts.size());
    }

    @Test
    void shouldReturnMostFrequentWordsFirst() {
        List<WordCount> top = WordCounter.topWords("b a b c b a", 2);

        assertEquals(List.of(new WordCount("b", 3), new WordCount("a", 2)), top);
    }

    @Test
    void shouldBreakTiesAlphabetically() {
        List<WordCount> top = WordCounter.topWords("pear apple fig", 2);

        assertEquals(List.of(new WordCount("apple", 1), new WordCount("fig", 1)), top);
    }

    @Test
    void shouldReturnAllWordsWhenFewerThanRequested() {
        List<WordCount> top = WordCounter.topWords("one two", 10);

        assertEquals(2, top.size());
    }
}
