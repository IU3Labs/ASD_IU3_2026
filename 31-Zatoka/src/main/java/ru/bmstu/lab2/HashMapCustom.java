package ru.bmstu.lab2;

import java.util.Objects;

/**
 * Хеш-таблица с открытой адресацией и линейным пробированием.
 * Удалённые элементы отмечаются специальным маркером.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int usedSlots;

    /** Добавляет или обновляет значение, среднее O(1), худшее O(n). */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "Ключ не должен быть null");
        int existing = findIndex(key);
        if (existing >= 0) {
            values[existing] = value;
            return;
        }
        if ((usedSlots + 1.0) / keys.length > MAX_LOAD_FACTOR) {
            resize(keys.length * 2);
        }
        insertNew(key, value);
    }

    /** Возвращает значение по ключу или null, если его нет. */
    public V get(K key) {
        int index = findIndex(Objects.requireNonNull(key, "Ключ не должен быть null"));
        return index < 0 ? null : valueAt(values[index]);
    }

    /** Проверяет, присутствует ли ключ (даже если значение null). */
    public boolean containsKey(K key) {
        return findIndex(Objects.requireNonNull(key, "Ключ не должен быть null")) >= 0;
    }

    /** Удаляет ключ, сохраняя последовательность пробирования. */
    public boolean remove(K key) {
        int index = findIndex(Objects.requireNonNull(key, "Ключ не должен быть null"));
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /** Возвращает число пар ключ-значение. */
    public int size() {
        return size;
    }

    /** Возвращает текущую ёмкость массива. */
    public int capacity() {
        return keys.length;
    }

    private void insertNew(K key, V value) {
        int index = indexFor(key, keys.length);
        int firstDeleted = -1;
        for (int probes = 0; probes < keys.length; probes++) {
            Object stored = keys[index];
            if (stored == null) {
                int destination = firstDeleted >= 0 ? firstDeleted : index;
                if (firstDeleted < 0) {
                    usedSlots++;
                }
                keys[destination] = key;
                values[destination] = value;
                size++;
                return;
            }
            if (stored == DELETED && firstDeleted < 0) {
                firstDeleted = index;
            }
            index = (index + 1) % keys.length;
        }
        if (firstDeleted >= 0) {
            keys[firstDeleted] = key;
            values[firstDeleted] = value;
            size++;
            return;
        }
        throw new IllegalStateException("Таблица переполнена");
    }

    private int findIndex(K key) {
        int index = indexFor(key, keys.length);
        for (int probes = 0; probes < keys.length; probes++) {
            Object stored = keys[index];
            if (stored == null) {
                return -1;
            }
            if (stored != DELETED && stored.equals(key)) {
                return index;
            }
            index = (index + 1) % keys.length;
        }
        return -1;
    }

    private int indexFor(K key, int capacity) {
        int hash = key.hashCode();
        // Math.abs(Integer.MIN_VALUE) остаётся отрицательным: обрабатываем отдельно.
        if (hash == Integer.MIN_VALUE) {
            return (int) (Math.abs((long) hash) % capacity);
        }
        return Math.abs(hash) % capacity;
    }

    private void resize(int capacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[capacity];
        values = new Object[capacity];
        size = 0;
        usedSlots = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            if (oldKeys[index] != null && oldKeys[index] != DELETED) {
                insertNew(keyAt(oldKeys[index]), valueAt(oldValues[index]));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private K keyAt(Object key) {
        return (K) key;
    }

    @SuppressWarnings("unchecked")
    private V valueAt(Object value) {
        return (V) value;
    }
}
