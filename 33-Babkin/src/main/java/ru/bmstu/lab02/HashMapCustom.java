package ru.bmstu.lab02;

import java.util.Objects;

/** A hash table with linear probing and a maximum load factor of 0.75. */
public final class HashMapCustom<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int occupied;

    /**
     * Inserts or replaces a mapping. Null values are supported.
     *
     * @throws NullPointerException if key is null
     */
    public void put(K key, V value) {
        int slot = findSlot(key);
        if (slot >= 0) {
            values[slot] = value;
            return;
        }
        int index = ~slot;
        if (size == threshold()) {
            rebuild(Math.multiplyExact(capacity(), 2));
            index = ~findSlot(key);
        } else if (occupied == capacity() - capacity() / 8 && keys[index] == null) {
            // Reclaim tombstones before they make searches through missing keys more expensive.
            rebuild(capacity());
            index = ~findSlot(key);
        }
        insertAt(index, key, value);
    }

    /**
     * Returns the value for a key, or null if the key is absent.
     *
     * @throws NullPointerException if key is null
     */
    public V get(K key) {
        int slot = findSlot(key);
        return slot < 0 ? null : valueAt(slot);
    }

    /**
     * Returns whether the key is present, including when it maps to null.
     *
     * @throws NullPointerException if key is null
     */
    public boolean containsKey(K key) {
        return findSlot(key) >= 0;
    }

    /**
     * Removes the mapping and returns whether the key was present.
     *
     * @throws NullPointerException if key is null
     */
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

    /** Returns the number of live mappings. */
    public int size() {
        return size;
    }

    /** Returns the number of slots in the backing table. */
    public int capacity() {
        return keys.length;
    }

    private int threshold() {
        return capacity() - capacity() / 4;
    }

    // A match returns its index. A miss returns the complemented insertion index.
    private int findSlot(Object key) {
        int index = (int) (Math.abs((long) key.hashCode()) % capacity());
        int firstDeleted = -1;
        while (keys[index] != null) {
            if (keys[index] == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = index;
                }
            } else if (Objects.equals(key, keys[index])) {
                return index;
            }
            index = (index + 1) % capacity();
        }
        return ~(firstDeleted < 0 ? index : firstDeleted);
    }

    private void insertAt(int index, Object key, Object value) {
        if (keys[index] == null) {
            occupied++;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    private void rebuild(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        Object[] newKeys = new Object[newCapacity];
        Object[] newValues = new Object[newCapacity];
        keys = newKeys;
        values = newValues;
        size = 0;
        occupied = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            Object key = oldKeys[index];
            if (key != null && key != DELETED) {
                insertAt(~findSlot(key), key, oldValues[index]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private V valueAt(int index) {
        return (V) values[index];
    }
}
