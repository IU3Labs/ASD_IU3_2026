package ru.bmstu.lab02.task03b;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import ru.bmstu.lab02.task02a.HashMapCustom;

/** Подсчёт частоты слов с помощью хеш-таблицы. */
public final class Task03B {
    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{N}][\\p{L}\\p{M}\\p{N}]*");

    private Task03B() {
    }

    /** Подсчитывает слова без учёта регистра. Пунктуация и пробелы разделяют слова. */
    public static Map<String, Integer> countWords(String text) {
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        Matcher words = WORD.matcher(text.toLowerCase(Locale.ROOT));
        while (words.find()) {
            String word = words.group();
            Integer count = counts.get(word);
            counts.put(word, count == null ? 1 : count + 1);
        }
        return asMap(counts);
    }

    private static Map<String, Integer> asMap(HashMapCustom<String, Integer> counts) {
        return new AbstractMap<>() {
            @Override
            public Integer get(Object key) {
                return key instanceof String word ? counts.get(word) : null;
            }

            @Override
            public boolean containsKey(Object key) {
                return get(key) != null;
            }

            @Override
            public int size() {
                return counts.size();
            }

            @Override
            public Set<Entry<String, Integer>> entrySet() {
                return snapshotEntries(counts);
            }
        };
    }

    private static Set<Map.Entry<String, Integer>> snapshotEntries(HashMapCustom<String, Integer> counts) {
        @SuppressWarnings("unchecked")
        Map.Entry<String, Integer>[] entries = (Map.Entry<String, Integer>[]) new Map.Entry<?, ?>[counts.size()];
        int[] index = {0};
        counts.forEach((word, count) -> entries[index[0]++] = Map.entry(word, count));

        return new AbstractSet<>() {
            @Override
            public Iterator<Map.Entry<String, Integer>> iterator() {
                return Stream.of(entries).iterator();
            }

            @Override
            public int size() {
                return entries.length;
            }
        };
    }
}
