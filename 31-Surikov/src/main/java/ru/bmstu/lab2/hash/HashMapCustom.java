package ru.bmstu.lab2.hash;

import java.util.Objects;

/** задача 2A — хеш-таблица с линейным пробированием и маркером удаления */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_LIMIT = 0.75;
    private static final double OCCUPIED_LIMIT = 0.875;
    private static final Object DELETED = new Object();
    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int occupied;

    /** вставляет или обновляет значение, ключ должен отличаться от null */
    public void put(K key, V value) {
        int slot = find(key);
        if (slot < 0) {
            if (size + 1 > capacity() * LOAD_LIMIT) {
                rehash(Math.multiplyExact(capacity(), 2));
            } else if (keys[~slot] == null && occupied + 1 > capacity() * OCCUPIED_LIMIT) {
                rehash(capacity());
            } else {
                insert(~slot, key, value);
                return;
            }
            insert(~find(key), key, value);
        } else {
            values[slot] = value;
        }
    }

    /** возвращает значение или null при отсутствии ключа */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int slot = find(key);
        return slot < 0 ? null : (V) values[slot];
    }

    /** проверяет наличие ключа независимо от значения */
    public boolean containsKey(K key) {
        return find(key) >= 0;
    }

    /** удаляет запись и сообщает, была ли она найдена */
    public boolean remove(K key) {
        int slot = find(key);
        if (slot < 0) {
            return false;
        }
        keys[slot] = DELETED;
        values[slot] = null;
        size--;
        return true;
    }

    /** возвращает число живых записей */
    public int size() {
        return size;
    }

    /** возвращает число ячеек */
    public int capacity() {
        return keys.length;
    }

    private int find(Object key) {
        int slot = (int) (Math.abs((long) Objects.requireNonNull(key).hashCode()) % capacity());
        int deleted = -1;
        while (keys[slot] != null) {
            if (keys[slot] == DELETED) {
                if (deleted < 0) {
                    deleted = slot;
                }
            } else if (key.equals(keys[slot])) {
                return slot;
            }
            slot = (slot + 1) % capacity();
        }
        // отрицательный результат кодирует место вставки, включая ячейку с индексом ноль
        return ~(deleted < 0 ? slot : deleted);
    }

    private void insert(int slot, Object key, Object value) {
        if (keys[slot] == null) {
            occupied++;
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    private void rehash(int capacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[capacity];
        values = new Object[capacity];
        occupied = 0;
        size = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                insert(~find(oldKeys[i]), oldKeys[i], oldValues[i]);
            }
        }
    }
}
