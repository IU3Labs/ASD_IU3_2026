package ru.bmstu.lab2.result;

import java.util.Set;

/** Множество уже уникальных элементов. */
public final class ResultSet<T> extends ReadOnlyCollection<T> implements Set<T> {
    private final ResultList<T> items;

    /** Создаёт неизменяемый результат из переданных элементов. */
    public ResultSet(ResultList<T> uniqueItems) {
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
        if (!(other instanceof Set)) {
            return false;
        }
        Set<?> set = (Set<?>) other;
        return set.size() == size() && containsAll(set);
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
