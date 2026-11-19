package ru.bmstu.lab02.task_02a;

public class HashMapCustom<K, V> {

    private static final int START_CAPACITY = 16;
    private static final double MAX_LOAD = 0.75;
    private static final Object DELETED = new Object();

    private Object[] keySlots;
    private Object[] valueSlots;
    private int size;
    private int usedSlots;

    public HashMapCustom() {
        this(START_CAPACITY);
    }

    public HashMapCustom(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Ёмкость должна быть больше нуля");
        }
        keySlots = new Object[capacity];
        valueSlots = new Object[capacity];
        size = 0;
        usedSlots = 0;
    }

    public void put(K key, V value) {
        requireKey(key);
        int slot = locate(key);
        if (slot >= 0) {
            valueSlots[slot] = value;
            return;
        }
        slot = freeSlot(key);
        if (keySlots[slot] == null) {
            usedSlots++;
        }
        keySlots[slot] = key;
        valueSlots[slot] = value;
        size++;
        if (usedSlots > keySlots.length * MAX_LOAD) {
            rebuild();
        }
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        requireKey(key);
        int slot = locate(key);
        if (slot < 0) {
            return null;
        }
        return (V) valueSlots[slot];
    }

    public boolean containsKey(K key) {
        requireKey(key);
        return locate(key) >= 0;
    }

    public boolean remove(K key) {
        requireKey(key);
        int slot = locate(key);
        if (slot < 0) {
            return false;
        }
        keySlots[slot] = DELETED;
        valueSlots[slot] = null;
        size--;
        return true;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return keySlots.length;
    }

    private int indexFor(Object key) {
        int index = Math.abs(key.hashCode()) % keySlots.length;
        return index < 0 ? -index : index;
    }

    private int next(int index) {
        return (index + 1) % keySlots.length;
    }

    private int locate(Object key) {
        int index = indexFor(key);
        while (keySlots[index] != null) {
            if (keySlots[index] != DELETED && keySlots[index].equals(key)) {
                return index;
            }
            index = next(index);
        }
        return -1;
    }

    private int freeSlot(Object key) {
        int index = indexFor(key);
        while (keySlots[index] != null && keySlots[index] != DELETED) {
            index = next(index);
        }
        return index;
    }

    private void rebuild() {
        int tombstones = usedSlots - size;
        int newCapacity = tombstones > size ? keySlots.length : keySlots.length * 2;
        Object[] oldKeys = keySlots;
        Object[] oldValues = valueSlots;
        keySlots = new Object[newCapacity];
        valueSlots = new Object[newCapacity];
        usedSlots = size;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                int slot = freeSlot(oldKeys[i]);
                keySlots[slot] = oldKeys[i];
                valueSlots[slot] = oldValues[i];
            }
        }
    }

    private void requireKey(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }
    }
}
