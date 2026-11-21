package ru.bmstu.lab2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/** Учебные замеры собственных структур и стандартных аналогов на одинаковых данных. */
public final class StructureBenchmark {
    private static final int WARMUP_ROUNDS = 5;
    private static final int MEASURE_ROUNDS = 10;
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static volatile long blackhole;

    private StructureBenchmark() {
    }

    /**
     * Запускает замеры и сохраняет Markdown-таблицы.
     *
     * @param args единственный аргумент — путь к benchmarks.md
     * @throws IOException если не удалось записать результат
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Укажите путь к benchmarks.md в Program arguments");
        }
        StringBuilder report = createHeader();
        for (int size : SIZES) {
            Integer[] data = createData(size);
            benchmarkQueues(data, report);
            benchmarkMaps(data, report);
        }
        appendConclusion(report);
        Path output = Path.of(args[0]).toAbsolutePath();
        Files.createDirectories(output.getParent());
        Files.writeString(output, report, StandardCharsets.UTF_8);
        System.out.println("Результаты записаны в " + output);
        System.out.println(report);
    }

    private static StringBuilder createHeader() {
        StringBuilder text = new StringBuilder("# Замеры производительности ЛР2\n\n");
        text.append("Дата запуска: ").append(LocalDate.now()).append(".\n\n");
        text.append("Java: ").append(System.getProperty("java.runtime.version")).append("; ");
        text.append(System.getProperty("java.vendor")).append(". ОС: ");
        text.append(System.getProperty("os.name")).append(" ").append(System.getProperty("os.arch"));
        text.append(". Доступно процессоров JVM: ").append(Runtime.getRuntime().availableProcessors());
        text.append(". Максимальная память JVM: ");
        text.append(Runtime.getRuntime().maxMemory() / (1024 * 1024)).append(" МиБ.\n\n");
        text.append("Для каждого N: 5 прогревочных и 10 измеряемых повторов, System.nanoTime(). ");
        text.append("В таблице среднее время всей серии из N операций, а не одной операции. ");
        text.append("Данные — уникальные случайные Integer, seed = 2026 + N. ");
        text.append("Создание данных и упаковка Integer находятся вне измерений. ");
        text.append("Порядок запуска вариантов чередуется. Структуры в каждом повторе новые.\n\n");
        text.append("Очередь: N добавлений, затем N извлечений; обе части в одном интервале. ");
        text.append("Таблица: N вставок, N успешных поисков и N удалений измеряются отдельно. ");
        text.append("Расширения включены во время вставок. Суммы значений проверяются. ");
        text.append("Стандартные структуры имеют начальную ёмкость 16, HashMap — порог 0.75.\n\n");
        text.append("Это учебный микробенчмарк, а не JMH: на результат влияют JIT, GC и нагрузка ОС. ");
        text.append("Память в байтах не измерялась; ниже приведено сравнение устройства структур.\n\n");
        text.append("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |\n");
        text.append("|---|---|---:|---:|---|\n");
        return text;
    }

    private static Integer[] createData(int size) {
        Random random = new Random(2026L + size);
        Set<Integer> used = new HashSet<>();
        Integer[] data = new Integer[size];
        for (int index = 0; index < size; index++) {
            int value;
            do {
                value = random.nextInt();
            } while (!used.add(value));
            data[index] = value;
        }
        return data;
    }

    private static void benchmarkQueues(Integer[] data, StringBuilder report) {
        long[] totals = new long[4];
        for (int round = 0; round < WARMUP_ROUNDS + MEASURE_ROUNDS; round++) {
            for (int offset = 0; offset < 4; offset++) {
                int mode = (round + offset) % 4;
                Comparator<Integer> order = mode < 2 ? Comparator.naturalOrder() : Comparator.reverseOrder();
                long elapsed = mode % 2 == 0 ? customQueue(data, order) : standardQueue(data, order);
                if (round >= WARMUP_ROUNDS) {
                    totals[mode] += elapsed;
                }
            }
        }
        for (int mode = 0; mode < 4; mode++) {
            String name = mode % 2 == 0 ? "PriorityQueueCustom" : "java.util.PriorityQueue";
            addRow(report, name, "add + poll", data.length, totals[mode], mode < 2 ? "min-heap" : "max-heap");
        }
    }

    private static long customQueue(Integer[] data, Comparator<Integer> order) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(order);
        long sum = 0;
        long start = System.nanoTime();
        for (Integer value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        long elapsed = System.nanoTime() - start;
        consume(data, sum);
        return elapsed;
    }

    private static long standardQueue(Integer[] data, Comparator<Integer> order) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(16, order);
        long sum = 0;
        long start = System.nanoTime();
        for (Integer value : data) {
            queue.add(value);
        }
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        long elapsed = System.nanoTime() - start;
        consume(data, sum);
        return elapsed;
    }

    private static void benchmarkMaps(Integer[] data, StringBuilder report) {
        long[][] totals = new long[2][3];
        for (int round = 0; round < WARMUP_ROUNDS + MEASURE_ROUNDS; round++) {
            for (int offset = 0; offset < 2; offset++) {
                int mode = (round + offset) % 2;
                long[] elapsed = mode == 0 ? customMap(data) : standardMap(data);
                if (round >= WARMUP_ROUNDS) {
                    for (int operation = 0; operation < 3; operation++) {
                        totals[mode][operation] += elapsed[operation];
                    }
                }
            }
        }
        String[] operations = {"put", "get", "remove"};
        for (int mode = 0; mode < 2; mode++) {
            for (int operation = 0; operation < 3; operation++) {
                String name = mode == 0 ? "HashMapCustom" : "java.util.HashMap";
                addRow(report, name, operations[operation], data.length, totals[mode][operation], "load = 0.75");
            }
        }
    }

    private static long[] customMap(Integer[] data) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        long start = System.nanoTime();
        for (Integer value : data) {
            map.put(value, value);
        }
        long inserted = System.nanoTime();
        long sum = 0;
        for (Integer value : data) {
            sum += map.get(value);
        }
        long found = System.nanoTime();
        int removed = 0;
        for (Integer value : data) {
            if (map.remove(value)) {
                removed++;
            }
        }
        long finished = System.nanoTime();
        checkRemoved(data.length, removed, map.size());
        consume(data, sum);
        return new long[]{inserted - start, found - inserted, finished - found};
    }

    private static long[] standardMap(Integer[] data) {
        HashMap<Integer, Integer> map = new HashMap<>(16, 0.75f);
        long start = System.nanoTime();
        for (Integer value : data) {
            map.put(value, value);
        }
        long inserted = System.nanoTime();
        long sum = 0;
        for (Integer value : data) {
            sum += map.get(value);
        }
        long found = System.nanoTime();
        int removed = 0;
        for (Integer value : data) {
            if (map.remove(value) != null) {
                removed++;
            }
        }
        long finished = System.nanoTime();
        checkRemoved(data.length, removed, map.size());
        consume(data, sum);
        return new long[]{inserted - start, found - inserted, finished - found};
    }

    private static void consume(Integer[] data, long actual) {
        long expected = 0;
        for (Integer value : data) {
            expected += value;
        }
        if (actual != expected) {
            throw new IllegalStateException("Неверная контрольная сумма");
        }
        blackhole = actual;
    }

    private static void checkRemoved(int expected, int actual, int remaining) {
        if (expected != actual || remaining != 0) {
            throw new IllegalStateException("Удалены не все элементы");
        }
    }

    private static void addRow(StringBuilder report, String name, String operation,
                               int size, long nanoseconds, String note) {
        double milliseconds = nanoseconds / (MEASURE_ROUNDS * 1_000_000.0);
        report.append(String.format(Locale.ROOT, "| %s | %s | %d | %.3f | %s |%n",
                name, operation, size, milliseconds, note));
    }

    private static void appendConclusion(StringBuilder report) {
        report.append("\n## Объяснение результатов\n\n");
        report.append("Min-heap и max-heap на одних 100 000 элементах выполняют одинаковые по ");
        report.append("асимптотике операции: O(N log N) на добавление и извлечение всех элементов; ");
        report.append("разница времени связана с порядком сравнений, просеиваниями, JIT и шумом замера. ");
        report.append("Стандартная очередь использует оптимизированные просеивания, а собственная — ");
        report.append("понятные обмены элементов; у обеих массив ссылок и память O(C), где C — ёмкость. ");
        report.append("У HashMapCustom два массива ссылок и массив состояний, а у HashMap есть отдельные ");
        report.append("объекты записей: собственная таблица избегает этих объектов, но требует запаса ");
        report.append("ячеек, поэтому точный выигрыш памяти без измерений утверждать нельзя. ");
        report.append("Различия скорости хеш-таблиц зависят от коллизий, перехеширования и GC; ");
        report.append("этот опыт относится к случайным ключам и не доказывает преимущество на любых данных.\n");
    }
}
