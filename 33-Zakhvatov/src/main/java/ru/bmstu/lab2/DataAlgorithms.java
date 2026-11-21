package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Задачи 3A–3C: приложения собственной очереди и собственной хеш-таблицы. */
public final class DataAlgorithms {
    private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{M}\\p{N}]+");
    private static final int INITIAL_CAPACITY = 16;

    private DataAlgorithms() {
    }

    /**
     * Задача 3A: возвращает k наибольших элементов по убыванию, сохраняя дубликаты.
     * Время O(n log(k + 1)), память O(k); k должен лежать в диапазоне [0, input.size()].
     * Результат неизменяемый; null в обрабатываемых элементах запрещён.
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        Objects.requireNonNull(input, "input");
        if (k < 0 || k > input.size()) {
            throw new IllegalArgumentException("k вне диапазона [0, n]");
        }
        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>();
        if (k > 0) {
            for (T item : input) {
                Objects.requireNonNull(item, "item");
                if (heap.size() < k) {
                    heap.add(item);
                } else if (item.compareTo(heap.peek()) > 0) {
                    heap.poll();
                    heap.add(item);
                }
            }
        }
        Object[] result = new Object[k];
        for (int index = k - 1; index >= 0; index--) {
            result[index] = heap.poll();
        }
        return new ResultList<>(result);
    }

    /**
     * Задача 3B: считает слова без учёта регистра через Locale.ROOT.
     * Слова — последовательности Unicode-букв, комбинируемых знаков и цифр;
     * пунктуация разделяет слова. Возвращает неизменяемый Map над собственной таблицей.
     */
    public static Map<String, Integer> countWords(String text) {
        Objects.requireNonNull(text, "text");
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        Matcher matcher = WORD.matcher(text.toLowerCase(Locale.ROOT));
        while (matcher.find()) {
            String word = matcher.group();
            Integer count = counts.get(word);
            counts.put(word, count == null ? 1 : Math.addExact(count, 1));
        }
        return new WordCounts(counts);
    }

    /**
     * Задача 3C: возвращает уникальные элементы в порядке первого вхождения.
     * Null сохраняется один раз; время ожидаемо O(n), память O(u), u — число уникальных.
     * Результат неизменяемый; входной список не меняется.
     */
    public static <T> List<T> distinct(List<T> items) {
        Objects.requireNonNull(items, "items");
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>();
        Object[] result = new Object[INITIAL_CAPACITY];
        int size = 0;
        boolean nullSeen = false;
        for (T item : items) {
            if (item == null ? !nullSeen : !seen.containsKey(item)) {
                if (size == result.length) {
                    result = Arrays.copyOf(result, Math.multiplyExact(size, 2));
                }
                result[size++] = item;
                if (item == null) {
                    nullSeen = true;
                } else {
                    seen.put(item, Boolean.TRUE);
                }
            }
        }
        return new ResultList<>(Arrays.copyOf(result, size));
    }
}
