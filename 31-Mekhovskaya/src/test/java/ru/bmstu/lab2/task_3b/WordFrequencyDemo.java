package ru.bmstu.lab2.task_3b;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/**
 * Демонстрация задачи 3B: обработка текста объёмом около 1 МБ и вывод 10 самых частых слов.
 *
 * <p>Запуск без аргументов — используется сгенерированный текст {@link SampleTextGenerator}
 * (1 МБ). Можно передать путь к любому текстовому файлу в UTF-8, например к книге:
 * {@code WordFrequencyDemo /path/to/book.txt}.
 */
public final class WordFrequencyDemo {

    private static final int TOP_COUNT = 10;
    private static final long SEED = 2026L;
    private static final double NANOS_IN_MILLI = 1_000_000.0;

    private WordFrequencyDemo() {
    }

    /**
     * Точка входа.
     *
     * @param args необязательный путь к текстовому файлу в UTF-8
     * @throws IOException если файл не удалось прочитать
     */
    public static void main(String[] args) throws IOException {
        String text = args.length > 0
                ? Files.readString(Path.of(args[0]), StandardCharsets.UTF_8)
                : SampleTextGenerator.generate(SampleTextGenerator.ONE_MEGABYTE, SEED);

        long start = System.nanoTime();
        HashMapCustom<String, Integer> counts = WordCounter.countWords(text);
        List<WordCount> top = WordCounter.topWords(counts, TOP_COUNT);
        double elapsedMs = (System.nanoTime() - start) / NANOS_IN_MILLI;

        int[] totalWords = {0};
        counts.forEach((word, count) -> totalWords[0] += count);
        System.out.printf("Размер текста: %d байт%n", text.getBytes(StandardCharsets.UTF_8).length);
        System.out.printf("Всего слов: %d, различных: %d, ёмкость таблицы: %d%n",
                totalWords[0], counts.size(), counts.capacity());
        System.out.printf("Время подсчёта и выбора топ-%d: %.1f мс%n%n", TOP_COUNT, elapsedMs);
        for (int i = 0; i < top.size(); i++) {
            System.out.printf("%2d. %-12s %d%n", i + 1, top.get(i).word(), top.get(i).count());
        }
    }
}
