package ru.bmstu.lab02.task_2;

import java.util.Objects;

/**
 * Хеш-таблица с открытой адресацией, маркерами удаления и удвоением ёмкости
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public final class HashMapCustom<K, V> {
    private static final Object DELETED = new Object();
    private static final int INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD = 0.75;
    private static final int TOMBSTONE_DIVISOR = 8;
    private final double loadFactor;
    private final boolean quadratic;
    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int used;
    private int lastProbes;

    /** Создаёт таблицу с линейным пробированием и порогом 0,75 */
    public HashMapCustom() {
        this(false, DEFAULT_LOAD);
    }

    /**
     * Создаёт таблицу с выбранным способом пробирования
     * @param quadratic - true для квадратичного, false для линейного пробирования
     */
    public HashMapCustom(boolean quadratic) {
        this(quadratic, DEFAULT_LOAD);
    }

    /**
     * Создаёт таблицу с настраиваемым порогом
     * @param quadratic - true для квадратичного пробирования
     * @param loadFactor - порог загрузки между нулём и единицей
     */
    public HashMapCustom(boolean quadratic, double loadFactor) {
        if (!(loadFactor > 0 && loadFactor < 1)) {
            throw new IllegalArgumentException("Порог должен быть между 0 и 1");
        }
        this.quadratic = quadratic;
        this.loadFactor = loadFactor;
    }

    /**
     * Вставляет пару или заменяет значение существующего ключа
     * @param key - ключ
     * @param value - новое значение
     */
    public void put(K key, V value) {
        int position = locate(key);
        if (position >= 0) {
            values[position] = value;
            return;
        }
        int index = ~position;
        if (size + 1 > keys.length * loadFactor) {
            int capacity = keys.length;
            do {
                capacity = Math.multiplyExact(capacity, 2);
            } while (size + 1 > capacity * loadFactor);
            rehash(capacity);
            index = ~locate(key);
        } else if (keys[index] == null && used + 1 > keys.length * loadFactor
                && used - size >= keys.length / TOMBSTONE_DIVISOR) {
            rehash(keys.length);
            index = ~locate(key);
        }
        insert(index, key, value);
    }

    /**
     * Возвращает значение по ключу
     * @param key - искомый ключ
     * @return - значение или null при отсутствии ключа
     */
    public V get(K key) {
        int index = locate(key);
        return index < 0 ? null : valueAt(index);
    }

    /**
     * Проверяет наличие ключа, даже если значение равно null
     * @param key - искомый ключ
     * @return - признак наличия ключа
     */
    public boolean containsKey(K key) {
        return locate(key) >= 0;
    }

    /**
     * Удаляет пару, оставляя маркер для дальнейшего поиска
     * @param key - удаляемый ключ
     * @return - true, если ключ существовал
     */
    public boolean remove(K key) {
        int index = locate(key);
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /**
     * Возвращает число живых пар
     * @return - размер таблицы
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает число ячеек
     * @return - физическая ёмкость
     */
    public int capacity() {
        return keys.length;
    }

    int lastProbes() {
        return lastProbes;
    }

    /**
     * Проверяет, содержит ли ячейка действующую запись
     * @param index - индекс в пределах ёмкости
     * @return - true, если в ячейке есть действующая запись
     */
    public boolean occupied(int index) {
        return keys[index] != null && keys[index] != DELETED;
    }

    /**
     * Возвращает ключ занятой ячейки
     * @param index - индекс в пределах ёмкости
     * @return - ключ записи
     */
    @SuppressWarnings("unchecked")
    public K keyAt(int index) {
        return keys[index] == NullKey.INSTANCE ? null : (K) keys[index];
    }

    /**
     * Возвращает значение занятой ячейки
     * @param index - индекс в пределах ёмкости
     * @return - значение записи
     */
    @SuppressWarnings("unchecked")
    public V valueAt(int index) {
        return (V) values[index];
    }

    private int locate(K key) {
        int index = (int) (Math.abs((long) Objects.hashCode(key)) % keys.length);
        int deleted = -1;
        lastProbes = 0;
        for (int step = 0; step < keys.length; step++) {
            lastProbes++;
            if (keys[index] == null) {
                return ~(deleted < 0 ? index : deleted);
            }
            if (keys[index] == DELETED) {
                if (deleted < 0) {
                    deleted = index;
                }
            } else if (Objects.equals(key, keyAt(index))) {
                return index;
            }
            // Нарастающие шаги 1, 2, 3... дают квадратичное смещение i(i+1)/2
            index = (index + (quadratic ? step + 1 : 1)) & (keys.length - 1);
        }
        if (deleted >= 0) {
            return ~deleted;
        }
        throw new IllegalStateException("Нет свободной ячейки");
    }

    private void insert(int index, K key, V value) {
        if (keys[index] == null) {
            used++;
        }
        keys[index] = key == null ? NullKey.INSTANCE : key;
        values[index] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    private void rehash(int capacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[capacity];
        values = new Object[capacity];
        size = 0;
        used = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            Object stored = oldKeys[index];
            if (stored != null && stored != DELETED) {
                K key = stored == NullKey.INSTANCE ? null : (K) stored;
                insert(~locate(key), key, (V) oldValues[index]);
            }
        }
    }

    private enum NullKey {
        INSTANCE
    }
}
