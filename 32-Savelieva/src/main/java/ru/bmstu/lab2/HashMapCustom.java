
package ru.bmstu.lab2;

import java.util.Objects;

public class HashMapCustom<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final Object DELETED = new Object();

    private static final class Entry<K, V> {
        final K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Object[] table;
    private int size;

    public HashMapCustom() {
        table = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void put(K key, V value) {
        Objects.requireNonNull(key, "key must not be null");

        // Сначала проверяем, существует ли ключ
        int existing = indexOf(key);

        if (existing != -1) {
            @SuppressWarnings("unchecked")
            Entry<K, V> entry = (Entry<K, V>) table[existing];
            entry.value = value;
            return;
        }

        // Учитываем только действующие элементы
        if (size + 1 > table.length * LOAD_FACTOR) {
            resize(table.length * 2);
        }

        int idx = findSlot(key);

        table[idx] = new Entry<>(key, value);
        size++;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        Objects.requireNonNull(key, "key must not be null");

        int idx = indexOf(key);

        if (idx == -1) {
            return null;
        }

        return ((Entry<K, V>) table[idx]).value;
    }

    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "key must not be null");
        return indexOf(key) != -1;
    }

    public boolean remove(K key) {
        Objects.requireNonNull(key, "key must not be null");

        int idx = indexOf(key);

        if (idx == -1) {
            return false;
        }

        table[idx] = DELETED;
        size--;

        return true;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return table.length;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), table.length);
    }

    private int findSlot(K key) {
        int idx = hash(key);
        int firstDeleted = -1;

        for (int probe = 0; probe < table.length; probe++) {
            Object o = table[idx];

            if (o == null) {
                return firstDeleted != -1 ? firstDeleted : idx;
            }

            if (o == DELETED && firstDeleted == -1) {
                firstDeleted = idx;
            }

            idx = (idx + 1) % table.length;
        }

        // Даже если нет null, можно занять DELETED
        if (firstDeleted != -1) {
            return firstDeleted;
        }

        throw new IllegalStateException("Hash table is full");
    }

    private int indexOf(K key) {
        int idx = hash(key);

        for (int probe = 0; probe < table.length; probe++) {
            Object o = table[idx];

            if (o == null) {
                return -1;
            }

            if (o != DELETED) {
                @SuppressWarnings("unchecked")
                Entry<K, V> e = (Entry<K, V>) o;

                if (e.key.equals(key)) {
                    return idx;
                }
            }

            idx = (idx + 1) % table.length;
        }

        return -1;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Object[] oldTable = table;

        table = new Object[newCapacity];
        size = 0;

        for (Object o : oldTable) {
            if (o != null && o != DELETED) {
                Entry<K, V> entry = (Entry<K, V>) o;
                int idx = findSlot(entry.key);

                table[idx] = new Entry<>(entry.key, entry.value);
                size++;
            }
        }
    }
}
