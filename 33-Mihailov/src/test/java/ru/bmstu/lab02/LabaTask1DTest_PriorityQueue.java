/*
Задача 1D (построение кучи за O(n)). Добавьте конструктор PriorityQueueCustom(T[] array), строящий кучу за O(n) методом просеивания вниз с последнего внутреннего узла к корню. Проведите замер: построение кучи из 100 000 элементов «последовательными add» против «за O(n)». Результаты оформите таблицей в benchmarks.md с кратким выводом.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask1DTest_PriorityQueue {

    @Test
    void newQueueIsEmpty() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); assertTrue(queue.isEmpty()); assertEquals(0,queue.size());
    }

    @Test
    void emptyPeekReturnsNull() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); assertNull(queue.peek());
    }

    @Test
    void emptyPollThrows() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void oneElementReturnsItself() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); queue.add(7); assertEquals(7,queue.poll()); assertTrue(queue.isEmpty());
    }

    @Test
    void keepsSmallestAtTop() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); queue.add(8);queue.add(-2);queue.add(3);assertEquals(-2,queue.peek());
    }

    @Test
    void peekDoesNotRemove() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); queue.add(9);assertEquals(9,queue.peek());assertEquals(1,queue.size());
    }

    @Test
    void acceptsDuplicates() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); queue.add(2);queue.add(2);assertEquals(2,queue.poll());assertEquals(2,queue.poll());
    }

    @Test
    void sortsReverseInput() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); for(int i=100;i>=1;i--)queue.add(i);for(int i=1;i<=100;i++)assertEquals(i,queue.poll());
    }

    @Test
    void growsBeyondInitialCapacity() {
        LabaTask1D_PriorityQueue<Integer> queue = new LabaTask1D_PriorityQueue<>(); for(int i=300;i>=0;i--)queue.add(i);assertEquals(301,queue.size());for(int i=0;i<=300;i++)assertEquals(i,queue.poll());
    }

    @Test
    void heapifiesUnsortedArray() {
        LabaTask1D_PriorityQueue<Integer> queue=new LabaTask1D_PriorityQueue<>(new Integer[]{5,1,9,2});assertEquals(1,queue.poll());assertEquals(2,queue.poll());
    }

    @Test
    void doesNotModifyInput() {
        Integer[] array={9,2,6};LabaTask1D_PriorityQueue<Integer> queue=new LabaTask1D_PriorityQueue<>(array);assertEquals(2,queue.poll());assertArrayEquals(new Integer[]{9,2,6},array);
    }

    @Test
    void rejectsNullArray() {
        assertThrows(NullPointerException.class,()->new LabaTask1D_PriorityQueue<Integer>((Integer[])null));
    }

    @Test
    void rejectsNullArrayElements() {
        assertThrows(NullPointerException.class,()->new LabaTask1D_PriorityQueue<>(new Integer[]{1,null}));
    }

    @Test
    void constructsEmptyArray() {
        LabaTask1D_PriorityQueue<Integer> queue=new LabaTask1D_PriorityQueue<>(new Integer[]{}); assertTrue(queue.isEmpty());
    }

    @Test
    void heapBuildsLargeInput() {
        Integer[] values=new Integer[10000];for(int i=0;i<values.length;i++)values[i]=values.length-i;LabaTask1D_PriorityQueue<Integer> queue=new LabaTask1D_PriorityQueue<>(values);for(int i=1;i<=values.length;i++)assertEquals(i,queue.poll());
    }


}
