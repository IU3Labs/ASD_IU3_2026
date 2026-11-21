package ru.bmstu.lab2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

/** Эксперименты 1A, 1B, 1D, 2A, 2B на одинаковых заранее подготовленных данных. */
final class StructureBenchmark {
    private final BenchmarkSupport timer;

    StructureBenchmark(BenchmarkSupport timer) {
        this.timer = timer;
    }

    String run() {
        StringBuilder report = new StringBuilder("## Структуры: 10 000, 50 000, 100 000 элементов\n\n");
        report.append("| Структура | Операция | N | Среднее, мс | σ, мс | Выделено, МиБ |\n");
        report.append("|---|---|---:|---:|---:|---:|\n");
        for (int size : new int[]{10_000, 50_000, 100_000}) {
            Integer[] input = randomInput(size);
            addRow(report, "PriorityQueueCustom", "add + poll", size,
                    timer.measure(() -> customQueue(input, null)));
            addRow(report, "java.util.PriorityQueue", "add + poll", size,
                    timer.measure(() -> standardQueue(input)));
            for (HashMapCustom.Probing probing : HashMapCustom.Probing.values()) {
                addRow(report, "HashMapCustom " + probing, "put + get + remove", size,
                        timer.measure(() -> customMap(input, probing)));
            }
            addRow(report, "java.util.HashMap", "put + get + remove", size,
                    timer.measure(() -> standardMap(input)));
        }
        appendHeapExperiments(report);
        appendProbeExperiment(report);
        return report.toString();
    }

    private void appendHeapExperiments(StringBuilder report) {
        Integer[] input = randomInput(100_000);
        report.append("\n## 1B: min-heap и max-heap, 100 000 одинаковых случайных чисел\n\n");
        report.append("| Режим | Среднее, мс | σ, мс | Выделено, МиБ |\n|---|---:|---:|---:|\n");
        report.append("| min-heap | ").append(timer.measure(() -> customQueue(input, null)).cells()).append(" |\n");
        report.append("| max-heap | ")
                .append(timer.measure(() -> customQueue(input, Comparator.reverseOrder())).cells()).append(" |\n");
        report.append("\nОба режима выполняют add + poll и имеют O(n log n). Разница определяется порядком\n");
        report.append("сравнений, ветвлениями, работой компаратора и шумом измерения, а не сменой асимптотики.\n");
        report.append("\n## 1D: построение кучи из 100 000 элементов\n\n");
        report.append("| Способ | Среднее, мс | σ, мс | Выделено, МиБ |\n|---|---:|---:|---:|\n");
        report.append("| Последовательные add | ").append(timer.measure(() -> buildByAdds(input)).cells());
        report.append(" |\n| Bottom-up heapify | ")
                .append(timer.measure(() -> new PriorityQueueCustom<>(input).peek()).cells()).append(" |\n");
        report.append("\nИзвлечение исключено из этого замера. Heapify имеет O(n), последовательные add —\n");
        report.append("O(n log n) в худшем случае; на случайных данных add часто требует мало подъёмов.\n");
    }

    private void appendProbeExperiment(StringBuilder report) {
        Integer[] input = randomInput(50_000);
        report.append("\n## 2B: среднее число проб на 50 000 get\n\n");
        report.append("25 000 успешных и 25 000 неуспешных запросов; таблицы содержат одинаковые ключи.\n\n");
        report.append("| Вариант | Пар | Ёмкость | Загрузка | Проб/get |\n|---|---:|---:|---:|---:|\n");
        for (HashMapCustom.Probing probing : HashMapCustom.Probing.values()) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing);
            for (int key : input) {
                map.put(key, key);
            }
            map.resetProbeStatistics();
            for (int index = 0; index < 25_000; index++) {
                map.get(input[index]);
                int absent = input[index] + 1;
                while (map.containsKey(absent)) {
                    absent++;
                }
                map.get(absent);
            }
            report.append(String.format(java.util.Locale.ROOT, "| %s | %d | %d | %.3f | %.3f |%n",
                    probing, map.size(), map.capacity(), map.size() / (double) map.capacity(),
                    map.averageGetProbes()));
        }
        report.append("\nКвадратичное пробирование разносит первичные кластеры. При одинаковом начальном\n");
        report.append("хеше последовательности всё ещё совпадают, поэтому вторичная кластеризация остаётся.\n");
    }

    private long customQueue(Integer[] input, Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(comparator);
        for (Integer value : input) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum = 31 * checksum + queue.poll();
        }
        return checksum;
    }

    private long standardQueue(Integer[] input) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Integer value : input) {
            queue.add(value);
        }
        long checksum = 0;
        while (!queue.isEmpty()) {
            checksum = 31 * checksum + queue.poll();
        }
        return checksum;
    }

    private long buildByAdds(Integer[] input) {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (Integer value : input) {
            queue.add(value);
        }
        return queue.peek();
    }

    private long customMap(Integer[] input, HashMapCustom.Probing probing) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing);
        for (Integer key : input) {
            map.put(key, key);
        }
        long checksum = 0;
        for (Integer key : input) {
            checksum += map.get(key);
        }
        for (Integer key : input) {
            map.remove(key);
        }
        return checksum + map.size();
    }

    private long standardMap(Integer[] input) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer key : input) {
            map.put(key, key);
        }
        long checksum = 0;
        for (Integer key : input) {
            checksum += map.get(key);
        }
        for (Integer key : input) {
            map.remove(key);
        }
        return checksum + map.size();
    }

    private Integer[] randomInput(int size) {
        return new Random(20261121L + size).ints(size).boxed().toArray(Integer[]::new);
    }

    private void addRow(StringBuilder report, String structure, String operation, int size,
            BenchmarkSupport.Measurement measurement) {
        report.append("| ").append(structure).append(" | ").append(operation).append(" | ").append(size);
        report.append(" | ").append(measurement.cells()).append(" |\n");
    }
}
