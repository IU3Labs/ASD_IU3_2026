package ru.bmstu.lab02;

import java.util.Objects;

/** Хеш-таблица с линейным пробированием и метками удалённых ячеек. Решает задачу 2A. */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int occupied;

    /** Создаёт пустую таблицу вместимостью 16 ячеек. Ключи должны быть ненулевыми. */
    public HashMapCustom() {}

    /** Связывает ключ со значением, в том числе null. Повторная запись обновляет значение. */
    public void put(K key, V value) {
        int slot = findSlot(key);
        if (slot >= 0) {
            values[slot] = value;
            return;
        }

        int previousCapacity = capacity();
        if (size == previousCapacity - previousCapacity / 4) {
            rehash(Math.multiplyExact(previousCapacity, 2));
            slot = findSlot(key);
        } else if (keys[~slot] == null && occupied == previousCapacity - previousCapacity / 8) {
            // При заполнении 7/8 ячеек хотя бы 1/8 занята метками удаления.
            rehash(previousCapacity);
            slot = findSlot(key);
        }

        insertAt(~slot, key, value);
        size++;
    }

    /** Возвращает значение ключа или null для отсутствующего ключа. */
    public V get(K key) {
        int slot = findSlot(key);
        return slot >= 0 ? valueAt(slot) : null;
    }

    /** Проверяет наличие ключа, в том числе если за ним закреплено значение null. */
    public boolean containsKey(K key) {
        return findSlot(key) >= 0;
    }

    /** Удаляет ключ и его значение. Возвращает true, если ключ присутствовал. */
    public boolean remove(K key) {
        int slot = findSlot(key);
        if (slot < 0) {
            return false;
        }
        keys[slot] = DELETED;
        values[slot] = null;
        size--;
        return true;
    }

    /** Возвращает количество пар за O(1). */
    public int size() {
        return size;
    }

    /** Возвращает количество ячеек за O(1). */
    public int capacity() {
        return keys.length;
    }

    // Для отсутствующего ключа возвращает побитовое дополнение индекса вставки.
    private int findSlot(Object key) {
        int hash = Objects.requireNonNull(key, "key").hashCode();
        int index = (int) (Math.abs((long) hash) % keys.length);
        int firstDeleted = -1;

        // В таблице остаётся хотя бы одна ячейка без ключа и без метки удаления.
        while (keys[index] != null) {
            Object storedKey = keys[index];
            if (storedKey == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = index;
                }
            } else if (storedKey.equals(key)) {
                return index;
            }
            index++;
            if (index == keys.length) {
                index = 0;
            }
        }
        return ~(firstDeleted < 0 ? index : firstDeleted);
    }

    private void rehash(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        Object[] newKeys = new Object[newCapacity];
        Object[] newValues = new Object[newCapacity];
        keys = newKeys;
        values = newValues;
        occupied = 0;

        for (int index = 0; index < oldKeys.length; index++) {
            Object key = oldKeys[index];
            if (key != null && key != DELETED) {
                insertAt(~findSlot(key), key, oldValues[index]);
            }
        }
    }

    private void insertAt(int index, Object key, Object value) {
        if (keys[index] == null) {
            occupied++;
        }
        keys[index] = key;
        values[index] = value;
    }

    @SuppressWarnings("unchecked")
    private V valueAt(int index) {
        return (V) values[index];
    }
}
