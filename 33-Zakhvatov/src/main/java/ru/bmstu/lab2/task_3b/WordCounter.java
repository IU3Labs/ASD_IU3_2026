package ru.bmstu.lab2.task_3b;

import java.util.Locale;
import java.util.Map;
import ru.bmstu.lab2.task_2a_2b.HashMapCustom;

/** задача 3B: подсчёт слов без учёта регистра. */
public final class WordCounter {
    private WordCounter() {
    }

    /** Считает слова из букв и цифр; пунктуация разделяет слова. */
    public static Map<String, Integer> countWords(String text) {
        if (text == null) {
            throw new NullPointerException("text");
        }
        text = text.toLowerCase(Locale.ROOT);
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length() && Character.isLetterOrDigit(text.charAt(i))) {
                word.append(text.charAt(i));
            } else if (word.length() > 0) {
                addWord(counts, word.toString());
                word.setLength(0);
            }
        }
        return new WordCounts(counts);
    }

    private static void addWord(HashMapCustom<String, Integer> counts, String word) {
        Integer count = counts.get(word);
        if (count == null) {
            counts.put(word, 1);
        } else {
            counts.put(word, count + 1);
        }
    }
}
