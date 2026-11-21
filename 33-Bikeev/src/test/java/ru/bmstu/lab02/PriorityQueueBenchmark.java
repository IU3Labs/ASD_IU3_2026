package ru.bmstu.lab02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

/**
 * Runs reproducible paired benchmarks for laboratory tasks 1A, 1B and 1D.
 * Input generation and correctness checks are excluded from measured intervals.
 */
public final class PriorityQueueBenchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int LARGE_SIZE = 100_000;
    private static final int WARMUP_PAIRS = 5;
    private static final int MEASURED_PAIRS = 10;
    private static final long RANDOM_SEED = 20_261_120L;
    private static final long CHECKSUM_MULTIPLIER = 31L;
    private static final double NANOS_PER_MILLISECOND = 1_000_000.0;
    private static final long BYTES_PER_MEBIBYTE = 1_048_576L;
    private static final String WARMUP_PHASE = "warmup";
    private static final String MEASURED_PHASE = "measured";
    private static final String DEFAULT_REPORT = "reports/lab02/benchmarks.md";
    private static volatile long checksumSink;

    private PriorityQueueBenchmark() {
    }

    /**
     * Executes benchmarks and writes a Markdown report and adjacent raw CSV file.
     *
     * @param args optional first argument specifying the Markdown output path
     * @throws IOException if the output directory or files cannot be written
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            throw new IllegalArgumentException("Usage: PriorityQueueBenchmark [report.md]");
        }
        Path reportPath = Path.of(args.length == 0 ? DEFAULT_REPORT : args[0]).toAbsolutePath();
        Path samplesPath = reportPath.resolveSibling("benchmark-samples.csv");
        Instant startedAt = Instant.now();
        List<Experiment> experiments = createExperiments();
        List<Sample> samples = new ArrayList<>();
        for (Experiment experiment : experiments) {
            runExperiment(experiment, samples);
        }
        Instant finishedAt = Instant.now();
        Files.createDirectories(reportPath.getParent());
        Files.writeString(samplesPath, renderCsv(samples), StandardCharsets.UTF_8);
        Files.writeString(reportPath, renderReport(experiments, samples, startedAt, finishedAt),
                StandardCharsets.UTF_8);
        System.out.println("Report: " + reportPath);
        System.out.println("Raw samples: " + samplesPath);
        System.out.println("Validated checksum: " + checksumSink);
    }

    private static List<Experiment> createExperiments() {
        List<Experiment> experiments = new ArrayList<>();
        for (int size : SIZES) {
            experiments.add(createLibraryComparison(createDataset(size)));
        }
        Dataset largeInput = createDataset(LARGE_SIZE);
        experiments.add(createOrderComparison(largeInput));
        experiments.add(createBuildComparison(largeInput));
        return experiments;
    }

    private static Dataset createDataset(int size) {
        Random random = new Random(RANDOM_SEED);
        Integer[] values = new Integer[size];
        for (int index = 0; index < size; index++) {
            values[index] = random.nextInt();
        }
        Integer[] ascending = Arrays.copyOf(values, size);
        Integer[] descending = Arrays.copyOf(values, size);
        Arrays.sort(ascending);
        Arrays.sort(descending, Comparator.reverseOrder());
        return new Dataset(values, ascending, descending);
    }

    private static Experiment createLibraryComparison(Dataset input) {
        Variant custom = new Variant("custom", "PriorityQueueCustom", "add + poll",
                "Естественный порядок, стандартная начальная ёмкость",
                () -> measureCustomCycle(input.values(), input.ascending(), null));
        Variant standard = new Variant("standard", "java.util.PriorityQueue", "add + poll",
                "Естественный порядок, стандартная начальная ёмкость",
                () -> measureStandardCycle(input.values(), input.ascending()));
        return new Experiment("library", input.values().length, custom, standard);
    }

    private static Experiment createOrderComparison(Dataset input) {
        Variant minimum = new Variant("min", "PriorityQueueCustom: min-heap", "add + poll",
                "Comparator.naturalOrder()",
                () -> measureCustomCycle(input.values(), input.ascending(), Comparator.naturalOrder()));
        Variant maximum = new Variant("max", "PriorityQueueCustom: max-heap", "add + poll",
                "Comparator.reverseOrder()",
                () -> measureCustomCycle(input.values(), input.descending(), Comparator.reverseOrder()));
        return new Experiment("order", input.values().length, minimum, maximum);
    }

    private static Experiment createBuildComparison(Dataset input) {
        Variant sequential = new Variant("sequential", "PriorityQueueCustom", "Последовательные add",
                "Создание очереди и все вставки; полное извлечение вне таймера",
                () -> measureBuild(input.values(), input.ascending(), false));
        Variant bottomUp = new Variant("bottom_up", "PriorityQueueCustom", "Построение снизу вверх",
                "Конструктор из массива, включая копирование; извлечение вне таймера",
                () -> measureBuild(input.values(), input.ascending(), true));
        return new Experiment("build", input.values().length, sequential, bottomUp);
    }

    private static void runExperiment(Experiment experiment, List<Sample> samples) {
        System.out.printf(Locale.ROOT, "Running %s, N=%d%n", experiment.id(), experiment.size());
        runPhase(experiment, WARMUP_PHASE, WARMUP_PAIRS, samples);
        runPhase(experiment, MEASURED_PHASE, MEASURED_PAIRS, samples);
    }

    private static void runPhase(Experiment experiment, String phase, int pairs, List<Sample> samples) {
        for (int pair = 1; pair <= pairs; pair++) {
            boolean leftFirst = pair % 2 == 1;
            Variant first = leftFirst ? experiment.left() : experiment.right();
            Variant second = leftFirst ? experiment.right() : experiment.left();
            recordSample(experiment, first, phase, pair, 1, samples);
            recordSample(experiment, second, phase, pair, 2, samples);
        }
    }

    private static void recordSample(Experiment experiment, Variant variant, String phase, int pair,
                                     int position, List<Sample> samples) {
        long elapsedNanos = variant.action().getAsLong();
        samples.add(new Sample(experiment.id(), variant.id(), experiment.size(), phase,
                pair, position, elapsedNanos));
    }

    private static long measureCustomCycle(Integer[] input, Integer[] expected, Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> queue = comparator == null
                ? new PriorityQueueCustom<>() : new PriorityQueueCustom<>(comparator);
        Integer[] extracted = new Integer[input.length];
        long started = System.nanoTime();
        for (Integer item : input) {
            queue.add(item);
        }
        for (int index = 0; index < extracted.length; index++) {
            extracted[index] = queue.poll();
        }
        long elapsed = System.nanoTime() - started;
        verifyEmpty(queue.size(), queue.isEmpty());
        verifyAndConsume(extracted, expected);
        return elapsed;
    }

    private static long measureStandardCycle(Integer[] input, Integer[] expected) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        Integer[] extracted = new Integer[input.length];
        // Identical direct loops avoid introducing adapter calls into either implementation's timing.
        long started = System.nanoTime();
        for (Integer item : input) {
            queue.add(item);
        }
        for (int index = 0; index < extracted.length; index++) {
            extracted[index] = queue.poll();
        }
        long elapsed = System.nanoTime() - started;
        verifyEmpty(queue.size(), queue.isEmpty());
        verifyAndConsume(extracted, expected);
        return elapsed;
    }

    private static long measureBuild(Integer[] input, Integer[] expected, boolean bottomUp) {
        long started = System.nanoTime();
        PriorityQueueCustom<Integer> queue = bottomUp
                ? new PriorityQueueCustom<>(input) : buildSequentially(input);
        long elapsed = System.nanoTime() - started;
        verifyBuiltQueue(queue, expected);
        return elapsed;
    }

    private static PriorityQueueCustom<Integer> buildSequentially(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer item : input) {
            queue.add(item);
        }
        return queue;
    }

    private static void verifyBuiltQueue(PriorityQueueCustom<Integer> queue, Integer[] expected) {
        if (queue.size() != expected.length) {
            throw new IllegalStateException("Incorrect size after building the heap");
        }
        Integer[] extracted = new Integer[expected.length];
        for (int index = 0; index < extracted.length; index++) {
            extracted[index] = queue.poll();
        }
        verifyEmpty(queue.size(), queue.isEmpty());
        verifyAndConsume(extracted, expected);
    }

    private static void verifyEmpty(int size, boolean empty) {
        if (size != 0 || !empty) {
            throw new IllegalStateException("The queue was not emptied completely");
        }
    }

    private static void verifyAndConsume(Integer[] actual, Integer[] expected) {
        long checksum = 1L;
        for (int index = 0; index < expected.length; index++) {
            if (!expected[index].equals(actual[index])) {
                throw new IllegalStateException("Incorrect extraction order at index " + index);
            }
            checksum = CHECKSUM_MULTIPLIER * checksum + actual[index];
        }
        // A volatile write makes the consumed result observable without entering the measured interval.
        checksumSink = checksum;
    }

    private static String renderCsv(List<Sample> samples) {
        StringBuilder csv = new StringBuilder();
        csv.append("experiment,variant,n,phase,pair,position,elapsed_ns,elapsed_ms\n");
        for (Sample sample : samples) {
            csv.append(String.format(Locale.ROOT, "%s,%s,%d,%s,%d,%d,%d,%.6f%n",
                    sample.experiment(), sample.variant(), sample.size(), sample.phase(),
                    sample.pair(), sample.position(), sample.elapsedNanos(),
                    sample.elapsedNanos() / NANOS_PER_MILLISECOND));
        }
        return csv.toString();
    }

    private static String renderReport(List<Experiment> experiments, List<Sample> samples,
                                       Instant startedAt, Instant finishedAt) {
        StringBuilder report = new StringBuilder("# Бенчмарки ЛР2\n\n");
        appendEnvironment(report, startedAt, finishedAt);
        appendMethodology(report);
        appendExperimentSection(report, "1. Сравнение со стандартной библиотекой", "library",
                experiments, samples);
        appendExperimentSection(report, "2. Min-heap и max-heap: задача 1B", "order", experiments, samples);
        appendExperimentSection(report, "3. Построение кучи: задача 1D", "build", experiments, samples);
        report.append("Последовательные вставки имеют верхнюю оценку O(n log n) на всю серию, ")
                .append("но на случайных данных суммарное число перестановок может быть близко к линейному. ")
                .append("Поэтому этот эксперимент не обязан показывать большой выигрыш конструктора O(n).\n\n");
        appendConclusions(report, samples);
        return report.toString();
    }

    private static void appendEnvironment(StringBuilder report, Instant startedAt, Instant finishedAt) {
        report.append("## Среда и параметры\n\n| Параметр | Значение |\n|---|---|\n");
        appendProperty(report, "Начало, UTC", startedAt.toString());
        appendProperty(report, "Окончание, UTC", finishedAt.toString());
        appendProperty(report, "Java", System.getProperty("java.version"));
        appendProperty(report, "Java runtime", System.getProperty("java.runtime.name"));
        appendProperty(report, "JVM", System.getProperty("java.vm.name"));
        appendProperty(report, "Поставщик Java", System.getProperty("java.vendor"));
        appendProperty(report, "ОС", System.getProperty("os.name") + " " + System.getProperty("os.version"));
        appendProperty(report, "Архитектура", System.getProperty("os.arch"));
        appendProperty(report, "Доступные JVM процессоры",
                Integer.toString(Runtime.getRuntime().availableProcessors()));
        appendProperty(report, "Максимальный heap JVM, МиБ",
                Long.toString(Runtime.getRuntime().maxMemory() / BYTES_PER_MEBIBYTE));
        appendProperty(report, "Random seed", Long.toString(RANDOM_SEED));
        appendProperty(report, "Прогревочных пар на эксперимент", Integer.toString(WARMUP_PAIRS));
        appendProperty(report, "Измеряемых пар на эксперимент", Integer.toString(MEASURED_PAIRS));
        appendProperty(report, "Таймер", "System.nanoTime()");
        report.append("\n");
    }

    private static void appendProperty(StringBuilder report, String name, String value) {
        report.append("| ").append(name).append(" | ")
                .append(value.replace("|", "\\|").replace("\n", " ")).append(" |\n");
    }

    private static void appendMethodology(StringBuilder report) {
        report.append("## Методика\n\n")
                .append("Все данные — Integer из Random.nextInt() с фиксированным seed; генерация, ")
                .append("упаковка чисел и сортировка эталона выполняются до замеров. ")
                .append("Размеры используют одинаковый seed и совпадающие префиксы последовательности. ")
                .append("Min-heap и max-heap получают один и тот же массив из 100 000 значений.\n\n")
                .append("В каждой паре порядок вариантов чередуется: A/B, затем B/A. ")
                .append("Таблицы учитывают только измеряемые пары; прогрев в среднее не включён. ")
                .append("Стандартное отклонение выборочное, с делением на количество замеров минус один. ")
                .append("Замеры выполняются последовательно в одной JVM, без принудительного System.gc().\n\n")
                .append("Цикл add + poll сначала заполняет очередь всеми N элементами, затем полностью её ")
                .append("опустошает. Создание пустой очереди и буфера результата выполняется до таймера; ")
                .append("расширения массива при вставках входят в измеряемое время. ")
                .append("В сравнении способов построения таймер включает создание очереди, ")
                .append("а извлечение элементов выполняется после остановки таймера.\n\n")
                .append("После каждого запуска проверяются размер, пустота и весь порядок извлечения ")
                .append("по отсортированному эталону. Checksum результата записывается в volatile-поле ")
                .append("после замера. CSV содержит все реальные интервалы, включая прогрев: ")
                .append("[benchmark-samples.csv](benchmark-samples.csv). ")
                .append("Это учебный benchmark runner, а не изолированный JMH-эксперимент; ")
                .append("разброс включает эффекты JIT, GC и нагрузки среды.\n\n");
    }

    private static void appendExperimentSection(StringBuilder report, String title, String experimentId,
                                                List<Experiment> experiments, List<Sample> samples) {
        report.append("## ").append(title).append("\n\n")
                .append("| Структура | Операция | N элементов | Среднее, мс | SD, мс | Мин.–макс., мс | Примечание |\n")
                .append("|---|---|---:|---:|---:|---:|---|\n");
        for (Experiment experiment : experiments) {
            if (experiment.id().equals(experimentId)) {
                appendResultRow(report, experiment, experiment.left(), samples);
                appendResultRow(report, experiment, experiment.right(), samples);
            }
        }
        report.append("\n");
    }

    private static void appendResultRow(StringBuilder report, Experiment experiment, Variant variant,
                                        List<Sample> samples) {
        Statistics statistics = summarize(samples, experiment.id(), variant.id(), experiment.size());
        report.append(String.format(Locale.ROOT, "| %s | %s | %d | %.4f | %.4f | %.4f–%.4f | %s |%n",
                variant.label(), variant.operation(), experiment.size(), statistics.mean(),
                statistics.standardDeviation(), statistics.minimum(), statistics.maximum(), variant.note()));
    }

    private static Statistics summarize(List<Sample> samples, String experiment, String variant, int size) {
        double[] milliseconds = samples.stream()
                .filter(sample -> sample.phase().equals(MEASURED_PHASE))
                .filter(sample -> sample.experiment().equals(experiment) && sample.variant().equals(variant))
                .filter(sample -> sample.size() == size)
                .mapToDouble(sample -> sample.elapsedNanos() / NANOS_PER_MILLISECOND)
                .toArray();
        if (milliseconds.length != MEASURED_PAIRS) {
            throw new IllegalStateException("Incomplete measured sample set");
        }
        double mean = Arrays.stream(milliseconds).average().orElseThrow();
        double squaredDeviations = 0.0;
        for (double value : milliseconds) {
            double difference = value - mean;
            squaredDeviations += difference * difference;
        }
        double deviation = Math.sqrt(squaredDeviations / (milliseconds.length - 1));
        double minimum = Arrays.stream(milliseconds).min().orElseThrow();
        double maximum = Arrays.stream(milliseconds).max().orElseThrow();
        return new Statistics(mean, deviation, minimum, maximum);
    }

    private static void appendConclusions(StringBuilder report, List<Sample> samples) {
        Statistics custom = summarize(samples, "library", "custom", LARGE_SIZE);
        Statistics standard = summarize(samples, "library", "standard", LARGE_SIZE);
        Statistics minimum = summarize(samples, "order", "min", LARGE_SIZE);
        Statistics maximum = summarize(samples, "order", "max", LARGE_SIZE);
        Statistics sequential = summarize(samples, "build", "sequential", LARGE_SIZE);
        Statistics bottomUp = summarize(samples, "build", "bottom_up", LARGE_SIZE);
        report.append("## Выводы\n\n");
        report.append(String.format(Locale.ROOT,
                "При N = 100 000 среднее время add + poll составило %.4f мс для собственной очереди и "
                        + "%.4f мс для стандартной, отношение custom/standard = %.3f. ",
                custom.mean(), standard.mean(), custom.mean() / standard.mean()));
        report.append(String.format(Locale.ROOT,
                "На одном наборе min-heap и max-heap заняли %.4f и %.4f мс соответственно; на различие "
                        + "могут влиять компаратор, направление просеивания и шум выполнения JVM. ",
                minimum.mean(), maximum.mean()));
        report.append(String.format(Locale.ROOT,
                "Построение последовательными add заняло %.4f мс, снизу вверх — %.4f мс; "
                        + "это соотношение на случайном наборе не гарантирует такого же результата "
                        + "на любых данных и в любой среде. ", sequential.mean(), bottomUp.mean()));
        report.append("Память отдельно не измерялась: при заполнении N элементами собственная очередь ")
                .append("требует O(N) памяти и сохраняет ёмкость после удалений, а разницу фактического ")
                .append("расхода памяти по одним временным замерам определить нельзя.\n");
    }

    private record Dataset(Integer[] values, Integer[] ascending, Integer[] descending) {
    }

    private record Variant(String id, String label, String operation, String note, LongSupplier action) {
    }

    private record Experiment(String id, int size, Variant left, Variant right) {
    }

    private record Sample(String experiment, String variant, int size, String phase, int pair, int position,
                          long elapsedNanos) {
    }

    private record Statistics(double mean, double standardDeviation, double minimum, double maximum) {
    }
}