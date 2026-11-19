package ru.bmstu.lab2.task_3b;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/** Представление частот слов для чтения через интерфейс Map. */
final class WordCounts extends AbstractMap<String, Integer> {

    private final HashMapCustom<String, Integer> counts;

    WordCounts(HashMapCustom<String, Integer> counts) {
        this.counts = counts;
    }

    /** {@inheritDoc} */
    @Override
    public Integer get(Object key) {
        return key instanceof String word ? counts.get(word) : null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsKey(Object key) {
        return key instanceof String word && counts.containsKey(word);
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return counts.size();
    }

    /** {@inheritDoc} */
    @Override
    public Set<Entry<String, Integer>> entrySet() {
        return new AbstractSet<>() {
            @Override
            public int size() {
                return counts.size();
            }

            @Override
            public Iterator<Entry<String, Integer>> iterator() {
                @SuppressWarnings("unchecked")
                Entry<String, Integer>[] entries = (Entry<String, Integer>[]) new Entry<?, ?>[counts.size()];
                int[] index = {0};
                counts.forEach((word, count) -> entries[index[0]++] = new SimpleImmutableEntry<>(word, count));
                return Arrays.asList(entries).iterator();
            }
        };
    }
}
