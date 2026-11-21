package ru.bmstu.lab02;

import java.util.Objects;

/** Хеш-таблица с открытой адресацией, линейным пробированием и маркером удаления. */
public final class HashMapCustom<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final Entry<?, ?> DELETED = new Entry<>(null, null);

    private Entry<?, ?>[] entries = new Entry<?, ?>[DEFAULT_CAPACITY];
    private int size;
    private int used;

    /** Создаёт пустую таблицу с начальной ёмкостью 16. */
    public HashMapCustom() {
    }

    /**
     * Вставляет новую пару или обновляет значение существующего ключа.
     * @throws NullPointerException если ключ равен null
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "Ключ");
        int slot = findSlot(key);
        Entry<K, V> entry = entryAt(slot);
        if (entry != null && entry != DELETED) {
            entry.value = value;
            return;
        }

        int loadLimit = capacity() - capacity() / 4;
        if (size == loadLimit) {
            rehash(capacity() * 2);
            slot = findSlot(key);
        } else if (entry == null && used == capacity() - capacity() / 8) {
            // Убираем маркеры удаления, когда они начинают удлинять цепочки поиска.
            rehash(capacity());
            slot = findSlot(key);
        }
        if (entries[slot] == null) {
            used++;
        }
        entries[slot] = new Entry<>(key, value);
        size++;
    }

    /**
     * Возвращает значение или null, если ключ отсутствует.
     * @throws NullPointerException если ключ равен null
     */
    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.value;
    }

    /**
     * Проверяет наличие ключа, в том числе связанного со значением null.
     * @throws NullPointerException если ключ равен null
     */
    public boolean containsKey(K key) {
        return findEntry(key) != null;
    }

    /**
     * Удаляет пару и возвращает true, если ключ присутствовал в таблице.
     * @throws NullPointerException если ключ равен null
     */
    public boolean remove(K key) {
        Objects.requireNonNull(key, "Ключ");
        int slot = findSlot(key);
        if (entries[slot] == null || entries[slot] == DELETED) {
            return false;
        }
        entries[slot] = DELETED;
        size--;
        return true;
    }

    /** Возвращает количество пар за O(1). */
    public int size() {
        return size;
    }

    /** Возвращает количество ячеек за O(1). */
    public int capacity() {
        return entries.length;
    }

    private int findSlot(K key) {
        // Расширение до long позволяет взять модуль Integer.MIN_VALUE.
        int index = (int) (Math.abs((long) key.hashCode()) % capacity());
        int firstDeleted = -1;
        while (entries[index] != null) {
            if (entries[index] == DELETED) {
                if (firstDeleted == -1) {
                    firstDeleted = index;
                }
            } else if (key.equals(entries[index].key)) {
                return index;
            }
            index = (index + 1) % capacity();
        }
        return firstDeleted == -1 ? index : firstDeleted;
    }

    private Entry<K, V> findEntry(K key) {
        Objects.requireNonNull(key, "Ключ");
        Entry<K, V> entry = entryAt(findSlot(key));
        return entry == DELETED ? null : entry;
    }

    private void rehash(int newCapacity) {
        Entry<?, ?>[] previous = entries;
        entries = new Entry<?, ?>[newCapacity];
        for (Entry<?, ?> entry : previous) {
            if (entry != null && entry != DELETED) {
                @SuppressWarnings("unchecked")
                K key = (K) entry.key;
                entries[findSlot(key)] = entry;
            }
        }
        used = size;
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V> entryAt(int index) {
        return (Entry<K, V>) entries[index];
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
