package ru.bmstu.lab02;

import java.util.Objects;

/** Хеш-таблица с линейным пробированием и маркером удалённой ячейки. */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final int LIVE_FREE_DIVISOR = 4;
    private static final int USED_FREE_DIVISOR = 8;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int used;

    /** Вставляет или обновляет запись. Ключ должен быть ненулевым, значение может быть null. */
    public void put(K key, V value) {
        int slot = search(key);
        if (slot >= 0) {
            values[slot] = value;
            return;
        }
        if (size == loadLimit()) {
            rehash(Math.multiplyExact(keys.length, 2));
            slot = search(key);
        } else if (used == keys.length - keys.length / USED_FREE_DIVISOR && keys[~slot] == null) {
            // При частых удалениях пустые слоты не должны исчезать из-за маркеров.
            rehash(keys.length);
            slot = search(key);
        }
        insertAt(~slot, key, value);
    }

    /** Возвращает значение по ключу или null, если ключа нет. */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int slot = search(key);
        return slot < 0 ? null : (V) values[slot];
    }

    /** Проверяет наличие ключа, в том числе со значением null. */
    public boolean containsKey(K key) {
        return search(key) >= 0;
    }

    /** Удаляет запись и возвращает true, если ключ существовал. */
    public boolean remove(K key) {
        int slot = search(key);
        if (slot < 0) {
            return false;
        }
        keys[slot] = DELETED;
        values[slot] = null;
        size--;
        return true;
    }

    /** Возвращает число живых записей. */
    public int size() {
        return size;
    }

    /** Возвращает число ячеек таблицы. */
    public int capacity() {
        return keys.length;
    }

    private int search(Object key) {
        Objects.requireNonNull(key, "key");
        int index = (int) (Math.abs((long) key.hashCode()) % keys.length);
        int deleted = -1;
        while (keys[index] != null) {
            Object current = keys[index];
            if (current == DELETED) {
                if (deleted < 0) {
                    deleted = index;
                }
            } else if (key.equals(current)) {
                return index;
            }
            index = (index + 1) % keys.length;
        }
        // Не прерываем поиск на DELETED: обновляемый ключ может быть дальше в цепочке.
        return ~(deleted < 0 ? index : deleted);
    }

    private int loadLimit() {
        return keys.length - keys.length / LIVE_FREE_DIVISOR;
    }

    private void insertAt(int slot, Object key, Object value) {
        if (keys[slot] == null) {
            used++;
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    private void rehash(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        size = 0;
        used = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            Object key = oldKeys[i];
            if (key != null && key != DELETED) {
                insertAt(~search(key), key, oldValues[i]);
            }
        }
    }
}
