/*
Задача 3A (Top-K элементов). Используя собственную очередь с приоритетом, реализуйте List<T> topK(List<T> input, int k) — k наибольших элементов. Требуемая асимптотика: O(n log k). Проверьте корректность сравнением с сортировкой и измерьте время на 1 000 000 элементов.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask3ATest_TopK {

    @Test
    void takesThreeLargest() {
        assertEquals(List.of(5,7,9),LabaTask3A_TopK.topK(List.of(1,9,5,2,7),3));
    }

    @Test
    void zeroReturnsEmpty() {
        assertEquals(List.of(),LabaTask3A_TopK.topK(List.of(1,2),0));
    }

    @Test
    void allElementsSorted() {
        assertEquals(List.of(1,2,3),LabaTask3A_TopK.topK(List.of(3,1,2),3));
    }

    @Test
    void negativeNumbers() {
        assertEquals(List.of(-2,-1),LabaTask3A_TopK.topK(List.of(-9,-2,-5,-1),2));
    }

    @Test
    void duplicateValues() {
        assertEquals(List.of(8,8),LabaTask3A_TopK.topK(List.of(1,8,8,3),2));
    }

    @Test
    void singleElement() {
        assertEquals(List.of(9),LabaTask3A_TopK.topK(List.of(9),1));
    }

    @Test
    void emptyListZero() {
        assertTrue(LabaTask3A_TopK.topK(List.<Integer>of(),0).isEmpty());
    }

    @Test
    void invalidNegativeK() {
        assertThrows(IllegalArgumentException.class,()->LabaTask3A_TopK.topK(List.of(1),-1));
    }

    @Test
    void invalidTooLargeK() {
        assertThrows(IllegalArgumentException.class,()->LabaTask3A_TopK.topK(List.of(1),2));
    }

    @Test
    void nullList() {
        assertThrows(NullPointerException.class,()->LabaTask3A_TopK.topK(null,0));
    }

    @Test
    void nullElement() {
        assertThrows(NullPointerException.class,()->LabaTask3A_TopK.topK(Arrays.asList(1,null,3),2));
    }

    @Test
    void doesNotModifyInput() {
        List<Integer> data=new ArrayList<>(List.of(3,1,2));LabaTask3A_TopK.topK(data,2);assertEquals(List.of(3,1,2),data);
    }

    @Test
    void matchesSortOfRandomData() {
        Random random=new Random(31);List<Integer> input=new ArrayList<>();for(int i=0;i<10000;i++)input.add(random.nextInt(100000));List<Integer> sorted=new ArrayList<>(input);Collections.sort(sorted);assertEquals(sorted.subList(9900,10000),LabaTask3A_TopK.topK(input,100));
    }


}
