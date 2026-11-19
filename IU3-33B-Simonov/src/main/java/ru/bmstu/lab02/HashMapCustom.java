package ru.bmstu.lab02;

import java.util.Objects;

/**
 * Хеш-таблица с линейным пробированием. Ключи и значения хранятся
 * в параллельных массивах. Допускаются ключ и значения {@code null}.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashMapCustom<K, V> {
    private static final int START_CAPACITY = 16;
    private static final double MAX_LOAD = 0.75;
    private static final double MAX_USED = 0.875;
    private static final Object DELETED = new Object();
    private static final Object NULL_KEY = new Object();

    private Object[] keys = new Object[START_CAPACITY];
    private Object[] values = new Object[START_CAPACITY];
    private int size;
    private int used;

    /**
     * Создаёт пустую таблицу с начальной ёмкостью 16.
     */
    public HashMapCustom() {
    }

    /**
     * Вставляет пару или обновляет значение существующего ключа.
     * При равномерных хешах выполняется в среднем за O(1) амортизированно.
     *
     * @param key ключ записи
     * @param value значение записи
     */
    public void put(K key, V value) {
        Object storedKey = maskNull(key);
        int i = findSlot(storedKey);
        if (keys[i] == null || keys[i] == DELETED) {
            if (size == capacity() * MAX_LOAD) {
                resize(capacity() * 2);
                i = findSlot(storedKey);
            } else if (keys[i] == null && used == capacity() * MAX_USED) {
                // Убираем старые метки, чтобы у поиска оставались пустые ячейки для остановки.
                resize(capacity());
                i = findSlot(storedKey);
            }
            if (keys[i] == null) {
                used++;
            }
            keys[i] = storedKey;
            size++;
        }
        values[i] = value;
    }

    /**
     * Ищет значение по ключу. При равномерных хешах поиск занимает O(1) в среднем.
     *
     * @param key искомый ключ
     * @return найденное значение или {@code null}, если ключ отсутствует
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int i = findKey(maskNull(key));
        return i < 0 ? null : (V) values[i];
    }

    /**
     * Проверяет наличие ключа, в том числе если записанное значение равно {@code null}.
     * При равномерных хешах поиск занимает O(1) в среднем.
     *
     * @param key искомый ключ
     * @return {@code true}, если ключ присутствует
     */
    public boolean containsKey(K key) {
        return findKey(maskNull(key)) >= 0;
    }

    /**
     * Удаляет запись по ключу. При равномерных хешах занимает O(1) в среднем.
     *
     * @param key ключ удаляемой записи
     * @return {@code true}, если запись была найдена и удалена
     */
    public boolean remove(K key) {
        int i = findKey(maskNull(key));
        if (i < 0) {
            return false;
        }
        keys[i] = DELETED;
        values[i] = null;
        size--;
        return true;
    }

    /**
     * Возвращает число записей за O(1).
     *
     * @return число записей без учёта удалённых ячеек
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает ёмкость таблицы за O(1).
     *
     * @return число ячеек в массиве ключей
     */
    public int capacity() {
        return keys.length;
    }

    private Object maskNull(K key) {
        return key == null ? NULL_KEY : key;
    }

    private int findKey(Object key) {
        int i = findSlot(key);
        return keys[i] == null || keys[i] == DELETED ? -1 : i;
    }

    private int findSlot(Object key) {
        // Приведение к long нужно, чтобы модуль Integer.MIN_VALUE был положительным.
        long hash = key == NULL_KEY ? 0 : key.hashCode();
        int i = (int) (Math.abs(hash) % capacity());
        int deleted = -1;
        while (keys[i] != null) {
            if (keys[i] == DELETED) {
                // Запоминаем место для вставки, но ищем совпадение дальше по цепочке.
                if (deleted < 0) {
                    deleted = i;
                }
            } else if (Objects.equals(key, keys[i])) {
                return i;
            }
            i = (i + 1) % capacity();
        }
        return deleted < 0 ? i : deleted;
    }

    private void resize(int capacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[capacity];
        values = new Object[capacity];
        for (int i = 0; i < oldKeys.length; i++) {
            Object key = oldKeys[i];
            if (key != null && key != DELETED) {
                int j = findSlot(key);
                keys[j] = key;
                values[j] = oldValues[i];
            }
        }
        used = size;
    }
}
