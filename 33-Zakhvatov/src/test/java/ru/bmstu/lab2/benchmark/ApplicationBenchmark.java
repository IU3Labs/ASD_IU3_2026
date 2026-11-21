package ru.bmstu.lab2.benchmark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import ru.bmstu.lab2.task_3a.TopK;
import ru.bmstu.lab2.task_3b.WordCounter;
import ru.bmstu.lab2.task_3c.DistinctElements;

/** Замеры для Top-K, подсчёта слов и удаления повторов. */
final class ApplicationBenchmark {
    private Object memoryResult;

    String run() {
        StringBuilder table = new StringBuilder();
        appendTopK(table);
        appendWords(table);
        appendDistinct(table);
        return table.toString();
    }

    private void appendTopK(StringBuilder table) {
        List<Integer> input = randomList(1_000_000, Integer.MAX_VALUE);
        int k = 100;
        assertEquals(sortedTopK(input, k), TopK.topK(input, k));
        table.append("| Метод (3A) | N элементов | k | Среднее время (мс) |\n");
        table.append("|---|---:|---:|---:|\n");
        table.append("| Своя очередь | 1000000 | 100 | ");
        table.append(Lab2BenchmarkTest.number(topKTime(input, k, false))).append(" |\n");
        table.append("| Полная сортировка | 1000000 | 100 | ");
        table.append(Lab2BenchmarkTest.number(topKTime(input, k, true))).append(" |\n\n");
    }

    private double topKTime(List<Integer> input, int k, boolean sorting) {
        List<Integer> expected = sortedTopK(input, k);
        long total = 0;
        for (int run = -1; run < Lab2BenchmarkTest.REPEATS; run++) {
            long start = System.nanoTime();
            List<Integer> result;
            if (sorting) {
                result = sortedTopK(input, k);
            } else {
                result = TopK.topK(input, k);
            }
            long elapsed = System.nanoTime() - start;
            assertEquals(expected, result);
            if (run >= 0) {
                total += elapsed;
            }
        }
        return total / Lab2BenchmarkTest.REPEATS / 1_000_000.0;
    }

    private List<Integer> sortedTopK(List<Integer> input, int k) {
        List<Integer> sorted = new ArrayList<>(input);
        Collections.sort(sorted, Collections.reverseOrder());
        return sorted.subList(0, k);
    }

    private void appendWords(StringBuilder table) {
        String text = wordCorpus();
        Map<String, Integer> counts = WordCounter.countWords(text);
        assertEquals(standardCount(text), counts);
        int bytes = text.getBytes(StandardCharsets.UTF_8).length;
        table.append("| Таблица (3B) | Объём текста (байт) | Среднее время (мс) |\n");
        table.append("|---|---:|---:|\n");
        table.append("| HashMapCustom | ").append(bytes).append(" | ");
        table.append(Lab2BenchmarkTest.number(wordsTime(text, false))).append(" |\n");
        table.append("| java.util.HashMap | ").append(bytes).append(" | ");
        table.append(Lab2BenchmarkTest.number(wordsTime(text, true))).append(" |\n\n");
        appendFrequentWords(table, counts);
    }

    private double wordsTime(String text, boolean standard) {
        Map<String, Integer> expected = standardCount(text);
        long total = 0;
        for (int run = -1; run < Lab2BenchmarkTest.REPEATS; run++) {
            long start = System.nanoTime();
            Map<String, Integer> result;
            if (standard) {
                result = standardCount(text);
            } else {
                result = WordCounter.countWords(text);
            }
            long elapsed = System.nanoTime() - start;
            assertEquals(expected, result);
            if (run >= 0) {
                total += elapsed;
            }
        }
        return total / Lab2BenchmarkTest.REPEATS / 1_000_000.0;
    }

