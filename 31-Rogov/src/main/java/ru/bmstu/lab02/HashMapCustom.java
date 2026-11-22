package ru.bmstu.lab02;

import java.util.Objects;

/** Хеш-таблица с открытой адресацией, линейным пробированием и маркерами удаления. */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final Entry<?, ?> DELETED = new Entry<>(null, null);

    private Entry<?, ?>[] entries = new Entry<?, ?>[INITIAL_CAPACITY];
    private int size;
    private int usedSlots;

    /** Создаёт пустую хеш-таблицу. */
    public HashMapCustom() {
    }

    /** Вставляет ключ или обновляет его значение. Допускает ключ и значение null. */
    public void put(K key, V value) {
        int index = findSlot(key);
        if (isOccupied(entries[index])) {
            entryAt(index).value = value;
            return;
        }

        int capacity = entries.length;
        if (size == capacity - capacity / 4) {
            rehash(Math.multiplyExact(capacity, 2));
            index = findSlot(key);
        } else if (entries[index] == null && usedSlots == capacity - capacity / 8) {
            // Очистка маркеров удерживает долю ячеек, участвующих в пробировании, до 7/8.
            rehash(capacity);
            index = findSlot(key);
        }

        if (entries[index] == null) {
            usedSlots++;
        }
        entries[index] = new Entry<>(key, value);
        size++;
    }

    /** Возвращает значение по ключу либо null, если ключ отсутствует. */
    public V get(K key) {
        Entry<K, V> entry = entryAt(findSlot(key));
        return isOccupied(entry) ? entry.value : null;
    }

    /** Проверяет наличие ключа, в том числе со значением null. */
    public boolean containsKey(K key) {
        return isOccupied(entries[findSlot(key)]);
    }

    /** Удаляет ключ и возвращает true, если он присутствовал. */
    public boolean remove(K key) {
        int index = findSlot(key);
        if (!isOccupied(entries[index])) {
            return false;
        }
        entries[index] = DELETED;
        size--;
        return true;
    }

    /** Возвращает число сохранённых пар. */
    public int size() {
        return size;
    }

    /** Возвращает текущее число ячеек массива. */
    public int capacity() {
        return entries.length;
    }

    private int findSlot(Object key) {
        int capacity = entries.length;
        int index = (int) (Math.abs((long) Objects.hashCode(key)) % capacity);
        int firstDeleted = -1;
        while (entries[index] != null) {
            Entry<?, ?> entry = entries[index];
            if (entry == DELETED) {
                if (firstDeleted == -1) {
                    firstDeleted = index;
                }
            } else if (Objects.equals(key, entry.key)) {
                return index;
            }
            // Поиск проходит за маркер, где может находиться уже существующий ключ.
            index = (index + 1) % capacity;
        }
        return firstDeleted == -1 ? index : firstDeleted;
    }

    private void rehash(int capacity) {
        Entry<?, ?>[] previous = entries;
        entries = new Entry<?, ?>[capacity];
        for (Entry<?, ?> entry : previous) {
            if (isOccupied(entry)) {
                entries[findSlot(entry.key)] = entry;
            }
        }
        usedSlots = size;
    }

    private boolean isOccupied(Entry<?, ?> entry) {
        return entry != null && entry != DELETED;
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
