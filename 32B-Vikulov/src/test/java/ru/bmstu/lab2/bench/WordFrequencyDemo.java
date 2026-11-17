package ru.bmstu.lab2.bench;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import ru.bmstu.lab2.task_2a.HashMapCustom;
import ru.bmstu.lab2.task_3b.WordFrequency;

/**
 * Демонстрация задачи 3B: подсчёт частоты слов в тексте около 1 МБ и вывод 10 самых частых слов.
 *
 * <p>Аргументы — пути к текстовым файлам (UTF-8); их содержимое склеивается в один текст. Один и тот же файл
 * можно передать несколько раз, чтобы набрать нужный объём.
 */
public final class WordFrequencyDemo {

    private static final int TOP = 10;
    private static final double BYTES_PER_MB = 1024.0 * 1024.0;

    private WordFrequencyDemo() {
    }

    /**
     * Читает файлы, считает слова и печатает результат.
     *
     * @param args пути к файлам
     * @throws IOException если файл не удалось прочитать
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: WordFrequencyDemo <file> [<file> ...]");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String file : args) {
            builder.append(Files.readString(Path.of(file), StandardCharsets.UTF_8)).append('\n');
        }
        String text = builder.toString();
        double millis = Bench.averageMillis(() -> WordFrequency.countWords(text));
        HashMapCustom<String, Integer> counts = WordFrequency.countWords(text);

        long bytes = text.getBytes(StandardCharsets.UTF_8).length;
        System.out.printf("Текст: %.2f МБ, уникальных слов: %d, ёмкость таблицы: %d%n",
                bytes / BYTES_PER_MB, counts.size(), counts.capacity());
        System.out.printf("Среднее время countWords: %s мс%n%n", HashMapBenchmark.format(millis));
        Bench.row("#", "Слово", "Частота");
        Bench.row("---", "---", "---");
        int place = 1;
        for (WordFrequency.WordCount entry : WordFrequency.topWords(counts, TOP)) {
            Bench.row(place++, entry.word(), entry.count());
        }
    }
}
