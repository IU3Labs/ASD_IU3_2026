package ru.bmstu.lab1.task_45;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class PartitionTest {

    @Test
    void shouldGroupLessEqualGreater() {
        int[] array = {5, 1, 3, 7, 3, 2, 9};
        Partition.partition(array, 3);
        assertTrue(isPartitioned(array, 3));
        // элементы не потерялись
        int[] sorted = array.clone();
        Arrays.sort(sorted);
        assertArrayEquals(new int[] {1, 2, 3, 3, 5, 7, 9}, sorted);
    }

    @Test
    void shouldWorkWhenPivotIsNotInArray() {
        int[] array = {8, 1, 6, 2};
        Partition.partition(array, 5);
        assertTrue(isPartitioned(array, 5));
    }

    @Test
    void shouldNotChangeArrayOfEqualElements() {
        int[] array = {4, 4, 4};
        Partition.partition(array, 4);
        assertArrayEquals(new int[] {4, 4, 4}, array);
    }

    @Test
    void shouldPrintPartitionedArray() {
        String output = MainRunner.run(Main::run, "5\n5 3 1 3 2\n3");
        int[] array = Arrays.stream(output.split(" ")).mapToInt(Integer::parseInt).toArray();
        assertTrue(isPartitioned(array, 3));
    }

    /** Проверяет порядок групп: сначала меньше pivot, потом равные, потом больше. */
    private static boolean isPartitioned(int[] array, int pivot) {
        int i = 0;
        while (i < array.length && array[i] < pivot) {
            i++;
        }
        while (i < array.length && array[i] == pivot) {
            i++;
        }
        while (i < array.length && array[i] > pivot) {
            i++;
        }
        return i == array.length;
    }
}
