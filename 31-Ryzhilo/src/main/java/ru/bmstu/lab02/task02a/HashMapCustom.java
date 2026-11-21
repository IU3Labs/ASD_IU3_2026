package ru.bmstu.lab02.task02a;

import java.util.function.BiConsumer;

/** Хеш-таблица с открытой адресацией и линейным пробированием. */
public class HashMapCustom<K, V> {
    private static final Object DELETED = new Object();
    private static final int INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD = 0.75;

    private final double loadThreshold;
    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int used;

    /** Создаёт таблицу с линейным пробированием и стандартным порогом загрузки. */
    public HashMapCustom() {
        this(DEFAULT_LOAD);
    }

    /** Задаёт порог загрузки строго между нулём и единицей. */
    protected HashMapCustom(double loadThreshold) {
        if (!(loadThreshold > 0 && loadThreshold < 1)) {
            throw new IllegalArgumentException("Порог загрузки должен быть строго между нулём и единицей");
        }
        this.loadThreshold = loadThreshold;
    }

    /** Добавляет или обновляет запись. Ключ обязателен, значение может быть null. */
    public void put(K key, V value) {
        int slot = findSlot(key);
        if (slot >= 0) {
            values[slot] = value;
            return;
        }
        int targetCapacity = capacity();
        while (size + 1 > targetCapacity * loadThreshold) {
            targetCapacity = Math.multiplyExact(targetCapacity, 2);
        }
        int insertionSlot = ~slot;
        if (targetCapacity != capacity() || (keys[insertionSlot] == null
                && used + 1 > capacity() * (1 + loadThreshold) / 2)) {
            rehash(targetCapacity);
            insertionSlot = ~findSlot(key);
        }
        insertAt(insertionSlot, key, value);
    }

    /** Возвращает значение по ключу или null, если ключа нет. */
    public V get(K key) {
        int slot = findSlot(key);
        return slot >= 0 ? valueAt(slot) : null;
    }

    /** Проверяет наличие ключа, в том числе со значением null. */
    public boolean containsKey(K key) {
        return findSlot(key) >= 0;
    }

    /** Удаляет запись по ключу и сообщает, была ли она найдена. */
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

    /** Возвращает число записей в таблице. */
    public int size() {
        return size;
    }

    /** Возвращает текущую ёмкость таблицы. */
    public int capacity() {
        return keys.length;
    }

    /** Применяет действие к каждой действующей записи таблицы. */
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (int slot = 0; slot < capacity(); slot++) {
            if (keys[slot] != null && keys[slot] != DELETED) {
                action.accept(keyAt(slot), valueAt(slot));
            }
        }
    }

    /** Возвращает число ячеек, проверяемых при поиске ключа. */
    public int getProbeCount(K key) {
        int[] probeCount = {0};
        findSlot(key, probeCount);
        return probeCount[0];
    }

    private int findSlot(K key) {
        return findSlot(key, null);
    }

    private int findSlot(K key, int[] probeCount) {
        int index = (int) (Math.abs((long) key.hashCode()) % capacity());
        int firstDeleted = -1;
        for (int step = 0; step < capacity(); step++) {
            int slot = probe(index, step);
            if (probeCount != null) {
                probeCount[0]++;
            }
            Object stored = keys[slot];
            if (stored == null) {
                return ~(firstDeleted >= 0 ? firstDeleted : slot);
            }
            if (stored == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = slot;
                }
            } else if (key.equals(stored)) {
                return slot;
            }
        }
        throw new IllegalStateException("В таблице должна оставаться свободная ячейка");
    }

    private int probe(int index, int step) {
        return (int) ((index + probeOffset(step)) % capacity());
    }

    /** Возвращает смещение очередной позиции при линейном пробировании. */
    protected long probeOffset(int step) {
        return step;
    }

    private void insertAt(int slot, K key, V value) {
        if (keys[slot] == null) {
            used++;
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    private void rehash(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        size = 0;
        used = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            Object key = oldKeys[index];
            if (key != null && key != DELETED) {
                insertAt(~findSlot((K) key), (K) key, (V) oldValues[index]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private K keyAt(int slot) {
        return (K) keys[slot];
    }

    @SuppressWarnings("unchecked")
    private V valueAt(int slot) {
        return (V) values[slot];
    }
}
