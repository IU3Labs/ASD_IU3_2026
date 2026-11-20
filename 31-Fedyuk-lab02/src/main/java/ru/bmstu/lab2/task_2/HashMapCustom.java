/*
 * ЛР2, задача 2A. Хеш-таблица с линейным пробированием и маркером DELETED.
 * Порог загрузки 0.75, удвоение ёмкости с перехешированием.
 */
package ru.bmstu.lab2.task_2;

import java.util.Objects;

/** Собственная хеш-таблица на двух массивах; null-ключи запрещены, null-значения допустимы. */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;
    private static final int LOAD_NUMERATOR = 3;
    private static final int LOAD_DENOMINATOR = 4;
    private static final int USED_LOAD_NUMERATOR = 7;
    private static final int USED_LOAD_DENOMINATOR = 8;
    private static final Object DELETED = new Object();
    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int usedSlots;

    /** Добавляет или обновляет пару; ожидаемо O(1) амортизированно при хорошем распределении. */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "Ключ не должен быть null");
        int existing = findKey(key);
        if (existing >= 0) {
            values[existing] = value;
            return;
        }
        ensureSpace();
        insertNew(key, value);
    }

    /** Возвращает значение или null; ожидаемо O(1), худшее время O(capacity). */
    public V get(K key) {
        int index = findKey(key);
        return index < 0 ? null : valueAt(index);
    }

    /** Проверяет присутствие ключа, в том числе со значением null. */
    public boolean containsKey(K key) {
        return findKey(key) >= 0;
    }

    /** Удаляет пару, сохраняя цепочку пробирования; возвращает признак удаления. */
    public boolean remove(K key) {
        int index = findKey(key);
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /** Возвращает число живых записей за O(1). */
    public int size() {
        return size;
    }

    /** Возвращает число ячеек каждого внутреннего массива за O(1). */
    public int capacity() {
        return keys.length;
    }

    private int findKey(K key) {
        Objects.requireNonNull(key, "Ключ не должен быть null");
        int index = indexFor(key);
        for (int probe = 0; probe < keys.length; probe++) {
            if (keys[index] == null) {
                return -1;
            }
            if (keys[index] != DELETED && keys[index].equals(key)) {
                return index;
            }
            index = nextIndex(index);
        }
        return -1;
    }

    private void insertNew(Object key, Object value) {
        int index = indexFor(key);
        while (keys[index] != null && keys[index] != DELETED) {
            index = nextIndex(index);
        }
        if (keys[index] == null) {
            usedSlots++;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    private void ensureSpace() {
        int threshold = keys.length / LOAD_DENOMINATOR * LOAD_NUMERATOR;
        if (size + 1 > threshold) {
            rehash(keys.length * GROWTH_FACTOR);
        } else if (usedSlots + 1 > keys.length / USED_LOAD_DENOMINATOR * USED_LOAD_NUMERATOR) {
            // Удалённые ячейки тоже удлиняют поиск: очищаем их без роста ёмкости.
            rehash(keys.length);
        }
    }

    private void rehash(int capacity) {
        Object[] previousKeys = keys;
        Object[] previousValues = values;
        keys = new Object[capacity];
        values = new Object[capacity];
        size = 0;
        usedSlots = 0;
        for (int index = 0; index < previousKeys.length; index++) {
            if (previousKeys[index] != null && previousKeys[index] != DELETED) {
                insertNew(previousKeys[index], previousValues[index]);
            }
        }
    }

    private int indexFor(Object key) {
        // Приведение до abs защищает от отрицательного результата для Integer.MIN_VALUE.
        return (int) (Math.abs((long) key.hashCode()) % keys.length);
    }

    private int nextIndex(int index) {
        return (index + 1) % keys.length;
    }

    @SuppressWarnings("unchecked")
    private V valueAt(int index) {
        return (V) values[index];
    }
}
