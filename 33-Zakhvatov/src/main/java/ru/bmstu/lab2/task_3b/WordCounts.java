package ru.bmstu.lab2.task_3b;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import ru.bmstu.lab2.result.ResultList;
import ru.bmstu.lab2.result.ResultSet;
import ru.bmstu.lab2.task_2a_2b.HashMapCustom;

/** Результат подсчёта слов. */
final class WordCounts implements Map<String, Integer> {
    private final HashMapCustom<String, Integer> counts;
    private final ResultList<String> words;

    WordCounts(HashMapCustom<String, Integer> ownedCounts) {
        counts = ownedCounts;
        words = counts.snapshotKeys();
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return counts.size();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsKey(Object key) {
        if (key instanceof String) {
            return counts.containsKey((String) key);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Integer get(Object key) {
        if (key instanceof String) {
            return counts.get((String) key);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        for (String word : words) {
            if (counts.get(word).equals(value)) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> keySet() {
        return new ResultSet<>(words);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Integer> values() {
        Object[] result = new Object[size()];
        for (int index = 0; index < size(); index++) {
            result[index] = counts.get(words.get(index));
        }
        return new ResultList<>(result);
    }

    /** {@inheritDoc} */
    @Override
    public Set<Entry<String, Integer>> entrySet() {
        Object[] result = new Object[size()];
        for (int index = 0; index < size(); index++) {
            String word = words.get(index);
            result[index] = new WordEntry(word, counts.get(word));
        }
        return new ResultSet<>(new ResultList<>(result));
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Map)) {
            return false;
        }
        Map<?, ?> map = (Map<?, ?>) other;
        if (map.size() != size()) {
            return false;
        }
        for (String word : words) {
            if (!counts.get(word).equals(map.get(word))) {
                return false;
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return entrySet().hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return entrySet().toString();
    }

    /** {@inheritDoc} */
    @Override
    public Integer put(String key, Integer value) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public Integer remove(Object key) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public void putAll(Map<? extends String, ? extends Integer> map) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

}
