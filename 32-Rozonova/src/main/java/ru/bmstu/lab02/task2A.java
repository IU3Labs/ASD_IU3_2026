package ru.bmstu.lab2;

import java.util.NoSuchElementException;

/**
 * Custom HashMap implementation using Open Addressing with Linear Probing.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashMapCustom<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("rawtypes")
    private static final Entry DELETED = new Entry(null, null);

    private Entry<K, V>[] table;
    private int size;         // Количество активных элементов
    private int deletedCount; // Количество яцек с маркером DELETED

    /**
     * Internal container class for key-value pairs.
     */
    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Constructs an empty hash map with default initial capacity.
     */
    @SuppressWarnings("unchecked")
    public HashMapCustom() {
        this.table = new Entry[DEFAULT_CAPACITY];
        this.size = 0;
        this.deletedCount = 0;
    }

    /**
     * Returns the number of key-value mappings in this map.
     * Time complexity: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Returns the current capacity of the internal table.
     * Time complexity: O(1)
     */
    public int capacity() {
        return table.length;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * Time complexity: O(1)
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * Time complexity: O(1) on average, O(n) in worst case (rehashing / full clusters)
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not supported");
        }

        // Проверяем необходимость увеличения емкости (учитываем и элементы, и удаленные слоты)
        if ((size + deletedCount + 1) >= table.length * LOAD_FACTOR) {
            resize();
        }

        int capacity = table.length;
        int hash = (key.hashCode() & 0x7fffffff) % capacity;
        int index = hash;
        int firstDeleted = -1;

        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[index];

            if (entry == null) {
                // Нашли пустую ячейку — можем вставлять
                int targetIndex = (firstDeleted != -1) ? firstDeleted : index;
                table[targetIndex] = new Entry<>(key, value);
                size++;
                if (firstDeleted != -1) {
                    deletedCount--;
                }
                return;
            } else if (entry == DELETED) {
                // Запоминаем первую встреченную удаленную ячейку для возможного переиспользования
                if (firstDeleted == -1) {
                    firstDeleted = index;
                }
            } else if (entry.key.equals(key)) {
                // Ключ уже существует — обновляем значение
                entry.value = value;
                return;
            }

            index = (index + 1) % capacity; // Линейное пробирование
        }

        // Если прошли весь круг и нашли удаленную ячейку
        if (firstDeleted != -1) {
            table[firstDeleted] = new Entry<>(key, value);
            size++;
            deletedCount--;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null.
     * Time complexity: O(1) on average
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        if (key == null) {
            return null;
        }

        int capacity = table.length;
        int hash = (key.hashCode() & 0x7fffffff) % capacity;
        int index = hash;

        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[index];
            if (entry == null) {
                return null; // Цепочка прервана, элемента нет
            }
            if (entry != DELETED && entry.key.equals(key)) {
                return entry.value;
            }
            index = (index + 1) % capacity;
        }
        return null;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     * Time complexity: O(1) on average
     */
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }

        int capacity = table.length;
        int hash = (key.hashCode() & 0x7fffffff) % capacity;
        int index = hash;

        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[index];
            if (entry == null) {
                return false;
            }
            if (entry != DELETED && entry.key.equals(key)) {
                return true;
            }
            index = (index + 1) % capacity;
        }
        return false;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * Time complexity: O(1) on average
     */
    @SuppressWarnings("unchecked")
    public boolean remove(K key) {
        if (key == null) {
            return false;
        }

        int capacity = table.length;
        int hash = (key.hashCode() & 0x7fffffff) % capacity;
        int index = hash;

        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[index];
            if (entry == null) {
                return false;
            }
            if (entry != DELETED && entry.key.equals(key)) {
                table[index] = (Entry<K, V>) DELETED;
                size--;
                deletedCount++;
                return true;
            }
            index = (index + 1) % capacity;
        }
        return false;
    }

    /**
     * Doubles the capacity of the table and rehashes all active entries.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        int newCapacity = oldTable.length * 2;
        table = new Entry[newCapacity];
        size = 0;
        deletedCount = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null && entry != DELETED) {
                put(entry.key, entry.value);
            }
        }
    }
}