    private void appendFrequentWords(StringBuilder table, Map<String, Integer> counts) {
        List<String> words = new ArrayList<>(counts.keySet());
        table.append("| Слово из топ-10 (3B) | Частота |\n|---|---:|\n");
        for (int place = 0; place < 10 && !words.isEmpty(); place++) {
            String best = words.get(0);
            for (String word : words) {
                if (counts.get(word) > counts.get(best)
                        || counts.get(word).equals(counts.get(best)) && word.compareTo(best) < 0) {
                    best = word;
                }
            }
            table.append("| ").append(best).append(" | ").append(counts.get(best)).append(" |\n");
            System.out.println(best + ": " + counts.get(best));
            words.remove(best);
        }
        table.append('\n');
    }

    private void appendDistinct(StringBuilder table) {
        table.append("| Метод (3C) | N элементов | Среднее время (мс) | Память результата (КиБ) | Примечание |\n");
        table.append("|---|---:|---:|---:|---|\n");
        for (int n : new int[]{10_000, 50_000, 100_000}) {
            List<Integer> input = randomList(n, n / 4);
            assertEquals(new ArrayList<>(new LinkedHashSet<>(input)), DistinctElements.distinct(input));
            table.append("| Своя таблица + List | ").append(n).append(" | ");
            table.append(Lab2BenchmarkTest.number(distinctTime(input, false))).append(" | ");
            table.append(Lab2BenchmarkTest.number(distinctMemory(input, false))).append(" | приблизительно |\n");
            table.append("| new LinkedHashSet<>(items) | ").append(n).append(" | ");
            table.append(Lab2BenchmarkTest.number(distinctTime(input, true))).append(" | ");
            table.append(Lab2BenchmarkTest.number(distinctMemory(input, true))).append(" | приблизительно |\n");
        }
    }

    private double distinctTime(List<Integer> input, boolean standard) {
        List<Integer> expected = new ArrayList<>(new LinkedHashSet<>(input));
        long total = 0;
        for (int run = -1; run < Lab2BenchmarkTest.REPEATS; run++) {
            long start = System.nanoTime();
            if (standard) {
                LinkedHashSet<Integer> result = new LinkedHashSet<>(input);
                long elapsed = System.nanoTime() - start;
                assertEquals(expected, new ArrayList<>(result));
                if (run >= 0) {
                    total += elapsed;
                }
            } else {
                List<Integer> result = DistinctElements.distinct(input);
                long elapsed = System.nanoTime() - start;
                assertEquals(expected, result);
                if (run >= 0) {
                    total += elapsed;
                }
            }
        }
        return total / Lab2BenchmarkTest.REPEATS / 1_000_000.0;
    }

    private double distinctMemory(List<Integer> input, boolean standard) {
        memoryResult = null;
        System.gc();
        long before = usedMemory();
        if (standard) {
            memoryResult = new LinkedHashSet<>(input);
        } else {
            memoryResult = DistinctElements.distinct(input);
        }
        System.gc();
        long bytes = usedMemory() - before;
        assertNotNull(memoryResult);
        memoryResult = null;
        if (bytes < 0) {
            bytes = 0;
        }
        return bytes / 1024.0;
    }

    private long usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private List<Integer> randomList(int n, int bound) {
        List<Integer> input = new ArrayList<>();
        Random random = new Random(42);
        for (int i = 0; i < n; i++) {
            input.add(random.nextInt(bound));
        }
        return input;
    }

    private String wordCorpus() {
        String line = "Алгоритм массив куча ключ таблица поиск список время память данные тест java "
                + "структура очередь элемент. Алгоритм массив куча! ";
        int lineBytes = line.getBytes(StandardCharsets.UTF_8).length;
        StringBuilder text = new StringBuilder();
        int bytes = 0;
        while (bytes < 1_048_576) {
            text.append(line);
            bytes += lineBytes;
        }
        return text.toString();
    }

    private Map<String, Integer> standardCount(String text) {
        Map<String, Integer> counts = new HashMap<>();
        StringBuilder word = new StringBuilder();
        text = text.toLowerCase(Locale.ROOT);
        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length() && Character.isLetterOrDigit(text.charAt(i))) {
                word.append(text.charAt(i));
            } else if (word.length() > 0) {
                String key = word.toString();
                Integer count = counts.get(key);
                if (count == null) {
                    counts.put(key, 1);
                } else {
                    counts.put(key, count + 1);
                }
                word.setLength(0);
            }
        }
        return counts;
    }
}
