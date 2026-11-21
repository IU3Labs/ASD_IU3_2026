package ru.bmstu.lab2;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Неизменяемый Map-адаптер над собственной таблицей для контракта задачи 3B. */
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
        return key instanceof String word && counts.containsKey(word);
    }

    /** {@inheritDoc} */
    @Override
    public Integer get(Object key) {
        return key instanceof String word ? counts.get(word) : null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        for (String word : words) {
            if (Objects.equals(counts.get(word), value)) {
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
        if (!(other instanceof Map<?, ?> map) || map.size() != size()) {
            return false;
        }
        for (String word : words) {
            if (!Objects.equals(counts.get(word), map.get(word))) {
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

    private record WordEntry(String word, Integer count) implements Entry<String, Integer> {
        /** {@inheritDoc} */
        @Override
        public String getKey() {
            return word;
        }

        /** {@inheritDoc} */
        @Override
        public Integer getValue() {
            return count;
        }

        /** {@inheritDoc} */
        @Override
        public Integer setValue(Integer value) {
            throw new UnsupportedOperationException("Результат неизменяемый");
        }

        /** {@inheritDoc} */
        @Override
        public boolean equals(Object other) {
            return other instanceof Entry<?, ?> entry
                    && Objects.equals(word, entry.getKey()) && Objects.equals(count, entry.getValue());
        }

        /** {@inheritDoc} */
        @Override
        public int hashCode() {
            return word.hashCode() ^ count.hashCode();
        }

        /** {@inheritDoc} */
        @Override
        public String toString() {
            return word + "=" + count;
        }
    }
}
