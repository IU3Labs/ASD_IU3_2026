package ru.bmstu.lab02.task02d;

import ru.bmstu.lab02.task02a.HashMapCustom;

/** Хеш-таблица с настраиваемым порогом загрузки. */
public final class LoadThresholdHashMap<K, V> extends HashMapCustom<K, V> {
    /** Создаёт таблицу со стандартным порогом загрузки 0,75. */
    public LoadThresholdHashMap() {
        super();
    }

    /** Создаёт таблицу с порогом загрузки строго между нулём и единицей. */
    public LoadThresholdHashMap(double loadThreshold) {
        super(loadThreshold);
    }
}
