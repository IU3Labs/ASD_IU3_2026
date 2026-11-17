package ru.bmstu.lab2.bench;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/**
 * Бенчмарк задачи 2A: {@link HashMapCustom} против {@link java.util.HashMap} на N = 10 000, 50 000, 100 000.
 * Для каждого N: N вставок, N успешных поисков, N удалений случайных различных ключей {@code Integer}.
 */
public final class HashMapBenchmark {

    private static final int SEED = 42;

    private HashMapBenchmark() {
    }

    /**
     * Печатает таблицу результатов в формате Markdown.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Bench.row("Структура", "Операция", "N элементов", "Среднее время (мс)", "Примечание");
        Bench.row("---", "---", "---", "---", "---");
        for (int size : Bench.SIZES) {
            Integer[] keys = distinctRandomKeys(size);
            measureCustom(keys);
            measureJava(keys);
        }
    }

    private static void measureCustom(Integer[] keys) {
        double put = Bench.averageMillis(() -> fillCustom(keys));
        HashMapCustom<Integer, Integer> filled = fillCustom(keys);
        double get = Bench.averageMillis(() -> {
            for (Integer key : keys) {
                filled.get(key);
            }
        });
        double remove = Bench.averageMillis(() -> fillCustom(keys), map -> {
            for (Integer key : keys) {
                map.remove(key);
            }
        });
        String note = "ёмкость " + filled.capacity();
        Bench.row("`HashMapCustom`", "put", keys.length, format(put), note);
        Bench.row("`HashMapCustom`", "get", keys.length, format(get), note);
        Bench.row("`HashMapCustom`", "remove", keys.length, format(remove), note);
    }

    private static void measureJava(Integer[] keys) {
        double put = Bench.averageMillis(() -> fillJava(keys));
        Map<Integer, Integer> filled = fillJava(keys);
        double get = Bench.averageMillis(() -> {
            for (Integer key : keys) {
                filled.get(key);
            }
        });
        double remove = Bench.averageMillis(() -> fillJava(keys), map -> {
            for (Integer key : keys) {
                map.remove(key);
            }
        });
        Bench.row("`java.util.HashMap`", "put", keys.length, format(put), "");
        Bench.row("`java.util.HashMap`", "get", keys.length, format(get), "");
        Bench.row("`java.util.HashMap`", "remove", keys.length, format(remove), "");
    }

    private static HashMapCustom<Integer, Integer> fillCustom(Integer[] keys) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        return map;
    }

    private static Map<Integer, Integer> fillJava(Integer[] keys) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        return map;
    }

    private static Integer[] distinctRandomKeys(int size) {
        return new Random(SEED).ints().distinct().limit(size).boxed().toArray(Integer[]::new);
    }

    static String format(double millis) {
        return String.format(Locale.ROOT, "%.2f", millis);
    }
}
