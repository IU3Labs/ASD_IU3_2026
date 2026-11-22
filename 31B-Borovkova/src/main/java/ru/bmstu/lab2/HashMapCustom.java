/*Задача 2A. Хеш-таблица с линейным пробированием
put, get, containsKey, remove, size, capacity.
Хеш-функция: Math.abs(key.hashCode()) % capacity. Коэффициент загрузки не более 0.75,
при превышении — увеличение ёмкости в 2 раза с перехешированием.
Удалённые ячейки помечаются маркером DELETED, чтобы не ломать цепочки пробирования. */

package ru.bmstu.lab2;

import java.util.Objects;

/**
 * Хеш-таблица с открытой адресацией и линейным пробированием: при коллизии проверяется
 * следующая ячейка {@code (index + 1) % capacity}. Ключи {@code null} не допускаются,
 * значения {@code null} допускаются (отличить их от отсутствия ключа можно через {@link #containsKey}).
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashMapCustom<K, V> {
    /** Начальная ёмкость по умолчанию. */
    private static final int DEFAULT_CAPACITY = 16;
    /** Наибольший допустимый коэффициент загрузки. */
    private static final double MAX_LOAD_FACTOR = 0.75;
    /** Во сколько раз увеличивается ёмкость при превышении загрузки. */
    private static final int GROW_FACTOR = 2;
    /** Маркер удалённой ячейки: поиск идёт через неё дальше, вставка может её занять. */
    private static final Object DELETED = new Object();

    /** Ключи; {@code null} — пустая ячейка, {@link #DELETED} — удалённая. */
    private Object[] keys;
    /** Значения, хранятся в тех же индексах, что и ключи. */
    private Object[] values;
    /** Количество живых пар ключ–значение. */
    private int size;
    /** Количество ячеек с маркером {@link #DELETED}. */
    private int deleted;

    /**
     * Создаёт пустую таблицу ёмкостью {@value #DEFAULT_CAPACITY}.
     */
    public HashMapCustom() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создаёт пустую таблицу заданной ёмкости.
     *
     * @param initialCapacity начальная ёмкость, не меньше 1
     * @throws IllegalArgumentException если ёмкость меньше 1
     */
    public HashMapCustom(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Ёмкость должна быть не меньше 1: " + initialCapacity);
        }
        keys = new Object[initialCapacity];
        values = new Object[initialCapacity];
    }

    /**
     * Вставляет пару или обновляет значение существующего ключа.
     * В среднем O(1), при перехешировании — O(n) (амортизированно O(1)).
     *
     * @param key   ключ
     * @param value значение
     * @throws NullPointerException если ключ равен {@code null}
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "key");
        int found = find(key);
        if (found >= 0) {
            values[found] = value; // ключ уже есть — только обновляем значение
            return;
        }
        ensureCapacityForInsert();
        int slot = freeSlot(key);
        if (keys[slot] == DELETED) {
            deleted--; // переиспользуем удалённую ячейку
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    /**
     * Возвращает значение по ключу, в среднем O(1).
     *
     * @param key ключ
     * @return значение либо {@code null}, если ключа нет
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        if (key == null) {
            return null;
        }
        int found = find(key);
        return found >= 0 ? (V) values[found] : null;
    }

    /**
     * Проверяет наличие ключа, в среднем O(1).
     *
     * @param key ключ
     * @return {@code true}, если ключ есть в таблице
     */
    public boolean containsKey(K key) {
        return key != null && find(key) >= 0;
    }

    /**
     * Удаляет ключ, помечая ячейку маркером {@code DELETED}, в среднем O(1).
     *
     * @param key ключ
     * @return {@code true}, если ключ был и удалён
     */
    public boolean remove(K key) {
        if (key == null) {
            return false;
        }
        int found = find(key);
        if (found < 0) {
            return false;
        }
        keys[found] = DELETED;
        values[found] = null;
        size--;
        deleted++;
        return true;
    }

    /**
     * Возвращает количество пар, O(1).
     *
     * @return размер таблицы
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает текущую ёмкость (число ячеек массива), O(1).
     *
     * @return ёмкость таблицы
     */
    public int capacity() {
        return keys.length;
    }

    /**
     * Начальный индекс ключа: Math.abs(hashCode) % capacity.
     * Math.abs(Integer.MIN_VALUE) остаётся отрицательным, поэтому остаток может быть
     * отрицательным — в этом случае сдвигаем его в диапазон [0, capacity).
     */
    private int indexFor(Object key, int capacity) {
        int index = Math.abs(key.hashCode()) % capacity;
        return index < 0 ? index + capacity : index;
    }

    /**
     * Ищет ячейку с ключом: идём по цепочке, пропуская DELETED, до пустой ячейки.
     *
     * @return индекс ключа либо -1
     */
    private int find(Object key) {
        int capacity = keys.length;
        int index = indexFor(key, capacity);
        // Пустые ячейки всегда есть (загрузка <= 0.75), но ограничиваем обход на всякий случай
        for (int step = 0; step < capacity; step++) {
            Object current = keys[index];
            if (current == null) {
                return -1;
            }
            if (current != DELETED && current.equals(key)) {
                return index;
            }
            index = (index + 1) % capacity;
        }
        return -1;
    }

    /**
     * Ищет первую свободную (пустую или удалённую) ячейку на цепочке ключа.
     * Вызывается только для отсутствующего ключа.
     */
    private int freeSlot(Object key) {
        int capacity = keys.length;
        int index = indexFor(key, capacity);
        while (keys[index] != null && keys[index] != DELETED) {
            index = (index + 1) % capacity;
        }
        return index;
    }

    /**
     * Готовит таблицу к вставке нового ключа.
     * Если живых элементов станет больше 0.75 * capacity — ёмкость удваивается.
     * Если превышение только из-за маркеров DELETED — перехешируем в ту же ёмкость,
     * чтобы очистить маркеры: так в таблице всегда остаются пустые ячейки и поиск конечен.
     */
    private void ensureCapacityForInsert() {
        int capacity = keys.length;
        if (size + 1 > capacity * MAX_LOAD_FACTOR) {
            rehash(capacity * GROW_FACTOR);
        } else if (size + deleted + 1 > capacity * MAX_LOAD_FACTOR) {
            rehash(capacity);
        }
    }

    /**
     * Перекладывает все живые пары в новые массивы заданной ёмкости, O(n).
     */
    private void rehash(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        deleted = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            Object key = oldKeys[i];
            if (key != null && key != DELETED) {
                int slot = freeSlot(key); // в новой таблице маркеров нет — это первая пустая ячейка
                keys[slot] = key;
                values[slot] = oldValues[i];
            }
        }
    }
}
