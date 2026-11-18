package ru.bmstu.lab2.task_1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Замеры производительности задач 1A, 1B и 1D.
 * Стандартная очередь используется только для сравнения, не в реализации структуры.
 */
public final class PriorityQueueBenchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int WARMUPS = 2;
    private static final int RUNS = 5;
    private static volatile long checksum;

    private PriorityQueueBenchmark() {
    }

    /**
     * Измеряет время операций и сохраняет таблицу с реальными результатами.
     *
     * @param args необязательный путь к выходному файлу benchmarks.md
     * @throws IOException при ошибке записи отчёта
     */
    public static void main(String[] args) throws IOException {
        Path output = Path.of(args.length == 0 ? "benchmarks.md" : args[0]);
        StringBuilder report = new StringBuilder();
        report.append("# Бенчмарки ЛР №2 — 32-Shchukin\n\n");
        report.append("Замеры получены запуском `PriorityQueueBenchmark`. ");
        report.append("Для каждого набора: ").append(WARMUPS).append(" прогревочных и ")
                .append(RUNS).append(" измеряемых запусков. ");
        report.append("Для 1A и 1B использованы одинаковые случайные данные `Random(42)`, ");
        report.append("для 1D — 100 000 чисел по убыванию.\n\n");
        report.append("**Среда:** Java ").append(System.getProperty("java.version"))
                .append(", ").append(System.getProperty("os.name"))
                .append(", ").append(System.getProperty("os.arch")).append(".\n\n");
        report.append("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |\n");
        report.append("|---|---|---:|---:|---|\n");

        for (int size : SIZES) {
            Integer[] data = randomArray(size);
            double custom = average(() -> addPollCustom(data, false));
            double standard = average(() -> addPollStandard(data, false));
            addRow(report, "PriorityQueueCustom", "add + poll (min-heap)", size, custom, "Собственная куча");
            addRow(report, "java.util.PriorityQueue", "add + poll (min-heap)", size, standard, "Библиотека Java");
        }

        Integer[] large = randomArray(100_000);
        double minTime = average(() -> addPollCustom(large, false));
        double maxTime = average(() -> addPollCustom(large, true));
        addRow(report, "PriorityQueueCustom", "add + poll (min-heap)", large.length, minTime, "Задача 1B");
        addRow(report, "PriorityQueueCustom", "add + poll (max-heap)",
                large.length, maxTime, "Тот же набор, обратный порядок");

        Integer[] descending = descendingArray(100_000);
        double sequential = average(() -> buildByAdds(descending));
        double bottomUp = average(() -> buildBottomUp(descending));
        addRow(report, "PriorityQueueCustom", "построение через add",
                descending.length, sequential, "Убывающий массив");
        addRow(report, "PriorityQueueCustom", "построение из массива",
                descending.length, bottomUp, "Убывающий массив, O(n)");

        report.append("\n## Вывод\n\n");
        report.append("Собственная очередь и `java.util.PriorityQueue` используют двоичную кучу, ");
        report.append("поэтому у них одинаковые основные асимптотические оценки; ");
        report.append("время зависит от реализации и работы JVM. ");
        report.append("Min-heap и max-heap выполняют одинаковые по сложности действия, ");
        report.append("а разница в замерах может объясняться сравнениями и особенностями входных данных. ");
        report.append("Построение снизу вверх имеет оценку O(n), а последовательное добавление — ");
        report.append("O(n log n) в худшем случае; на конкретном наборе времена могут быть близкими. ");
        report.append("Обе очереди используют O(n) ссылок в массивах; ");
        report.append("отдельное измерение фактического потребления памяти не проводилось.\n");

        Files.writeString(output, report.toString(), StandardCharsets.UTF_8);
        System.out.println("Результаты записаны: " + output.toAbsolutePath());
        System.out.println("Контрольная сумма: " + checksum);
    }

    private static Integer[] randomArray(int size) {
        Random random = new Random(42);
        Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(2_000_000) - 1_000_000;
        }
        return data;
    }

    private static Integer[] descendingArray(int size) {
        Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            data[i] = size - i;
        }
        return data;
    }

    private static double average(Runnable action) {
        for (int i = 0; i < WARMUPS; i++) {
            action.run();
        }
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            action.run();
            total += System.nanoTime() - start;
        }
        return total / (RUNS * 1_000_000.0);
    }

    private static void addPollCustom(Integer[] data, boolean max) {
        PriorityQueueCustom<Integer> queue = max
                ? new PriorityQueueCustom<>(Comparator.reverseOrder())
                : new PriorityQueueCustom<>();
        for (Integer value : data) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        checksum = sum;
    }

    private static void addPollStandard(Integer[] data, boolean max) {
        PriorityQueue<Integer> queue = max
                ? new PriorityQueue<>(Comparator.reverseOrder())
                : new PriorityQueue<>();
        for (Integer value : data) {
            queue.add(value);
        }
        long sum = 0;
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        checksum = sum;
    }

    private static void buildByAdds(Integer[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : data) {
            queue.add(value);
        }
        checksum = queue.size() + queue.peek();
    }

    private static void buildBottomUp(Integer[] data) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(data);
        checksum = queue.size() + queue.peek();
    }

    private static void addRow(StringBuilder report, String structure, String operation,
                               int count, double milliseconds, String note) {
        report.append(String.format(Locale.US, "| `%s` | %s | %d | %.3f | %s |%n",
                structure, operation, count, milliseconds, note));
    }
}
