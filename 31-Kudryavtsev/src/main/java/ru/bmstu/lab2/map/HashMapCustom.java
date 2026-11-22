package ru.bmstu.lab2.map;

import java.util.Objects;

/** Задача 2A: хеш-таблица с линейным пробированием. Null-ключи запрещены, null-значения разрешены. */
public class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;

    /** Создаёт пустую таблицу ёмкостью 16. */
    public HashMapCustom() {
    }

    /**
     * Вставляет или обновляет значение. При хорошем распределении и коротких цепочках ожидаемо O(1)
     * амортизированно; худшее O(m²) при перехешировании коллизий, m — ёмкость.
     */
    public void put(K key, V value) {
        int existing = findIndex(key);
        if (existing >= 0) {
            values[existing] = value;
            return;
        }
        if (size + 1 > keys.length * 3 / 4) {
            resize();
        }
        insertNew(key, value);
    }

    /** Возвращает значение или null: ожидаемо O(1) для коротких цепочек, худшее O(capacity). */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int index = findIndex(key);
        return index < 0 ? null : (V) values[index];
    }

    /** Проверяет наличие ключа, в том числе с null-значением; ожидаемо O(1), худшее O(capacity). */
    public boolean containsKey(K key) {
        return findIndex(key) >= 0;
    }

    /** Удаляет ключ, сохраняя цепочку пробирования; ожидаемо O(1), худшее O(capacity). */
    public boolean remove(K key) {
        int index = findIndex(key);
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /** Возвращает число записей за O(1). */
    public int size() {
        return size;
    }

    /** Возвращает ёмкость за O(1). */
    public int capacity() {
        return keys.length;
    }

    private int startIndex(Object key) {
        Objects.requireNonNull(key, "key");
        // Приведение к long до abs исправляет переполнение для Integer.MIN_VALUE.
        return (int) (Math.abs((long) key.hashCode()) % keys.length);
    }

    private int findIndex(Object key) {
        int index = startIndex(key);
        // Даже если все ячейки заняты маркерами DELETED, поиск завершится за capacity шагов.
        for (int probes = 0; probes < keys.length; probes++) {
            if (keys[index] == null) {
                return -1;
            }
            if (keys[index] != DELETED && keys[index].equals(key)) {
                return index;
            }
            index = (index + 1) % keys.length;
        }
        return -1;
    }

    private void insertNew(Object key, Object value) {
        int index = startIndex(key);
        while (keys[index] != null && keys[index] != DELETED) {
            index = (index + 1) % keys.length;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    private void resize() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[oldKeys.length * 2];
        values = new Object[keys.length];
        size = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            if (oldKeys[index] != null && oldKeys[index] != DELETED) {
                insertNew(oldKeys[index], oldValues[index]);
            }
        }
    }
}
