package ru.bmstu.lab02;

import com.sun.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;
import ru.bmstu.lab02.task01a.PriorityQueueCustom;
import ru.bmstu.lab02.task01b.ComparatorPriorityQueue;
import ru.bmstu.lab02.task01d.BulkPriorityQueue;
import ru.bmstu.lab02.task02a.HashMapCustom;
import ru.bmstu.lab02.task02b.QuadraticHashMap;
import ru.bmstu.lab02.task02c.CollisionKey;
import ru.bmstu.lab02.task02d.LoadThresholdHashMap;
import ru.bmstu.lab02.task03a.Task03A;
import ru.bmstu.lab02.task03b.Task03B;
import ru.bmstu.lab02.task03c.Task03C;
import ru.bmstu.lab02.task03d.Task03D;

/** Воспроизводимые однопоточные замеры для отчёта по ЛР2. */
public final class Lab02Benchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int HEAP_SIZE = 100_000;
    private static final int SEARCH_COUNT = 50_000;
    private static final int SEED = 42;
    private static final int WARMUPS = 4;
    private static final int SAMPLES = 7;
    private static final int WORD_SIZE = 1_048_576;
    private static final int TOP_COUNT = 1_000;
    private static final int WINDOW_SIZE = 1_000;
    private static final ThreadMXBean ALLOCATION = (ThreadMXBean) ManagementFactory.getThreadMXBean();

    // Запись результата в volatile-поле не даёт JVM устранить вычисления.
    private static volatile Object result;

    private Lab02Benchmark() {
    }

    /** Выводит время работы, объём выделенной памяти, число проб и частоты слов. */
    public static void main(String[] args) {
        ALLOCATION.setThreadAllocatedMemoryEnabled(true);
        System.out.println("| Реализация | Операция | N | Среднее, мс | Выделено, байт за запуск |");
        System.out.println("|---|---|---:|---:|---:|");
        for (int size : SIZES) {
            compareWithStandard(randomKeys(size));
        }
        heapExperiments(randomKeys(HEAP_SIZE));
        compareProbing(randomKeys(SEARCH_COUNT));
        compareThresholds(randomKeys(HEAP_SIZE));
        compareCollisions();
        measureApplications();
    }

    private static void compareWithStandard(Integer[] keys) {
        int size = keys.length;
        measure("PriorityQueueCustom", "add + poll", size, () -> heapCycle(keys, Comparator.naturalOrder()));
        measure("java.util.PriorityQueue", "add + poll", size, () -> standardHeapCycle(keys));
        measure("HashMapCustom (линейное)", "put + get + remove", size, () -> customMapCycle(keys, false));
        measure("HashMapCustom (квадратичное)", "put + get + remove", size, () -> customMapCycle(keys, true));
        measure("java.util.HashMap", "put + get + remove", size, () -> standardMapCycle(keys));
    }

    private static long heapCycle(Integer[] keys, Comparator<Integer> order) {
        PriorityQueueCustom<Integer> heap = heapWithAdds(keys, order);
        long checksum = 0;
        while (!heap.isEmpty()) {
            checksum += heap.poll();
        }
        return checksum;
    }

    private static long standardHeapCycle(Integer[] keys) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (Integer key : keys) {
            heap.add(key);
        }
        long checksum = 0;
        while (!heap.isEmpty()) {
            checksum += heap.remove();
        }
        return checksum;
    }

    private static PriorityQueueCustom<Integer> heapWithAdds(Integer[] keys, Comparator<Integer> order) {
        PriorityQueueCustom<Integer> heap = new ComparatorPriorityQueue<>(order);
        for (Integer key : keys) {
            heap.add(key);
        }
        return heap;
    }

    private static <K> long customMapCycle(K[] keys, boolean quadratic) {
        HashMapCustom<K, K> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        for (K key : keys) {
            map.put(key, key);
        }
        long checksum = lookup(map, keys);
        for (K key : keys) {
            checksum += map.remove(key) ? 1 : 0;
        }
        return checksum;
    }

    private static long standardMapCycle(Integer[] keys) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer key : keys) {
            map.put(key, key);
        }
        long checksum = 0;
        for (Integer key : keys) {
            checksum += map.get(key);
        }
        for (Integer key : keys) {
            checksum += map.remove(key) != null ? 1 : 0;
        }
        return checksum;
    }

    private static void heapExperiments(Integer[] keys) {
        measure("Собственная мин-куча", "add + poll", keys.length, () -> heapCycle(keys, Comparator.naturalOrder()));
        measure("Собственная макс-куча", "add + poll", keys.length, () -> heapCycle(keys, Comparator.reverseOrder()));
        measure("PriorityQueueCustom", "Построение через add", keys.length,
                () -> heapWithAdds(keys, Comparator.naturalOrder()));
        measure("PriorityQueueCustom", "Построение снизу вверх", keys.length, () -> new BulkPriorityQueue<>(keys));
    }

    private static void compareProbing(Integer[] keys) {
        Integer[] searches = makeSearches(keys);
        for (boolean quadratic : new boolean[]{false, true}) {
            HashMapCustom<Integer, Integer> map = buildMap(keys, quadratic, 0.75);
            long probes = 0;
            for (Integer key : searches) {
                probes += map.getProbeCount(key);
            }
            System.out.printf(Locale.ROOT, "Пробирование (%s, %d запросов get): %.3f%n",
                    quadratic ? "квадратичное" : "линейное", searches.length, (double) probes / searches.length);
        }
    }

    private static void compareThresholds(Integer[] keys) {
        Integer[] searches = makeSearches(keys);
        for (double load : new double[]{0.5, 0.9}) {
            HashMapCustom<Integer, Integer> map = buildMap(keys, false, load);
            System.out.printf(Locale.ROOT, "Ёмкость (%.1f): %d%n", load, map.capacity());
            String label = "HashMapCustom (" + load + ")";
            measure(label, "put в пустую таблицу", keys.length, () -> buildMap(keys, false, load));
            measure(label, "get для разных ключей", searches.length, () -> lookup(map, searches));
        }
    }

    private static void compareCollisions() {
        int count = 10_000;
        String[] strings = new String[count];
        CollisionKey[] collisions = new CollisionKey[count];
        for (int index = 0; index < count; index++) {
            strings[index] = "key-" + index;
            collisions[index] = new CollisionKey(strings[index]);
        }
        measure("HashMapCustom (String)", "put + get + remove", count, () -> customMapCycle(strings, false));
        measure("HashMapCustom (CollisionKey)", "put + get + remove", count,
                () -> customMapCycle(collisions, false));
    }

    private static void measureApplications() {
        int million = 1_000_000;
        List<Integer> input = Arrays.asList(randomKeys(million));
        if (!Task03A.topK(input, TOP_COUNT).equals(sortedTop(input))) {
            throw new AssertionError("Результат topK расходится с результатом сортировки");
        }
        measure("Собственная куча", "topK (k = 1000)", million, () -> Task03A.topK(input, TOP_COUNT));
        measure("Полная сортировка", "topK (k = 1000)", million, () -> sortedTop(input));
        List<Integer> duplicates = new Random(SEED).ints(100_000, 0, 50_000).boxed().toList();
        measure("Собственная хеш-таблица", "distinct", duplicates.size(), () -> Task03C.distinct(duplicates));
        measure("LinkedHashSet", "distinct", duplicates.size(),
                () -> new ArrayList<>(new LinkedHashSet<>(duplicates)));
        int[] windowInput = new Random(SEED).ints(200_000).toArray();
        measure("Собственная куча", "Скользящий минимум (k = 1000)", windowInput.length,
                () -> Task03D.slidingMinimum(windowInput, WINDOW_SIZE));
        measureWords();
    }

    private static List<Integer> sortedTop(List<Integer> input) {
        List<Integer> sorted = new ArrayList<>(input);
        sorted.sort(Comparator.reverseOrder());
        return new ArrayList<>(sorted.subList(0, TOP_COUNT));
    }

    private static void measureWords() {
        String paragraph = "Алгоритм и структура данных работают вместе. "
                + "Таблица хранит ключ и значение, куча хранит минимум. "
                + "Поиск, вставка и удаление меняют состояние структуры данных. ";
        int paragraphBytes = paragraph.getBytes(StandardCharsets.UTF_8).length;
        String text = paragraph.repeat(WORD_SIZE / paragraphBytes);
        int bytes = text.getBytes(StandardCharsets.UTF_8).length;
        measure("Собственная хеш-таблица", "countWords (байты UTF-8)", bytes, () -> Task03B.countWords(text));
        System.out.println("10 самых частых слов:");
        Task03B.countWords(text).entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry::getKey))
                .limit(10)
                .forEach(entry -> System.out.printf("%s: %d%n", entry.getKey(), entry.getValue()));
    }

    private static <K> long lookup(HashMapCustom<K, K> map, K[] keys) {
        long checksum = 0;
        for (K key : keys) {
            K value = map.get(key);
            checksum += value != null ? value.hashCode() : 0;
        }
        return checksum;
    }

    private static HashMapCustom<Integer, Integer> buildMap(Integer[] keys, boolean quadratic, double load) {
        HashMapCustom<Integer, Integer> map = quadratic ? new QuadraticHashMap<>() : new LoadThresholdHashMap<>(load);
        for (Integer key : keys) {
            map.put(key, key);
        }
        return map;
    }

    private static Integer[] randomKeys(int count) {
        return new Random(SEED).ints().distinct().limit(count).boxed().toArray(Integer[]::new);
    }

    private static Integer[] makeSearches(Integer[] keys) {
        Random random = new Random(SEED + 1);
        Integer[] searches = new Integer[SEARCH_COUNT];
        for (int index = 0; index < searches.length; index++) {
            searches[index] = index % 2 == 0 ? keys[random.nextInt(keys.length)] : random.nextInt();
        }
        return searches;
    }

    private static void measure(String implementation, String operation, int size, Supplier<?> action) {
        for (int sample = 0; sample < WARMUPS; sample++) {
            result = action.get();
        }
        long elapsed = 0;
        long allocated = 0;
        for (int sample = 0; sample < SAMPLES; sample++) {
            long initialBytes = ALLOCATION.getCurrentThreadAllocatedBytes();
            long initialTime = System.nanoTime();
            result = action.get();
            elapsed += System.nanoTime() - initialTime;
            allocated += ALLOCATION.getCurrentThreadAllocatedBytes() - initialBytes;
        }
        System.out.printf(Locale.ROOT, "| %s | %s | %d | %.3f | %d |%n",
                implementation, operation, size, elapsed / (SAMPLES * 1_000_000.0), allocated / SAMPLES);
    }
}
