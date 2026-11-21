package ru.bmstu.lab2.task_3b;

import java.util.Map;

/** Одно слово и число его вхождений. */
final class WordEntry implements Map.Entry<String, Integer> {
    private final String word;
    private final Integer count;

    WordEntry(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

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
        if (!(other instanceof Map.Entry)) {
            return false;
        }
        Map.Entry<?, ?> entry = (Map.Entry<?, ?>) other;
        return word.equals(entry.getKey()) && count.equals(entry.getValue());
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
