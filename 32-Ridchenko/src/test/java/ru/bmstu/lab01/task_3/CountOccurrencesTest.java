package ru.bmstu.lab01.task_3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CountOccurrencesTest {

    @Test
    public void testCountExists() {
        int[] array = {5, 2, 5, 1, 7};
        //число 5 встречается дважды
        assertEquals(2, CountOccurrences.count(array, 5));
    }

    @Test
    public void testCountNotFound() {
        int[] array = {5, 2, 5, 1, 7};
        //числа 10 в массиве нет
        assertEquals(0, CountOccurrences.count(array, 10));
    }

    @Test
    public void testCountEmptyArray() {
        int[] array = {};
        //в пустом массиве ничего не найдем
        assertEquals(0, CountOccurrences.count(array, 5));
    }
}