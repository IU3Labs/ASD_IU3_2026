package ru.bmstu.lab02;

/**
 * Хеш-таблица с открытой адресацией и линейным пробированием.
 *
 * Хеш-функция: Math.abs(key.hashCode()) % capacity.
 * Коэффициент загрузки не превышает 0.75; при превышении ёмкость увеличивается
 * в 2 раза с перехешированием всех элементов.
 * Удалённые ячейки помечаются маркером DELETED, чтобы не рвать цепочки пробирования.
 *
 * Асимптотика put/get/containsKey/remove: O(1) в среднем (O(n) в худшем случае
 * при массовых коллизиях). size() и capacity() — O(1).
 *
 * Ограничения: null-ключи не поддерживаются (NullPointerException),
 * null-значения допустимы (отличить их от отсутствия ключа можно через containsKey).
 */
public class HashMapCustom<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double MAX_LOAD_FACTOR = 0.75;
    /** Перестройка на месте допустима, если живых элементов не больше 1/этого от порога загрузки. */
    private static final int IN_PLACE_REHASH_DIVISOR = 2;

    /** Маркер удалённой ячейки. Сравнивается только по ссылке. */
    private static final Object DELETED = new Object();

    private Object[] keys;
    private Object[] values;

    /** Число живых элементов. */
    private int size;
    /** Число занятых ячеек: живые элементы + маркеры DELETED. */
    private int used;

    /** Создаёт пустую таблицу с начальной ёмкостью по умолчанию. */
    public HashMapCustom() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создаёт пустую таблицу с заданной начальной ёмкостью.
     *
     * @throws IllegalArgumentException если ёмкость меньше 1
     */
    public HashMapCustom(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be positive: " + initialCapacity);
        }
        this.keys = new Object[initialCapacity];
        this.values = new Object[initialCapacity];
    }

    /** Вставка или обновление значения по ключу. */
    public void put(K key, V value) {
        requireNonNullKey(key);

        int existing = findIndex(key);
        if (existing >= 0) {
            values[existing] = value;
            return;
        }

        ensureCapacityForInsert();

        // Ключа в таблице нет: берём первую свободную или удалённую ячейку цепочки.
        int index = hash(key, keys.length);
        while (keys[index] != null && keys[index] != DELETED) {
            index = (index + 1) % keys.length;
        }
        if (keys[index] == null) {
            used++; // занимаем ранее пустую ячейку (повторное использование DELETED счётчик не меняет)
        }
        keys[index] = key;
        values[index] = value;
        size++;
    }

    /** Значение по ключу либо null, если ключа нет. */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        requireNonNullKey(key);
        int index = findIndex(key);
        return index >= 0 ? (V) values[index] : null;
    }

    /** Возвращает true, если ключ есть в таблице. */
    public boolean containsKey(K key) {
        requireNonNullKey(key);
        return findIndex(key) >= 0;
    }

    /** Удаляет ключ. Возвращает true, если ключ был в таблице. */
    public boolean remove(K key) {
        requireNonNullKey(key);
        int index = findIndex(key);
        if (index < 0) {
            return false;
        }
        keys[index] = DELETED;
        values[index] = null;
        size--;
        return true;
    }

    /** Количество живых элементов. O(1). */
    public int size() {
        return size;
    }

    /** Текущая ёмкость массива (число ячеек). O(1). */
    public int capacity() {
        return keys.length;
    }

    // ------------------------------------------------------------------
    // Внутренние методы
    // ------------------------------------------------------------------

    /**
     * Ищет ключ. Возвращает индекс ячейки или -1.
     * Поиск идёт до первой пустой ячейки (DELETED пропускаются).
     * Цикл ограничен ёмкостью, поэтому завершается даже если пустых ячеек нет.
     */
    private int findIndex(Object key) {
        int capacity = keys.length;
        int index = hash(key, capacity);
        for (int probes = 0; probes < capacity; probes++) {
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

    /** Math.abs(hashCode) % capacity; случай Integer.MIN_VALUE (abs < 0) обработан отдельно. */
    private static int hash(Object key, int capacity) {
        int h = key.hashCode();
        h = (h == Integer.MIN_VALUE) ? 0 : Math.abs(h);
        return h % capacity;
    }

    private static int threshold(int capacity) {
        return (int) (capacity * MAX_LOAD_FACTOR);
    }

    /**
     * Перед вставкой нового ключа гарантирует, что доля занятых ячеек (с учётом DELETED)
     * не превысит 0.75. Это же гарантирует наличие пустых ячеек, на которых останавливается поиск.
     *
     * Если живых элементов слишком много, ёмкость удваивается. Если мешают маркеры DELETED,
     * таблица перестраивается с той же ёмкостью, но только когда живых элементов не больше половины порога.
     * Иначе перестройка на месте повторялась бы после каждой пары put/remove и стоила бы O(n) за операцию;
     * при удвоении между перестройками остаётся не меньше половины порога вставок, и стоимость
     * амортизируется до O(1).
     */
    private void ensureCapacityForInsert() {
        if (used + 1 <= threshold(keys.length)) {
            return;
        }
        int newCapacity = keys.length;
        while (size + 1 > threshold(newCapacity)) {
            newCapacity *= 2;
        }
        if (newCapacity == keys.length && size + 1 > threshold(newCapacity) / IN_PLACE_REHASH_DIVISOR) {
            newCapacity *= 2;
        }
        rehash(newCapacity);
    }

    private void rehash(int newCapacity) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;

        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        used = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            Object key = oldKeys[i];
            if (key != null && key != DELETED) {
                int index = hash(key, newCapacity);
                while (keys[index] != null) {
                    index = (index + 1) % newCapacity;
                }
                keys[index] = key;
                values[index] = oldValues[i];
                used++;
            }
        }
        // size не меняется: переносим только живые элементы
    }

    private static void requireNonNullKey(Object key) {
        if (key == null) {
            throw new NullPointerException("Null keys are not allowed");
        }
    }
}
