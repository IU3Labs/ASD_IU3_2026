package ru.bmstu.lab02;

/**
 * Хеш-таблица с открытой адресацией (линейное пробирование).
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashMapCustom<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final Object DELETED = new Object();

    private Object[] keys;
    private Object[] values;
    private int size;
    private int deletedCount;
    private int cap;

    // Создаёт пустую хеш-таблицу с начальной ёмкостью 16
    public HashMapCustom() {
        this.cap = DEFAULT_CAPACITY;
        this.keys = new Object[cap];
        this.values = new Object[cap];
        this.size = 0;
        this.deletedCount = 0;
    }

    /**
     * Вставляет пару ключ-значение или обновляет значение существующего ключа.
     *
     * @param key   ключ (не может быть null)
     * @param value значение
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        int idx = hash(key);
        int firstDeleted = -1;

        for (int i = 0; i < cap; i++) {
            int pos = (idx + i) % cap;
            if (keys[pos] == null) {
                int target;
                if (firstDeleted != -1) {
                    target = firstDeleted;
                    deletedCount--;
                } else {
                    target = pos;
                }
                keys[target] = key;
                values[target] = value;
                size++;
                if ((double) (size + deletedCount) / cap > LOAD_FACTOR) {
                    resize();
                }
                return;
            }
            if (keys[pos] == DELETED) {
                if (firstDeleted == -1) {
                    firstDeleted = pos;
                }
            } else if (keys[pos].equals(key)) {
                values[pos] = value;
                return;
            }
        }
    }

    /**
     * Возвращает значение по ключу или null, если ключ не найден
     *
     * @param key ключ для поиска
     * @return значение или null
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int pos = findIndex(key);
        if (pos == -1) {
            return null;
        }
        return (V) values[pos];
    }

    /**
     * Проверяет наличие ключа в таблице.
     *
     * @param key ключ для проверки
     * @return true, если ключ присутствует
     */
    public boolean containsKey(K key) {
        return findIndex(key) != -1;
    }

    /**
     * Удаляет запись по ключу, помечая ячейку маркером DELETED
     *
     * @param key удаляемый ключ
     * @return true, если ключ найден и удалён
     */
    public boolean remove(K key) {
        int pos = findIndex(key);
        if (pos == -1) {
            return false;
        }
        keys[pos] = DELETED;
        values[pos] = null; // очищаем ссылку на значение
        size--;
        deletedCount++;
        return true;
    }

    /**
     * Возвращает количество активных элементов в таблице.
     *
     * @return размер таблицы
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает текущую ёмкость внутреннего массива.
     *
     * @return ёмкость таблицы
     */
    public int capacity() {
        return cap;
    }

    private int findIndex(K key) {
        if (key == null) {
            return -1;
        }
        int idx = hash(key);
        for (int i = 0; i < cap; i++) {
            int pos = (idx + i) % cap;
            if (keys[pos] == null) {
                return -1;
            }
            if (keys[pos] != DELETED && keys[pos].equals(key)) {
                return pos;
            }
        }
        return -1;
    }

    private int hash(K key) {
        int h = Math.abs(key.hashCode()) % cap;
        if (h < 0) {
            return 0; // защита от Integer.MIN_VALUE
        }
        return h;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        cap = cap * 2;
        keys = new Object[cap];
        values = new Object[cap];
        size = 0;
        deletedCount = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                put((K) oldKeys[i], (V) oldValues[i]);
            }
        }
    }
}