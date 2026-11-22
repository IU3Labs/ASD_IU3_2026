/*Задача 2A (линейное пробирование). Реализуйте HashMapCustom<K, V> с линейным пробированием:

void put(K key, V value) — вставка или обновление значения по ключу;
V get(K key) — значение по ключу либо null;
boolean containsKey(K key);
boolean remove(K key);
int size(), int capacity().
Хеш-функция: Math.abs(key.hashCode()) % capacity. Коэффициент загрузки не более 0.75, при превышении —
увеличение ёмкости в 2 раза с перехешированием. Для удалённых ячеек используйте маркер DELETED,
чтобы не ломать цепочки пробирования.*/

package ru.bmstu.lab02;

import java.util.Objects;

public class Task2A_HashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final Object DELETED = new Object();

    private Object[] keys;
    private Object[] values;
    private int size;
    private int deletedCount;
    private final double loadFactor;
    private final boolean quadratic;

    public Task2A_HashMap() {
        this(DEFAULT_LOAD_FACTOR);
    }

    public Task2A_HashMap(double loadFactor) {
        this(loadFactor, false);
    }

    protected Task2A_HashMap(double loadFactor, boolean quadratic) {
        if (!Double.isFinite(loadFactor) || loadFactor <= 0 || loadFactor >= 1) {
            throw new IllegalArgumentException("load factor must be between 0 and 1");
        }
        this.loadFactor = loadFactor;
        this.quadratic = quadratic;
        keys = new Object[INITIAL_CAPACITY];
        values = new Object[INITIAL_CAPACITY];
    }

    public void put(K key, V value) {
        Objects.requireNonNull(key, "key must not be null");
        int slot = findSlot(key);
        if (slot >= 0) {
            values[slot] = value;
            return;
        }
        if (size + deletedCount + 1 > keys.length * loadFactor) {
            resize(keys.length * 2);
        }
        insert(key, value);
    }

    public V get(K key) {
        Objects.requireNonNull(key, "key must not be null");
        int index = findSlot(key);
        return index < 0 ? null : valueAt(index);
    }

    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "key must not be null");
        return findSlot(key) >= 0;
    }

    public boolean remove(K key) {
        Objects.requireNonNull(key, "key must not be null");
        int index = findSlot(key);
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        deletedCount++;
        return true;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return keys.length;
    }

    // Число просмотренных ячеек при поиске ключа.
    public int getProbeCount(K key) {
        Objects.requireNonNull(key, "key must not be null");
        int base = hash(key);
        for (int i = 0; i < keys.length; i++) {
            Object current = keys[probe(base, i)];
            if (current == null || (current != DELETED && current.equals(key))) {
                return i + 1;
            }
        }
        return keys.length;
    }

    private int findSlot(K key) {
        int base = hash(key);
        for (int i = 0; i < keys.length; i++) {
            int index = probe(base, i);
            Object current = keys[index];
            if (current == null) {
                return -1;
            }
            if (current != DELETED && current.equals(key)) {
                return index;
            }
        }
        return -1;
    }

    private void insert(K key, V value) {
        int base = hash(key);
        int firstDeleted = -1;
        for (int i = 0; i < keys.length; i++) {
            int index = probe(base, i);
            if (keys[index] == DELETED && firstDeleted == -1) {
                firstDeleted = index;
            } else if (keys[index] == null) {
                int target = firstDeleted == -1 ? index : firstDeleted;
                if (firstDeleted != -1) {
                    deletedCount--;
                }
                keys[target] = key;
                values[target] = value;
                size++;
                return;
            }
        }
        if (firstDeleted != -1) {
            keys[firstDeleted] = key;
            values[firstDeleted] = value;
            deletedCount--;
            size++;
            return;
        }
        throw new IllegalStateException("hash table is full");
    }

    private void resize(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        size = 0;
        deletedCount = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                insert(keyAt(oldKeys[i]), valueAt(oldValues, i));
            }
        }
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % keys.length;
    }

    private int probe(int base, int i) {
        long offset = quadratic ? (long) i * (i + 1) / 2 : i;
        return (int) ((base + offset) % keys.length);
    }

    @SuppressWarnings("unchecked")
    private K keyAt(Object item) {
        return (K) item;
    }

    @SuppressWarnings("unchecked")
    private V valueAt(int index) {
        return (V) values[index];
    }

    @SuppressWarnings("unchecked")
    private V valueAt(Object[] source, int index) {
        return (V) source[index];
    }
}
