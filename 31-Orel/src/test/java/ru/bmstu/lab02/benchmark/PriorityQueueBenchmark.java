package ru.bmstu.lab02.benchmark;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

import ru.bmstu.lab02.PriorityQueueCustom;

/**
 * Замеры производительности {@link PriorityQueueCustom} и сравнение с {@link java.util.PriorityQueue}.
 *
 * <p>Это не JUnit-тест, а программа с {@code main}: при {@code mvn test} она не запускается, чтобы не
 * замедлять сборку. Запуск в IDEA — зелёная кнопка ▶ рядом с {@code main}. Результат печатается
 * в виде markdown-таблиц, которые переносятся в {@code benchmarks.md}.
 *
 * <p>Методика: для каждой операции сначала {@value #WARMUP_RUNS} прогревочных запусков (JIT успевает
 * скомпилировать горячий код), затем {@value #MEASURED_RUNS} замеров; в таблицу идёт среднее время.
 * Подготовка данных (например, заполнение очереди перед замером poll) в замер не входит.
 * Стандартные коллекции здесь используются только для сравнения, что разрешено условием ЛР.
 */
public final class PriorityQueueBenchmark {

    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int LARGE_SIZE = 100_000;
    private static final int REMOVALS = 1_000;
    private static final int WARMUP_RUNS = 5;
    private static final int MEASURED_RUNS = 10;
    private static final long SEED = 42L;
    private static final double NANOS_IN_MILLI = 1_000_000.0;

    private static final String CUSTOM = "PriorityQueueCustom";
    private static final String STANDARD = "java.util.PriorityQueue";

    /** Сюда складываются результаты операций, чтобы JIT не выбросил «бесполезные» вычисления. */
    private static long blackhole;

    private PriorityQueueBenchmark() {
    }

    /**
     * Запускает все замеры и печатает таблицы.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        System.out.printf(Locale.ROOT, "Java %s, %s %s, процессоров: %d%n%n", System.getProperty("java.version"),
                System.getProperty("os.name"), System.getProperty("os.arch"),
                Runtime.getRuntime().availableProcessors());
        printComparisonTable();
        printMinMaxTable();
        printBuildTable();
        System.out.println("checksum: " + blackhole);
    }

    /** Общая таблица: своя очередь против стандартной на 10 000 / 50 000 / 100 000 элементов. */
    private static void printComparisonTable() {
        printHeader("Сравнение с java.util.PriorityQueue");
        for (int size : SIZES) {
            Integer[] data = randomData(size);
            Integer[] toRemove = Arrays.copyOf(data, REMOVALS);
            printRow(CUSTOM, "add × N", size, measure(() -> null, ignored -> fillCustom(data).size()),
                    "N вставок в пустую очередь");
            printRow(STANDARD, "add × N", size, measure(() -> null, ignored -> fillStandard(data).size()), "");
            printRow(CUSTOM, "poll × N", size, measure(() -> fillCustom(data), PriorityQueueBenchmark::pollAll),
                    "извлечь все элементы");
            printRow(STANDARD, "poll × N", size, measure(() -> fillStandard(data), PriorityQueueBenchmark::pollAll),
                    "");
            printRow(CUSTOM, "построение из массива", size,
                    measure(() -> null, ignored -> new PriorityQueueCustom<>(data).size()), "O(n), задача 1D");
            printRow(STANDARD, "построение из коллекции", size,
                    measure(() -> Arrays.asList(data), list -> new PriorityQueue<>(list).size()),
                    "тоже heapify O(n)");
            printRow(CUSTOM, "remove × " + REMOVALS, size,
                    measure(() -> fillCustom(data), queue -> removeAll(queue, toRemove)), "O(n) на удаление");
            printRow(STANDARD, "remove × " + REMOVALS, size,
                    measure(() -> fillStandard(data), queue -> removeAll(queue, toRemove)), "");
        }
        System.out.println();
    }

    /** Задача 1B: min-heap и max-heap на одном и том же наборе из 100 000 случайных чисел. */
    private static void printMinMaxTable() {
        printHeader("Задача 1B: min-heap и max-heap, N = " + LARGE_SIZE);
        Integer[] data = randomData(LARGE_SIZE);
        Comparator<Integer> min = Comparator.naturalOrder();
        Comparator<Integer> max = Comparator.reverseOrder();
        printRow("min-heap", "add × N + poll × N", LARGE_SIZE, measure(() -> null, ignored -> addAndPoll(data, min)),
                "сравнений: " + countComparisons(data, min));
        printRow("max-heap", "add × N + poll × N", LARGE_SIZE, measure(() -> null, ignored -> addAndPoll(data, max)),
                "сравнений: " + countComparisons(data, max));
        System.out.println();
    }

