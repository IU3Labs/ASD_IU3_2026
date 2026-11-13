package ru.bmstu.lab2.task_2a;

import java.util.function.BiConsumer;

/**
 * Задача 2A. Хеш-таблица с открытой адресацией и линейным пробированием.
 *
 * <p>Ключи и значения лежат в двух параллельных массивах {@code keys} и {@code values}.
 * Начальная ячейка ключа — {@code Math.abs(key.hashCode()) % capacity}; если она занята другим
 * ключом, проверяются следующие ячейки {@code +1, +2, ...} по кругу (линейное пробирование).
 *
 * <p>Ячейка может быть в трёх состояниях:
 * <ul>
 *   <li>{@code null} — пустая, в ней никогда ничего не было: поиск на ней останавливается;</li>
 *   <li>{@code DELETED} — «надгробие» на месте удалённого ключа: поиск идёт дальше, потому что
 *       искомый ключ мог быть вставлен после удалённого, а вставка может занять эту ячейку;</li>
 *   <li>ключ — занятая ячейка.</li>
 * </ul>
 *
 * <p>Коэффициент загрузки {@code size / capacity} не превышает 0.75: при
 * превышении ёмкость удваивается, все живые ключи перехешируются, надгробия выбрасываются.
 * Надгробия тоже учитываются в заполненности — иначе после множества удалений в таблице могло бы
 * не остаться пустых ячеек и поиск отсутствующего ключа просматривал бы всю таблицу. Если таблица
 * «засорена» в основном надгробиями, она перехешируется с той же ёмкостью
 * (подробнее — в комментарии к методу {@code ensureCapacityForInsert}).
 *
 * <p>Средняя сложность {@code put/get/containsKey/remove} — {@code O(1)}, худшая — {@code O(n)}
 * (все ключи в одной цепочке). Ключ {@code null} не поддерживается, значения {@code null} разрешены.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashMapCustom<K, V> {

    /** Начальная ёмкость по умолчанию. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Максимальный коэффициент загрузки из условия задачи. */
    private static final double MAX_LOAD_FACTOR = 0.75;

    /** Во сколько раз растёт таблица при превышении коэффициента загрузки. */
    private static final int GROWTH_FACTOR = 2;

    /** Маркер удалённой ячейки. Отдельный объект, поэтому не совпадёт ни с одним ключом. */
    private static final Object DELETED = new Object();

    private Object[] keys;
    private Object[] values;
    private int size;
    private int deletedCount;

    /** Создаёт пустую таблицу ёмкостью 16. */
    public HashMapCustom() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создаёт пустую таблицу заданной начальной ёмкости.
     *
     * @param initialCapacity начальная ёмкость, не меньше 1
     * @throws IllegalArgumentException если {@code initialCapacity < 1}
     */
    public HashMapCustom(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be positive: " + initialCapacity);
        }
        keys = new Object[initialCapacity];
        values = new Object[initialCapacity];
    }

    /**
     * Вставляет пару «ключ — значение» или обновляет значение существующего ключа.
     * Время: {@code O(1)} в среднем (амортизированно, с учётом перехеширования).
     *
     * @param key ключ, не {@code null}
     * @param value значение (может быть {@code null})
     * @throws NullPointerException если {@code key == null}
     */
    public void put(K key, V value) {
        int slot = findSlot(requireKey(key));
        if (slot >= 0) {
            values[slot] = value;
            return;
        }
        ensureCapacityForInsert();
        if (insertNew(keys, values, key, value)) {
            deletedCount--;
        }
        size++;
    }

    /**
     * Возвращает значение по ключу. Время: {@code O(1)} в среднем.
     *
     * @param key ключ, не {@code null}
     * @return значение или {@code null}, если ключа нет
     * @throws NullPointerException если {@code key == null}
     */
    public V get(K key) {
        return getOrDefault(key, null);
    }

    /**
     * Возвращает значение по ключу либо {@code defaultValue}, если ключа нет. Время: {@code O(1)} в среднем.
     *
     * @param key ключ, не {@code null}
     * @param defaultValue значение, возвращаемое для отсутствующего ключа
     * @return значение по ключу или {@code defaultValue}
     * @throws NullPointerException если {@code key == null}
     */
    public V getOrDefault(K key, V defaultValue) {
        int slot = findSlot(requireKey(key));
        return slot >= 0 ? valueAt(slot) : defaultValue;
    }

    /**
     * Проверяет наличие ключа. Время: {@code O(1)} в среднем.
     *
     * @param key ключ, не {@code null}
     * @return {@code true}, если ключ есть в таблице
     * @throws NullPointerException если {@code key == null}
     */
    public boolean containsKey(K key) {
        return findSlot(requireKey(key)) >= 0;
    }

    /**
     * Удаляет ключ: ячейка помечается маркером {@code DELETED}, чтобы не разорвать цепочки пробирования
     * других ключей. Время: {@code O(1)} в среднем.
     *
     * @param key ключ, не {@code null}
     * @return {@code true}, если ключ был и удалён; {@code false}, если его не было
     * @throws NullPointerException если {@code key == null}
     */
    public boolean remove(K key) {
        int slot = findSlot(requireKey(key));
        if (slot < 0) {
            return false;
        }
        keys[slot] = DELETED;
        values[slot] = null;
        size--;
        deletedCount++;
        return true;
    }

    /**
     * Возвращает количество пар в таблице. Время: {@code O(1)}.
     *
     * @return число хранимых ключей
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли таблица. Время: {@code O(1)}.
     *
     * @return {@code true}, если ключей нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает текущую ёмкость (длину внутреннего массива). Время: {@code O(1)}.
     *
     * @return число ячеек таблицы
     */
    public int capacity() {
        return keys.length;
    }

    /**
     * Вызывает {@code action} для каждой пары «ключ — значение» в порядке ячеек таблицы
     * (порядок не определён). Время: {@code O(capacity)}.
     *
     * @param action действие над парой
     */
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (int slot = 0; slot < keys.length; slot++) {
            if (isLive(keys[slot])) {
                action.accept(keyAt(slot), valueAt(slot));
            }
        }
    }

    /**
     * Начальная ячейка ключа по формуле из условия: {@code Math.abs(hashCode) % capacity}.
     * {@code Math.abs(Integer.MIN_VALUE)} отрицателен, поэтому отрицательный остаток
     * приводится к диапазону {@code [0, capacity)}.
     */
    private static int indexFor(Object key, int capacity) {
        int index = Math.abs(key.hashCode()) % capacity;
        return index < 0 ? index + capacity : index;
    }

    /**
     * Ищет ячейку с ключом {@code key}, двигаясь по цепочке пробирования до первой пустой ячейки.
     *
     * @return индекс ячейки или {@code -1}, если ключа нет
     */
    private int findSlot(Object key) {
        int capacity = keys.length;
        int start = indexFor(key, capacity);
        for (int step = 0; step < capacity; step++) {
            int slot = (start + step) % capacity;
            Object current = keys[slot];
            if (current == null) {
                return -1;
            }
            if (current != DELETED && current.equals(key)) {
                return slot;
            }
        }
        return -1;
    }

    /**
     * Кладёт заведомо отсутствующий ключ в первую свободную ячейку его цепочки
     * (пустую или {@link #DELETED}). Свободная ячейка всегда есть: загрузка не больше 0.75.
     *
     * @return {@code true}, если занято место надгробия
     */
    private static boolean insertNew(Object[] keys, Object[] values, Object key, Object value) {
        int capacity = keys.length;
        int slot = indexFor(key, capacity);
        while (keys[slot] != null && keys[slot] != DELETED) {
            slot = (slot + 1) % capacity;
        }
        boolean reusedDeleted = keys[slot] == DELETED;
        keys[slot] = key;
        values[slot] = value;
        return reusedDeleted;
    }

    /**
     * Перед вставкой нового ключа проверяет заполненность таблицы с учётом надгробий. Если после вставки
     * она превысит {@code 0.75 * capacity}, таблица перехешируется: ёмкость удваивается, когда живых ключей
     * больше половины порога, иначе достаточно выбросить надгробия при той же ёмкости. Без удвоения во
     * втором случае таблица, заполненная живыми ключами почти до порога, перехешировалась бы через каждые
     * несколько пар «удалить + вставить» — по {@code O(capacity)} за раз.
     */
    private void ensureCapacityForInsert() {
        int capacity = keys.length;
        if (size + deletedCount + 1 <= capacity * MAX_LOAD_FACTOR) {
            return;
        }
        boolean crowded = size + 1 > capacity * MAX_LOAD_FACTOR / GROWTH_FACTOR;
        rehash(crowded ? capacity * GROWTH_FACTOR : capacity);
    }

    /** Переносит все живые пары в новые массивы ёмкости {@code newCapacity}: {@code O(capacity)}. */
    private void rehash(int newCapacity) {
        Object[] newKeys = new Object[newCapacity];
        Object[] newValues = new Object[newCapacity];
        for (int slot = 0; slot < keys.length; slot++) {
            if (isLive(keys[slot])) {
                insertNew(newKeys, newValues, keys[slot], values[slot]);
            }
        }
        keys = newKeys;
        values = newValues;
        deletedCount = 0;
    }

    private static boolean isLive(Object slotKey) {
        return slotKey != null && slotKey != DELETED;
    }

    private static Object requireKey(Object key) {
        if (key == null) {
            throw new NullPointerException("HashMapCustom does not permit null keys");
        }
        return key;
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
