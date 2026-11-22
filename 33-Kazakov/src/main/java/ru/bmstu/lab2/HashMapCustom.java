package ru.bmstu.lab2;

import java.util.Objects;

/**
 * Задача 2A — хеш-таблица с линейным пробированием и маркером удалённой ячейки
 *
 * @param <K> тип ключей, ключи null не поддерживаются
 * @param <V> тип значений, значения null допускаются
 */
public final class HashMapCustom<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final int LIVE_SLOT_DIVISOR = 4;
    private static final int USED_SLOT_DIVISOR = 8;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int usedSlots;

    /**
     * Вставляет пару или обновляет значение существующего ключа
     *
     * @param key ключ
     * @param value значение
     * @throws NullPointerException если ключ равен null
     */
    public void put(K key, V value) {
        int slot = findSlot(key);
        if (slot >= 0) {
            values[slot] = value;
            return;
        }

        int capacity = keys.length;
        if (size >= capacity - capacity / LIVE_SLOT_DIVISOR) {
            rehash(Math.multiplyExact(capacity, 2));
            slot = findSlot(key);
        } else if (keys[~slot] == null && usedSlots >= capacity - capacity / USED_SLOT_DIVISOR) {
            // Очищаем маркеры только после накопления заметной доли удалённых ячеек
            rehash(capacity);
            slot = findSlot(key);
        }
        insertAt(~slot, key, value);
    }

    /**
     * Возвращает значение ключа или null при его отсутствии
     *
     * @param key искомый ключ
     * @return значение ключа или null
     * @throws NullPointerException если ключ равен null
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int slot = findSlot(key);
        return slot >= 0 ? (V) values[slot] : null;
    }

    /**
     * Проверяет наличие ключа, в том числе если его значение равно null
     *
     * @param key искомый ключ
     * @return true, если ключ хранится в таблице
     * @throws NullPointerException если ключ равен null
     */
    public boolean containsKey(K key) {
        return findSlot(key) >= 0;
    }

    /**
     * Удаляет ключ, сохраняя цепочку пробирования
     *
     * @param key удаляемый ключ
     * @return true, если ключ существовал и был удалён
     * @throws NullPointerException если ключ равен null
     */
    public boolean remove(K key) {
        int slot = findSlot(key);
        if (slot < 0) {
            return false;
        }
        keys[slot] = DELETED;
        values[slot] = null;
        size--;
        return true;
    }

    /**
     * Возвращает количество пар за O(1)
     *
     * @return размер таблицы
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает количество ячеек за O(1)
     *
     * @return ёмкость таблицы
     */
    public int capacity() {
        return keys.length;
    }

    // Для отсутствующего ключа возвращаем побитовое отрицание индекса для вставки
    private int findSlot(Object key) {
        int hash = Objects.requireNonNull(key).hashCode();
        int index = (int) (Math.abs((long) hash) % keys.length);
        int firstDeleted = -1;

        while (keys[index] != null) {
            Object storedKey = keys[index];
            if (storedKey == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = index;
                }
            } else if (key.equals(storedKey)) {
                return index;
            }
            index = (index + 1) % keys.length;
        }

        return ~(firstDeleted >= 0 ? firstDeleted : index);
    }

    private void insertAt(int index, Object key, Object value) {
        if (keys[index] == null) {
            usedSlots++;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    private void rehash(int capacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[capacity];
        values = new Object[capacity];
        size = 0;
        usedSlots = 0;

        for (int index = 0; index < oldKeys.length; index++) {
            Object key = oldKeys[index];
            if (key != null && key != DELETED) {
                insertAt(~findSlot(key), key, oldValues[index]);
            }
        }
    }
}
