package ru.bmstu.lab2.benchmark;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;
import ru.bmstu.lab2.task_1a.PriorityQueueCustom;
import ru.bmstu.lab2.task_2a.HashMapCustom;
import ru.bmstu.lab2.task_3b.SampleTextGenerator;
import ru.bmstu.lab2.task_3b.WordCounter;
import ru.bmstu.lab2.task_3b.WordTokenizer;

/**
 * Бенчмарки ЛР2: собственные структуры против {@link PriorityQueue} и {@link HashMap}.
 *
 * <p>Для каждого N из {@link #SIZES} операция сначала прогоняется {@link #WARMUP_RUNS} раз без замера
 * (чтобы JIT-компилятор успел скомпилировать горячий код), затем {@link #MEASURED_RUNS} раз с замером
 * {@link System#nanoTime()}; печатается среднее время в миллисекундах. Данные генерируются с
 * фиксированным зерном, поэтому обе реализации получают одинаковый вход. Результаты операций
 * складываются в {@link #sink}, чтобы JIT не выбросил «бесполезные» вычисления.
 *
 * <p>Результат печатается строками markdown-таблицы для {@code benchmarks.md}.
 * Запуск: кнопка «Run» над методом {@code main} в VS Code (класс лежит в тестах, потому что
 * стандартные коллекции разрешены только в тестах и бенчмарках).
 */
