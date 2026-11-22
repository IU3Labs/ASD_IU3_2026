package ru.bmstu.lab2.bench;

import com.sun.management.ThreadMXBean;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;
import ru.bmstu.lab2.algorithms.CollectionAlgorithms;
import ru.bmstu.lab2.map.CollisionKey;
import ru.bmstu.lab2.map.HashMapCustom;
import ru.bmstu.lab2.queue.PriorityQueueCustom;

/** Воспроизводимые учебные замеры. Стандартные коллекции используются только как эталоны. */
public final class Benchmarks {
    private static final int WARMUPS = 3;
    private static final int REPETITIONS = 5;
    private static final int SEED = 2026;
    private static volatile Object blackhole;

    private Benchmarks() {
    }

    /** Создаёт benchmarks.md; первый аргумент — путь к выходному файлу. */
    public static void main(String[] args) throws IOException {
        Path output = Path.of(args.length == 0 ? "benchmarks.md" : args[0]);
        StringBuilder report = new StringBuilder(header());
        for (int size : new int[] {10_000, 50_000, 100_000}) {
            runStructures(report, size);
        }
        runHeapModes(report);
        runCollisions(report);
        runTopK(report);
        runDistinct(report);
        report.append("\n## Выводы\n\n")
                .append("Min-heap и max-heap имеют одинаковую сложность O(n log n), ")
                .append("а разница во времени зависит от сравнений элементов и работы Java. ")
                .append("В этих замерах собственная хеш-таблица медленнее стандартной из-за длинных цепочек проверок; ")
                .append("при одинаковом хеше обработка всех n ключей требует O(n²) действий. ")
                .append("Свои структуры в этих замерах выделили меньше памяти, чем стандартные аналоги, ")
                .append("хотя часть памяти массивов занимают свободные ячейки и копии при расширении. ")
                .append("Измеренная память включает все выделения за один запуск, ")
                .append("в том числе временные объекты, поэтому это не размер готового результата.\n");
        Files.writeString(output, report, StandardCharsets.UTF_8);
        System.out.println("Saved: " + output.toAbsolutePath());
    }

    private static String header() {
        return "# Бенчмарки ЛР2\n\n"
                + "Дата: " + LocalDate.now(ZoneId.of("Europe/Moscow")) + ".\n\n"
                + "Среда: " + System.getProperty("java.vm.name") + " " + System.getProperty("java.version")
                + "; " + System.getProperty("os.name") + " " + System.getProperty("os.arch")
                + "; CPU: " + System.getenv("PROCESSOR_IDENTIFIER") + ".\n\n"
                + "Первые " + WARMUPS + " запуска нужны для прогрева: Java успевает подготовить код, "
                + "а их время не учитывается. Затем выполняются " + REPETITIONS
                + " запусков и вычисляется среднее время всего пакета операций. "
                + "Для времени используется System.nanoTime().\n\n"
                + "Обе реализации получают одинаковые входные данные. Начальное значение генератора "
                + "случайных чисел (seed = " + SEED + ") фиксировано, чтобы повторять тот же набор. "
                + "Подготовка входа и проверка ответов не входят в замер; создание исследуемых структур входит. "
                + "Результаты сохраняются, чтобы Java не исключила вычисления как ненужные.\n\n"
                + "Условия запуска: один процесс, без JaCoCo, -Xms512m -Xmx512m "
                + "(начальный и максимальный размер памяти Java — 512 МиБ). "
                + "Время может меняться из-за оптимизации кода Java, освобождения памяти "
                + "и других работающих программ.\n\n"
                + "## Основные структуры\n\n"
                + "Сначала добавляются все N элементов, затем извлекаются все N; "
                + "для таблиц — N put, N успешных get и N remove. "
                + "В очередях есть повторы; ключи таблиц уникальны.\n\n"
                + tableHeader();
    }

    private static String tableHeader() {
        return "| Структура | Операция | N элементов | Среднее время (мс) | Примечание |\n"
                + "|---|---|---:|---:|---|\n";
    }

