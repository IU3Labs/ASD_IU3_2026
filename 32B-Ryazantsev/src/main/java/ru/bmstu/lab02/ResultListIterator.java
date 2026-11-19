package ru.bmstu.lab02;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/** Двунаправленный обход списка с доступом по индексу. */
final class ResultListIterator<T> implements ListIterator<T> {
    private final List<T> elements;
    private int index;

    ResultListIterator(List<T> elements, int index) {
        this.elements = elements;
        this.index = index;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasNext() {
        return index < elements.size();
    }

    /** {@inheritDoc} */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return elements.get(index++);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    /** {@inheritDoc} */
    @Override
    public T previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        return elements.get(--index);
    }

    /** {@inheritDoc} */
    @Override
    public int nextIndex() {
        return index;
    }

    /** {@inheritDoc} */
    @Override
    public int previousIndex() {
        return index - 1;
    }

    /** {@inheritDoc} */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public void set(T value) {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override
    public void add(T value) {
        throw new UnsupportedOperationException();
    }
}
