package ru.bmstu.lab02;

import java.util.Objects;

/** Хеш-таблица с открытой адресацией, линейным пробированием и маркером удаления. */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final double OCCUPIED_LIMIT = 0.875;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int occupied;

    /** Создаёт пустую таблицу. */
    public HashMapCustom() {
    }

    /**
     * Вставляет или обновляет значение. Ожидаемое амортизированное время O(1).
     *
     * @throws NullPointerException если ключ равен null
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);
        int slot = locate(key);
        if (isEmptySlot(slot)) {
            int limit = (int) (keys.length * LOAD_FACTOR);
            if (size + 1 > limit) {
                rebuild(Math.multiplyExact(keys.length, 2));
                slot = locate(key);
            } else if (keys[slot] == null && occupied + 1 > (int) (keys.length * OCCUPIED_LIMIT)) {
                // Удалённые ячейки удлиняют поиск. Очистка сохраняет текущую ёмкость.
                rebuild(keys.length);
                slot = locate(key);
            }
            insert(slot, key, value);
        } else {
            values[slot] = value;
        }
    }

    /**
     * Возвращает значение или null при отсутствии ключа. Ожидаемое время O(1).
     *
     * @throws NullPointerException если ключ равен null
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int slot = locate(Objects.requireNonNull(key));
        return isEmptySlot(slot) ? null : (V) values[slot];
    }

    /**
     * Проверяет наличие ключа, включая ключи со значением null. Ожидаемое время O(1).
     *
     * @throws NullPointerException если ключ равен null
     */
    public boolean containsKey(K key) {
        return !isEmptySlot(locate(Objects.requireNonNull(key)));
    }

    /**
     * Удаляет ключ и возвращает признак успеха. Ожидаемое время O(1).
     *
     * @throws NullPointerException если ключ равен null
     */
    public boolean remove(K key) {
        int slot = locate(Objects.requireNonNull(key));
        if (isEmptySlot(slot)) {
            return false;
        }
        keys[slot] = DELETED;
        values[slot] = null;
        size--;
        return true;
    }

    /** Возвращает число пар за O(1). */
    public int size() {
        return size;
    }

    /** Возвращает число ячеек за O(1). */
    public int capacity() {
        return keys.length;
    }

    private int locate(Object key) {
        // Преобразование к long позволяет взять модуль Integer.MIN_VALUE.
        int slot = (int) (Math.abs((long) key.hashCode()) % keys.length);
        int firstDeleted = -1;
        // Доля занятых и удалённых ячеек не превышает OCCUPIED_LIMIT, поэтому null встретится.
        while (keys[slot] != null) {
            Object storedKey = keys[slot];
            if (storedKey == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = slot;
                }
            } else if (key.equals(storedKey)) {
                return slot;
            }
            slot = (slot + 1) % keys.length;
        }
        // Продолжаем искать за удалённой ячейкой, чтобы не создать дубликат ключа.
        return firstDeleted < 0 ? slot : firstDeleted;
    }

    private boolean isEmptySlot(int slot) {
        return keys[slot] == null || keys[slot] == DELETED;
    }

    private void insert(int slot, Object key, Object value) {
        if (keys[slot] == null) {
            occupied++;
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    private void rebuild(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        Object[] newKeys = new Object[newCapacity];
        Object[] newValues = new Object[newCapacity];
        keys = newKeys;
        values = newValues;
        size = 0;
        occupied = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            Object key = oldKeys[index];
            if (key != null && key != DELETED) {
                insert(locate(key), key, oldValues[index]);
            }
        }
    }
}
