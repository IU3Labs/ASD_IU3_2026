package ru.bmstu.lab2.task_2a_2b;

import java.util.Objects;
import ru.bmstu.lab2.result.ResultList;

/**
 * Задачи 2A, 2B: хеш-таблица с открытой адресацией и маркерами удаления.
 * Ключи null запрещены, значения null разрешены; загрузка не превышает 0.75.
 *
 * @param <K> тип ключа с согласованными equals и hashCode
 * @param <V> тип значения
 */
public final class HashMapCustom<K, V> {
    /** Выбор последовательности пробирования. */
    public enum Probing {
        LINEAR,
        QUADRATIC
    }

    private static final int INITIAL_CAPACITY = 16;
    private static final Object DELETED = new Object();
    private final Probing probing;
    private Object[] keys;
    private Object[] values;
    private int size;
    private int deletedCount;
    private long getProbes;
    private long getCount;

    /** Создаёт таблицу с линейным пробированием. */
    public HashMapCustom() {
        this(Probing.LINEAR);
    }

    /** Создаёт таблицу с выбранным пробированием и начальной ёмкостью 16. */
    public HashMapCustom(Probing probing) {
        this.probing = Objects.requireNonNull(probing, "probing");
        keys = new Object[INITIAL_CAPACITY];
        values = new Object[INITIAL_CAPACITY];
    }

    /** Вставляет или обновляет пару; ожидаемо O(1) амортизированно при хороших хешах. */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "key");
        int index = findSlot(key, false);
        if (keys[index] != null && keys[index] != DELETED) {
            values[index] = value;
            return;
        }
        if (size + 1 > keys.length - keys.length / 4) {
            rehash(Math.multiplyExact(keys.length, 2));
            index = findSlot(key, false);
        } else if (size + deletedCount + 1 > keys.length - keys.length / 8) {
            // При 7/8 использованных ячеек убираем маркеры; запас 1/8 окупает очистку.
            rehash(keys.length);
            index = findSlot(key, false);
        }
        if (keys[index] == DELETED) {
            deletedCount--;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    /** Возвращает значение или null; учитывает число проб для эксперимента 2B. */
    public V get(K key) {
        Objects.requireNonNull(key, "key");
        getCount++;
        int index = findSlot(key, true);
        return index < 0 || keys[index] == null || keys[index] == DELETED ? null : valueAt(index);
    }

    /** Проверяет наличие ключа независимо от его значения. */
    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "key");
        int index = findSlot(key, false);
        return index >= 0 && keys[index] != null && keys[index] != DELETED;
    }

    /** Удаляет пару, сохраняя цепочку пробирования маркером DELETED. */
    public boolean remove(K key) {
        Objects.requireNonNull(key, "key");
        int index = findSlot(key, false);
        if (index < 0 || keys[index] == null || keys[index] == DELETED) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        deletedCount++;
        return true;
    }

    /** Возвращает число живых пар за O(1). */
    public int size() {
        return size;
    }

    /** Возвращает ёмкость массивов за O(1). */
    public int capacity() {
        return keys.length;
    }

    /** Возвращает среднее число просмотренных ячеек на get после последнего сброса. */
    public double averageGetProbes() {
        return getCount == 0 ? 0.0 : (double) getProbes / getCount;
    }

    /** Сбрасывает счётчики пробирования за O(1). */
    public void resetProbeStatistics() {
        getCount = 0;
        getProbes = 0;
    }

    /** Возвращает снимок ключей за O(capacity) для результата задачи 3B. */
    public ResultList<K> snapshotKeys() {
        Object[] result = new Object[size];
        int position = 0;
        for (Object key : keys) {
            if (key != null && key != DELETED) {
                result[position++] = key;
            }
        }
        return new ResultList<>(result);
    }

    private int findSlot(K key, boolean countProbes) {
        // Приведение к long до abs устраняет отрицательный результат для MIN_VALUE.
        int base = (int) (Math.abs((long) key.hashCode()) % keys.length);
        int deleted = -1;
        for (int step = 0; step < keys.length; step++) {
            int index = probeIndex(base, step);
            if (countProbes) {
                getProbes++;
            }
            if (keys[index] == null) {
                return deleted < 0 ? index : deleted;
            }
            if (keys[index] == DELETED) {
                deleted = deleted < 0 ? index : deleted;
            } else if (keys[index].equals(key)) {
                return index;
            }
        }
        return deleted;
    }

    private int probeIndex(int base, int step) {
        // i + 2*i² переставляет все остатки по модулю 2^p (см. asymptotics.md).
        long offset = probing == Probing.LINEAR ? step : step + 2L * step * step;
        return (int) ((base + offset) & (keys.length - 1));
    }

    private void rehash(int capacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[capacity];
        values = new Object[keys.length];
        size = 0;
        deletedCount = 0;
        for (int index = 0; index < oldKeys.length; index++) {
            if (oldKeys[index] != null && oldKeys[index] != DELETED) {
                put(keyAt(oldKeys, index), valueAt(oldValues, index));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private K keyAt(Object[] array, int index) {
        return (K) array[index];
    }

    private V valueAt(int index) {
        return valueAt(values, index);
    }

    @SuppressWarnings("unchecked")
    private V valueAt(Object[] array, int index) {
        return (V) array[index];
    }
}
