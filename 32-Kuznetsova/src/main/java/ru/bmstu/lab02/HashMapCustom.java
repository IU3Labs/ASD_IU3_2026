package ru.bmstu.lab02;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Хеш-таблица с открытой адресацией (линейное или квадратичное пробирование).
 *
 * <p>Ёмкость всегда степень двойки. Индекс ячейки: {@code Math.abs(key.hashCode()) % capacity}, далее
 * {@code (index + c1*i + c2*i^2) % capacity}, где для линейного пробирования {@code c1 = 1, c2 = 0}, а для
 * квадратичного {@code c1 = 1, c2 = 2}. При таких константах и ёмкости 2^k последовательность проб за
 * {@code capacity} шагов обходит каждую ячейку ровно один раз (см. {@code README.md}), поэтому поиск не
 * зацикливается и всегда находит свободную ячейку, пока коэффициент загрузки меньше 1.
 *
 * <p>Удалённые ячейки помечаются маркером {@code DELETED}, чтобы не рвать цепочки пробирования.
 * Ключи {@code null} не поддерживаются.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashMapCustom<K, V> {

    /** Способ разрешения коллизий. */
    public enum Probing {
        /** Шаг {@code i}: смещение {@code i}. */
        LINEAR,
        /** Шаг {@code i}: смещение {@code i + 2*i^2}. */
        QUADRATIC
    }

    /** Маркер удалённой ячейки: такая ячейка не пуста для поиска, но свободна для вставки. */
    private static final Object DELETED = new Object();

    private static final int MIN_CAPACITY = 4;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int GROWTH_FACTOR = 2;
    private static final int QUADRATIC_C1 = 1;
    private static final int QUADRATIC_C2 = 2;

    private final Probing probing;
    private final double loadFactor;
    private Object[] keys;
    private Object[] values;
    private int size;
    /** Занятые ячейки плюс {@code DELETED}: именно они сокращают число пустых ячеек для поиска. */
    private int used;

    private int lastProbeCount;
    private long lookupCount;
    private long lookupProbeTotal;

    /** Линейное пробирование, ёмкость 16, порог загрузки 0.75. */
    public HashMapCustom() {
        this(Probing.LINEAR);
    }

    /** Заданное пробирование, ёмкость 16, порог загрузки 0.75. */
    public HashMapCustom(Probing probing) {
        this(probing, DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Полностью настраиваемая таблица.
     *
     * @param probing         способ пробирования
     * @param initialCapacity желаемая начальная ёмкость; округляется вверх до степени двойки (минимум 4)
     * @param loadFactor      порог загрузки, строго между 0 и 1
     */
    public HashMapCustom(Probing probing, int initialCapacity, double loadFactor) {
        this.probing = Objects.requireNonNull(probing, "probing");
        if (!(loadFactor > 0 && loadFactor < 1)) {
            throw new IllegalArgumentException("Порог загрузки должен быть в интервале (0, 1): " + loadFactor);
        }
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Ёмкость должна быть положительной: " + initialCapacity);
        }
        this.loadFactor = loadFactor;
        int capacity = MIN_CAPACITY;
        while (capacity < initialCapacity) {
            capacity *= GROWTH_FACTOR;
        }
        this.keys = new Object[capacity];
        this.values = new Object[capacity];
    }

    /**
     * Вставляет пару или обновляет значение существующего ключа; амортизированно O(1).
     *
     * @param key   ключ, не {@code null}
     * @param value значение (допускается {@code null})
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "key");
        int found = locate(key);
        if (found >= 0) {
            values[found] = value;
            return;
        }
        int slot = -found - 1;
        if (keys[slot] == null && used + 1 > threshold(keys.length)) {
            rehash();
            slot = -locate(key) - 1;
        }
        if (keys[slot] == null) {
            used++;
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
    }

    /**
     * Возвращает значение по ключу; в среднем O(1).
     *
     * @param key ключ
     * @return значение или {@code null}, если ключа нет
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int found = lookup(key);
        return found >= 0 ? (V) values[found] : null;
    }

    /**
     * Проверяет наличие ключа; в среднем O(1).
     *
     * @param key ключ
     * @return {@code true}, если ключ есть в таблице
     */
    public boolean containsKey(K key) {
        return lookup(key) >= 0;
    }

    /**
     * Удаляет ключ, оставляя на его месте маркер {@code DELETED}; в среднем O(1).
     *
     * @param key ключ
     * @return {@code true}, если ключ был в таблице
     */
    public boolean remove(K key) {
        Objects.requireNonNull(key, "key");
        int found = locate(key);
        if (found < 0) {
            return false;
        }
        keys[found] = DELETED;
        values[found] = null;
        size--;
        return true;
    }

    /** Число пар в таблице. */
    public int size() {
        return size;
    }

    /** Проверяет, пуста ли таблица. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Текущая ёмкость (число ячеек). */
    public int capacity() {
        return keys.length;
    }

    /** Порог загрузки, заданный при создании. */
    public double loadFactor() {
        return loadFactor;
    }

    /** Способ пробирования, заданный при создании. */
    public Probing probing() {
        return probing;
    }

    /**
     * Обходит все пары в порядке расположения в таблице.
     *
     * @param action действие над парой «ключ, значение»
     */
    @SuppressWarnings("unchecked")
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (int i = 0; i < keys.length; i++) {
            if (isOccupied(keys[i])) {
                action.accept((K) keys[i], (V) values[i]);
            }
        }
    }

    /** Среднее число проб на один вызов {@code get}/{@code containsKey} с момента создания или сброса. */
    public double averageLookupProbes() {
        return lookupCount == 0 ? 0 : (double) lookupProbeTotal / lookupCount;
    }

    /** Обнуляет статистику проб (используется в бенчмарках). */
    public void resetProbeStatistics() {
        lookupCount = 0;
        lookupProbeTotal = 0;
    }

    private int lookup(K key) {
        Objects.requireNonNull(key, "key");
        int found = locate(key);
        lookupCount++;
        lookupProbeTotal += lastProbeCount;
        return found;
    }

    /**
     * Ищет ключ по цепочке проб.
     *
     * @return индекс найденного ключа, иначе {@code -(слот для вставки) - 1}; слот для вставки — первая
     *         встреченная ячейка {@code DELETED} либо пустая ячейка, завершившая цепочку
     */
    private int locate(Object key) {
        int capacity = keys.length;
        int base = baseIndex(key, capacity);
        int firstDeleted = -1;
        lastProbeCount = 0;
        for (int step = 0; step < capacity; step++) {
            int index = probeIndex(base, step, capacity);
            Object current = keys[index];
            lastProbeCount++;
            if (current == null) {
                return -(firstDeleted >= 0 ? firstDeleted : index) - 1;
            }
            if (current == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = index;
                }
            } else if (current.equals(key)) {
                return index;
            }
        }
        // Пустых ячеек нет, но порог загрузки < 1 гарантирует, что здесь будет хотя бы одна DELETED.
        return -firstDeleted - 1;
    }

    private int probeIndex(int base, int step, int capacity) {
        long offset = probing == Probing.LINEAR
                ? step
                : (long) QUADRATIC_C1 * step + (long) QUADRATIC_C2 * step * step;
        return (int) ((base + offset) % capacity);
    }

    /** {@code Math.abs(hashCode) % capacity}; {@code Math.abs(Integer.MIN_VALUE)} отрицателен, поэтому он отдельно. */
    private static int baseIndex(Object key, int capacity) {
        int hash = key.hashCode();
        int nonNegative = hash == Integer.MIN_VALUE ? 0 : Math.abs(hash);
        return nonNegative % capacity;
    }

    private static boolean isOccupied(Object slotKey) {
        return slotKey != null && slotKey != DELETED;
    }

    /**
     * Максимальное число занятых ячеек (с учётом {@code DELETED}) до перестройки. Не меньше 1 и не больше
     * {@code capacity - 1}: так на малых ёмкостях таблица не растёт бесконечно, а пустая ячейка есть всегда.
     */
    private int threshold(int capacity) {
        return Math.max(1, Math.min((int) (capacity * loadFactor), capacity - 1));
    }

    /**
     * Перестраивает таблицу. Ёмкость удваивается, если переполнена из-за живых пар; если порог превышен лишь
     * из-за маркеров {@code DELETED}, таблица перестраивается без роста и маркеры исчезают.
     */
    private void rehash() {
        int capacity = keys.length;
        int newCapacity = size + 1 > threshold(capacity) ? capacity * GROWTH_FACTOR : capacity;
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[newCapacity];
        values = new Object[newCapacity];
        for (int i = 0; i < oldKeys.length; i++) {
            if (isOccupied(oldKeys[i])) {
                int slot = -locate(oldKeys[i]) - 1;
                keys[slot] = oldKeys[i];
                values[slot] = oldValues[i];
            }
        }
        used = size;
    }
}
