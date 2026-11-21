package ru.bmstu.lab2.benchmark;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import ru.bmstu.lab2.priorityqueue.PriorityQueueCustom;

/**
 * Измеряет очередь для задач 1B, 1D и общего сравнения со стандартной библиотекой.
 * Стандартная PriorityQueue используется только здесь, в разрешённом бенчмарке.
 */
public final class PriorityQueueBenchmark {
    private static final int[] SIZES = {10000, 50000, 100000};
    private static final int WARMUP_ROUNDS = 3;
    private static final int MEASURED_ROUNDS = 7;
    private static final int COMPARISON_SIZE = 100000;
    private static final long RANDOM_SEED = 20261121L;
    private static final double NANOS_PER_MILLISECOND = 1000000.0;
    private static volatile long blackhole;

    private PriorityQueueBenchmark() {
    }

    /**
     * Запускает эксперименты и сохраняет Markdown с реальными результатами.
     *
     * @param args ровно один аргумент: путь к выходному benchmarks.md
     * @throws IOException если невозможно записать файл
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Укажите путь к benchmarks.md одним аргументом");
        }
        StringBuilder report = new StringBuilder();
        appendEnvironment(report);
        appendQueueComparison(report);
        Integer[] input = createInput(COMPARISON_SIZE);
        appendMinMaxComparison(report, input);
        appendBuildComparison(report, input);
        appendConclusions(report);
        Path output = Path.of(args[0]).toAbsolutePath();
        Files.writeString(output, report, StandardCharsets.UTF_8);
        System.out.println(report);
        System.out.println("Результаты сохранены: " + output);
    }

    private static void appendEnvironment(StringBuilder report) {
        report.append("# Бенчмарки ЛР2: задачи 1A–1D\n\n");
        report.append("- Дата запуска: ").append(java.time.LocalDate.now()).append('\n');
        report.append("- Java: ").append(System.getProperty("java.version")).append('\n');
        report.append("- JVM: ").append(System.getProperty("java.vm.name")).append('\n');
        report.append("- ОС: ").append(System.getProperty("os.name"));
        report.append(' ').append(System.getProperty("os.arch")).append('\n');
        report.append("- Доступно процессоров JVM: ");
        report.append(Runtime.getRuntime().availableProcessors()).append('\n');
        report.append("- Прогревов: ").append(WARMUP_ROUNDS).append('\n');
        report.append("- Измеряемых повторений: ").append(MEASURED_ROUNDS).append('\n');
        report.append("- Seed: ").append(RANDOM_SEED).append("\n\n");
        report.append("Данные генерируются до измерения; все варианты получают одинаковые значения. ");
        report.append("Каждый запуск создаёт новую очередь. Время выделения и расширения её массива включено. ");
        report.append("Печать и генерация входа исключены; порядок вариантов чередуется между повторами. ");
        report.append("Приведено среднее арифметическое времени, измеренного System.nanoTime(). ");
        report.append("Это учебный микробенчмарк, а не измерение точного коэффициента ускорения.\n\n");
    }

    private static void appendQueueComparison(StringBuilder report) {
        report.append("## Общие измерения\n\n");
        report.append("Цикл: добавить все N значений, затем извлечь все N значений.\n\n");
        appendTableHeader(report);
        for (int size : SIZES) {
            Integer[] input = createInput(size);
            double[] times = measure(input, Mode.CUSTOM_MIN, Mode.JDK_MIN);
            appendRow(report, "PriorityQueueCustom", "add N + poll N", size, times[0], "min-heap");
            appendRow(report, "java.util.PriorityQueue", "add N + poll N", size, times[1], "min-heap");
        }
    }

    private static void appendMinMaxComparison(StringBuilder report, Integer[] input) {
        report.append("\n## Задача 1B: min-heap и max-heap\n\n");
        appendTableHeader(report);
        double[] times = measure(input, Mode.CUSTOM_MIN, Mode.CUSTOM_MAX);
        appendRow(report, "PriorityQueueCustom", "add N + poll N", input.length, times[0], "min-heap");
        appendRow(report, "PriorityQueueCustom", "add N + poll N", input.length, times[1], "max-heap");
        report.append("\nУ обоих режимов одинаковая асимптотика: O(N log N) для полного цикла. ");
        report.append("Конкретные времена зависят от компаратора, порядка просеиваний, JIT и фоновой нагрузки; ");
        report.append("один результат не доказывает постоянного преимущества одного режима.\n");
    }

    private static void appendBuildComparison(StringBuilder report, Integer[] input) {
        report.append("\n## Задача 1D: два способа построения\n\n");
        report.append("Измеряется только создание и наполнение кучи, без извлечения элементов. ");
        report.append("Конструктор копирует входной массив; копирование включено в замер.\n\n");
        appendTableHeader(report);
        double[] times = measure(input, Mode.BUILD_ADD, Mode.BUILD_HEAP);
        appendRow(report, "PriorityQueueCustom", "N вызовов add", input.length, times[0], "O(N log N), худший случай");
        appendRow(report, "PriorityQueueCustom", "конструктор из массива", input.length, times[1], "O(N)");
        report.append("\nПостроение снизу вверх гарантирует O(N); последовательные add дают O(N log N) ");
        report.append("в худшем случае. На случайном входе разница времени не обязана повторять ");
        report.append("отношение оценок худшего случая: многие вставки требуют короткого подъёма.\n");
    }

    private static void appendTableHeader(StringBuilder report) {
        report.append("| Структура | Операция | N элементов | Среднее время (мс) | Примечание |\n");
        report.append("|---|---|---:|---:|---|\n");
    }

    private static void appendRow(StringBuilder report, String structure, String operation,
                                  int size, double milliseconds, String note) {
        report.append(String.format(Locale.ROOT, "| %s | %s | %d | %.3f | %s |%n",
                structure, operation, size, milliseconds, note));
    }

    private static Integer[] createInput(int size) {
        Random random = new Random(RANDOM_SEED);
        Integer[] input = new Integer[size];
        for (int index = 0; index < size; index++) {
            input[index] = random.nextInt();
        }
        return input;
    }

    private static double[] measure(Integer[] input, Mode first, Mode second) {
        Mode[] modes = {first, second};
        double[] totals = new double[modes.length];
        for (int round = -WARMUP_ROUNDS; round < MEASURED_ROUNDS; round++) {
            for (int position = 0; position < modes.length; position++) {
                int index = Math.floorMod(round + position, modes.length);
                long start = System.nanoTime();
                long result = runCase(input, modes[index]);
                long elapsed = System.nanoTime() - start;
                // Использование результата мешает JIT считать всю работу ненужной.
                blackhole = result;
                if (round >= 0) {
                    totals[index] += elapsed / NANOS_PER_MILLISECOND;
                }
            }
        }
        for (int index = 0; index < totals.length; index++) {
            totals[index] /= MEASURED_ROUNDS;
        }
        return totals;
    }

    private static long runCase(Integer[] input, Mode mode) {
        return switch (mode) {
            case CUSTOM_MIN -> runCustom(input, Comparator.naturalOrder());
            case CUSTOM_MAX -> runCustom(input, Comparator.reverseOrder());
            case JDK_MIN -> runJdk(input);
            case BUILD_ADD -> buildByAdding(input);
            case BUILD_HEAP -> buildFromArray(input);
        };
    }

    private static long runCustom(Integer[] input, Comparator<Integer> comparator) {
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

    private static long runJdk(Integer[] input) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.naturalOrder());
        for (Integer value : input) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum += queue.poll();
        }
        return checksum;
    }

    private static long buildByAdding(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : input) {
            queue.add(value);
        }
        return (long) queue.size() + queue.peek();
    }

    private static long buildFromArray(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);
        return (long) queue.size() + queue.peek();
    }

    private static void appendConclusions(StringBuilder report) {
        report.append("\n## Выводы\n\n");
        report.append("Таблицы фиксируют фактически полученное соотношение времён на данной машине; ");
        report.append("разброс объясняется JIT-компиляцией, сборкой мусора и фоновой нагрузкой. ");
        report.append("Обе очереди имеют O(N) памяти под ссылки на элементы, но отличаются стратегией ");
        report.append("роста массива и константами реализации, что влияет на скорость и резерв ёмкости. ");
        report.append("Память здесь оценена аналитически: значения потребления в байтах не измерялись. ");
        report.append("Для устойчивых выводов о преимуществе конкретной реализации следует повторить запуск ");
        report.append("и сопоставить таблицы, сохраняя одинаковые условия.\n");
    }

    private enum Mode {
        CUSTOM_MIN, CUSTOM_MAX, JDK_MIN, BUILD_ADD, BUILD_HEAP
    }
}
