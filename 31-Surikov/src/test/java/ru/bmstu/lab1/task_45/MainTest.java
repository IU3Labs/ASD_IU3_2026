package ru.bmstu.lab1.task_45;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleSupport;

/** граничные случаи и независимая проверка алгоритма задачи 45 */
class MainTest {
    @Test
    void shouldPartitionMixedValuesAndPreserveMultiplicity() {
        checkPartition(new int[] {3, 1, 4, 3, 2, 5, 3}, 3);
        checkPartition(new int[] {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, 0}, 0);
        checkPartition(new int[] {3, 2, 1}, 2);
    }

    @Test
    void shouldHandleMissingGroupsAndEmptyInput() {
        checkPartition(new int[0], 0);
        checkPartition(new int[] {2, 2, 2}, 2);
        checkPartition(new int[] {1, 2, 3}, 5);
        checkPartition(new int[] {1, 2, 3}, -5);
        checkPartition(new int[] {1, 3}, 2);
    }

    @Test
    void shouldMaintainPartitionInvariantOnRandomArrays() {
        Random random = new Random(45);
        for (int attempt = 0; attempt < 200; attempt++) {
            int[] values = random.ints(random.nextInt(50), -20, 21).toArray();
            checkPartition(values, random.nextInt(41) - 20);
        }
    }

    private void checkPartition(int[] values, int pivot) {
        int[] expected = values.clone();
        Main.partition(values, pivot);
        int previousGroup = -1;
        for (int value : values) {
            int group = Integer.compare(value, pivot);
            assertTrue(group >= previousGroup, "Порядок групп нарушен");
            previousGroup = group;
        }
        Arrays.sort(expected);
        Arrays.sort(values);
        assertArrayEquals(expected, values, "Перестановка должна сохранять все элементы");
    }

    @Test
    void shouldReadAndPrintAnswer() throws Exception {
        assertEquals("1 2 3\n", ConsoleSupport.runMain(45, "3 3 2 1 2"));
    }
}
