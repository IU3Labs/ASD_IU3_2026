/*
 * ЛР2, задачи 2A, 2B и 2D. Хеш-таблица с открытой адресацией,
 * линейным и квадратичным пробированием и настраиваемым порогом загрузки.
 */
package ru.bmstu.lab2.task_2;

import java.util.Objects;

/** Хеш-таблица на массиве. Ключи должны быть ненулевыми, значения могут быть null. */
public final class HashMapCustom<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final int MAX_CAPACITY = 1 << 30;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final Entry<?, ?> DELETED = new Entry<>(null, null, 0);

    private final Probing probing;
    private final double loadFactor;
    private Entry<K, V>[] table = newTable(INITIAL_CAPACITY);
    private int size;
    private int deleted;

    /** Способ вычисления смещения от начальной позиции ключа. */
    public enum Probing {
        LINEAR,
        QUADRATIC;

        long offset(int attempt) {
            // i + 2i² перебирает все остатки по модулю степени двойки.
            return this == LINEAR ? attempt : (long) attempt * (1 + 2L * attempt);
        }
    }

    /** Создаёт таблицу с линейным пробированием и порогом 0.75. */
    public HashMapCustom() {
        this(Probing.LINEAR, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Создаёт таблицу с линейным пробированием.
     *
     * @param loadFactor порог загрузки строго между нулём и единицей
     */
    public HashMapCustom(double loadFactor) {
        this(Probing.LINEAR, loadFactor);
    }

    /**
     * Создаёт таблицу с заданным способом пробирования и порогом загрузки.
     *
     * @param probing способ выбора следующей ячейки
     * @param loadFactor доля заполнения, при превышении которой таблица растёт
     */
    public HashMapCustom(Probing probing, double loadFactor) {
        this.probing = Objects.requireNonNull(probing, "Укажите способ пробирования");
        if (!(loadFactor > 0.0 && loadFactor < 1.0)) {
            throw new IllegalArgumentException("Порог загрузки должен быть строго между 0 и 1");
        }
        this.loadFactor = loadFactor;
    }

    /**
     * Вставляет пару или обновляет значение по существующему ключу.
     *
     * @param key ненулевой ключ
     * @param value сохраняемое значение
     */
    public void put(K key, V value) {
        int hash = hashOf(key);
        int index = findSlot(table, key, hash).index();
        Entry<K, V> entry = table[index];
        if (isOccupied(entry)) {
            entry.value = value;
            return;
        }
        if (prepareForInsert()) {
            index = findSlot(table, key, hash).index();
        }
        boolean reused = table[index] == DELETED;
        table[index] = new Entry<>(key, value, hash);
        if (reused) {
            deleted--;
        }
        size++;
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key ненулевой ключ
     * @return сохранённое значение или null при отсутствии ключа
     */
    public V get(K key) {
        int index = indexOf(key);
        return index < 0 ? null : table[index].value;
    }

    /**
     * Проверяет наличие ключа, в том числе со значением null.
     *
     * @param key ненулевой ключ
     * @return true, если ключ присутствует
     */
    public boolean containsKey(K key) {
        return indexOf(key) >= 0;
    }

    /**
     * Удаляет пару по ключу, сохраняя цепочку пробирования.
     *
     * @param key ненулевой ключ
     * @return true, если пара была найдена и удалена
     */
    public boolean remove(K key) {
        int index = indexOf(key);
        if (index < 0) {
            return false;
        }
        table[index] = deletedEntry();
        size--;
        deleted++;
        return true;
    }

    /**
     * Возвращает число сохранённых пар.
     *
     * @return число пар
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает число ячеек массива.
     *
     * @return ёмкость таблицы
     */
    public int capacity() {
        return table.length;
    }

    /**
     * Считает, сколько ячеек проверил бы get при поиске ключа.
     *
     * @param key ненулевой ключ
     * @return количество пробирований, включая последнее
     */
    int probesForGet(K key) {
        return findSlot(table, key, hashOf(key)).probes();
    }

    private int indexOf(K key) {
        int index = findSlot(table, key, hashOf(key)).index();
        return isOccupied(table[index]) ? index : -1;
    }

    private Search findSlot(Entry<K, V>[] entries, K key, int hash) {
        int capacity = entries.length;
        int start = (int) (Math.abs((long) hash) % capacity);
        int firstDeleted = -1;
        for (int attempt = 0; attempt < capacity; attempt++) {
            int index = (int) ((start + probing.offset(attempt)) & (capacity - 1));
            Entry<K, V> entry = entries[index];
            if (entry == null) {
                return new Search(firstDeleted < 0 ? index : firstDeleted, attempt + 1);
            }
            if (entry == DELETED) {
                // Запоминаем место для вставки, а поиск ключа продолжаем дальше.
                if (firstDeleted < 0) {
                    firstDeleted = index;
                }
            } else if (entry.hash == hash && key.equals(entry.key)) {
                return new Search(index, attempt + 1);
            }
        }
        return new Search(firstDeleted, capacity);
    }

    private boolean prepareForInsert() {
        int capacity = table.length;
        int newCapacity = capacity;
        while (size + 1 > newCapacity * loadFactor) {
            if (newCapacity == MAX_CAPACITY) {
                throw new IllegalStateException("Достигнута предельная ёмкость таблицы");
            }
            newCapacity *= 2;
        }
        if (newCapacity != capacity || deleted > capacity * (1 - loadFactor) / 2) {
            rehash(newCapacity);
            return true;
        }
        return false;
    }

    private void rehash(int capacity) {
        Entry<K, V>[] moved = newTable(capacity);
        for (Entry<K, V> entry : table) {
            if (isOccupied(entry)) {
                moved[findSlot(moved, entry.key, entry.hash).index()] = entry;
            }
        }
        table = moved;
        deleted = 0;
    }

    private static int hashOf(Object key) {
        return Objects.requireNonNull(key, "Ключ должен быть ненулевым").hashCode();
    }

    private static boolean isOccupied(Entry<?, ?> entry) {
        return entry != null && entry != DELETED;
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Entry<K, V>[] newTable(int capacity) {
        return (Entry<K, V>[]) new Entry<?, ?>[capacity];
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Entry<K, V> deletedEntry() {
        return (Entry<K, V>) DELETED;
    }

    private record Search(int index, int probes) {}

    private static final class Entry<K, V> {
        private final K key;
        private final int hash;
        private V value;

        private Entry(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }
}
