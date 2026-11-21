package ru.bmstu.lab2.result;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;

/** Неизменяемый список на собственном массиве для результатов задач 3A и 3C. */
public final class ResultList<T> extends ReadOnlyCollection<T> implements List<T>, RandomAccess {
    private final Object[] elements;

    /** Создаёт неизменяемый результат из переданных элементов. */
    public ResultList(Object[] ownedElements) {
        elements = ownedElements.clone();
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return elements.length;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        Objects.checkIndex(index, size());
        return (T) elements[index];
    }

    @Override
    T elementAt(int index) {
        return get(index);
    }

    /** {@inheritDoc} */
    @Override
    public int indexOf(Object item) {
        for (int index = 0; index < size(); index++) {
            if (Objects.equals(elements[index], item)) {
                return index;
            }
        }
        return -1;
    }

    /** {@inheritDoc} */
    @Override
    public int lastIndexOf(Object item) {
        for (int index = size() - 1; index >= 0; index--) {
            if (Objects.equals(elements[index], item)) {
                return index;
            }
        }
        return -1;
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
        return new Cursor(index);
    }

    /** {@inheritDoc} */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        Objects.checkFromToIndex(fromIndex, toIndex, size());
        Object[] result = new Object[toIndex - fromIndex];
        System.arraycopy(elements, fromIndex, result, 0, result.length);
        return new ResultList<>(result);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof List<?> list) || list.size() != size()) {
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
        int result = 1;
        for (Object element : elements) {
            result = 31 * result + Objects.hashCode(element);
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public T set(int index, T item) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public void add(int index, T item) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    /** {@inheritDoc} */
    @Override
    public boolean addAll(int index, Collection<? extends T> items) {
        throw new UnsupportedOperationException("Результат неизменяемый");
    }

    private final class Cursor implements ListIterator<T> {
        private int position;

        private Cursor(int position) {
            this.position = position;
        }

        /** {@inheritDoc} */
        @Override
        public boolean hasNext() {
            return position < size();
        }

        /** {@inheritDoc} */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(position++);
        }

        /** {@inheritDoc} */
        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        /** {@inheritDoc} */
        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return get(--position);
        }

        /** {@inheritDoc} */
        @Override
        public int nextIndex() {
            return position;
        }

        /** {@inheritDoc} */
        @Override
        public int previousIndex() {
            return position - 1;
        }

        /** {@inheritDoc} */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Результат неизменяемый");
        }

        /** {@inheritDoc} */
        @Override
        public void set(T item) {
            throw new UnsupportedOperationException("Результат неизменяемый");
        }

        /** {@inheritDoc} */
        @Override
        public void add(T item) {
            throw new UnsupportedOperationException("Результат неизменяемый");
        }
    }
}
