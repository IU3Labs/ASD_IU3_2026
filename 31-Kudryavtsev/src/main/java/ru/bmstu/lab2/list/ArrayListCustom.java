package ru.bmstu.lab2.list;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Список на собственном массиве для результатов 3A и 3C, без готовых реализаций коллекций.
 * Поддерживает чтение и добавление в конец. Представления subList доступны только для чтения.
 * Остальные изменяющие операции необязательны по контракту List и не поддерживаются.
 */
public class ArrayListCustom<T> implements List<T> {
    private static final int INITIAL_CAPACITY = 16;

    private Object[] elements;
    private int size;
    private int version;
    private final ArrayListCustom<T> root;
    private final int offset;
    private final int expectedVersion;

    /** Создаёт пустой список. */
    public ArrayListCustom() {
        elements = new Object[INITIAL_CAPACITY];
        root = this;
        offset = 0;
        expectedVersion = 0;
    }

    private ArrayListCustom(ArrayListCustom<T> source, int from, int to) {
        root = source.root;
        offset = source.offset + from;
        size = to - from;
        expectedVersion = root.version;
    }

    /** Возвращает элемент за O(1). */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        Objects.checkIndex(index, size());
        return (T) root.elements[offset + index];
    }

    /** Возвращает размер за O(1). */
    @Override
    public int size() {
        if (root != this && root.version != expectedVersion) {
            throw new ConcurrentModificationException();
        }
        return size;
    }

    /** Проверяет пустоту за O(1). */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /** Добавляет элемент в конец за O(1) амортизированно. */
    @Override
    public boolean add(T item) {
        if (root != this) {
            throw new UnsupportedOperationException();
        }
        if (size == elements.length) {
            Object[] expanded = new Object[elements.length * 2];
            System.arraycopy(elements, 0, expanded, 0, size);
            elements = expanded;
        }
        elements[size++] = item;
        version++;
        return true;
    }

    /** Проверяет наличие элемента за O(n). */
    @Override
    public boolean contains(Object item) {
        return indexOf(item) >= 0;
    }

    /** Ищет первое вхождение за O(n), возвращает -1 при отсутствии. */
    @Override
    public int indexOf(Object item) {
        for (int index = 0; index < size(); index++) {
            if (Objects.equals(item, get(index))) {
                return index;
            }
        }
        return -1;
    }

    /** Ищет последнее вхождение за O(n), возвращает -1 при отсутствии. */
    @Override
    public int lastIndexOf(Object item) {
        for (int index = size() - 1; index >= 0; index--) {
            if (Objects.equals(item, get(index))) {
                return index;
            }
        }
        return -1;
    }

    /** Проверяет наличие всех переданных элементов за O(n * c). */
    @Override
    public boolean containsAll(Collection<?> items) {
        for (Object item : items) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    /** Возвращает независимую копию массива элементов за O(n). */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];
        System.arraycopy(root.elements, offset, result, 0, size);
        return result;
    }

    /** Заполняет массив заданного типа за O(n), при необходимости создавая новый. */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] target) {
        int count = size();
        E[] result = target.length >= count ? target
                : (E[]) Array.newInstance(target.getClass().getComponentType(), count);
        System.arraycopy(root.elements, offset, result, 0, count);
        if (result.length > count) {
            result[count] = null;
        }
        return result;
    }

    /** Создаёт итератор за O(1); один шаг занимает O(1). */
    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    /** Создаёт двунаправленный итератор с начала за O(1). */
    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    /** Создаёт двунаправленный итератор с позиции index за O(1). */
    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(index);
        }
        return new Cursor(index);
    }

    /** Возвращает представление диапазона за O(1); изменение исходного списка его инвалидирует. */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex > toIndex");
        }
        Objects.checkFromToIndex(fromIndex, toIndex, size());
        return new ArrayListCustom<>(this, fromIndex, toIndex);
    }

    /** Сравнивает содержимое с другим List за O(n). */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof List<?> list) || size() != list.size()) {
            return false;
        }
        Iterator<?> iterator = list.iterator();
        for (T item : this) {
            if (!Objects.equals(item, iterator.next())) {
                return false;
            }
        }
        return true;
    }

    /** Вычисляет хеш по контракту List за O(n). */
    @Override
    public int hashCode() {
        int hash = 1;
        for (T item : this) {
            hash = 31 * hash + Objects.hashCode(item);
        }
        return hash;
    }

    /** Форматирует элементы в квадратных скобках за O(n), без стоимости преобразования самих элементов. */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int index = 0; index < size(); index++) {
            if (index > 0) {
                result.append(", ");
            }
            Object item = get(index);
            result.append(item == this ? "(this Collection)" : item);
        }
        return result.append(']').toString();
    }

    /** Замена по индексу не поддерживается. */
    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    /** Вставка по индексу не поддерживается; используйте add(T). */
    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    /** Удаление по индексу не поддерживается. */
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    /** Удаление по значению не поддерживается. */
    @Override
    public boolean remove(Object item) {
        throw new UnsupportedOperationException();
    }

    /** Пакетное добавление не поддерживается. */
    @Override
    public boolean addAll(Collection<? extends T> items) {
        throw new UnsupportedOperationException();
    }

    /** Пакетная вставка по индексу не поддерживается. */
    @Override
    public boolean addAll(int index, Collection<? extends T> items) {
        throw new UnsupportedOperationException();
    }

    /** Пакетное удаление не поддерживается. */
    @Override
    public boolean removeAll(Collection<?> items) {
        throw new UnsupportedOperationException();
    }

    /** Фильтрация с удалением не поддерживается. */
    @Override
    public boolean retainAll(Collection<?> items) {
        throw new UnsupportedOperationException();
    }

    /** Очистка не поддерживается. */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /** Итератор только для чтения с проверкой изменения исходного списка. */
    private class Cursor implements ListIterator<T> {
        private int position;
        private final int iteratorVersion = root.version;

        Cursor(int position) {
            this.position = position;
        }

        private void checkVersion() {
            if (iteratorVersion != root.version) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            return position < size();
        }

        @Override
        public T next() {
            checkVersion();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(position++);
        }

        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        @Override
        public T previous() {
            checkVersion();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return get(--position);
        }

        @Override
        public int nextIndex() {
            return position;
        }

        @Override
        public int previousIndex() {
            return position - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T item) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T item) {
            throw new UnsupportedOperationException();
        }
    }
}
