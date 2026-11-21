
package ru.bmstu.lab2;

import java.util.Objects;

/**
 * Собственная хеш-таблица с открытой адресацией и линейным пробированием.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashMapCustom<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;

    private static final byte EMPTY = 0;
    private static final byte OCCUPIED = 1;
    private static final byte DELETED = 2;

    private Object[] keys;
    private Object[] values;
    private byte[] states;
    private int size;

    /** Создаёт пустую хеш-таблицу. */
    public HashMapCustom() {
        keys = new Object[INITIAL_CAPACITY];
        values = new Object[INITIAL_CAPACITY];
        states = new byte[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Добавляет или обновляет пару ключ-значение.
     *
     * @return предыдущее значение или null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "Ключ не может быть null");

        int existingIndex = findIndex(key);
        if (existingIndex >= 0) {
            V oldValue = valueAt(existingIndex);
            values[existingIndex] = value;
            return oldValue;
        }

        if ((size + 1) > capacity() * MAX_LOAD_FACTOR) {
    resize();
}

        int index = findInsertionIndex(key);
        keys[index] = key;
        values[index] = value;
        states[index] = OCCUPIED;
        size++;

        return null;
    }

    /**
     * Возвращает значение по ключу или null, если ключ отсутствует.
     */
    public V get(K key) {
        Objects.requireNonNull(key, "Ключ не может быть null");

        int index = findIndex(key);
        return index < 0 ? null : valueAt(index);
    }

    /**
     * Проверяет наличие ключа в таблице.
     */
    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "Ключ не может быть null");
        return findIndex(key) >= 0;
    }

    /**
     * Удаляет ключ и возвращает связанное с ним значение.
     */
    public V remove(K key) {
        Objects.requireNonNull(key, "Ключ не может быть null");

        int index = findIndex(key);
        if (index < 0) {
            return null;
        }

        V oldValue = valueAt(index);
        keys[index] = null;
        values[index] = null;
        states[index] = DELETED;
        size--;

        return oldValue;
    }

    /** Возвращает количество пар ключ-значение. */
    public int size() {
        return size;
    }

    /** Возвращает текущую вместимость таблицы. */
    public int capacity() {
        return keys.length;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity();
    }

    private int findIndex(K key) {
        int start = hash(key);

        for (int step = 0; step < capacity(); step++) {
            int index = (start + step) % capacity();

            if (states[index] == EMPTY) {
                return -1;
            }

            if (states[index] == OCCUPIED && keys[index].equals(key)) {
                return index;
            }
        }

        return -1;
    }

    private int findInsertionIndex(K key) {
        int start = hash(key);
        int firstDeleted = -1;

        for (int step = 0; step < capacity(); step++) {
            int index = (start + step) % capacity();

            if (states[index] == DELETED && firstDeleted < 0) {
                firstDeleted = index;
            }

            if (states[index] == EMPTY) {
                return firstDeleted >= 0 ? firstDeleted : index;
            }
        }

        if (firstDeleted >= 0) {
            return firstDeleted;
        }

        throw new IllegalStateException("Нет свободных ячеек");
    }

    private void resize() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        byte[] oldStates = states;

        keys = new Object[oldKeys.length * 2];
        values = new Object[oldValues.length * 2];
        states = new byte[oldStates.length * 2];
        size = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldStates[i] == OCCUPIED) {
                insertRehashed(oldKeys[i], oldValues[i]);
            }
        }
    }

    private void insertRehashed(Object key, Object value) {
        int start = (key.hashCode() & 0x7fffffff) % capacity();

        for (int step = 0; step < capacity(); step++) {
            int index = (start + step) % capacity();

            if (states[index] == EMPTY) {
                keys[index] = key;
                values[index] = value;
                states[index] = OCCUPIED;
                size++;
                return;
            }
        }

        throw new IllegalStateException("Ошибка повторного хеширования");
    }

    @SuppressWarnings("unchecked")
    private V valueAt(int index) {
        return (V) values[index];
    }
}
