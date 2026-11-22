/*
Задача 2A (линейное пробирование). Реализуйте HashMapCustom<K, V> с линейным пробированием:

- void put(K key, V value) — вставка или обновление значения по ключу;
- V get(K key) — значение по ключу либо null;
- boolean containsKey(K key);
- boolean remove(K key);
- int size(), int capacity().

Хеш-функция: Math.abs(key.hashCode()) % capacity. Коэффициент загрузки не более 0.75, при превышении — увеличение ёмкости в 2 раза с перехешированием. Для удалённых ячеек используйте маркер DELETED, чтобы не ломать цепочки пробирования.
*/

package ru.bmstu.lab02;

import java.util.Objects;

public class LabaTask2A_HashMap<K, V> {
    private static final Object REMOVED = new Object();
    private Object[] keyTable;
    private Object[] valueTable;
    private int used;
    private int removed;
    private final double maxLoad;
    private final boolean quadratic;

    public LabaTask2A_HashMap() { this(0.75, false); }

    public LabaTask2A_HashMap(double load) { this(load, false); }

    protected LabaTask2A_HashMap(double load, boolean quadratic) {
        if (!Double.isFinite(load) || load <= 0 || load >= 1) {
            throw new IllegalArgumentException("load must be between 0 and 1");
        }
        this.maxLoad = load;
        this.quadratic = quadratic;
        keyTable = new Object[16];
        valueTable = new Object[16];
    }

    public int size() { return used; }

    public int capacity() { return keyTable.length; }

    public V get(K key) {
        int index = locate(key);
        return index == -1 ? null : valueAt(index);
    }

    public boolean containsKey(K key) { return locate(key) != -1; }

    public boolean remove(K key) {
        int index = locate(key);
        if (index < 0) return false;
        keyTable[index] = REMOVED;
        valueTable[index] = null;
        used--;
        removed++;
        return true;
    }

    public void put(K key, V value) {
        Objects.requireNonNull(key, "key cannot be null");
        int index = locate(key);
        if (index != -1) {
            valueTable[index] = value;
            return;
        }
        if (used + removed + 1 > keyTable.length * maxLoad) {
            rehash(keyTable.length * 2);
        }
        insertNew(key, value);
    }

    public int getProbeCount(K key) {
        Objects.requireNonNull(key, "key cannot be null");
        int start = hash(key);
        for (int step = 0; step < capacity(); step++) {
            Object stored = keyTable[slot(start, step)];
            if (stored == null || (stored != REMOVED && stored.equals(key))) return step + 1;
        }
        return capacity();
    }

    private int locate(K key) {
        Objects.requireNonNull(key, "key cannot be null");
        int start = hash(key);
        for (int step = 0; step < capacity(); step++) {
            int index = slot(start, step);
            Object stored = keyTable[index];
            if (stored == null) return -1;
            if (stored != REMOVED && stored.equals(key)) return index;
        }
        return -1;
    }

    private void insertNew(K key, V value) {
        int start = hash(key);
        int vacant = -1;
        for (int step = 0; step < capacity(); step++) {
            int index = slot(start, step);
            if (keyTable[index] == REMOVED && vacant < 0) vacant = index;
            if (keyTable[index] == null) {
                fill(vacant >= 0 ? vacant : index, key, value);
                return;
            }
        }
        if (vacant < 0) throw new IllegalStateException("table has no free slot");
        fill(vacant, key, value);
    }

    private void fill(int index, K key, V value) {
        if (keyTable[index] == REMOVED) removed--;
        keyTable[index] = key;
        valueTable[index] = value;
        used++;
    }

    private void rehash(int target) {
        Object[] previousKeys = keyTable;
        Object[] previousValues = valueTable;
        keyTable = new Object[target];
        valueTable = new Object[target];
        used = removed = 0;
        for (int i = 0; i < previousKeys.length; i++) {
            Object key = previousKeys[i];
            if (key != null && key != REMOVED) insertNew(keyAt(key), valueAt(previousValues[i]));
        }
    }

    private int hash(K key) { return (key.hashCode() & 0x7fffffff) % capacity(); }

    private int slot(int hash, int step) {
        // Треугольные числа при ёмкости 2^m обходят каждую ячейку ровно один раз.
        long offset = quadratic ? (long) step * (step + 1) / 2 : step;
        return (int) ((hash + offset) % capacity());
    }

    @SuppressWarnings("unchecked")
    private K keyAt(Object key) { return (K) key; }

    @SuppressWarnings("unchecked")
    private V valueAt(Object value) { return (V) value; }

    private V valueAt(int index) { return valueAt(valueTable[index]); }
}
