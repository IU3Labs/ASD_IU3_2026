package ru.bmstu.lab2;

/**
 * Хеш-таблица с линейным пробированием.
 */
public class HashMapCustom<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final double USED_LIMIT = 0.875;

    private static final byte EMPTY = 0;
    private static final byte OCCUPIED = 1;
    private static final byte DELETED = 2;

    private Object[] keys;
    private Object[] values;
    private byte[] states;
    private int size;
    private int used;

    /**
     * Создаёт пустую хеш-таблицу.
     */
    public HashMapCustom() {
        keys = new Object[DEFAULT_CAPACITY];
        values = new Object[DEFAULT_CAPACITY];
        states = new byte[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Возвращает количество элементов.
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает вместимость таблицы.
     */
    public int capacity() {
        return keys.length;
    }

    private int hash(K key) {
        return (int) (Math.abs((long) key.hashCode()) % capacity());
    }

    /**
     * Добавляет или обновляет значение по ключу.
     */
    public void put(K key, V value) {
        int index = findIndex(key);

        if (index != -1) {
            values[index] = value;
            return;
        }

        if (size + 1 > capacity() * LOAD_FACTOR) {
            resize(capacity() * 2);
        } else if (used + 1 > capacity() * USED_LIMIT) {
            resize(capacity());
        }

        insertNew(key, value);
    }

    // Поиск ключа линейным пробированием.
    private int findIndex(K key) {
        int index = hash(key);

        for (int i = 0; i < capacity(); i++) {
            if (states[index] == EMPTY) {
                return -1;
            }

            if (states[index] == OCCUPIED && keys[index].equals(key)) {
                return index;
            }

            index = (index + 1) % capacity();
        }

        return -1;
    }

    private void insertNew(K key, V value) {
        int index = hash(key);

        while (states[index] == OCCUPIED) {
            index = (index + 1) % capacity();
        }

        if (states[index] == EMPTY) {
            used++;
        }

        keys[index] = key;
        values[index] = value;
        states[index] = OCCUPIED;
        size++;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        byte[] oldStates = states;

        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        states = new byte[newCapacity];
        size = 0;
        used = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldStates[i] == OCCUPIED) {
                insertNew((K) oldKeys[i], (V) oldValues[i]);
            }
        }
    }

    /**
     * Возвращает значение по ключу или null.
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int index = findIndex(key);

        if (index == -1) {
            return null;
        }

        return (V) values[index];
    }

    /**
     * Проверяет наличие ключа.
     */
    public boolean containsKey(K key) {
        return findIndex(key) != -1;
    }

    /**
     * Удаляет элемент по ключу.
     */
    public boolean remove(K key) {
        int index = findIndex(key);

        if (index == -1) {
            return false;
        }

        keys[index] = null;
        values[index] = null;
        states[index] = DELETED;
        size--;

        return true;
    }
}