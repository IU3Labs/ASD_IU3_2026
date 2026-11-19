package ru.bmstu.lab2.task_3b;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/**
 * Задача 3B. Частота слов в тексте.
 *
 * <p>Слово — максимальная последовательность букв и цифр (любого алфавита). Всё остальное — пробелы,
 * знаки препинания, дефисы, апострофы — считается разделителем и отбрасывается. Регистр не учитывается:
 * текст приводится к нижнему регистру. Для каждого слова счётчик в {@link HashMapCustom} увеличивается на 1.
 *
 * <p>Подсчёт: один проход по тексту длины L и не больше L/2 операций с таблицей по O(1) в среднем — O(L).
 * Выбор самых частых слов: сортировка u уникальных слов — O(u log u).
 */
public final class WordFrequency {

    /** Порядок для топа: по убыванию частоты, при равенстве — по алфавиту (чтобы результат был однозначным). */
    private static final Comparator<WordCount> BY_FREQUENCY =
            Comparator.comparingInt(WordCount::count).reversed().thenComparing(WordCount::word);

    private WordFrequency() {
    }

    /**
     * Пара «слово — сколько раз встретилось».
     *
     * @param word  слово в нижнем регистре
     * @param count число вхождений
     */
    public record WordCount(String word, int count) {
    }

    /**
     * Считает частоту каждого слова без учёта регистра.
     *
     * @param text исходный текст
     * @return таблица «слово → число вхождений»
     */
    public static Map<String, Integer> countWords(String text) {
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        String lower = text.toLowerCase(Locale.ROOT);
        int start = -1;
        for (int i = 0; i < lower.length();) {
            int codePoint = lower.codePointAt(i);
            boolean partOfWord = Character.isLetterOrDigit(codePoint);
            if (partOfWord && start < 0) {
                start = i;
            } else if (!partOfWord && start >= 0) {
                increment(counts, lower.substring(start, i));
                start = -1;
            }
            i += Character.charCount(codePoint);
        }
        if (start >= 0) {
            increment(counts, lower.substring(start));
        }
        return new WordCounts(counts);
    }

    /**
     * Возвращает {@code limit} самых частых слов.
     *
     * @param counts таблица частот из {@link #countWords(String)}
     * @param limit  сколько слов вернуть, не меньше 0
     * @return слова по убыванию частоты (при равной частоте — по алфавиту)
     * @throws IllegalArgumentException если {@code limit} отрицателен
     */
    public static List<WordCount> topWords(Map<String, Integer> counts, int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Limit must not be negative: " + limit);
        }
        WordCount[] all = new WordCount[counts.size()];
        int index = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            all[index++] = new WordCount(entry.getKey(), entry.getValue());
        }
        Arrays.sort(all, BY_FREQUENCY);
        return Arrays.asList(Arrays.copyOf(all, Math.min(limit, all.length)));
    }

    private static void increment(HashMapCustom<String, Integer> counts, String word) {
        Integer current = counts.get(word);
        counts.put(word, current == null ? 1 : current + 1);
    }
}
