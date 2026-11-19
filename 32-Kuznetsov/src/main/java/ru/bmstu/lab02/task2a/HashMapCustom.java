package ru.bmstu.lab02.task2a;

/**
 * Хеш-таблица с открытой адресацией и линейным пробированием (задача 2A).
 *
 * <p>Ключи и значения хранятся в двух массивах одинаковой длины: пара лежит под одним индексом,
 * отдельные объекты-узлы не создаются. При коллизии проверяется следующая ячейка по кругу.
 * Удалённая ячейка помечается маркером {@code DELETED}, чтобы не разорвать цепочку пробирования.
 *
 * <p>Загрузка считается по занятым ячейкам вместе с маркерами и не превышает 0.75.
 * Ключ {@code null} запрещён, значение {@code null} разрешено.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashMapCustom<K, V> {

    private static final int START_CAPACITY = 16;
    private static final double MAX_LOAD = 0.75;
    private static final Object DELETED = new Object();

    private Object[] keySlots;
    private Object[] valueSlots;
    private int size;
    private int usedSlots;

    /**
     * Создаёт пустую таблицу ёмкостью 16.
     */
    public HashMapCustom() {
        this(START_CAPACITY);
    }

    /**
     * Создаёт пустую таблицу заданной ёмкости.
     *
     * @param capacity начальная ёмкость
     * @throws IllegalArgumentException если ёмкость меньше 1
     */
    public HashMapCustom(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Ёмкость должна быть больше нуля");
        }
        keySlots = new Object[capacity];
        valueSlots = new Object[capacity];
        size = 0;
        usedSlots = 0;
    }

    /**
     * Вставляет пару или обновляет значение существующего ключа. O(1) в среднем, амортизированно.
     *
     * @param key ключ
     * @param value значение, может быть {@code null}
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    public void put(K key, V value) {
        requireKey(key);
        int slot = locate(key);
        if (slot >= 0) {
            valueSlots[slot] = value;
            return;
        }
        slot = freeSlot(key);
        if (keySlots[slot] == null) {
            usedSlots++;
        }
        keySlots[slot] = key;
        valueSlots[slot] = value;
        size++;
        if (usedSlots > keySlots.length * MAX_LOAD) {
            rebuild();
        }
    }

    /**
     * Возвращает значение по ключу. O(1) в среднем.
     *
     * @param key ключ
     * @return значение или {@code null}, если ключа нет
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    @SuppressWarnings("unchecked")
    public V get(K key) {
        requireKey(key);
        int slot = locate(key);
        if (slot < 0) {
            return null;
        }
        return (V) valueSlots[slot];
    }

    /**
     * Проверяет наличие ключа, в том числе с {@code null}-значением. O(1) в среднем.
     *
     * @param key ключ
     * @return {@code true}, если ключ есть в таблице
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    public boolean containsKey(K key) {
        requireKey(key);
        return locate(key) >= 0;
    }

    /**
     * Удаляет пару по ключу, оставляя в ячейке маркер {@code DELETED}. O(1) в среднем.
     *
     * @param key ключ
     * @return {@code true}, если пара была удалена
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    public boolean remove(K key) {
        requireKey(key);
        int slot = locate(key);
        if (slot < 0) {
            return false;
        }
        keySlots[slot] = DELETED;
        valueSlots[slot] = null;
        size--;
        return true;
    }

    /**
     * Возвращает число пар в таблице за O(1).
     *
     * @return количество ключей
     */
    public int size() {
        return size;
    }

    /**
     * Возвращает ёмкость таблицы (длину массивов) за O(1).
     *
     * @return число ячеек
     */
    public int capacity() {
        return keySlots.length;
    }

    /**
     * Начальный индекс по формуле задания. {@code Math.abs(Integer.MIN_VALUE)} в Java отрицателен,
     * поэтому отрицательный остаток переворачивается, иначе индекс вышел бы за массив.
     */
    private int indexFor(Object key) {
        int index = Math.abs(key.hashCode()) % keySlots.length;
        return index < 0 ? -index : index;
    }

    private int next(int index) {
        return (index + 1) % keySlots.length;
    }

    /**
     * Ищет ключ по цепочке до первой пустой ячейки; маркеры {@code DELETED} пропускаются.
     * Пустая ячейка есть всегда, потому что занято не больше 75 % ячеек, — цикл конечен.
     */
    private int locate(Object key) {
        int index = indexFor(key);
        while (keySlots[index] != null) {
            if (keySlots[index] != DELETED && keySlots[index].equals(key)) {
                return index;
            }
            index = next(index);
        }
        return -1;
    }

    /**
     * Находит первую ячейку для вставки: пустую или с маркером, чтобы удалённые места переиспользовались.
     */
    private int freeSlot(Object key) {
        int index = indexFor(key);
        while (keySlots[index] != null && keySlots[index] != DELETED) {
            index = next(index);
        }
        return index;
    }

    /**
     * Перехеширует живые ключи в новые массивы и выбрасывает маркеры. Обычно ёмкость удваивается;
     * если маркеров больше, чем ключей, ёмкость сохраняется, иначе при чередовании put/remove
     * таблица росла бы без новых элементов.
     */
    private void rebuild() {
        int tombstones = usedSlots - size;
        int newCapacity = tombstones > size ? keySlots.length : keySlots.length * 2;
        Object[] oldKeys = keySlots;
        Object[] oldValues = valueSlots;
        keySlots = new Object[newCapacity];
        valueSlots = new Object[newCapacity];
        usedSlots = size;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != DELETED) {
                int slot = freeSlot(oldKeys[i]);
                keySlots[slot] = oldKeys[i];
                valueSlots[slot] = oldValues[i];
            }
        }
    }

    private void requireKey(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }
    }
}
