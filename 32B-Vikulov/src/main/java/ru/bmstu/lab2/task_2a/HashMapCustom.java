package ru.bmstu.lab2.task_2a;

import java.util.function.BiConsumer;

/**
 * Задача 2A. Хеш-таблица с открытой адресацией и линейным пробированием.
 *
 * <p>Ключи и значения лежат в двух параллельных массивах. Ячейка ключа бывает в трёх состояниях:
 * {@code null} — пустая (никогда не занималась), {@link #DELETED} — удалённая, иначе — занятая.
 * Индекс ключа: {@code |hashCode % capacity|}; при коллизии проверяются ячейки {@code index + 1, index + 2, …}
 * по кругу, пока не встретится искомый ключ или пустая ячейка.
 *
 * <p>Удалённую ячейку нельзя сделать пустой: тогда поиск ключа, вставленного «дальше» по той же цепочке
 * пробирования, остановился бы на ней и не нашёл бы ключ. Поэтому ставится маркер {@code DELETED}:
 * поиск через него проходит, а вставка может его переиспользовать.
 *
 * <p>Коэффициент загрузки держится не выше {@value #MAX_LOAD_FACTOR}: при превышении ёмкость удваивается
 * и все элементы перехешируются. Так в таблице всегда есть пустые ячейки, и цепочки пробирования короткие:
 * {@code put}, {@code get}, {@code containsKey}, {@code remove} работают за O(1) в среднем (O(n) в худшем
 * случае, когда все ключи попали в одну цепочку), {@code size} и {@code capacity} — за O(1).
 *
 * @param <K> тип ключа ({@code null} не допускается)
 * @param <V> тип значения ({@code null} допускается)
 */
public class HashMapCustom<K, V> {

    /** Ёмкость таблицы, созданной конструктором без параметров. */
    public static final int DEFAULT_CAPACITY = 16;

    /** Максимальная доля занятых ячеек (живые элементы + маркеры {@code DELETED}). */
    public static final double MAX_LOAD_FACTOR = 0.75;

    /** Маркер удалённой ячейки; сравнивается по ссылке, поэтому не совпадёт ни с одним ключом. */
    private static final Object DELETED = new Object();

    private Object[] keys;
    private Object[] values;
    /** Число живых элементов. */
    private int size;
    /** Число непустых ячеек: живые элементы + маркеры {@code DELETED}. */
    private int occupied;

    /** Создаёт пустую таблицу ёмкости {@value #DEFAULT_CAPACITY}. */
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
            throw new IllegalArgumentException("Capacity must be positive: " + initialCapacity);
        }
        keys = new Object[initialCapacity];
        values = new Object[initialCapacity];
    }

    /**
     * Вставляет пару или обновляет значение существующего ключа. O(1) в среднем.
     *
     * @param key   ключ, не {@code null}
     * @param value значение
     * @throws NullPointerException если ключ {@code null}
     */
    public void put(K key, V value) {
        requireKey(key);
        int index = findIndex(key);
        if (index >= 0) {
            values[index] = value;
            return;
        }
        ensureRoomForInsert();
        insertAbsent(key, value);
    }

    /**
     * Возвращает значение по ключу. O(1) в среднем.
     *
     * @param key ключ, не {@code null}
     * @return значение или {@code null}, если ключа нет
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        requireKey(key);
        int index = findIndex(key);
        return index >= 0 ? (V) values[index] : null;
    }

    /**
     * Проверяет наличие ключа. O(1) в среднем.
     *
     * @param key ключ, не {@code null}
     * @return {@code true}, если ключ есть в таблице
     */
    public boolean containsKey(K key) {
        requireKey(key);
        return findIndex(key) >= 0;
    }

    /**
     * Удаляет ключ, оставляя на его месте маркер {@code DELETED}. O(1) в среднем.
     *
     * @param key ключ, не {@code null}
     * @return {@code true}, если ключ был удалён; {@code false}, если его не было
     */
    public boolean remove(K key) {
        requireKey(key);
        int index = findIndex(key);
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /**
     * Возвращает число элементов. O(1).
     *
     * @return количество пар ключ-значение
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли таблица. O(1).
     *
     * @return {@code true}, если элементов нет
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает текущую ёмкость — длину внутреннего массива. O(1).
     *
     * @return ёмкость таблицы
     */
    public int capacity() {
        return keys.length;
    }

    /**
     * Передаёт каждую пару в {@code action}. Порядок обхода — порядок ячеек таблицы, а не порядок вставки.
     * O(capacity).
     *
     * @param action действие над парой ключ-значение
     */
    @SuppressWarnings("unchecked")
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (int i = 0; i < keys.length; i++) {
            if (isLive(keys[i])) {
                action.accept((K) keys[i], (V) values[i]);
            }
        }
    }

    /**
     * Индекс ячейки, с которой начинается пробирование. Остаток берётся до {@code Math.abs}: у
     * {@code Integer.MIN_VALUE} модуль не представим в {@code int} и остался бы отрицательным,
     * а остаток от деления на ёмкость по модулю всегда меньше ёмкости.
     */
    private int indexFor(Object key) {
        return Math.abs(key.hashCode() % keys.length);
    }

    /** Ищет ячейку с ключом; возвращает её индекс или {@code -1}. */
    private int findIndex(Object key) {
        int index = indexFor(key);
        // не больше capacity шагов: каждая ячейка проверяется не более одного раза
        for (int probe = 0; probe < keys.length; probe++) {
            Object current = keys[index];
            if (current == null) {
                return -1;
            }
            if (current != DELETED && current.equals(key)) {
                return index;
            }
            index = (index + 1) % keys.length;
        }
        return -1;
    }

    /** Кладёт ключ, которого точно нет в таблице, в первую пустую или удалённую ячейку цепочки. */
    private void insertAbsent(Object key, Object value) {
        int index = indexFor(key);
        while (isLive(keys[index])) {
            index = (index + 1) % keys.length;
        }
        if (keys[index] == null) {
            occupied++;
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    /**
     * Перед вставкой проверяет загрузку. Если после вставки непустых ячеек станет больше
     * {@code capacity · MAX_LOAD_FACTOR}: при большом числе живых элементов ёмкость удваивается, иначе
     * загрузку создают маркеры {@code DELETED}, и таблица перестраивается в той же ёмкости, чтобы их убрать.
     */
    private void ensureRoomForInsert() {
        double threshold = keys.length * MAX_LOAD_FACTOR;
        if (occupied + 1 <= threshold) {
            return;
        }
        int newCapacity = size + 1 > threshold ? keys.length * 2 : keys.length;
        rehash(newCapacity);
    }

    /** Переносит все живые элементы в новые массивы; маркеры {@code DELETED} при этом исчезают. O(capacity). */
    private void rehash(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        size = 0;
        occupied = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            if (isLive(oldKeys[i])) {
                insertAbsent(oldKeys[i], oldValues[i]);
            }
        }
    }

    private static boolean isLive(Object key) {
        return key != null && key != DELETED;
    }

    private static void requireKey(Object key) {
        if (key == null) {
            throw new NullPointerException("Key must not be null");
        }
    }
}
