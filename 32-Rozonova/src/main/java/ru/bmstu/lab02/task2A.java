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