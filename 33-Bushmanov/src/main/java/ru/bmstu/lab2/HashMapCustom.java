package ru.bmstu.lab2;

import java.util.Objects;

/**
 * Хеш-таблица с линейным пробированием и маркерами удалённых ячеек.
 * Null-ключи запрещены, null-значения разрешены.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double MAX_LOAD = 0.75;
    private static final byte EMPTY = 0;
    private static final byte OCCUPIED = 1;
    private static final byte DELETED = 2;

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private byte[] states = new byte[INITIAL_CAPACITY];
    private int size;

    /** Создаёт пустую таблицу ёмкостью 16 ячеек. */
    public HashMapCustom() {
    }

    /**
     * Вставляет или обновляет значение. Ожидаемое амортизированное время O(1).
     *
     * @param key ключ, отличный от null
     * @param value значение, в том числе null
     * @throws NullPointerException если ключ равен null
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "Ключ не должен быть null");
        int index = findIndex(key);
        if (index >= 0) {
            values[index] = value;
            return;
        }
        if (size + 1 > capacity() * MAX_LOAD) {
            resize();
        }
        insertNew(key, value);
    }

    /**
     * Ищет значение. Ожидаемое время O(1), худшее O(capacity).
     *
     * @param key искомый ключ, отличный от null
     * @return значение или null, если ключ отсутствует
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int index = findIndex(key);
        return index < 0 ? null : (V) values[index];
    }

    /**
     * Проверяет наличие ключа независимо от его значения.
     *
     * @param key ключ, отличный от null
     * @return true, если ключ найден; ожидаемое время O(1), худшее O(capacity)
     */
    public boolean containsKey(K key) {
        return findIndex(key) >= 0;
    }

    /**
     * Удаляет запись. Ожидаемое время O(1), худшее O(capacity).
     *
     * @param key удаляемый ключ, отличный от null
     * @return true, если запись существовала
     */
    public boolean remove(K key) {
        int index = findIndex(key);
        if (index < 0) {
            return false;
        }
        states[index] = DELETED; // EMPTY оборвал бы поиск следующих ключей с тем же хешем.
        keys[index] = null;
        values[index] = null;
        size--;
        return true;
    }

    /**
     * Возвращает размер таблицы за O(1).
     *
     * @return число записей
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает ёмкость таблицы за O(1).
     *
     * @return число ячеек массива
     */
    public int capacity() {
        return keys.length;
    }

    private int indexFor(Object key) {
        // Приведение до abs защищает от отрицательного Math.abs(Integer.MIN_VALUE).
        return (int) (Math.abs((long) key.hashCode()) % capacity());
    }

    private int findIndex(Object key) {
        Objects.requireNonNull(key, "Ключ не должен быть null");
        int index = indexFor(key);
        for (int checked = 0; checked < capacity(); checked++) {
            if (states[index] == EMPTY) {
                return -1;
            }
            if (states[index] == OCCUPIED && keys[index].equals(key)) {
                return index;
            }
            index = (index + 1) % capacity();
        }
        return -1; // Ограничение цикла важно, если все ячейки когда-либо использовались.
    }

    private void insertNew(Object key, Object value) {
        int index = indexFor(key);
        while (states[index] == OCCUPIED) {
            index = (index + 1) % capacity();
        }
        keys[index] = key;
        values[index] = value;
        states[index] = OCCUPIED;
        size++;
    }

    private void resize() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        byte[] oldStates = states;
        int newCapacity = capacity() * 2;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        states = new byte[newCapacity];
        size = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            if (oldStates[index] == OCCUPIED) {
                insertNew(oldKeys[index], oldValues[index]);
            }
        }
    }
}
