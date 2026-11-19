package ru.bmstu.lab02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.LongSupplier;

/** Воспроизводимые учебные замеры ЛР2; стандартная коллекция используется только как эталон. */
public final class HeapBenchmark {
    private static final int WARMUP_ROUNDS = 8;
    private static final int MEASURED_ROUNDS = 12;
    private static final long SEED = 20261119L;
    private static volatile long sink;

    private HeapBenchmark() {
    }

    /** Печатает таблицы Markdown в stdout; один процесс, прогрев и чередование порядка вариантов. */
    public static void main(String[] args) {
        System.out.println("# Бенчмарки ЛР2\n");
        System.out.printf("Среда: Java %s, %s %s (%s), доступных CPU: %d.%n%n",
                System.getProperty("java.version"), System.getProperty("os.name"),
                System.getProperty("os.version"), System.getProperty("os.arch"),
                Runtime.getRuntime().availableProcessors());
        System.out.printf("Seed: %d. Прогрев: %d раундов; измерения: %d раундов. "
                + "Таймер: System.nanoTime; в таблицах арифметическое среднее.\n\n",
                SEED, WARMUP_ROUNDS, MEASURED_ROUNDS);
        System.out.println("Сначала добавляются все N элементов, затем извлекаются все N элементов. "
                + "min использует естественный порядок, max — Comparator.reverseOrder(). "
                + "В каждом раунде варианты получают один и тот же массив; "
                + "порядок запуска циклически меняется. Генерация входа и проверка суммы вне замера; "
                + "создание очереди, расширения и извлечения внутри замера.\n");
        System.out.println("## 1A, 1B: заполнение и опустошение\n");
        printHeader();
        for (int size : new int[]{10_000, 50_000, 100_000}) {
            measureCycles(size);
        }
        System.out.println("\n## 1D: построение без извлечения, N = 100 000\n");
        System.out.println("Входы: случайный и убывающий; второй показывает худший случай "
                + "последовательных add. Копирование входа в собственный массив входит "
                + "в стоимость heapify. Представление Arrays.asList для JDK подготовлено вне таймера.\n");
        printHeader();
        measureBuild(false);
        measureBuild(true);
        System.out.println("\n## Ограничения и выводы\n");
        System.out.println("Это учебный микробенчмарк в одном JVM-процессе, а не JMH: "
                + "на числа влияют JIT, GC и нагрузка общей машины. "
                + "min- и max-кучи имеют одинаковую асимптотику O(n log n) для полного цикла; "
                + "различия связаны с компаратором, ветвлениями и шумом измерения. "
                + "На случайном входе последовательные вставки могут быть близки к линейному "
                + "построению; на убывающем их худший случай O(n log n), а heapify остаётся O(n). "
                + "Обе реализации хранят O(n) ссылок: собственный массив удваивается и сохраняет "
                + "ёмкость после удаления, поэтому имеет запас памяти; фактический heap JVM "
                + "этот эксперимент не измеряет.");
    }

    private static void measureCycles(int size) {
        Integer[] input = randomInput(size, SEED + size);
        long expectedSum = sum(input);
        String[] names = {"PriorityQueueCustom min", "java.util.PriorityQueue min",
                "PriorityQueueCustom max", "java.util.PriorityQueue max"};
        LongSupplier[] runs = {() -> customCycle(input, null), () -> standardCycle(input, null),
                () -> customCycle(input, Comparator.reverseOrder()),
                () -> standardCycle(input, Comparator.reverseOrder())};
        double[] averages = measure(runs, expectedSum);
        for (int i = 0; i < runs.length; i++) {
            printRow(names[i], "add + poll", size, averages[i], "случайные целые");
        }
    }

    private static void measureBuild(boolean descending) {
        int size = 100_000;
        Integer[] input = randomInput(size, SEED + size);
        if (descending) {
            for (int i = 0; i < size; i++) {
                input[i] = size - i;
            }
        }
        var view = Arrays.asList(input);
        LongSupplier[] runs = {() -> buildCustomByAdds(input).size(),
                () -> retainCustom(new PriorityQueueCustom<>(input)),
                () -> buildStandardByAdds(input).size(),
                () -> retainStandard(new PriorityQueue<>(view))};
        double[] averages = measure(runs, size);
        String note = descending ? "убывающий вход" : "случайный вход";
        printRow("PriorityQueueCustom", "последовательные add", size, averages[0], note);
        printRow("PriorityQueueCustom", "heapify O(n)", size, averages[1], note);
        printRow("java.util.PriorityQueue", "последовательные add", size, averages[2], note);
        printRow("java.util.PriorityQueue", "конструктор из коллекции", size, averages[3], note);
    }

    private static double[] measure(LongSupplier[] runs, long expected) {
        long[] elapsed = new long[runs.length];
        for (int round = 0; round < WARMUP_ROUNDS + MEASURED_ROUNDS; round++) {
            for (int offset = 0; offset < runs.length; offset++) {
                int index = (round + offset) % runs.length;
                long start = System.nanoTime();
                long result = runs[index].getAsLong();
                long duration = System.nanoTime() - start;
                sink = result;
                if (result != expected) {
                    throw new AssertionError("Incorrect benchmark result: " + result);
                }
                if (round >= WARMUP_ROUNDS) {
                    elapsed[index] += duration;
                }
            }
        }
        double[] averages = new double[runs.length];
        for (int i = 0; i < runs.length; i++) {
            averages[i] = elapsed[i] / (double) MEASURED_ROUNDS / 1_000_000;
        }
        return averages;
    }

    private static long customCycle(Integer[] input, Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (Integer item : input) {
            queue.add(item);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }

    private static long standardCycle(Integer[] input, Comparator<Integer> comparator) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(comparator);
        for (Integer item : input) {
            queue.add(item);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.remove();
        }
        return sum;
    }

    private static PriorityQueueCustom<Integer> buildCustomByAdds(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer item : input) {
            queue.add(item);
        }
        builtQueue = queue;
        return queue;
    }

    private static PriorityQueue<Integer> buildStandardByAdds(Integer[] input) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer item : input) {
            queue.add(item);
        }
        builtQueue = queue;
        return queue;
    }

    // Ссылка на построенную кучу остаётся наблюдаемой: JIT не может убрать её построение.
    private static volatile Object builtQueue;

    private static long retainCustom(PriorityQueueCustom<Integer> queue) {
        builtQueue = queue;
        return queue.size();
    }

    private static long retainStandard(PriorityQueue<Integer> queue) {
        builtQueue = queue;
        return queue.size();
    }

    private static Integer[] randomInput(int size, long seed) {
        Random random = new Random(seed);
        Integer[] input = new Integer[size];
        for (int i = 0; i < size; i++) {
            input[i] = random.nextInt();
        }
        return input;
    }

    private static long sum(Integer[] input) {
        long result = 0;
        for (int value : input) {
            result += value;
        }
        return result;
    }

    private static void printHeader() {
        System.out.println("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
    }

    private static void printRow(String name, String operation, int size, double time, String note) {
        System.out.printf(Locale.ROOT, "| %s | %s | %d | %.3f | %s |%n", name, operation, size, time, note);
    }
}
