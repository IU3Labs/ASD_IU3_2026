package ru.bmstu.lab2.task_3c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** проверки задачи 3C. */
class DistinctElementsTest {
    @Test
    void shouldDistinctInFirstOccurrenceOrder() {
        assertEquals(List.of(3, 1, 2), DistinctElements.distinct(List.of(3, 1, 3, 2, 1)));
    }

    @Test
    void shouldPreserveOneNullInDistinct() {
        assertEquals(Arrays.asList(null, 2, 1), DistinctElements.distinct(Arrays.asList(null, 2, null, 1, 2)));
    }

    @Test
    void shouldHandleEmptyAndEqualItems() {
        assertEquals(List.of(), DistinctElements.distinct(List.of()));
        assertEquals(List.of(7), DistinctElements.distinct(List.of(7, 7, 7)));
    }

    @Test
    void shouldGrowDistinctOutputAndKeepInput() {
        List<Integer> input = new ArrayList<>();
        for (int index = 0; index < 1_000; index++) {
            input.add(index);
        }
        assertEquals(input, DistinctElements.distinct(input));
        assertEquals(1_000, input.size());
    }

    @Test
    void shouldRejectNullDistinctInput() {
        assertThrows(NullPointerException.class, () -> DistinctElements.distinct(null));
    }

    @Test
    void shouldMatchLinkedHashSetOnRandomInput() {
        Random random = new Random(17);
        List<Integer> input = new ArrayList<>();
        for (int index = 0; index < 10_000; index++) {
            input.add(random.nextInt(10) == 0 ? null : random.nextInt(300));
        }
        assertEquals(new ArrayList<>(new LinkedHashSet<>(input)), DistinctElements.distinct(input));
    }

    @Test
    void shouldHandleDistinctHashCollisionsAndEqualObjects() {
        assertEquals(List.of(new Item(2), new Item(1)),
                DistinctElements.distinct(List.of(new Item(2), new Item(1), new Item(2))));
    }
    private static class Item {
        private final int value;

        private Item(int value) {
            this.value = value;
        }

        /** {@inheritDoc} */
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Item)) {
                return false;
            }
            Item item = (Item) other;
            return value == item.value;
        }

        /** {@inheritDoc} */
        @Override
        public int hashCode() {
            return 1;
        }
    }

}
