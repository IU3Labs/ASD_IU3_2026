package ru.bmstu.lab2.task_3b;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ru.bmstu.lab2.task_1a.PriorityQueueCustom;
import ru.bmstu.lab2.task_2a.HashMapCustom;

/**
 * Задача 3B. Подсчёт частоты слов с помощью собственной хеш-таблицы {@link HashMapCustom}.
 *
 * <p>Текст разбивается на слова {@link WordTokenizer} (без учёта регистра, без знаков препинания),
 * для каждого слова счётчик в таблице увеличивается на 1 — {@code O(1)} в среднем. Итого
 * {@code O(L)} по времени, где {@code L} — длина текста, и {@code O(U)} памяти, где {@code U} —
 * число различных слов.
 *
 * <p>Самые частые слова выбираются собственной очередью с приоритетом {@link PriorityQueueCustom}
 * размера не больше {@code k}: на вершине держим «худшее» из лучших слов и выбрасываем его,
 * когда очередь переполняется. Это {@code O(U log k)} вместо {@code O(U log U)} у полной сортировки.
 */
public final class WordCounter {

    /**
     * Порядок «от лучшего к худшему»: чаще — раньше, при равной частоте — по алфавиту,
     * чтобы результат был однозначным.
     */
    private static final Comparator<WordCount> BEST_FIRST =
            Comparator.comparingInt(WordCount::count).reversed().thenComparing(WordCount::word);

    private WordCounter() {
    }

    /**
     * Считает, сколько раз встречается каждое слово. Время: {@code O(L)} в среднем.
     *
     * <p>В условии указан тип {@code Map<String, Integer>}, но {@code java.util.Map}-реализации
     * в решении использовать нельзя, поэтому возвращается собственная таблица.
     *
     * @param text текст
     * @return таблица «слово в нижнем регистре → число вхождений»
     * @throws NullPointerException если {@code text == null}
     */
    public static HashMapCustom<String, Integer> countWords(String text) {
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        WordTokenizer.forEachWord(text, word -> counts.put(word, counts.getOrDefault(word, 0) + 1));
        return counts;
    }

    /**
     * Возвращает {@code k} самых частых слов по убыванию частоты (при равенстве — по алфавиту).
     * Время: {@code O(U log k)}, доп. память: {@code O(k)}.
     *
     * @param counts результат {@link #countWords}
     * @param k сколько слов вернуть; если различных слов меньше — вернутся все
     * @return список из не более чем {@code k} слов
     * @throws IllegalArgumentException если {@code k < 0}
     */
    public static List<WordCount> topWords(HashMapCustom<String, Integer> counts, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("k must be non-negative: " + k);
        }
        if (k == 0) {
            return new ArrayList<>();
        }
        // Вершина очереди — самое «плохое» слово среди k отобранных.
        PriorityQueueCustom<WordCount> best = new PriorityQueueCustom<>(BEST_FIRST.reversed());
        counts.forEach((word, count) -> {
            best.add(new WordCount(word, count));
            if (best.size() > k) {
                best.poll();
            }
        });
        List<WordCount> result = new ArrayList<>(best.size());
        while (!best.isEmpty()) {
            result.add(best.poll());
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Удобная обёртка: подсчёт частот и выбор {@code k} самых частых слов текста.
     *
     * @param text текст
     * @param k сколько слов вернуть
     * @return список из не более чем {@code k} самых частых слов
     */
    public static List<WordCount> topWords(String text, int k) {
        return topWords(countWords(text), k);
    }
}
