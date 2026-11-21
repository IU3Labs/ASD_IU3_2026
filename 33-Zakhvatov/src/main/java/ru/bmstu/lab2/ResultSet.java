package ru.bmstu.lab2;

import java.util.Set;

/** Неизменяемое представление уже уникальных ключей или пар собственной таблицы. */
final class ResultSet<T> extends ReadOnlyCollection<T> implements Set<T> {
    private final ResultList<T> items;

    ResultSet(ResultList<T> uniqueItems) {
        items = uniqueItems;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return items.size();
    }

    @Override
    T elementAt(int index) {
        return items.get(index);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof Set<?> set && set.size() == size() && containsAll(set);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int result = 0;
        for (T item : items) {
            result += java.util.Objects.hashCode(item);
        }
        return result;
    }
}
