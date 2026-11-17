package ru.bmstu.lab2.task_3b;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
    public static HashMapCustom<String, Integer> countWords(String text) {
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        String lower = text.toLowerCase(Locale.ROOT);
        int start = -1;
        // i доходит до length включительно: «виртуальный» разделитель в конце закрывает последнее слово
        for (int i = 0; i <= lower.length(); i++) {
            boolean partOfWord = i < lower.length() && Character.isLetterOrDigit(lower.charAt(i));
            if (partOfWord && start < 0) {
                start = i;
            } else if (!partOfWord && start >= 0) {
                increment(counts, lower.substring(start, i));
                start = -1;
            }
        }
        return counts;
    }

    /**
     * Возвращает {@code limit} самых частых слов.
     *
     * @param counts таблица частот из {@link #countWords(String)}
     * @param limit  сколько слов вернуть, не меньше 0
     * @return слова по убыванию частоты (при равной частоте — по алфавиту)
     * @throws IllegalArgumentException если {@code limit} отрицателен
     */
    public static List<WordCount> topWords(HashMapCustom<String, Integer> counts, int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Limit must not be negative: " + limit);
        }
        List<WordCount> all = new ArrayList<>(counts.size());
        counts.forEach((word, count) -> all.add(new WordCount(word, count)));
        all.sort(BY_FREQUENCY);
        return new ArrayList<>(all.subList(0, Math.min(limit, all.size())));
    }

    private static void increment(HashMapCustom<String, Integer> counts, String word) {
        Integer current = counts.get(word);
        counts.put(word, current == null ? 1 : current + 1);
    }
}
