package ru.bmstu.lab02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Подсчёт частоты слов на собственных структурах: счётчики хранятся в {@link HashMapCustom},
 * самые частые слова выбираются через {@link TopK} (то есть {@link PriorityQueueCustom}).
 *
 * <p>Словом считается максимальная последовательность букв и цифр; всё остальное (пробелы, знаки препинания)
 * служит разделителем. Регистр не учитывается.
 */
public final class WordCounter {

    /** Чаще — «больше»; при равной частоте «больше» то слово, которое раньше по алфавиту. */
    private static final Comparator<WordCount> BY_FREQUENCY = Comparator
            .comparingInt(WordCount::count)
            .thenComparing(WordCount::word, Comparator.reverseOrder());

    private WordCounter() {
    }

    /**
     * Считает частоты слов.
     *
     * <p>Подсчёт идёт в {@link HashMapCustom}; в {@link Map} результат переносится только потому, что этого
     * требует сигнатура метода. Порядок записей в результате — порядок обхода собственной таблицы.
     *
     * @param text исходный текст
     * @return слово (в нижнем регистре) → число вхождений
     */
    public static Map<String, Integer> countWords(String text) {
        Map<String, Integer> result = new LinkedHashMap<>();
        countInto(text).forEach(result::put);
        return result;
    }

    /**
     * Возвращает {@code n} самых частых слов по убыванию частоты, при равенстве — по алфавиту.
     *
     * @param text исходный текст
     * @param n    сколько слов вернуть
     * @return список слов с частотами
     */
    public static List<WordCount> topWords(String text, int n) {
        List<WordCount> all = new ArrayList<>();
        countInto(text).forEach((word, count) -> all.add(new WordCount(word, count)));
        return TopK.topK(all, n, BY_FREQUENCY);
    }

    private static HashMapCustom<String, Integer> countInto(String text) {
        Objects.requireNonNull(text, "text");
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i <= text.length(); i++) {
            char symbol = i < text.length() ? text.charAt(i) : ' ';
            if (Character.isLetterOrDigit(symbol)) {
                word.append(Character.toLowerCase(symbol));
            } else if (!word.isEmpty()) {
                increment(counts, word.toString());
                word.setLength(0);
            }
        }
        return counts;
    }

    private static void increment(HashMapCustom<String, Integer> counts, String word) {
        Integer current = counts.get(word);
        counts.put(word, current == null ? 1 : current + 1);
    }
}
