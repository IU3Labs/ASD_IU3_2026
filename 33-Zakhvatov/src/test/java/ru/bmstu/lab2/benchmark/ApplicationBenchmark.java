package ru.bmstu.lab2.benchmark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.bmstu.lab2.task_3a.TopK;
import ru.bmstu.lab2.task_3b.WordCounter;
import ru.bmstu.lab2.task_3c.DistinctElements;

/** Эксперименты прикладных задач 3A, 3B, 3C с библиотечными эталонами. */
final class ApplicationBenchmark {
    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{M}\\p{N}]+");
    private final BenchmarkSupport timer;

    ApplicationBenchmark(BenchmarkSupport timer) {
        this.timer = timer;
    }

    String run() {
        StringBuilder report = new StringBuilder("\n## 3A: Top-K из 1 000 000 элементов\n\n");
        List<Integer> input = new Random(3).ints(1_000_000).boxed().toList();
        int k = 100;
        List<Integer> sorted = sortedTopK(input, k);
        assertEquals(sorted, TopK.topK(input, k));
        report.append("k = 100; корректность проверена сравнением всех k значений с сортировкой.\n\n");
        report.append("| Метод | Среднее, мс | σ, мс | Выделено, МиБ |\n|---|---:|---:|---:|\n");
        report.append("| Собственная куча O(n log k) | ")
                .append(timer.measure(() -> TopK.topK(input, k).hashCode()).cells()).append(" |\n");
        report.append("| Полная сортировка O(n log n) | ")
                .append(timer.measure(() -> sortedTopK(input, k).hashCode()).cells()).append(" |\n");
        appendWords(report);
        appendDistinct(report);
        return report.toString();
    }

    private void appendWords(StringBuilder report) {
        String text = wordCorpus();
        Map<String, Integer> counts = WordCounter.countWords(text);
        assertEquals(standardCount(text), counts);
        report.append("\n## 3B: частота слов, текст около 1 МиБ\n\n");
        report.append("Детерминированный синтетический корпус, UTF-8: ")
                .append(text.getBytes(StandardCharsets.UTF_8).length).append(" байт.\n\n");
        report.append("| Таблица | Среднее, мс | σ, мс | Выделено, МиБ |\n|---|---:|---:|---:|\n");
        report.append("| HashMapCustom | ")
                .append(timer.measure(() -> WordCounter.countWords(text).size()).cells()).append(" |\n");
        report.append("| java.util.HashMap | ")
                .append(timer.measure(() -> standardCount(text).size()).cells()).append(" |\n\n");
        List<Map.Entry<String, Integer>> frequent = new ArrayList<>(counts.entrySet());
        frequent.sort(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed().thenComparing(Map.Entry::getKey));
        report.append("10 самых частых слов (при равенстве — по алфавиту):\n\n| Слово | Частота |\n|---|---:|\n");
        for (Map.Entry<String, Integer> entry : frequent.subList(0, 10)) {
            report.append("| ").append(entry.getKey()).append(" | ").append(entry.getValue()).append(" |\n");
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void appendDistinct(StringBuilder report) {
        report.append("\n## 3C: уникальные элементы, время и выделение памяти\n\n");
        report.append("Вход содержит повторы: случайные значения из диапазона [0, N/4).\n\n");
        report.append("| Метод | N | Среднее, мс | σ, мс | Выделено, МиБ |\n|---|---:|---:|---:|---:|\n");
        for (int size : new int[]{10_000, 50_000, 100_000}) {
            List<Integer> input = new Random(5).ints(size, 0, size / 4).boxed().toList();
            assertEquals(new ArrayList<>(new LinkedHashSet<>(input)), DistinctElements.distinct(input));
            report.append("| Собственная таблица + List | ").append(size).append(" | ")
                    .append(timer.measure(() -> DistinctElements.distinct(input).hashCode()).cells()).append(" |\n");
            report.append("| new LinkedHashSet<>(items) | ").append(size).append(" | ")
                    .append(timer.measure(() -> new LinkedHashSet<>(input).size()).cells()).append(" |\n");
            report.append("| LinkedHashSet + ArrayList (тот же List-контракт) | ").append(size).append(" | ")
                    .append(timer.measure(() -> new ArrayList<>(new LinkedHashSet<>(input)).hashCode()).cells())
                    .append(" |\n");
        }
    }

    private List<Integer> sortedTopK(List<Integer> input, int k) {
        List<Integer> sorted = new ArrayList<>(input);
        sorted.sort(Comparator.reverseOrder());
        return sorted.subList(0, k);
    }

    private String wordCorpus() {
        String[] words = {
            "алгоритм", "массив", "куча", "ключ", "таблица", "поиск", "список", "время",
            "память", "данные", "тест", "java", "структура", "очередь", "элемент"
        };
        String[] separators = {" ", ", ", ". ", "!\n", "; "};
        Random random = new Random(41);
        StringBuilder text = new StringBuilder();
        int bytes = 0;
        while (bytes < 1_048_576) {
            int index = (int) (Math.pow(random.nextDouble(), 2) * words.length);
            String word = random.nextBoolean() ? words[index].toUpperCase(Locale.ROOT) : words[index];
            String token = word + separators[random.nextInt(separators.length)];
            text.append(token);
            bytes += token.getBytes(StandardCharsets.UTF_8).length;
        }
        return text.toString();
    }

    private Map<String, Integer> standardCount(String text) {
        Map<String, Integer> result = new HashMap<>();
        Matcher matcher = WORD.matcher(text.toLowerCase(Locale.ROOT));
        while (matcher.find()) {
            result.merge(matcher.group(), 1, Integer::sum);
        }
        return result;
    }
}
