package ru.bmstu.lab02.task_3;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;

import ru.bmstu.lab02.task_1.PriorityQueueCustom;
import ru.bmstu.lab02.task_2.HashMapCustom;

/** Прикладные алгоритмы на собственной очереди и хеш-таблице */
public final class LabAlgorithms {
    private LabAlgorithms() {
    }

    /**
     * Возвращает k наибольших элементов по убыванию с учётом повторов
     * @param input - исходный список
     * @param k - количество элементов от нуля до размера списка
     * @param <T> - тип сравнимого элемента
     * @return - список выбранных элементов
     */
    public static <T extends Comparable<? super T>> List<T> topK(List<T> input, int k) {
        checkSize(k, input.size());
        Object[] result = new Object[k];
        if (k == 0) {
            return new ArrayResult<>(result, 0);
        }
        PriorityQueueCustom<T> heap = new PriorityQueueCustom<>();
        for (T item : input) {
            if (heap.size() < k) {
                heap.add(item);
            } else if (item.compareTo(heap.peek()) > 0) {
                heap.poll();
                heap.add(item);
            }
        }
        for (int index = k - 1; index >= 0; index--) {
            result[index] = heap.poll();
        }
        return new ArrayResult<>(result, k);
    }

    /**
     * Подсчитывает слова без учёта регистра, используя знаки вне букв и цифр как разделители
     * @param text - исходный текст
     * @return - доступное для чтения отображение слов в частоты
     */
    public static Map<String, Integer> countWords(String text) {
        HashMapCustom<String, Integer> counts = new HashMapCustom<>();
        int start = 0;
        for (int index = 0; index < text.length();) {
            int symbol = text.codePointAt(index);
            int next = index + Character.charCount(symbol);
            if (!isWordPart(symbol)) {
                addWord(text, start, index, counts);
                start = next;
            }
            index = next;
        }
        addWord(text, start, text.length(), counts);
        return new WordCounts(counts);
    }

    private static boolean isWordPart(int symbol) {
        int type = Character.getType(symbol);
        return Character.isLetterOrDigit(symbol) || type == Character.NON_SPACING_MARK
                || type == Character.COMBINING_SPACING_MARK || type == Character.ENCLOSING_MARK;
    }

    private static void addWord(String text, int start, int end, HashMapCustom<String, Integer> counts) {
        if (start < end) {
            String word = text.substring(start, end).toLowerCase(Locale.ROOT);
            Integer previous = counts.get(word);
            counts.put(word, previous == null ? 1 : previous + 1);
        }
    }

    /**
     * Оставляет первые вхождения уникальных элементов
     * @param items - исходный список
     * @param <T> - тип элемента
     * @return - уникальные значения в исходном порядке
     */
    public static <T> List<T> distinct(List<T> items) {
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>(true);
        Object[] result = new Object[items.size()];
        int length = 0;
        for (T item : items) {
            if (!seen.containsKey(item)) {
                seen.put(item, true);
                result[length++] = item;
            }
        }
        return new ArrayResult<>(result, length);
    }

    /**
     * Находит минимумы всех последовательных окон массива
     * @param numbers - исходный массив
     * @param k - положительная длина окна
     * @return - минимумы в порядке смещения окна
     */
    public static int[] slidingMinimum(int[] numbers, int k) {
        checkSize(k, numbers.length);
        if (k == 0) {
            throw new IllegalArgumentException("Окно должно быть положительным");
        }
        int[] result = new int[numbers.length - k + 1];
        PriorityQueueCustom<WindowItem> heap = new PriorityQueueCustom<>();
        for (int index = 0; index < numbers.length; index++) {
            heap.add(new WindowItem(numbers[index], index));
            int start = index - k + 1;
            if (start >= 0) {
                while (heap.peek().index() < start) {
                    heap.poll();
                }
                result[start] = heap.peek().value();
            }
        }
        return result;
    }

    private static void checkSize(int k, int size) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Недопустимое значение k");
        }
    }

    private record WindowItem(int value, int index) implements Comparable<WindowItem> {
        @Override
        public int compareTo(WindowItem other) {
            int byValue = Integer.compare(value, other.value);
            return byValue == 0 ? Integer.compare(index, other.index) : byValue;
        }
    }

    // Абстрактные классы служат адаптерами интерфейсов List и Map; данные хранятся в своих структурах
    private static final class ArrayResult<T> extends AbstractList<T> implements RandomAccess {
        private final Object[] items;
        private final int size;

        ArrayResult(Object[] items, int size) {
            this.items = items;
            this.size = size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T get(int index) {
            return (T) items[Objects.checkIndex(index, size)];
        }

        @Override
        public int size() {
            return size;
        }
    }

    private static final class WordCounts extends AbstractMap<String, Integer> {
        private final HashMapCustom<String, Integer> counts;

        WordCounts(HashMapCustom<String, Integer> counts) {
            this.counts = counts;
        }

        @Override
        public Integer get(Object key) {
            return key instanceof String word ? counts.get(word) : null;
        }

        @Override
        public boolean containsKey(Object key) {
            return key instanceof String word && counts.containsKey(word);
        }

        @Override
        public Set<Entry<String, Integer>> entrySet() {
            return new AbstractSet<>() {
                @Override
                public int size() {
                    return counts.size();
                }

                @Override
                public Iterator<Entry<String, Integer>> iterator() {
                    return new Iterator<>() {
                        private int index;

                        @Override
                        public boolean hasNext() {
                            while (index < counts.capacity() && !counts.occupied(index)) {
                                index++;
                            }
                            return index < counts.capacity();
                        }

                        @Override
                        public Entry<String, Integer> next() {
                            if (!hasNext()) {
                                throw new NoSuchElementException();
                            }
                            int current = index++;
                            return new SimpleImmutableEntry<>(counts.keyAt(current), counts.valueAt(current));
                        }
                    };
                }
            };
        }
    }
}
