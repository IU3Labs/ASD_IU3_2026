package ru.bmstu.lab2;

/**
 * Хеш-таблица с линейным пробированием. Ключи должны быть отличны от {@code null}.
 * Значения могут быть {@code null}. Маркер {@code DELETED} сохраняет цепочку поиска после удаления.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public final class HashMapCustom<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;
    private static final double MAX_USED_FACTOR = 0.875;
    private static final Object DELETED = new Object();

    private Object[] keys = new Object[INITIAL_CAPACITY];
    private Object[] values = new Object[INITIAL_CAPACITY];
    private int size;
    private int usedSlots;

    /**
     * Добавляет запись или обновляет значение существующего ключа.
     * При превышении коэффициента загрузки ёмкость удваивается.
     *
     * @param key ключ
     * @param value значение
     * @throws NullPointerException если ключ равен {@code null}
     */
    public void put(K key, V value) {
        int slot = findSlot(key, keys);
        if (slot >= 0) {
            values[slot] = value;
            return;
        }

        int index = ~slot;
        if (size + 1 > keys.length * MAX_LOAD_FACTOR) {
            rehash(Math.multiplyExact(keys.length, 2));
            index = ~findSlot(key, keys);
        } else if (keys[index] == null && usedSlots + 1 > keys.length * MAX_USED_FACTOR) {
            // Очищаем маркеры, чтобы поиск всегда мог закончиться на пустой ячейке.
            rehash(keys.length);
            index = ~findSlot(key, keys);
        }

        if (keys[index] == null) {
            usedSlots++;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    /**
     * Ищет значение по ключу.
     *
     * @param key ключ
     * @return значение или {@code null}, если ключ отсутствует
     * @throws NullPointerException если ключ равен {@code null}
     */
    public V get(K key) {
        int index = findSlot(key, keys);
        return index >= 0 ? valueAt(index) : null;
    }

    /**
     * Проверяет наличие ключа, в том числе ключа со значением {@code null}.
     *
     * @param key ключ
     * @return {@code true}, если ключ присутствует
     * @throws NullPointerException если ключ равен {@code null}
     */
    public boolean containsKey(K key) {
        return findSlot(key, keys) >= 0;
    }

    /**
     * Удаляет запись по ключу и оставляет маркер для последующего поиска.
     *
     * @param key ключ
     * @return {@code true}, если запись была удалена
     * @throws NullPointerException если ключ равен {@code null}
     */
    public boolean remove(K key) {
        int index = findSlot(key, keys);
        if (index < 0) {
            return false;
        }

        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /**
     * Возвращает число записей.
     *
     * @return размер таблицы
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает число ячеек таблицы.
     *
     * @return текущая ёмкость
     */
    public int capacity() {
        return keys.length;
    }

    // Для нового ключа возвращаем побитовое дополнение индекса места вставки.
    private static int findSlot(Object key, Object[] table) {
        int index = (int) (Math.abs((long) key.hashCode()) % table.length);
        int firstDeleted = -1;
        while (table[index] != null) {
            Object current = table[index];
            if (current == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = index;
                }
            } else if (key.equals(current)) {
                return index;
            }
            index = (index + 1) % table.length;
        }
        return ~(firstDeleted >= 0 ? firstDeleted : index);
    }

    private void rehash(int newCapacity) {
        Object[] newKeys = new Object[newCapacity];
        Object[] newValues = new Object[newCapacity];
        for (int index = 0; index < keys.length; index++) {
            Object key = keys[index];
            if (key != null && key != DELETED) {
                int newIndex = ~findSlot(key, newKeys);
                newKeys[newIndex] = key;
                newValues[newIndex] = values[index];
            }
        }
        keys = newKeys;
        values = newValues;
        usedSlots = size;
    }

    @SuppressWarnings("unchecked")
    private V valueAt(int index) {
        return (V) values[index];
    }
}
