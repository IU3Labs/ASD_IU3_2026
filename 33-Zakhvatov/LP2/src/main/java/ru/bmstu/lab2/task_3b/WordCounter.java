package ru.bmstu.lab2.task_3b;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.bmstu.lab2.task_2a_2b.HashMapCustom;

/** задача 3B: подсчёт слов без учёта регистра. */
public final class WordCounter {
    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{M}\\p{N}]+");

    private WordCounter() {
    }

    /** Считает слова: буквы и цифры, пунктуация разделяет слова. */
    public static Map<String, Integer> countWords(String text) {
        Objects.requireNonNull(text, "text");
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        Matcher matcher = WORD.matcher(text.toLowerCase(Locale.ROOT));
        while (matcher.find()) {
            String word = matcher.group();
            Integer count = counts.get(word);
            counts.put(word, count == null ? 1 : Math.addExact(count, 1));
        }
        return new WordCounts(counts);
    }
}
