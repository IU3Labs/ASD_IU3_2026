package ru.bmstu.lab02.task02b;

import ru.bmstu.lab02.task02a.HashMapCustom;

/** Хеш-таблица с квадратичным пробированием. */
public final class QuadraticHashMap<K, V> extends HashMapCustom<K, V> {
    private static final int QUADRATIC_COEFFICIENT = 2;

    /** Возвращает смещение, обеспечивающее обход всех ячеек при ёмкости степени двойки. */
    @Override
    protected long probeOffset(int step) {
        // Разность смещений равна произведению разности шагов на нечётное число.
        return step + (long) QUADRATIC_COEFFICIENT * step * step;
    }
}
