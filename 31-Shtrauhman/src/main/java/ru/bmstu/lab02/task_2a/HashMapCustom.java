package ru.bmstu.lab02.task_2a;

import java.util.Objects;

/**
 * Задача 2A. Хеш-таблица с открытой адресацией и линейным пробированием.
 *
 * <p>Удалённая ячейка помечается маркером {@link #DELETED}: если просто обнулить её, поиск остановится
 * на «дыре» и не найдёт ключи, вставленные дальше по той же цепочке.
 *
 * <p>Коэффициент загрузки считается по занятым ячейкам вместе с маркерами {@code DELETED} и не превышает 0.75.
 * Поэтому в массиве всегда есть пустая ячейка и цикл пробирования гарантированно завершается.
 *
 * @param <K> тип ключа (null не допускается)
 * @param <V> тип значения (null допускается)
 */
public class HashMapCustom<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;
    private static final int GROWTH_FACTOR = 2;
    private static final Object DELETED = new Object();

    private Object[] keys;
    private Object[] values;
    private int size;
    private int deletedCount;

    /**
     * Начальная ёмкость - 16.
     */
    public HashMapCustom() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Ёмкость должна быть не меньше 1, иначе {@code IllegalArgumentException}.
     */
    public HashMapCustom(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной: " + initialCapacity);
        }
        keys = new Object[initialCapacity];
        values = new Object[initialCapacity];
    }

    /**
     * Вставка или обновление, O(1) в среднем, амортизированно.
     *
     * @throws NullPointerException если ключ null
     */
    public void put(K key, V value) {
        requireKey(key);
        int index = findIndex(key);
        if (index >= 0) {
            values[index] = value;
            return;
        }
        ensureCapacityForInsert();
        insertAbsent(key, value);
        size++;
    }

    /**
     * O(1) в среднем. null возвращается и для отсутствующего ключа, и для значения null -
     * различить их можно через {@link #containsKey}.
     *
     * @throws NullPointerException если ключ null
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        requireKey(key);
        int index = findIndex(key);
        return index >= 0 ? (V) values[index] : null;
    }

    /**
     * O(1) в среднем.
     *
     * @throws NullPointerException если ключ null
     */
    public boolean containsKey(K key) {
        requireKey(key);
        return findIndex(key) >= 0;
    }

    /**
     * O(1) в среднем.
     *
     * @return false, если ключа не было
     * @throws NullPointerException если ключ null
     */
    public boolean remove(K key) {
        requireKey(key);
        int index = findIndex(key);
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        deletedCount++;
        return true;
    }

    /**
     * Время O(1).
     */
    public int size() {
        return size;
    }

    /**
     * Длина внутреннего массива (не число ключей), O(1).
     */
    public int capacity() {
        return keys.length;
    }

    private static int indexFor(Object key, int capacity) {
        int index = Math.abs(key.hashCode()) % capacity;
        // Math.abs(Integer.MIN_VALUE) переполняется и остаётся отрицательным, тогда и остаток отрицателен.
        if (index < 0) {
            index += capacity;
        }
        return index;
    }

    private int findIndex(Object key) {
        int index = indexFor(key, keys.length);
        while (keys[index] != null) {
            if (keys[index] != DELETED && keys[index].equals(key)) {
                return index;
            }
            index = (index + 1) % keys.length;
        }
        return -1;
    }

    // Вызывается только для ключа, которого точно нет в таблице, поэтому сравнивать ключи не нужно.
    private void insertAbsent(Object key, Object value) {
        int index = indexFor(key, keys.length);
        while (keys[index] != null && keys[index] != DELETED) {
            index = (index + 1) % keys.length;
        }
        if (keys[index] == DELETED) {
            deletedCount--;
        }
        keys[index] = key;
        values[index] = value;
    }

    /**
     * Если живых ключей мало, а порог превышен в основном маркерами DELETED, ёмкость не удваивается:
     * таблица перехешируется в тот же размер, и маркеры исчезают. Иначе при чередовании put/remove
     * ёмкость росла бы бесконечно. Граница - половина порога: после перехеширования загрузка не выше 0.375,
     * и до следующего перехеширования пройдёт не меньше 0.375 * capacity вставок, что даёт амортизированную O(1).
     */
    private void ensureCapacityForInsert() {
        double threshold = keys.length * MAX_LOAD_FACTOR;
        if (size + deletedCount + 1 <= threshold) {
            return;
        }
        int newCapacity = size + 1 > threshold / 2 ? keys.length * GROWTH_FACTOR : keys.length;
        rehash(newCapacity);
    }

    private void rehash(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        deletedCount = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                insertAbsent(oldKeys[i], oldValues[i]);
            }
        }
    }

    private static void requireKey(Object key) {
        Objects.requireNonNull(key, "Ключ не может быть null");
    }
}
