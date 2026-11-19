package ru.bmstu.lab02;

import java.util.Objects;

/**
 * Хеш-таблица с линейным пробированием. Ключи ненулевые, значения могут быть null.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final Object DELETED = new Object();
    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;

    /**
     * Вставляет пару или обновляет значение существующего ключа.
     *
     * @param key ключ для вставки или обновления
     * @param value сохраняемое значение, может быть {@code null}
     * @throws NullPointerException если ключ равен {@code null}
     */
    public void put(K key, V value) {
        int slot = locate(key);
        if (keys[slot] != null && keys[slot] != DELETED) {
            values[slot] = value;
            return;
        }
        if (size + 1 > capacity() - capacity() / 4) {
            grow();
            slot = locate(key);
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    /**
     * Возвращает значение или null, если ключ отсутствует.
     *
     * @param key искомый ключ
     * @return значение по ключу или {@code null}, если ключ отсутствует
     * @throws NullPointerException если ключ равен {@code null}
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        return (V) values[locate(key)];
    }

    /**
     * Проверяет наличие ключа, включая пары со значением null.
     *
     * @param key проверяемый ключ
     * @return {@code true}, если ключ есть в таблице
     * @throws NullPointerException если ключ равен {@code null}
     */
    public boolean containsKey(K key) {
        Object stored = keys[locate(key)];
        return stored != null && stored != DELETED;
    }

    /**
     * Удаляет пару и сообщает, существовала ли она.
     *
     * @param key ключ удаляемой пары
     * @return {@code true}, если пара была удалена
     * @throws NullPointerException если ключ равен {@code null}
     */
    public boolean remove(K key) {
        int slot = locate(key);
        if (keys[slot] == null || keys[slot] == DELETED) {
            return false;
        }
        keys[slot] = DELETED;
        values[slot] = null;
        size--;
        return true;
    }

    /**
     * Возвращает количество активных пар.
     *
     * @return текущее число пар ключ–значение
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает количество ячеек массива.
     *
     * @return число ячеек массива
     */
    public int capacity() {
        return keys.length;
    }

    // Возвращает ячейку ключа либо место вставки. После DELETED ключ может встретиться дальше.
    private int locate(Object key) {
        int slot = (int) (Math.abs((long) Objects.requireNonNull(key).hashCode()) % capacity());
        int firstDeleted = -1;
        for (int visited = 0; visited < capacity(); visited++) {
            Object current = keys[slot];
            if (current == null) {
                return firstDeleted >= 0 ? firstDeleted : slot;
            }
            if (current == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = slot;
                }
            } else if (key.equals(current)) {
                return slot;
            }
            slot = (slot + 1) % capacity();
        }
        return firstDeleted;
    }

    private void grow() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[Math.multiplyExact(capacity(), 2)];
        values = new Object[keys.length];
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                int slot = locate(oldKeys[i]);
                keys[slot] = oldKeys[i];
                values[slot] = oldValues[i];
            }
        }
    }
}
