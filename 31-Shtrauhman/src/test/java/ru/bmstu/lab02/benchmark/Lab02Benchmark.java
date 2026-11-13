package ru.bmstu.lab02.benchmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Function;
import ru.bmstu.lab02.task_1a.PriorityQueueCustom;
import ru.bmstu.lab02.task_2a.HashMapCustom;
import ru.bmstu.lab02.task_2c.CollisionKey;
import ru.bmstu.lab02.task_3c.Distinct;

/**
 * Бенчмарки ЛР2: собственные структуры против аналогов из {@code java.util}.
 *
 * <p>Запуск - метод {@code main} (из IDE). Печатает строки Markdown-таблиц для {@code docs/lab02/benchmarks.md}.
 * Данные генерируются с фиксированным зерном, поэтому обе реализации получают одинаковый вход.
 */
public final class Lab02Benchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    // Для 2C размеры меньше: при одной цепочке коллизий время растёт как n^2, и 100 000 ключей шли бы минуты.
    private static final int[] COLLISION_SIZES = {1_000, 5_000, 10_000};
    private static final int COLLISION_WARMUP_RUNS = 3;
    private static final int COLLISION_MEASURED_RUNS = 5;
    private static final long SEED = 20261108L;
    private static final double BYTES_PER_MEGABYTE = 1024.0 * 1024.0;

    private Lab02Benchmark() {
    }

    /**
     * Работает около 20 секунд, больше всего времени занимают замеры с коллизиями.
     */
    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---|---|---|");
        for (int size : SIZES) {
            benchmarkPriorityQueues(randomIntegers(size));
        }
        for (int size : SIZES) {
            benchmarkHashMaps(randomIntegers(size));
        }
        for (int size : COLLISION_SIZES) {
            benchmarkCollisions(size);
        }
        System.out.println();
        System.out.println("| Реализация | N элементов | Уникальных | Среднее время (мс) | Выделено памяти (МБ) |");
        System.out.println("|---|---|---|---|---|");
        for (int size : SIZES) {
            benchmarkDistinct(size);
        }
    }

    private static void benchmarkPriorityQueues(Integer[] data) {
        printRow("`PriorityQueueCustom`", "add + poll (цикл)", data.length,
                Measurement.measure(() -> customHeapCycle(data)), "");
        printRow("`java.util.PriorityQueue`", "add + poll (цикл)", data.length,
                Measurement.measure(() -> javaHeapCycle(data)), "");
    }

    private static void benchmarkHashMaps(Integer[] keys) {
        HashMapCustom<Integer, Integer> custom = fillCustom(keys);
        Map<Integer, Integer> standard = fillJava(keys);
        printRow("`HashMapCustom`", "put (в пустую таблицу)", keys.length,
                Measurement.measure(() -> fillCustom(keys)), "с перехешированием");
        printRow("`java.util.HashMap`", "put (в пустую таблицу)", keys.length,
                Measurement.measure(() -> fillJava(keys)), "с перехешированием");
        printRow("`HashMapCustom`", "get (все ключи есть)", keys.length,
                Measurement.measure(() -> sumValues(keys, custom::get)), "");
        printRow("`java.util.HashMap`", "get (все ключи есть)", keys.length,
                Measurement.measure(() -> sumValues(keys, standard::get)), "");
    }

    private static void benchmarkCollisions(int size) {
        String[] plain = new String[size];
        CollisionKey[] colliding = new CollisionKey[size];
        for (int i = 0; i < size; i++) {
            plain[i] = "key-" + i;
            colliding[i] = new CollisionKey(plain[i]);
        }
        printRow("`HashMapCustom` + `String`", "put + get", size, measureCollisionCase(plain, false),
                "обычные хеши, база для сравнения");
        printRow("`HashMapCustom` + `CollisionKey`", "put + get", size, measureCollisionCase(colliding, false),
                "все ключи в одной цепочке");
        printRow("`java.util.HashMap` + `CollisionKey`", "put + get", size, measureCollisionCase(colliding, true),
                "все ключи в одной корзине");
    }

    private static Measurement.Result measureCollisionCase(Object[] keys, boolean useJavaMap) {
        return Measurement.measure(() -> useJavaMap ? javaPutGet(keys) : customPutGet(keys),
                COLLISION_WARMUP_RUNS, COLLISION_MEASURED_RUNS);
    }

    private static void benchmarkDistinct(int size) {
        Random random = new Random(SEED);
        List<Integer> items = new ArrayList<>(size);
        // При таком диапазоне около 43% элементов уникальны, остальное - повторы.
        for (int i = 0; i < size; i++) {
            items.add(random.nextInt(size / 2));
        }
        int unique = Distinct.distinct(items).size();
        printDistinctRow("`Distinct.distinct` (`HashMapCustom`)", size, unique,
                Measurement.measure(() -> Distinct.distinct(items)));
        printDistinctRow("`new LinkedHashSet<>(items)`", size, unique,
                Measurement.measure(() -> new LinkedHashSet<>(items)));
    }

    private static long customHeapCycle(Integer[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : data) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static long javaHeapCycle(Integer[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : data) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
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

    private static long sumValues(Integer[] keys, Function<Integer, Integer> lookup) {
        long checksum = 0;
        for (Integer key : keys) {
            checksum += lookup.apply(key);
        }
        return checksum;
    }

    private static int customPutGet(Object[] keys) {
        HashMapCustom<Object, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], i);
        }
        int checksum = 0;
        for (Object key : keys) {
            checksum += map.get(key);
        }
        return checksum;
    }

    private static int javaPutGet(Object[] keys) {
        Map<Object, Integer> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], i);
        }
        int checksum = 0;
        for (Object key : keys) {
            checksum += map.get(key);
        }
        return checksum;
    }

    private static Integer[] randomIntegers(int size) {
        Random random = new Random(SEED);
        Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    private static void printRow(String structure, String operation, int size, Measurement.Result result,
                                 String note) {
        System.out.println(String.format(Locale.ROOT, "| %s | %s | %s | %.2f | %s |",
                structure, operation, formatCount(size), result.millis(), note));
    }

    private static void printDistinctRow(String implementation, int size, int unique, Measurement.Result result) {
        System.out.println(String.format(Locale.ROOT, "| %s | %s | %s | %.2f | %.2f |", implementation,
                formatCount(size), formatCount(unique), result.millis(),
                result.allocatedBytes() / BYTES_PER_MEGABYTE));
    }

    private static String formatCount(int count) {
        return String.format(Locale.ROOT, "%,d", count).replace(',', ' ');
    }
}
