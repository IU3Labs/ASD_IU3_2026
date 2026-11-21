package ru.bmstu.lab2.result;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/** Общие методы для списка и множества. */
abstract class ReadOnlyCollection<T> implements Collection<T> {
    abstract T elementAt(int index);

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean contains(Object item) {
        for (T element : this) {
            if (Objects.equals(element, item)) {
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsAll(Collection<?> items) {
        for (Object item : items) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];
        for (int index = 0; index < size(); index++) {
            result[index] = elementAt(index);
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] array) {
        E[] result = array.length < size() ? Arrays.copyOf(array, size()) : array;
        for (int index = 0; index < size(); index++) {
            result[index] = (E) elementAt(index);
        }
        if (result.length > size()) {
            result[size()] = null;
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<T> iterator() {
        return new ResultList<T>(toArray()).listIterator();
    }

    /** {@inheritDoc} */
    @Override
    public boolean add(T item) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public boolean remove(Object item) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public boolean addAll(Collection<? extends T> items) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public boolean removeAll(Collection<?> items) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public boolean retainAll(Collection<?> items) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int index = 0; index < size(); index++) {
            if (index > 0) {
                result.append(", ");
            }
            result.append(elementAt(index));
        }
        return result.append(']').toString();
    }
}