    private static void runStructures(StringBuilder report, int size) {
        List<Integer> input = randomIntegers(size);
        checkEqual(customQueue(input, null), standardQueue(input, null));
        row(report, "PriorityQueueCustom", "add + poll", size, "min-heap",
                () -> customQueue(input, null));
        row(report, "java.util.PriorityQueue", "add + poll", size, "min-heap",
                () -> standardQueue(input, null));
        String[] keys = stringKeys(size);
        checkEqual(customMap(keys), standardMap(keys));
        row(report, "HashMapCustom", "put + get + remove", size, "строковые ключи",
                () -> customMap(keys));
        row(report, "java.util.HashMap", "put + get + remove", size, "строковые ключи",
                () -> standardMap(keys));
    }

    private static long customQueue(List<Integer> input, Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (Integer value : input) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static long standardQueue(List<Integer> input, Comparator<Integer> comparator) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(11, comparator);
        for (Integer value : input) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static <K> long customMap(K[] keys) {
        HashMapCustom<K, K> map = new HashMapCustom<>();
        for (K key : keys) {
            map.put(key, key);
        }
        long checksum = 0;
        for (K key : keys) {
            if (map.get(key) != null) {
                checksum++;
            }
        }
        for (K key : keys) {
            if (map.remove(key)) {
                checksum++;
            }
        }
        return checksum;
    }

    private static <K> long standardMap(K[] keys) {
        HashMap<K, K> map = new HashMap<>();
        for (K key : keys) {
            map.put(key, key);
        }
        long checksum = 0;
        for (K key : keys) {
            if (map.get(key) != null) {
                checksum++;
            }
        }
        for (K key : keys) {
            if (map.remove(key) != null) {
                checksum++;
            }
        }
        return checksum;
    }

    private static void runHeapModes(StringBuilder report) {
        List<Integer> input = randomIntegers(100_000);
        report.append("\n## 1B — min-heap и max-heap\n\n")
                .append("Один и тот же набор и явный компаратор в обоих режимах.\n\n")
                .append(tableHeader());
        row(report, "PriorityQueueCustom", "add + poll", input.size(), "min: naturalOrder",
                () -> customQueue(input, Comparator.naturalOrder()));
        row(report, "PriorityQueueCustom", "add + poll", input.size(), "max: reverseOrder",
                () -> customQueue(input, Comparator.reverseOrder()));
        report.append("\nВ одном режиме первым извлекается минимум, в другом — максимум. ")
                .append("В обоих случаях выполняются просеивания по куче, поэтому сложность одинакова. ")
                .append("Число сравнений и обменов может различаться; на время также влияют ")
                .append("оптимизация кода Java и освобождение памяти.\n");
    }

    private static void runCollisions(StringBuilder report) {
        String[] regular = stringKeys(10_000);
        CollisionKey[] collisions = new CollisionKey[regular.length];
        for (int index = 0; index < regular.length; index++) {
            collisions[index] = new CollisionKey(regular[index]);
        }
        checkEqual(2L * regular.length, customMap(regular));
        checkEqual(2L * regular.length, customMap(collisions));
        report.append("\n## 2C — искусственные коллизии\n\n").append(tableHeader());
        row(report, "HashMapCustom", "put + get + remove", regular.length, "обычный hashCode строки",
                () -> customMap(regular));
        row(report, "HashMapCustom", "put + get + remove", collisions.length, "CollisionKey: hashCode = 1",
                () -> customMap(collisions));
        report.append("\nПодробное объяснение деградации — в README.md.\n");
    }

    private static void runTopK(StringBuilder report) {
        List<Integer> input = randomIntegers(1_000_000);
        int k = 100;
        checkEqual(sortedTopK(input, k), CollectionAlgorithms.topK(input, k));
        report.append("\n## 3A — Top-K\n\n").append(tableHeader());
        row(report, "Собственная куча", "topK", input.size(), "k = 100",
                () -> CollectionAlgorithms.topK(input, k));
        row(report, "Collections.sort", "копия + сортировка + последние k", input.size(), "k = 100",
                () -> sortedTopK(input, k));
    }

    private static List<Integer> sortedTopK(List<Integer> input, int k) {
        List<Integer> sorted = new ArrayList<>(input);
        Collections.sort(sorted);
        return new ArrayList<>(sorted.subList(sorted.size() - k, sorted.size()));
    }

    private static void runDistinct(StringBuilder report) {
        report.append("\n## 3C — уникальные элементы: время и память\n\n")
                .append("В столбце «выделено» указано, сколько байтов программа выделила за один запуск, ")
                .append("в среднем по пяти запускам. Счётчик ThreadMXBean учитывает выделения текущего потока, ")
                .append("включая временную таблицу и новые массивы при расширении. ")
                .append("Подготовленный заранее вход не учитывается. Это расход памяти за запуск, ")
                .append("а не объём, занятый результатом после удаления временных объектов. ")
                .append("u — число уникальных элементов; n/a означает недоступность счётчика. ")
                .append("LinkedHashSet показан отдельно и вместе с преобразованием в List, ")
                .append("чтобы сравнить также получение результата того же типа.\n\n")
                .append(tableHeader());
        for (int size : new int[] {10_000, 50_000, 100_000}) {
            List<Integer> input = randomIntegers(size);
            int unique = new LinkedHashSet<>(input).size();
            checkEqual(new ArrayList<>(new LinkedHashSet<>(input)), CollectionAlgorithms.distinct(input));
            row(report, "Собственная таблица + список", "distinct", size, "u = " + unique,
                    () -> CollectionAlgorithms.distinct(input));
            row(report, "LinkedHashSet", "new LinkedHashSet<>(items)", size, "u = " + unique,
                    () -> new LinkedHashSet<>(input));
            row(report, "LinkedHashSet + ArrayList", "distinct в List", size, "u = " + unique,
                    () -> new ArrayList<>(new LinkedHashSet<>(input)));
        }
    }

    private static void row(StringBuilder report, String structure, String operation, int size,
            String note, Supplier<?> action) {
        for (int iteration = 0; iteration < WARMUPS; iteration++) {
            blackhole = action.get();
        }
        ThreadMXBean memory = memoryCounter();
        long nanos = 0;
        long bytes = 0;
        long threadId = Thread.currentThread().threadId();
        for (int iteration = 0; iteration < REPETITIONS; iteration++) {
            long beforeBytes = memory == null ? 0 : memory.getThreadAllocatedBytes(threadId);
            long start = System.nanoTime();
            blackhole = action.get();
            nanos += System.nanoTime() - start;
            bytes += memory == null ? 0 : memory.getThreadAllocatedBytes(threadId) - beforeBytes;
        }
        String allocation = memory == null ? "n/a" : String.valueOf(bytes / REPETITIONS);
        report.append(String.format(Locale.ROOT, "| %s | %s | %d | %.3f | %s; выделено %s байт |%n",
                structure, operation, size, noteTime(nanos), note, allocation));
        System.out.println(structure + ": " + operation + ", N=" + size);
    }

    private static double noteTime(long nanos) {
        return nanos / (double) REPETITIONS / 1_000_000;
    }

    private static ThreadMXBean memoryCounter() {
        java.lang.management.ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        if (bean instanceof ThreadMXBean memory && memory.isThreadAllocatedMemorySupported()) {
            if (!memory.isThreadAllocatedMemoryEnabled()) {
                memory.setThreadAllocatedMemoryEnabled(true);
            }
            return memory;
        }
        return null;
    }

    private static List<Integer> randomIntegers(int size) {
        Random random = new Random(SEED);
        List<Integer> result = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            result.add(random.nextInt(Math.max(1, size / 2)));
        }
        return result;
    }

    private static String[] stringKeys(int size) {
        String[] keys = new String[size];
        for (int index = 0; index < size; index++) {
            keys[index] = "key-" + index;
        }
        return keys;
    }

    private static void checkEqual(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Benchmark results differ");
        }
    }
}
