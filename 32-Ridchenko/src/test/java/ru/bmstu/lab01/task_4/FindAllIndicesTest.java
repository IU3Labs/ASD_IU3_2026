package ru.bmstu.lab01.task_4;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FindAllIndicesTest {

    @Test
    public void testFindAllIndices() {
        int[] array = {5, 2, 9, 5, 7, 5};
        //число 5 стоит на позициях 0, 3 и 5
        List<Integer> expected = List.of(0, 3, 5);
        assertEquals(expected, FindAllIndices.findAllIndices(array, 5));
    }

    @Test
    public void testNoIndices() {
        int[] array = {5, 2, 9, 5, 7, 5};
        //числа 10 нет, список должен быть пустым
        List<Integer> expected = List.of();
        assertEquals(expected, FindAllIndices.findAllIndices(array, 10));
    }
}