    /**
     * Задача 1D: построение кучи за O(n) против N последовательных add. Замеряется на случайных данных
     * и на массиве, отсортированном по убыванию: для min-кучи это худший случай, в котором каждый add
     * поднимает новый элемент до самого корня.
     */
    private static void printBuildTable() {
        printHeader("Задача 1D: построение кучи");
        for (int size : SIZES) {
            printBuildRows(randomData(size), "случайные данные");
            printBuildRows(descendingData(size), "убывающий массив (худший случай)");
        }
        System.out.println();
    }

    private static void printBuildRows(Integer[] data, String dataKind) {
        printRow("последовательные add", "построение", data.length,
                measure(() -> null, ignored -> fillCustom(data).size()),
                dataKind + ", сравнений: " + countAddComparisons(data));
        printRow("конструктор из массива", "построение", data.length,
                measure(() -> null, ignored -> new PriorityQueueCustom<>(data).size()),
                dataKind + ", сравнений: " + countHeapifyComparisons(data));
    }

    /**
     * Среднее время операции в миллисекундах. {@code setup} готовит данные вне замера,
     * {@code operation} — замеряемое действие; его результат уходит в {@link #blackhole}.
     */
    private static <S> double measure(Supplier<S> setup, ToLongFunction<S> operation) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            blackhole += operation.applyAsLong(setup.get());
        }
        long total = 0;
        for (int i = 0; i < MEASURED_RUNS; i++) {
            S state = setup.get();
            long start = System.nanoTime();
            blackhole += operation.applyAsLong(state);
            total += System.nanoTime() - start;
        }
        return total / NANOS_IN_MILLI / MEASURED_RUNS;
    }

    private static PriorityQueueCustom<Integer> fillCustom(Integer[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : data) {
            queue.add(value);
        }
        return queue;
    }

    private static PriorityQueue<Integer> fillStandard(Integer[] data) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : data) {
            queue.add(value);
        }
        return queue;
    }

    private static long pollAll(PriorityQueueCustom<Integer> queue) {
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long pollAll(PriorityQueue<Integer> queue) {
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long removeAll(PriorityQueueCustom<Integer> queue, Integer[] values) {
        long removed = 0;
        for (Integer value : values) {
            removed += queue.remove(value) ? 1 : 0;
        }
        return removed;
    }

    private static long removeAll(PriorityQueue<Integer> queue, Integer[] values) {
        long removed = 0;
        for (Integer value : values) {
            removed += queue.remove(value) ? 1 : 0;
        }
        return removed;
    }

    private static long addAndPoll(Integer[] data, Comparator<Integer> order) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(order);
        for (Integer value : data) {
            queue.add(value);
        }
        return pollAll(queue);
    }

    private static long countComparisons(Integer[] data, Comparator<Integer> order) {
        long[] counter = new long[1];
        addAndPoll(data, counting(order, counter));
        return counter[0];
    }

    private static long countAddComparisons(Integer[] data) {
        long[] counter = new long[1];
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(counting(Comparator.naturalOrder(), counter));
        for (Integer value : data) {
            queue.add(value);
        }
        return counter[0];
    }

    private static long countHeapifyComparisons(Integer[] data) {
        long[] counter = new long[1];
        new PriorityQueueCustom<>(data, counting(Comparator.naturalOrder(), counter));
        return counter[0];
    }

    /** Обёртка над компаратором, считающая число сравнений: эта метрика не зависит от «шума» машины. */
    private static Comparator<Integer> counting(Comparator<Integer> order, long[] counter) {
        return (first, second) -> {
            counter[0]++;
            return order.compare(first, second);
        };
    }

    private static Integer[] randomData(int size) {
        Random random = new Random(SEED);
        Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    private static Integer[] descendingData(int size) {
        Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            data[i] = size - i;
        }
        return data;
    }

    private static void printHeader(String title) {
        System.out.println("### " + title);
        System.out.println();
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|-----------|----------|------------:|-------------------:|------------|");
    }

    private static void printRow(String structure, String operation, int size, double millis, String note) {
        System.out.printf(Locale.ROOT, "| %s | %s | %d | %.3f | %s |%n", structure, operation, size, millis, note);
    }
}
