package ru.bmstu.lab02.task_2;

import java.util.Objects;

/** Хеш-таблица с линейным пробированием и маркерами удаления. */
public class HashMapCustom<K, V> {

    private static final int INITIAL_CAPACITY = 8;
    private static final int QUARTER_DIVISOR = 4;
    private static final Object DELETED = new Object();

    private Object[] table = new Object[INITIAL_CAPACITY];
    private int size;
    private int deletedCount;

    /** Добавляет пару или заменяет значение существующего ключа. */
    public void put(K key, V value) {
        int index = findSlot(key);
        if (isEntry(table[index])) {
            entryAt(index).value = value;
            return;
        }

        int quarter = table.length / QUARTER_DIVISOR;
        if (size + 1 > table.length - quarter) {
            rehash(table.length * 2);
            index = findSlot(key);
        } else if (deletedCount >= quarter) {
            rehash(table.length);
            index = findSlot(key);
        }

        if (table[index] == DELETED) {
            deletedCount--;
        }
        table[index] = new Entry<>(key, value);
        size++;
    }

    /** Возвращает значение по ключу или null, если ключ отсутствует. */
    public V get(K key) {
        int index = findSlot(key);
        return isEntry(table[index]) ? entryAt(index).value : null;
    }

    /** Проверяет наличие ключа, в том числе со значением null. */
    public boolean containsKey(K key) {
        return isEntry(table[findSlot(key)]);
    }

    /** Удаляет пару и возвращает true, если ключ был найден. */
    public boolean remove(K key) {
        int index = findSlot(key);
        if (!isEntry(table[index])) {
            return false;
        }

        table[index] = DELETED;
        size--;
        deletedCount++;
        return true;
    }

    /** Возвращает число хранимых пар. */
    public int size() {
        return size;
    }

    /** Возвращает текущую ёмкость массива. */
    public int capacity() {
        return table.length;
    }

    private int findSlot(K key) {
        Objects.requireNonNull(key, "Ключ не может быть null");
        int index = (int) (Math.abs((long) key.hashCode()) % table.length);
        int firstDeleted = -1;

        for (int checked = 0; checked < table.length; checked++) {
            Object cell = table[index];
            if (cell == null) {
                return firstDeleted >= 0 ? firstDeleted : index;
            }
            if (cell == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = index;
                }
            } else if (key.equals(entryAt(index).key)) {
                return index;
            }

            index = (index + 1) % table.length;
        }

        if (firstDeleted >= 0) {
            return firstDeleted;
        }
        throw new IllegalStateException("Нет свободной ячейки");
    }

    private void rehash(int newCapacity) {
        Object[] oldTable = table;
        table = new Object[newCapacity];
        deletedCount = 0;

        for (Object cell : oldTable) {
            if (isEntry(cell)) {
                Entry<K, V> entry = asEntry(cell);
                table[findSlot(entry.key)] = entry;
            }
        }
    }

    private boolean isEntry(Object cell) {
        return cell != null && cell != DELETED;
    }

    private Entry<K, V> entryAt(int index) {
        return asEntry(table[index]);
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V> asEntry(Object cell) {
        return (Entry<K, V>) cell;
    }

    private static final class Entry<K, V> {

        private final K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}