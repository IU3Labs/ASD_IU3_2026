package ru.bmstu.lab02.task_3c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task_2c.CollisionKey;

/**
 * Тесты задачи 3C: уникальные элементы в порядке первого вхождения.
 */
class DistinctTest {

    private static final long SEED = 20261108L;

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        assertTrue(Distinct.distinct(List.of()).isEmpty());
    }

    @Test
    void shouldKeepSingleElement() {
        assertEquals(List.of(5), Distinct.distinct(List.of(5)));
    }

    @Test
    void shouldRemoveDuplicatesKeepingFirstOccurrenceOrder() {
        List<Integer> input = List.of(3, 1, 3, 2, 1, 4, 2);

        assertEquals(List.of(3, 1, 2, 4), Distinct.distinct(input));
    }

    @Test
    void shouldReturnSameListWhenAllUnique() {
        List<String> input = List.of("c", "a", "b");

        assertEquals(input, Distinct.distinct(input));
    }

    @Test
    void shouldCollapseListOfEqualElements() {
        assertEquals(List.of(7), Distinct.distinct(List.of(7, 7, 7, 7, 7)));
    }

    @Test
    void shouldCompareByEqualsNotByReference() {
        String first = new String("same");
        String second = new String("same");

        List<String> actual = Distinct.distinct(List.of(first, second));

        assertEquals(1, actual.size());
        assertSame(first, actual.get(0));
    }

    @Test
    void shouldKeepSingleNullAtItsFirstPosition() {
        List<Integer> input = Arrays.asList(1, null, 2, null, 1);

        assertEquals(Arrays.asList(1, null, 2), Distinct.distinct(input));
    }

    @Test
    void shouldNotModifyInputList() {
        List<Integer> input = new ArrayList<>(List.of(2, 2, 1));

        List<Integer> actual = Distinct.distinct(input);

        assertEquals(List.of(2, 2, 1), input);
        assertNotSame(input, actual);
    }

    @Test
    void shouldWorkWithCollidingKeys() {
        List<CollisionKey> input = List.of(
                new CollisionKey("b"), new CollisionKey("a"), new CollisionKey("b"), new CollisionKey("c"));

        List<CollisionKey> expected = List.of(new CollisionKey("b"), new CollisionKey("a"), new CollisionKey("c"));
        assertEquals(expected, Distinct.distinct(input));
    }

    @Test
    void shouldMatchLinkedHashSetOnRandomData() {
        Random random = new Random(SEED);
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            input.add(random.nextInt(10_000));
        }

        List<Integer> expected = new ArrayList<>(new LinkedHashSet<>(input));
        assertEquals(expected, Distinct.distinct(input));
    }

    @Test
    void shouldRejectNullList() {
        assertThrows(NullPointerException.class, () -> Distinct.distinct(null));
    }
}
