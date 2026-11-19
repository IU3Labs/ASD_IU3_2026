package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/** Список только для чтения, хранящий результат Top-K в собственном массиве. */
final class ResultList<T> implements List<T> {
    private final Object[] elements;

    ResultList(Object[] elements) {
        this.elements = elements;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return elements.length;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) elements[index];
    }

    /** {@inheritDoc} */
    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    /** {@inheritDoc} */
    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(value, elements[i])) {
                return i;
            }
        }
        return -1;
    }

    /** {@inheritDoc} */
    @Override
    public int lastIndexOf(Object value) {
        for (int i = size() - 1; i >= 0; i--) {
            if (Objects.equals(value, elements[i])) {
                return i;
            }
        }
        return -1;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsAll(Collection<?> values) {
        for (Object value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Object[] toArray() {
        return elements.clone();
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public <A> A[] toArray(A[] destination) {
        if (destination.length < size()) {
            return Arrays.copyOf(elements, size(), (Class<? extends A[]>) destination.getClass());
        }
        System.arraycopy(elements, 0, destination, 0, size());
        if (destination.length > size()) {
            destination[size()] = null;
        }
        return destination;
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    /** {@inheritDoc} */
    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    /** {@inheritDoc} */
    @Override
    public ListIterator<T> listIterator(int index) {
        Objects.checkFromToIndex(index, index, size());
        return new ResultListIterator<>(this, index);
    }

    /** {@inheritDoc} */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        Objects.checkFromToIndex(fromIndex, toIndex, size());
        return new ResultList<>(Arrays.copyOfRange(elements, fromIndex, toIndex));
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof List<?> list) || size() != list.size()) {
            return false;
        }
        Iterator<?> iterator = list.iterator();
        for (Object element : elements) {
            if (!Objects.equals(element, iterator.next())) {
                return false;
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    /** {@inheritDoc} */
    @Override
    public boolean add(T item) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public void add(int index, T item) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public boolean addAll(Collection<? extends T> items) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public boolean addAll(int index, Collection<? extends T> items) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public T set(int index, T item) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public boolean remove(Object item) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public boolean removeAll(Collection<?> items) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public boolean retainAll(Collection<?> items) {
        throw new UnsupportedOperationException();
    }
    /** {@inheritDoc} */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
