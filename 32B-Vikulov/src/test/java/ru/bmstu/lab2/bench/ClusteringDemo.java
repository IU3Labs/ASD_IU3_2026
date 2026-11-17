package ru.bmstu.lab2.bench;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Random;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/**
 * Эксперимент к задаче 2A: насколько далеко от «домашней» ячейки {@code |hash % capacity|} оказываются ключи
 * при линейном пробировании (среднее и максимальное смещение после вставки).
 *
 * <p>Сравниваются плотные маленькие ключи (как во входе {@link DistinctBenchmark}: числа из [0, N/2)) и
 * случайные ключи по всему диапазону {@code int}. У {@code Integer} хеш равен самому числу, поэтому плотные
 * ключи занимают подряд идущие ячейки и могут образовывать длинные кластеры.
 *
 * <p>Внутренний массив ключей читается через reflection — только для измерения, в коде таблицы это не нужно.
 */
public final class ClusteringDemo {

    private static final int SEED = 7;

    private ClusteringDemo() {
    }

    /**
     * Печатает таблицу смещений в формате Markdown.
     *
     * @param args не используются
     * @throws ReflectiveOperationException если не удалось прочитать внутренний массив
     */
    public static void main(String[] args) throws ReflectiveOperationException {
        Bench.row("Ключи", "N", "Уникальных", "Ёмкость", "Среднее смещение", "Макс. смещение");
        Bench.row("---", "---", "---", "---", "---", "---");
        for (int size : new int[] {10_000, 50_000}) {
            measure("плотные [0, N/2)", size, true);
            measure("случайные int", size, false);
        }
    }

    private static void measure(String name, int size, boolean dense) throws ReflectiveOperationException {
        Field keysField = HashMapCustom.class.getDeclaredField("keys");
        keysField.setAccessible(true);
        HashMapCustom<Integer, Boolean> map = new HashMapCustom<>();
        Random random = new Random(SEED);
        long total = 0;
        int max = 0;
        for (int i = 0; i < size; i++) {
            Integer key = dense ? random.nextInt(size / 2) : random.nextInt();
            if (map.containsKey(key)) {
                continue;
            }
            map.put(key, Boolean.TRUE);
            int displacement = displacement((Object[]) keysField.get(map), key);
            total += displacement;
            max = Math.max(max, displacement);
        }
        String average = String.format(Locale.ROOT, "%.1f", (double) total / map.size());
        Bench.row(name, size, map.size(), map.capacity(), average, max);
    }

    /** На сколько ячеек ключ сдвинут вправо от домашней ячейки. */
    private static int displacement(Object[] keys, Integer key) {
        int index = Math.abs(key.hashCode() % keys.length);
        int distance = 0;
        while (!key.equals(keys[index])) {
            index = (index + 1) % keys.length;
            distance++;
        }
        return distance;
    }
}
