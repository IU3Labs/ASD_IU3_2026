package ru.bmstu.lab2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TopKTest {

    @Test
    void kGreaterThanSizeReturnsAllSorted() {
        List<Integer> in = Arrays.asList(3, 1, 2);
        assertEquals(Arrays.asList(3, 2, 1), TopK.topK(in, 10));
    }

    @Test
    void kZeroReturnsEmpty() {
        assertTrue(TopK.topK(Arrays.asList(1, 2, 3), 0).isEmpty());
    }

    @Test
    void negativeKReturnsEmpty() {
        assertTrue(TopK.topK(Arrays.asList(1, 2, 3), -5).isEmpty());
    }

    @Test
    void emptyInput() {
        List<Integer> empty = new ArrayList<>();
        assertTrue(TopK.topK(empty, 3).isEmpty());
    }

    @Test
    void basicTopK() {
        List<Integer> in = Arrays.asList(5, 1, 9, 3, 7, 2, 8);
        assertEquals(Arrays.asList(9, 8, 7), TopK.topK(in, 3));
    }

    @Test
    void duplicatesHandled() {
        List<Integer> in = Arrays.asList(4, 4, 4, 2, 2, 1);
        assertEquals(Arrays.asList(4, 4, 4), TopK.topK(in, 3));
    }

    @Test
    void allEqual() {
        List<Integer> in = Arrays.asList(7, 7, 7, 7);
        assertEquals(Arrays.asList(7, 7), TopK.topK(in, 2));
    }

    @Test
    void matchesSortOnRandom() {
        Random rnd = new Random(123);
        List<Integer> in = new ArrayList<>();
        for (int i = 0; i < 5000; i++) in.add(rnd.nextInt(1000));
        int k = 50;
        List<Integer> expected = new ArrayList<>(in);
        expected.sort(Comparator.reverseOrder());
        expected = expected.subList(0, k);
        assertEquals(expected, TopK.topK(in, k));
    }

    @Test
    void nullInputThrows() {
        assertThrows(NullPointerException.class, () -> TopK.topK(null, 3));
    }

    @Test
    void nullElementThrows() {
        List<Integer> in = new ArrayList<>();
        in.add(1);
        in.add(null);
        assertThrows(NullPointerException.class, () -> TopK.topK(in, 1));
    }
}