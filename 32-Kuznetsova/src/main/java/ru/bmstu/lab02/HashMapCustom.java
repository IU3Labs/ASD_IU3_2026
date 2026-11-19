package ru.bmstu.lab02;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Хеш-таблица с открытой адресацией: пары лежат прямо в массивах, а при коллизии ищется другая ячейка.
 *
 * <p>Ёмкость всегда степень двойки. Начальная ячейка: {@code Math.abs(key.hashCode()) % capacity}.
 * Дальше на шаге {@code i} смотрим ячейку {@code (index + c1*i + c2*i^2) % capacity}:
 * для линейного пробирования {@code c1 = 1, c2 = 0}, для квадратичного {@code c1 = 1, c2 = 2}.
 * При таких значениях и ёмкости 2^k за {@code capacity} шагов мы побываем в каждой ячейке по одному разу
 * (доказательство в README), поэтому поиск не зацикливается.
 *
 * <p>Удалённая ячейка помечается маркером {@code DELETED}, чтобы не оборвать цепочку проб других ключей.
 * Ключи {@code null} не поддерживаются.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashMapCustom<K, V> {

    /** Способ поиска свободной ячейки при коллизии. */
    public enum Probing {
        /** Шаги: 0, 1, 2, 3, ... */
        LINEAR,
        /** Шаги: i + 2*i^2, то есть 0, 3, 10, 21, ... */
        QUADRATIC
    }

    /** Маркер удалённой ячейки: при поиске её проходим дальше, а при вставке можем занять. */
    private static final Object DELETED = new Object();

    private static final int MIN_CAPACITY = 4;
    private static final int START_CAPACITY = 16;
    private static final double START_LOAD_FACTOR = 0.75;

    private final Probing probing;
    private final double loadFactor;
    private Object[] keys;
    private Object[] values;
    private int size;
    /** Занятые ячейки вместе с DELETED: именно они уменьшают число пустых ячеек. */
    private int usedCells;

    private int lastProbeCount;
    private long lookupCount;
    private long lookupProbeTotal;

    /** Линейное пробирование, ёмкость 16, порог загрузки 0.75. */
    public HashMapCustom() {
        this(Probing.LINEAR);
    }

    /** Заданное пробирование, ёмкость 16, порог загрузки 0.75. */
    public HashMapCustom(Probing probing) {
        this(probing, START_CAPACITY, START_LOAD_FACTOR);
    }

    /**
     * Таблица с настройками.
     *
     * @param probing         способ пробирования
     * @param initialCapacity нужная начальная ёмкость, округляется вверх до степени двойки (минимум 4)
     * @param loadFactor      порог загрузки, строго между 0 и 1
     */
    public HashMapCustom(Probing probing, int initialCapacity, double loadFactor) {
        this.probing = Objects.requireNonNull(probing, "probing");
        if (!(loadFactor > 0 && loadFactor < 1)) {
            throw new IllegalArgumentException("Порог загрузки должен быть в интервале (0, 1): " + loadFactor);
        }
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной: " + initialCapacity);
        }
        this.loadFactor = loadFactor;
        int capacity = MIN_CAPACITY;
        while (capacity < initialCapacity) {
            capacity *= 2;
        }
        this.keys = new Object[capacity];
        this.values = new Object[capacity];
    }

    /**
     * Добавляет пару или меняет значение, если ключ уже есть. В среднем O(1).
     *
     * @param key   ключ, не {@code null}
     * @param value значение (может быть {@code null})
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "key");
        int index = findKey(key);
        if (index != -1) {
            values[index] = value;
            return;
        }
        int slot = findFreeSlot(key);
        if (keys[slot] == null && usedCells + 1 > maxUsedCells(keys.length)) {
            rehash();
            slot = findFreeSlot(key);
        }
        if (keys[slot] == null) {
            usedCells++;
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    /**
     * Возвращает значение по ключу. В среднем O(1).
     *
     * @param key ключ
     * @return значение или {@code null}, если ключа нет
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int index = lookup(key);
        return index == -1 ? null : (V) values[index];
    }

    /**
     * Проверяет, есть ли ключ в таблице. В среднем O(1).
     *
     * @param key ключ
     * @return {@code true}, если ключ есть
     */
    public boolean containsKey(K key) {
        return lookup(key) != -1;
    }

    /**
     * Удаляет ключ, на его месте остаётся маркер {@code DELETED}. В среднем O(1).
     *
     * @param key ключ
     * @return {@code true}, если ключ был в таблице
     */
    public boolean remove(K key) {
        Objects.requireNonNull(key, "key");
        int index = findKey(key);
        if (index == -1) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /** Сколько пар в таблице. */
    public int size() {
        return size;
    }

    /** Пуста ли таблица. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Сколько ячеек в таблице сейчас. */
    public int capacity() {
        return keys.length;
    }

    /** Порог загрузки. */
    public double loadFactor() {
        return loadFactor;
    }

    /** Способ пробирования. */
    public Probing probing() {
        return probing;
    }

    /**
     * Вызывает действие для каждой пары в таблице.
     *
     * @param action действие над ключом и значением
     */
    @SuppressWarnings("unchecked")
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (int i = 0; i < keys.length; i++) {
            if (isRealKey(keys[i])) {
                action.accept((K) keys[i], (V) values[i]);
            }
        }
    }

    /** Среднее число проб на один {@code get} или {@code containsKey} (для бенчмарков). */
    public double averageLookupProbes() {
        return lookupCount == 0 ? 0 : (double) lookupProbeTotal / lookupCount;
    }

    /** Обнуляет счётчики проб. */
    public void resetProbeStatistics() {
        lookupCount = 0;
        lookupProbeTotal = 0;
    }

    private int lookup(K key) {
        Objects.requireNonNull(key, "key");
        int index = findKey(key);
        lookupCount++;
        lookupProbeTotal += lastProbeCount;
        return index;
    }

    /** Идёт по цепочке проб до пустой ячейки. Возвращает индекс ключа или -1, если ключа нет. */
    private int findKey(Object key) {
        int start = startIndex(key);
        lastProbeCount = 0;
        for (int step = 0; step < keys.length; step++) {
            int index = probeIndex(start, step);
            lastProbeCount++;
            if (keys[index] == null) {
                return -1;
            }
            if (keys[index] != DELETED && keys[index].equals(key)) {
                return index;
            }
        }
        return -1;
    }

    /** Первая по цепочке проб ячейка, в которую можно записать ключ: пустая или с маркером DELETED. */
    private int findFreeSlot(Object key) {
        int start = startIndex(key);
        for (int step = 0; step < keys.length; step++) {
            int index = probeIndex(start, step);
            if (keys[index] == null || keys[index] == DELETED) {
                return index;
            }
        }
        throw new IllegalStateException("В таблице нет свободных ячеек");
    }

    private int probeIndex(int start, int step) {
        long shift = step;
        if (probing == Probing.QUADRATIC) {
            shift = step + 2L * step * step;
        }
        return (int) ((start + shift) % keys.length);
    }

    /** {@code Math.abs(hashCode) % capacity}. Для {@code Integer.MIN_VALUE} abs даёт минус, поэтому берём 0. */
    private int startIndex(Object key) {
        int hash = key.hashCode();
        if (hash == Integer.MIN_VALUE) {
            hash = 0;
        }
        return Math.abs(hash) % keys.length;
    }

    private static boolean isRealKey(Object slot) {
        return slot != null && slot != DELETED;
    }

    /** Сколько ячеек (с учётом DELETED) можно занять до перестройки. Минимум 1, максимум capacity - 1. */
    private int maxUsedCells(int capacity) {
        int limit = (int) (capacity * loadFactor);
        return Math.max(1, Math.min(limit, capacity - 1));
    }

    /**
     * Перестраивает таблицу. Если много живых пар - ёмкость растёт вдвое. Если порог превышен только из-за
     * маркеров DELETED - ёмкость не меняется, просто маркеры пропадают.
     */
    private void rehash() {
        int newCapacity = keys.length;
        if (size + 1 > maxUsedCells(keys.length)) {
            newCapacity = keys.length * 2;
        }
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        for (int i = 0; i < oldKeys.length; i++) {
            if (isRealKey(oldKeys[i])) {
                int slot = findFreeSlot(oldKeys[i]);
                keys[slot] = oldKeys[i];
                values[slot] = oldValues[i];
            }
        }
        usedCells = size;
    }
}
