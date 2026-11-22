package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Хеш-таблица с открытой адресацией (линейное или квадратичное пробирование).
 * <p>
 * Хеш-функция: {@code Math.abs(key.hashCode()) % capacity}. Удалённые ячейки помечаются маркером
 * {@link #DELETED}, чтобы не рвать цепочки пробирования. Если доля занятых ячеек (живые + DELETED)
 * превысит порог загрузки (по умолчанию 0.75), таблица увеличивается в 2 раза и перехешируется
 * (при этом маркеры DELETED исчезают).
 * <p>
 * Ёмкость всегда степень двойки. Для квадратичного пробирования {@code (h + c1*i + c2*i^2) % capacity}
 * последовательность проб при ёмкости 2^k является перестановкой всех ячеек, если c1 + c2 нечётно
 * (по умолчанию c1 = 1, c2 = 2). Для «примерных» c1 = 1, c2 = 3 сумма чётна — последовательность
 * покрывает лишь половину ячеек; таблица всё равно корректна: поиск ограничен {@code capacity} пробами,
 * а если свободная ячейка не нашлась, ёмкость удваивается (см. {@link #rehash(int)}).
 * <p>
 * Ключи {@code null} не допускаются. Значения {@code null} допускаются.
 */
public class HashMapCustom<K, V> {

    public enum Probing { LINEAR, QUADRATIC }

    public static final double DEFAULT_LOAD_FACTOR = 0.75;
    public static final int DEFAULT_CAPACITY = 16;
    public static final int DEFAULT_C1 = 1;
    public static final int DEFAULT_C2 = 2;

    /** Маркер удалённой ячейки. */
    private static final Object DELETED = new Object();

    private Object[] keys;   // null — пустая ячейка, DELETED — удалена
    private Object[] values;
    private int size;
    private int deleted;
    private final double loadFactor;
    private final Probing probing;
    private final int c1;
    private final int c2;
    private long probeCount;

    /** Линейное пробирование, порог 0.75, ёмкость 16. */
    public HashMapCustom() {
        this(Probing.LINEAR, DEFAULT_LOAD_FACTOR, DEFAULT_CAPACITY);
    }

    public HashMapCustom(Probing probing) {
        this(probing, DEFAULT_LOAD_FACTOR, DEFAULT_CAPACITY);
    }

    public HashMapCustom(Probing probing, double loadFactor) {
        this(probing, loadFactor, DEFAULT_CAPACITY);
    }

    public HashMapCustom(Probing probing, double loadFactor, int initialCapacity) {
        this(probing, loadFactor, initialCapacity, DEFAULT_C1, DEFAULT_C2);
    }

    public HashMapCustom(Probing probing, double loadFactor, int initialCapacity, int c1, int c2) {
        if (probing == null) {
            throw new NullPointerException("probing");
        }
        if (!(loadFactor > 0.0 && loadFactor < 1.0)) {
            throw new IllegalArgumentException("loadFactor must be in (0, 1): " + loadFactor);
        }
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("initialCapacity < 1: " + initialCapacity);
        }
        this.probing = probing;
        this.loadFactor = loadFactor;
        this.c1 = c1;
        this.c2 = c2;
        int cap = 1;
        while (cap < initialCapacity) {
            cap <<= 1;
        }
        this.keys = new Object[cap];
        this.values = new Object[cap];
    }

    /** Вставка или обновление значения по ключу. */
    public void put(K key, V value) {
        putInternal(key, value, false);
    }

    /**
     * Вставляет пару, только если ключа ещё нет.
     *
     * @return true, если пара добавлена
     */
    public boolean putIfAbsent(K key, V value) {
        return putInternal(key, value, true);
    }

    /** Значение по ключу либо null. */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        int idx = find(key);
        return idx < 0 ? null : (V) values[idx];
    }

    public boolean containsKey(K key) {
        return find(key) >= 0;
    }

    /** Удаляет ключ; true, если ключ был в таблице. */
    public boolean remove(K key) {
        int idx = find(key);
        if (idx < 0) {
            return false;
        }
        keys[idx] = DELETED;
        values[idx] = null;
        size--;
        deleted++;
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return keys.length;
    }

    public double loadFactorThreshold() {
        return loadFactor;
    }

    /** Фактическая загрузка: живые элементы / ёмкость. */
    public double currentLoad() {
        return (double) size / keys.length;
    }

    public Probing probing() {
        return probing;
    }

    /** Суммарное число проб (просмотренных ячеек) во всех операциях с момента последнего сброса. */
    public long probeCount() {
        return probeCount;
    }

    public void resetProbeCount() {
        probeCount = 0;
    }

    /** Обход всех пар в порядке расположения в таблице. */
    @SuppressWarnings("unchecked")
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (int i = 0; i < keys.length; i++) {
            Object k = keys[i];
            if (k != null && k != DELETED) {
                action.accept((K) k, (V) values[i]);
            }
        }
    }

    // ---------------------------------------------------------------- внутренности

    private int hash(Object key) {
        if (key == null) {
            throw new NullPointerException("null keys are not allowed");
        }
        int h = key.hashCode();
        // Math.abs(Integer.MIN_VALUE) < 0, поэтому защищаемся от этого случая
        return h == Integer.MIN_VALUE ? 0 : Math.abs(h);
    }

    /**
     * Индекс i-й пробы. {@code start = hash % capacity} вычисляется один раз на операцию;
     * так как ёмкость — степень двойки, {@code (start + c1*i + c2*i^2) % capacity} равно
     * {@code & (capacity - 1)}, что быстрее деления.
     */
    private int probe(int start, int i, int mask) {
        long offset = probing == Probing.LINEAR
                ? i
                : (long) c1 * i + (long) c2 * i * i;
        return (int) ((start + offset) & mask);
    }

    /** Ищет ключ, возвращает индекс ячейки или -1. Поиск идёт до пустой ячейки либо capacity проб. */
    private int find(Object key) {
        int cap = keys.length;
        int start = hash(key) % cap;
        int mask = cap - 1;
        for (int i = 0; i < cap; i++) {
            int idx = probe(start, i, mask);
            Object k = keys[idx];
            probeCount++;
            if (k == null) {
                return -1;
            }
            if (k != DELETED && k.equals(key)) {
                return idx;
            }
        }
        return -1;
    }

    private boolean putInternal(K key, V value, boolean onlyIfAbsent) {
        int cap = keys.length;
        int start = hash(key) % cap;
        int mask = cap - 1;
        int firstDeleted = -1;
        int emptySlot = -1;
        for (int i = 0; i < cap; i++) {
            int idx = probe(start, i, mask);
            Object k = keys[idx];
            probeCount++;
            if (k == null) {
                emptySlot = idx;
                break;
            }
            if (k == DELETED) {
                if (firstDeleted < 0) {
                    firstDeleted = idx;
                }
            } else if (k.equals(key)) {
                if (!onlyIfAbsent) {
                    values[idx] = value;
                }
                return false;
            }
        }
        // ключа нет — вставляем новый
        if (size + deleted + 1 > cap * loadFactor) {
            int newCap = cap;
            while (size + 1 > newCap * loadFactor) {
                newCap <<= 1;
            }
            rehash(newCap); // если живых мало, а мусора много — перехеширование без роста
            insertNew(key, value);
            return true;
        }
        int slot = firstDeleted >= 0 ? firstDeleted : emptySlot;
        if (slot < 0) { // квадратичная последовательность не нашла свободной ячейки
            rehash(cap << 1);
            insertNew(key, value);
            return true;
        }
        if (keys[slot] == DELETED) {
            deleted--;
        }
        keys[slot] = key;
        values[slot] = value;
        size++;
        return true;
    }

    /** Вставка заведомо нового ключа; при нехватке места удваивает ёмкость. */
    private void insertNew(Object key, Object value) {
        while (!tryInsert(keys, values, key, value)) {
            rehash(keys.length << 1);
        }
        size++;
    }

    private boolean tryInsert(Object[] ks, Object[] vs, Object key, Object value) {
        int cap = ks.length;
        int start = hash(key) % cap;
        int mask = cap - 1;
        for (int i = 0; i < cap; i++) {
            int idx = probe(start, i, mask);
            probeCount++;
            Object k = ks[idx];
            if (k == null || k == DELETED) {
                if (k == DELETED) {
                    deleted--;
                }
                ks[idx] = key;
                vs[idx] = value;
                return true;
            }
        }
        return false;
    }

    /** Перехеширование в таблицу ёмкости newCap; если элементы не помещаются — удваивает ещё раз. */
    private void rehash(int newCap) {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        int oldSize = size;
        int oldDeleted = deleted;
        long savedProbes = probeCount;
        outer:
        while (true) {
            Object[] newKeys = new Object[newCap];
            Object[] newValues = new Object[newCap];
            for (int j = 0; j < oldKeys.length; j++) {
                Object k = oldKeys[j];
                if (k != null && k != DELETED) {
                    if (!tryInsert(newKeys, newValues, k, oldValues[j])) {
                        newCap <<= 1;
                        continue outer;
                    }
                }
            }
            keys = newKeys;
            values = newValues;
            size = oldSize;
            deleted = 0;
            break;
        }
        // пробы перехеширования в статистику не включаем
        probeCount = savedProbes;
    }

    // ================================================================ задачи группы 3
    // ---------------------------------------------------------------- 3B: частота слов



    /** Слово и число его вхождений. */
    public record WordCount(String word, int count) {
    }

    /**
     * Считает частоты слов без учёта регистра; любые символы, кроме букв и цифр, считаются
     * разделителями (знаки препинания отбрасываются). Подсчёт идёт в {@link HashMapCustom};
     * в {@code Map} результат копируется только для удобства возврата (порядок — как в таблице).
     */
    public static Map<String, Integer> countWords(String text) {
        HashMapCustom<String, Integer> counts = countWordsCustom(text);
        Map<String, Integer> result = new LinkedHashMap<>(Math.max(16, counts.size() * 2));
        counts.forEach(result::put);
        return result;
    }

    /** Подсчёт частот непосредственно в собственной таблице. */
    public static HashMapCustom<String, Integer> countWordsCustom(String text) {
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        StringBuilder word = new StringBuilder();
        int n = text.length();
        for (int i = 0; i <= n; i++) {
            char c = i < n ? text.charAt(i) : ' ';
            if (Character.isLetterOrDigit(c)) {
                word.append(Character.toLowerCase(c));
            } else if (word.length() > 0) {
                String w = word.toString();
                word.setLength(0);
                Integer old = counts.get(w);
                counts.put(w, old == null ? 1 : old + 1);
            }
        }
        return counts;
    }

    /**
     * n самых частых слов (по убыванию частоты, при равенстве — по алфавиту).
     * Использует min-heap размера n на {@link PriorityQueueCustom}: O(m log n).
     */
    public static List<WordCount> topWords(HashMapCustom<String, Integer> counts, int n) {
        // «меньше» = хуже: сначала меньшая частота, затем лексикографически больший ключ
        Comparator<WordCount> worstFirst = (a, b) -> {
            int c = Integer.compare(a.count(), b.count());
            return c != 0 ? c : b.word().compareTo(a.word());
        };
        PriorityQueueCustom<WordCount> heap = new PriorityQueueCustom<>(worstFirst);
        counts.forEach((w, cnt) -> {
            WordCount wc = new WordCount(w, cnt);
            if (heap.size() < n) {
                heap.add(wc);
            } else if (n > 0 && worstFirst.compare(wc, heap.peek()) > 0) {
                heap.poll();
                heap.add(wc);
            }
        });
        List<WordCount> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        java.util.Collections.reverse(result);
        return result;
    }

    // ---------------------------------------------------------------- 3C: уникальные элементы



    /** null допускается (как у LinkedHashSet): сам null хранится отдельным флагом. */
    public static <T> List<T> distinct(List<T> items) {
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>();
        List<T> result = new ArrayList<>();
        boolean seenNull = false;
        for (T item : items) {
            if (item == null) {
                if (!seenNull) {
                    seenNull = true;
                    result.add(null);
                }
            } else if (seen.putIfAbsent(item, Boolean.TRUE)) {
                result.add(item);
            }
        }
        return result;
    }
}
