package ru.bmstu.lab2.benchmark;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import ru.bmstu.lab2.task_2.CollisionKey;
import ru.bmstu.lab2.task_2.HashMapCustom;

/** Задачи 2A и 2C: одинаковые ключи для собственных и стандартных таблиц. */
final class MapBenchmarks {
    private static final int COLLISION_SIZE = 10_000;
    private static final long SEED = 20261120L;

    private MapBenchmarks() {}

    static void run() {
        for (int size : new int[] {10_000, 50_000, 100_000}) {
            Integer[] keys =
                    new Random(SEED).ints().distinct().limit(size).boxed().toArray(Integer[]::new);
            measureAll("map", keys);
        }
        String[] strings = new String[COLLISION_SIZE];
        CollisionKey[] collisions = new CollisionKey[COLLISION_SIZE];
        for (int index = 0; index < COLLISION_SIZE; index++) {
            strings[index] = "key-" + index;
            collisions[index] = new CollisionKey(strings[index]);
        }
        measureAll("collision-normal", strings);
        measureAll("collision-constant", collisions);
    }

    private static <K> void measureAll(String group, K[] keys) {
        measureCustomPut(group, keys);
        measureStandardPut(group, keys);
        measureCustomGet(group, keys);
        measureStandardGet(group, keys);
        measureCustomRemove(group, keys);
        measureStandardRemove(group, keys);
    }

    private static <K> void measureCustomPut(String group, K[] keys) {
        BenchmarkTimer.measure(
                group,
                "put",
                "HashMapCustom",
                keys.length,
                () -> () -> customFilled(keys).size(),
                keys.length);
    }

    private static <K> void measureStandardPut(String group, K[] keys) {
        BenchmarkTimer.measure(
                group,
                "put",
                "java.util.HashMap",
                keys.length,
                () -> () -> standardFilled(keys).size(),
                keys.length);
    }

    private static <K> void measureCustomGet(String group, K[] keys) {
        BenchmarkTimer.measure(
                group,
                "get",
                "HashMapCustom",
                keys.length,
                () -> {
                    HashMapCustom<K, Integer> map = customFilled(keys);
                    return () -> {
                        long sum = 0;
                        for (K key : keys) {
                            sum += map.get(key);
                        }
                        return sum;
                    };
                },
                (long) keys.length * (keys.length - 1) / 2);
    }

    private static <K> void measureStandardGet(String group, K[] keys) {
        BenchmarkTimer.measure(
                group,
                "get",
                "java.util.HashMap",
                keys.length,
                () -> {
                    Map<K, Integer> map = standardFilled(keys);
                    return () -> {
                        long sum = 0;
                        for (K key : keys) {
                            sum += map.get(key);
                        }
                        return sum;
                    };
                },
                (long) keys.length * (keys.length - 1) / 2);
    }

    private static <K> void measureCustomRemove(String group, K[] keys) {
        BenchmarkTimer.measure(
                group,
                "remove",
                "HashMapCustom",
                keys.length,
                () -> {
                    HashMapCustom<K, Integer> map = customFilled(keys);
                    return () -> {
                        int removed = 0;
                        for (K key : keys) {
                            if (map.remove(key)) {
                                removed++;
                            }
                        }
                        return removed - map.size();
                    };
                },
                keys.length);
    }

    private static <K> void measureStandardRemove(String group, K[] keys) {
        BenchmarkTimer.measure(
                group,
                "remove",
                "java.util.HashMap",
                keys.length,
                () -> {
                    Map<K, Integer> map = standardFilled(keys);
                    return () -> {
                        int removed = 0;
                        for (K key : keys) {
                            if (map.remove(key) != null) {
                                removed++;
                            }
                        }
                        return removed - map.size();
                    };
                },
                keys.length);
    }

    private static <K> HashMapCustom<K, Integer> customFilled(K[] keys) {
        HashMapCustom<K, Integer> map = new HashMapCustom<>();
        for (int index = 0; index < keys.length; index++) {
            map.put(keys[index], index);
        }
        return map;
    }

    private static <K> Map<K, Integer> standardFilled(K[] keys) {
        Map<K, Integer> map = new HashMap<>();
        for (int index = 0; index < keys.length; index++) {
            map.put(keys[index], index);
        }
        return map;
    }
}
