package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки 3A–3C с независимыми эталонами и граничными случаями. */
class DataAlgorithmsTest {
    @Test
    void shouldFindTopKWithDuplicates() {
        assertEquals(List.of(9, 9, 5), DataAlgorithms.topK(List.of(1, 9, 3, 9, 5), 3));
    }

    @Test
    void shouldFindTopKAmongNegativeValues() {
        assertEquals(List.of(-1, -3), DataAlgorithms.topK(List.of(-9, -1, -3), 2));
    }

    @Test
    void shouldHandleZeroKAndEmptyInput() {
        assertEquals(List.of(), DataAlgorithms.topK(List.of(1, 2), 0));
        assertEquals(List.of(), DataAlgorithms.topK(List.<Integer>of(), 0));
    }

    @Test
    void shouldHandleKEqualToInputSizeAndExtremeValues() {
        List<Integer> input = List.of(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
        assertEquals(List.of(Integer.MAX_VALUE, 0, Integer.MIN_VALUE), DataAlgorithms.topK(input, 3));
    }

    @Test
    void shouldHandleKEqualToOne() {
        assertEquals(List.of(7), DataAlgorithms.topK(List.of(4, 7, 5), 1));
    }

    @Test
    void shouldRejectInvalidK() {
        assertThrows(IllegalArgumentException.class, () -> DataAlgorithms.topK(List.of(1), -1));
        assertThrows(IllegalArgumentException.class, () -> DataAlgorithms.topK(List.of(1), 2));
    }

    @Test
    void shouldRejectNullTopKInputAndItems() {
        assertThrows(NullPointerException.class, () -> DataAlgorithms.topK(null, 1));
        assertThrows(NullPointerException.class, () -> DataAlgorithms.topK(Arrays.asList(1, null), 1));
    }

    @Test
    void shouldProcessLinkedInputWithoutChangingIt() {
        List<Integer> input = new LinkedList<>(List.of(4, 2, 7, 1));
        assertEquals(List.of(7, 4), DataAlgorithms.topK(input, 2));
        assertEquals(List.of(4, 2, 7, 1), input);
    }

    @Test
    void shouldSupportComparableStrings() {
        assertEquals(List.of("z", "b"), DataAlgorithms.topK(List.of("a", "z", "b"), 2));
    }

    @Test
    void shouldMatchSortingForTopK() {
        Random random = new Random(71);
        for (int size = 0; size <= 100; size++) {
            List<Integer> input = random.ints(size, -20, 20).boxed().toList();
            List<Integer> sorted = new ArrayList<>(input);
            sorted.sort(Comparator.reverseOrder());
            for (int k = 0; k <= size; k++) {
                assertEquals(sorted.subList(0, k), DataAlgorithms.topK(input, k));
            }
        }
    }

    @Test
    void shouldCountWordsWithoutCaseOrPunctuation() {
        assertEquals(Map.of("hello", 3, "world", 2),
                DataAlgorithms.countWords("Hello, world! HELLO; World... hello"));
    }

    @Test
    void shouldCountCyrillicAndUnicodeWords() {
        assertEquals(Map.of("ёж", 2, "мир", 2, "café", 1, "42", 1),
                DataAlgorithms.countWords("ЁЖ ёж — Мир\nМИР café 42"));
    }

    @Test
    void shouldSeparateWordsAtPunctuationAndWhitespace() {
        assertEquals(Map.of("a", 2, "b", 2, "c", 2), DataAlgorithms.countWords("a,b;c\ta-b\nc"));
    }

    @Test
    void shouldHandleEmptyAndPunctuationOnlyText() {
        assertTrue(DataAlgorithms.countWords("").isEmpty());
        assertTrue(DataAlgorithms.countWords(" ,.!? — \n\t").isEmpty());
    }

    @Test
    void shouldNormalizeCaseIndependentlyOfDefaultLocale() {
        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr"));
            assertEquals(Map.of("i", 2), DataAlgorithms.countWords("I i"));
        } finally {
            Locale.setDefault(previous);
        }
    }

    @Test
    void shouldRejectNullText() {
        assertThrows(NullPointerException.class, () -> DataAlgorithms.countWords(null));
    }

    @Test
    void shouldCountManyUniqueWordsAfterResizing() {
        StringBuilder text = new StringBuilder();
        for (int index = 0; index < 1_000; index++) {
            text.append("word").append(index).append(' ');
        }
        Map<String, Integer> result = DataAlgorithms.countWords(text + "word99 word99");
        assertEquals(1_000, result.size());
        assertEquals(3, result.get("word99"));
        assertEquals(1, result.get("word999"));
    }

    @Test
    void shouldDistinctInFirstOccurrenceOrder() {
        assertEquals(List.of(3, 1, 2), DataAlgorithms.distinct(List.of(3, 1, 3, 2, 1)));
    }

    @Test
    void shouldPreserveOneNullInDistinct() {
        assertEquals(Arrays.asList(null, 2, 1), DataAlgorithms.distinct(Arrays.asList(null, 2, null, 1, 2)));
    }

    @Test
    void shouldHandleEmptyAndEqualItems() {
        assertEquals(List.of(), DataAlgorithms.distinct(List.of()));
        assertEquals(List.of(7), DataAlgorithms.distinct(List.of(7, 7, 7)));
    }

    @Test
    void shouldGrowDistinctOutputAndKeepInput() {
        List<Integer> input = new ArrayList<>();
        for (int index = 0; index < 1_000; index++) {
            input.add(index);
        }
        assertEquals(input, DataAlgorithms.distinct(input));
        assertEquals(1_000, input.size());
    }

    @Test
    void shouldRejectNullDistinctInput() {
        assertThrows(NullPointerException.class, () -> DataAlgorithms.distinct(null));
    }

    @Test
    void shouldMatchLinkedHashSetOnRandomInput() {
        Random random = new Random(17);
        List<Integer> input = new ArrayList<>();
        for (int index = 0; index < 10_000; index++) {
            input.add(random.nextInt(10) == 0 ? null : random.nextInt(300));
        }
        assertEquals(new ArrayList<>(new LinkedHashSet<>(input)), DataAlgorithms.distinct(input));
    }

    @Test
    void shouldHandleDistinctHashCollisionsAndEqualObjects() {
        record Item(int value) {
            /** {@inheritDoc} */
            @Override
            public int hashCode() {
                return 1;
            }
        }
        assertEquals(List.of(new Item(2), new Item(1)),
                DataAlgorithms.distinct(List.of(new Item(2), new Item(1), new Item(2))));
    }
}