public final class Lab2Benchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_RUNS = 10;
    private static final int MEASURED_RUNS = 20;
    private static final long SEED = 12_345L;
    private static final double NANOS_IN_MILLI = 1_000_000.0;
    private static final int BYTES_IN_KILOBYTE = 1024;
    private static final int TOP_COUNT = 10;

    /** Сколько раз вызывать сборщик мусора перед замером памяти, чтобы куча «успокоилась». */
    private static final int GC_ROUNDS = 3;
    private static final long GC_PAUSE_MS = 50;

    private static long sink;

    private Lab2Benchmark() {
    }

    /**
     * Запускает все замеры и печатает таблицы.
     *
     * @param args не используются
     * @throws InterruptedException если поток прервали во время паузы перед замером памяти
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Java %s, %s %s, ядер: %d%n%n", System.getProperty("java.version"),
                System.getProperty("os.name"), System.getProperty("os.arch"),
                Runtime.getRuntime().availableProcessors());
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---|---|---|");
        for (int size : SIZES) {
            benchmarkPriorityQueues(randomInts(size));
            benchmarkHashMaps(randomInts(size));
            benchmarkWordCount(SampleTextGenerator.generateWords(size, SEED), size);
        }
        System.out.println();
        printMemoryTable();
        System.out.println("(служебное значение: " + sink + ")");
    }

    private static void benchmarkPriorityQueues(int[] data) {
        report("`PriorityQueueCustom`", "add + poll (цикл)", data.length,
                measure(() -> customQueueCycle(data)), "двоичная куча на Object[]");
        report("`java.util.PriorityQueue`", "add + poll (цикл)", data.length,
                measure(() -> javaQueueCycle(data)), "");
    }

    private static void benchmarkHashMaps(int[] keys) {
        report("`HashMapCustom`", "put", keys.length, measure(() -> customPut(keys).size()),
                "линейное пробирование");
        report("`java.util.HashMap`", "put", keys.length, measure(() -> javaPut(keys).size()), "");
        HashMapCustom<Integer, Integer> custom = customPut(keys);
        Map<Integer, Integer> java = javaPut(keys);
        report("`HashMapCustom`", "get", keys.length, measure(() -> customGetAll(custom, keys)), "все ключи есть");
        report("`java.util.HashMap`", "get", keys.length, measure(() -> javaGetAll(java, keys)), "");
        report("`HashMapCustom`", "put + remove всех", keys.length,
                measure(() -> customPutRemove(keys)), "удаление → DELETED");
        report("`java.util.HashMap`", "put + remove всех", keys.length, measure(() -> javaPutRemove(keys)), "");
    }

    private static void benchmarkWordCount(String text, int words) {
        report("`WordCounter` (на `HashMapCustom`)", "countWords + топ-10", words,
                measure(() -> WordCounter.topWords(text, TOP_COUNT).size()), "N — число слов в тексте");
        report("`java.util.HashMap`", "подсчёт слов", words, measure(() -> javaCountWords(text)),
                "тот же разбор на слова");
    }

    private static long customQueueCycle(int[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : data) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static long javaQueueCycle(int[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int value : data) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static HashMapCustom<Integer, Integer> customPut(int[] keys) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], i);
        }
        return map;
    }

    private static Map<Integer, Integer> javaPut(int[] keys) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], i);
        }
        return map;
    }

    private static long customGetAll(HashMapCustom<Integer, Integer> map, int[] keys) {
        long checksum = 0;
        for (int key : keys) {
            checksum += map.get(key);
        }
        return checksum;
    }

    private static long javaGetAll(Map<Integer, Integer> map, int[] keys) {
        long checksum = 0;
        for (int key : keys) {
            checksum += map.get(key);
        }
        return checksum;
    }

    private static long customPutRemove(int[] keys) {
        HashMapCustom<Integer, Integer> map = customPut(keys);
        for (int key : keys) {
            map.remove(key);
        }
        return map.size();
    }

    private static long javaPutRemove(int[] keys) {
        Map<Integer, Integer> map = javaPut(keys);
        for (int key : keys) {
            map.remove(key);
        }
        return map.size();
    }

    private static long javaCountWords(String text) {
        Map<String, Integer> counts = new HashMap<>();
        WordTokenizer.forEachWord(text, word -> counts.merge(word, 1, Integer::sum));
        return counts.size();
    }

    /** Прогрев, затем среднее время {@link #MEASURED_RUNS} запусков в миллисекундах. */
    private static double measure(Supplier<Number> operation) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            sink += operation.get().longValue();
        }
        System.gc(); // мусор от прогрева не должен собираться посреди замера
        long total = 0;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            long start = System.nanoTime();
            sink += operation.get().longValue();
            total += System.nanoTime() - start;
        }
        return total / (double) MEASURED_RUNS / NANOS_IN_MILLI;
    }

    private static void printMemoryTable() throws InterruptedException {
        int size = SIZES[SIZES.length - 1];
        int[] data = randomInts(size);
        System.out.printf("Память, N = %d (примерная оценка через Runtime, КБ)%n%n", size);
        System.out.println("| Структура | Занято памяти (КБ) |");
        System.out.println("|---|---|");
        printMemory("`PriorityQueueCustom`", () -> filledCustomQueue(data));
        printMemory("`java.util.PriorityQueue`", () -> filledJavaQueue(data));
        printMemory("`HashMapCustom`", () -> customPut(data));
        printMemory("`java.util.HashMap`", () -> javaPut(data));
        HashMapCustom<Integer, Integer> custom = customPut(data);
        System.out.printf(Locale.ROOT, "%nЁмкость HashMapCustom после %d вставок: %d (загрузка %.2f)%n",
                size, custom.capacity(), custom.size() / (double) custom.capacity());
    }

    private static void printMemory(String name, Supplier<Object> factory) throws InterruptedException {
        long before = usedMemory();
        Object structure = factory.get();
        long after = usedMemory();
        sink += structure.hashCode() & 1;
        System.out.printf("| %s | %d |%n", name, (after - before) / BYTES_IN_KILOBYTE);
    }

    private static PriorityQueueCustom<Integer> filledCustomQueue(int[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : data) {
            queue.add(value);
        }
        return queue;
    }

    private static PriorityQueue<Integer> filledJavaQueue(int[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int value : data) {
            queue.add(value);
        }
        return queue;
    }

    private static long usedMemory() throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < GC_ROUNDS; i++) {
            System.gc();
            Thread.sleep(GC_PAUSE_MS);
        }
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static int[] randomInts(int size) {
        Random random = new Random(SEED);
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    private static void report(String structure, String operation, int size, double millis, String note) {
        String groupedSize = String.format(Locale.ROOT, "%,d", size).replace(',', ' ');
        System.out.printf(Locale.ROOT, "| %s | %s | %s | %.3f | %s |%n",
                structure, operation, groupedSize, millis, note);
    }
}
