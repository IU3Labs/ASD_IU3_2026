package ru.bmstu.lab02;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;

import ru.bmstu.lab02.task_01a.PriorityQueueCustom;
import ru.bmstu.lab02.task_01b.ComparatorPriorityQueue;
import ru.bmstu.lab02.task_01c.RemovablePriorityQueue;
import ru.bmstu.lab02.task_01d.HeapifyPriorityQueue;

/** Воспроизводимые учебные замеры очередей; библиотечные коллекции используются только для сравнения. */
public final class PriorityQueueBenchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUP_ROUNDS = 8;
    private static final int MEASUREMENT_ROUNDS = 15;
    private static final long SEED = 20261118L;
    private static volatile Object blackhole;
    private static volatile long checksum;

    private PriorityQueueBenchmark() {
    }

    /**
     * Запускает замеры и записывает Markdown-отчёт, средние значения и отдельные измерения в CSV.
     *
     * @param args необязательный путь к каталогу отчётов, по умолчанию reports/lab02
     * @throws IOException если файлы отчётов не удалось записать
     */
    public static void main(String[] args) throws IOException {
        Path directory = Path.of(args.length == 0 ? "reports/lab02" : args[0]);
        Files.createDirectories(directory);
        List<Result> results = new ArrayList<>();
        for (int size : SIZES) {
            Integer[] values = randomValues(size);
            results.addAll(measure(cases(values), size));
            System.out.println("Измерен набор N = " + size);
        }
        writeCsv(directory, results);
        try (PrintWriter writer = writer(directory.resolve("benchmarks.md"))) {
            writeHeader(writer);
            writeCycleTable(writer, results);
            writeMinMaxTable(writer, results);
            writeBuildTable(writer, results);
            writeConclusions(writer, results);
        }
        System.out.println("Отчёты: " + directory.toAbsolutePath());
        System.out.println("Контрольная сумма: " + checksum + "; объект: " + blackhole.getClass().getSimpleName());
    }

    private static Integer[] randomValues(int size) {
        Random random = new Random(SEED);
        Integer[] values = new Integer[size];
        for (int index = 0; index < size; index++) {
            values[index] = random.nextInt();
        }
        return values;
    }

    private static List<BenchmarkCase> cases(Integer[] values) {
        Comparator<Integer> minOrder = Comparator.naturalOrder();
        Comparator<Integer> maxOrder = Comparator.reverseOrder();
        return List.of(
                customCycle("1A", PriorityQueueCustom::new, values),
                customCycle("1B min", () -> new ComparatorPriorityQueue<>(minOrder), values),
                customCycle("1C", RemovablePriorityQueue::new, values),
                customCycle("1D", HeapifyPriorityQueue::new, values),
                standardCycle("JDK min", null, values),
                customCycle("1B max", () -> new ComparatorPriorityQueue<>(maxOrder), values),
                standardCycle("JDK max", maxOrder, values),
                new BenchmarkCase("1D add", "build", () -> buildWithAdds(values)),
                new BenchmarkCase("1D heapify", "build", () -> {
                    blackhole = new HeapifyPriorityQueue<>(values);
                }),
                new BenchmarkCase("JDK heapify", "build", () -> {
                    blackhole = new PriorityQueue<>(Arrays.asList(values));
                }));
    }

    private static BenchmarkCase customCycle(String name, Supplier<PriorityQueueCustom<Integer>> factory,
            Integer[] values) {
        return new BenchmarkCase(name, "add + poll", () -> {
            PriorityQueueCustom<Integer> queue = factory.get();
            for (Integer value : values) {
                queue.add(value);
            }
            long sum = 0;
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
            checksum = sum;
        });
    }

    private static BenchmarkCase standardCycle(String name, Comparator<Integer> comparator, Integer[] values) {
        return new BenchmarkCase(name, "add + poll", () -> {
            PriorityQueue<Integer> queue = new PriorityQueue<>(comparator);
            for (Integer value : values) {
                queue.add(value);
            }
            long sum = 0;
            while (!queue.isEmpty()) {
                sum += queue.poll();
            }
            checksum = sum;
        });
    }

    private static void buildWithAdds(Integer[] values) {
        PriorityQueueCustom<Integer> queue = new HeapifyPriorityQueue<>();
        for (Integer value : values) {
            queue.add(value);
        }
        blackhole = queue;
    }

    private static List<Result> measure(List<BenchmarkCase> cases, int size) {
        double[][] samples = new double[cases.size()][MEASUREMENT_ROUNDS];
        for (int round = 0; round < WARMUP_ROUNDS + MEASUREMENT_ROUNDS; round++) {
            // Меняем порядок сценариев, чтобы один вариант не выполнялся всегда первым.
            for (int offset = 0; offset < cases.size(); offset++) {
                int index = (round + offset) % cases.size();
                long start = System.nanoTime();
                cases.get(index).action().run();
                double milliseconds = (System.nanoTime() - start) / 1_000_000.0;
                if (round >= WARMUP_ROUNDS) {
                    samples[index][round - WARMUP_ROUNDS] = milliseconds;
                }
            }
        }
        List<Result> results = new ArrayList<>();
        for (int index = 0; index < cases.size(); index++) {
            results.add(new Result(cases.get(index), size, samples[index]));
        }
        return results;
    }

    private static void writeCsv(Path directory, List<Result> results) throws IOException {
        try (PrintWriter summary = writer(directory.resolve("benchmark-data.csv"));
                PrintWriter samples = writer(directory.resolve("benchmark-samples.csv"))) {
            summary.println("structure,operation,n,mean_ms,median_ms,stddev_ms");
            samples.println("structure,operation,n,sample,elapsed_ms");
            for (Result result : results) {
                summary.printf(Locale.ROOT, "%s,%s,%d,%.6f,%.6f,%.6f%n", result.scenario().name(),
                        result.scenario().operation(), result.size(), result.mean(),
                        result.median(), result.deviation());
                for (int index = 0; index < result.samples().length; index++) {
                    samples.printf(Locale.ROOT, "%s,%s,%d,%d,%.6f%n", result.scenario().name(),
                            result.scenario().operation(), result.size(), index + 1, result.samples()[index]);
                }
            }
        }
    }

    private static void writeHeader(PrintWriter writer) {
        writer.println("# Бенчмарки lab02 — группа 1");
        writer.println();
        writer.println("Дата: " + OffsetDateTime.now() + ".");
        writer.printf("Среда: %s %s; %s %s; доступно процессоров: %d.%n", System.getProperty("java.vm.name"),
                System.getProperty("java.version"), System.getProperty("os.name"), System.getProperty("os.arch"),
                Runtime.getRuntime().availableProcessors());
        writer.printf("Для каждого сценария: %d прогревов, %d измерений, среднее арифметическое.%n",
                WARMUP_ROUNDS, MEASUREMENT_ROUNDS);
        writer.println("Общий Integer[]: Random(" + SEED + "), значения во всём диапазоне int.");
        writer.println("Сценарии чередуются с циклическим сдвигом порядка. Замер — System.nanoTime().");
        writer.println("Создание набора и упаковка Integer исключены; "
                + "создание очереди и расширения включены.");
        writer.println("add + poll: сначала N вставок, затем N извлечений до пустой очереди.");
        writer.println("build: только построение, без извлечений; копирование массива и выделение памяти включены.");
        writer.println("Результаты используются через volatile, чтобы вычисления не были устранены JIT.");
        writer.println("CSV со средним, медианой и отклонением: [benchmark-data.csv](benchmark-data.csv).");
        writer.println("Все отдельные измерения: [benchmark-samples.csv](benchmark-samples.csv).");
        writer.println("Повторный запуск из 32-Rayvo: `mvn test-compile exec:java`.");
        writer.println("Это учебный замер в общей JVM, без изоляции процессов: возможны шум, GC и влияние JIT.");
        writer.println();
    }

    private static void writeCycleTable(PrintWriter writer, List<Result> results) {
        writer.println("## Сравнение с java.util.PriorityQueue");
        writer.println();
        writer.println("1A — PriorityQueueCustom, 1B — ComparatorPriorityQueue, 1C — RemovablePriorityQueue,");
        writer.println("1D — HeapifyPriorityQueue; JDK — java.util.PriorityQueue.");
        writer.println();
        writer.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        writer.println("|---|---|---:|---:|---|");
        for (Result result : results) {
            if (result.scenario().operation().equals("add + poll")) {
                writer.printf(Locale.ROOT, "| %s | add + poll | %d | %.3f | %s |%n", result.scenario().name(),
                        result.size(), result.mean(),
                        result.scenario().name().contains("max") ? "max-heap" : "min-heap");
            }
        }
        writer.println();
    }

    private static void writeMinMaxTable(PrintWriter writer, List<Result> results) {
        writer.println("## Задача 1B — min-heap и max-heap на одном наборе 100 000 элементов");
        writer.println();
        writer.println("| Вариант | Среднее время add + poll (мс) | Медиана (мс) | Отклонение (мс) |");
        writer.println("|---|---:|---:|---:|");
        for (Result result : results) {
            if (result.size() == 100_000 && result.scenario().name().startsWith("1B")) {
                writer.printf(Locale.ROOT, "| %s | %.3f | %.3f | %.3f |%n", result.scenario().name(),
                        result.mean(), result.median(), result.deviation());
            }
        }
        writer.println();
        writer.println("Оба режима имеют O(n log n): меняется направление сравнения, а не высота дерева.");
        writer.println("Различия замеров объясняются сравнением, разными путями просеивания, JIT и шумом среды;");
        writer.println("по одному набору нельзя утверждать, что min-heap или max-heap всегда быстрее.");
        writer.println();
    }

    private static void writeBuildTable(PrintWriter writer, List<Result> results) {
        writer.println("## Задача 1D — последовательные add и линейное построение");
        writer.println();
        writer.println("| Построение | N элементов | Среднее время (мс) | Медиана (мс) | Сложность в худшем случае |");
        writer.println("|---|---:|---:|---:|---|");
        for (Result result : results) {
            if (result.scenario().operation().equals("build")) {
                writer.printf(Locale.ROOT, "| %s | %d | %.3f | %.3f | %s |%n", result.scenario().name(),
                        result.size(), result.mean(), result.median(),
                        result.scenario().name().equals("1D add") ? "O(n log n)" : "O(n)");
            }
        }
        writer.println();
        writeBuildComparison(writer, results);
        writer.println("Heapify обрабатывает внутренние узлы снизу вверх, без повторных вставок и расширений.");
        writer.println("Случайный набор часто даёт линейное ожидаемое суммарное время add; разрыв с heapify");
        writer.println("на нём может быть небольшим. Худшая граница последовательных add остаётся O(n log n).");
        writer.println();
    }

    private static void writeBuildComparison(PrintWriter writer, List<Result> results) {
        Result added = resultAtLargestSize(results, "1D add");
        Result heapified = resultAtLargestSize(results, "1D heapify");
        writer.printf(Locale.ROOT, "На 100 000 элементах: последовательные add — %.3f мс, heapify — %.3f мс.%n",
                added.mean(), heapified.mean());
        writer.printf(Locale.ROOT, "Отношение времени add / heapify — %.2f; это результат одного запуска.%n",
                added.mean() / heapified.mean());
    }

    private static Result resultAtLargestSize(List<Result> results, String structure) {
        return results.stream()
                .filter(result -> result.size() == 100_000 && result.scenario().name().equals(structure))
                .findFirst()
                .orElseThrow();
    }

    private static void writeConclusions(PrintWriter writer, List<Result> results) {
        writer.println("## Выводы о скорости и памяти");
        writer.println();
        Result custom = resultAtLargestSize(results, "1A");
        Result standard = resultAtLargestSize(results, "JDK min");
        String faster = custom.mean() < standard.mean() ? "PriorityQueueCustom" : "java.util.PriorityQueue";
        writer.println("На 100 000 элементах в add + poll быстрее " + faster + "; при одинаковой асимптотике");
        writer.println("разница связана с присваиваниями, сравнением и оптимизациями JVM.");
        writer.println("Оба режима используют O(n) памяти для пикового числа элементов n, "
                + "а компаратор добавляет O(1).");
        writer.println("Конструктор массива выделяет max(16, n) ссылок; последовательные add оставляют запас");
        writer.println("ёмкости при удвоении и временно удерживают старый массив во время копирования.");
        writer.println("Точные байты памяти здесь не измерялись: они зависят от размера ссылок и настроек JVM.");
    }

    private static PrintWriter writer(Path path) throws IOException {
        return new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8));
    }

    private record BenchmarkCase(String name, String operation, Runnable action) { }

    private record Result(BenchmarkCase scenario, int size, double[] samples) {
        private double mean() {
            double sum = 0;
            for (double value : samples) {
                sum += value;
            }
            return sum / samples.length;
        }

        private double median() {
            double[] sorted = samples.clone();
            Arrays.sort(sorted);
            return sorted[sorted.length / 2];
        }

        private double deviation() {
            double average = mean();
            double variance = 0;
            for (double value : samples) {
                variance += (value - average) * (value - average);
            }
            return Math.sqrt(variance / samples.length);
        }
    }
}
