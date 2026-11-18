
package ru.bmstu.lab2;

import java.util.Objects;

/**
 * Хеш-таблица с линейным пробированием.
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
    private int usedSlots;

    /**
     * Создаёт пустую хеш-таблицу.
     */
    public HashMapCustom() {
        initialize(INITIAL_CAPACITY);
    }

    /**
     * Добавляет пару или обновляет значение существующего ключа.
     *
     * @param key ключ
     * @param value значение
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "Ключ не может быть null");

        int existing = findIndex(key);
        if (existing >= 0) {
            values[existing] = value;
            return;
        }

        if ((usedSlots + 1.0) / capacity() > MAX_LOAD_FACTOR) {
            resize(capacity() * 2);
        }

        insertNew(key, value);
    }

    /**
     * Возвращает значение по ключу или null.
     *
     * @param key ключ
     * @return значение либо null
     */
    public V get(K key) {
        Objects.requireNonNull(key, "Ключ не может быть null");

        int index = findIndex(key);
        return index < 0 ? null : valueAt(index);
    }

    /**
     * Проверяет наличие ключа.
     *
     * @param key ключ
     * @return true, если ключ найден
     */
    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "Ключ не может быть null");
        return findIndex(key) >= 0;
    }

    /**
     * Удаляет ключ из таблицы.
     *
     * @param key удаляемый ключ
     * @return true, если ключ был найден
     */
    public boolean remove(K key) {
        Objects.requireNonNull(key, "Ключ не может быть null");

        int index = findIndex(key);
        if (index < 0) {
            return false;
        }

        keys[index] = null;
        values[index] = null;
        states[index] = DELETED;
        size--;
        return true;
    }

    /**
     * Возвращает количество пар ключ-значение.
     *
     * @return количество пар
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает вместимость внутреннего массива.
     *
     * @return вместимость
     */
    public int capacity() {
        return keys.length;
    }

    private void initialize(int capacity) {
        keys = new Object[capacity];
        values = new Object[capacity];
        states = new byte[capacity];
        size = 0;
        usedSlots = 0;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity();
    }

    private int findIndex(K key) {
        int start = hash(key);

        for (int i = 0; i < capacity(); i++) {
            int index = (start + i) % capacity();

            if (states[index] == EMPTY) {
                return -1;
            }

            if (states[index] == OCCUPIED && keys[index].equals(key)) {
                return index;
            }
        }

        return -1;
    }

    private void insertNew(K key, V value) {
        int start = hash(key);
        int firstDeleted = -1;

        for (int i = 0; i < capacity(); i++) {
            int index = (start + i) % capacity();

            if (states[index] == DELETED && firstDeleted < 0) {
                firstDeleted = index;
            }

            if (states[index] == EMPTY) {
                int target = firstDeleted >= 0 ? firstDeleted : index;
                occupy(target, key, value);
                return;
            }
        }

        if (firstDeleted >= 0) {
            occupy(firstDeleted, key, value);
            return;
        }

        throw new IllegalStateException("В таблице нет свободных ячеек");
    }

    private void occupy(int index, K key, V value) {
        if (states[index] == EMPTY) {
            usedSlots++;
        }

        keys[index] = key;
        values[index] = value;
        states[index] = OCCUPIED;
        size++;
    }

    private void resize(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        byte[] oldStates = states;

        initialize(newCapacity);

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldStates[i] == OCCUPIED) {
                insertNew(keyAt(oldKeys, i), valueAt(oldValues, i));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private K keyAt(Object[] array, int index) {
        return (K) array[index];
    }

    @SuppressWarnings("unchecked")
    private V valueAt(Object[] array, int index) {
        return (V) array[index];
    }

    private V valueAt(int index) {
        return valueAt(values, index);
    }
}
