/*
Задача 1B (min-heap и max-heap через компаратор). Реализуйте очередь с приоритетом, конструктор которой принимает Comparator<T> (при отсутствии — естественный порядок). Класс должен корректно работать и как min-heap, и как max-heap. В тестах продемонстрируйте оба режима. Дополнительно: сравните время работы min- и max-варианта на одном и том же наборе из 100 000 случайных элементов и объясните результат.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask1BTest_PriorityQueue {

    @Test
    void newQueueIsEmpty() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); assertTrue(queue.isEmpty()); assertEquals(0,queue.size());
    }

    @Test
    void emptyPeekReturnsNull() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); assertNull(queue.peek());
    }

    @Test
    void emptyPollThrows() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void oneElementReturnsItself() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); queue.add(7); assertEquals(7,queue.poll()); assertTrue(queue.isEmpty());
    }

    @Test
    void keepsSmallestAtTop() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); queue.add(8);queue.add(-2);queue.add(3);assertEquals(-2,queue.peek());
    }

    @Test
    void peekDoesNotRemove() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); queue.add(9);assertEquals(9,queue.peek());assertEquals(1,queue.size());
    }

    @Test
    void acceptsDuplicates() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); queue.add(2);queue.add(2);assertEquals(2,queue.poll());assertEquals(2,queue.poll());
    }

    @Test
    void sortsReverseInput() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); for(int i=100;i>=1;i--)queue.add(i);for(int i=1;i<=100;i++)assertEquals(i,queue.poll());
    }

    @Test
    void growsBeyondInitialCapacity() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); for(int i=300;i>=0;i--)queue.add(i);assertEquals(301,queue.size());for(int i=0;i<=300;i++)assertEquals(i,queue.poll());
    }

    @Test
    void supportsIntegerExtremes() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); queue.add(Integer.MAX_VALUE);queue.add(Integer.MIN_VALUE);assertEquals(Integer.MIN_VALUE,queue.poll());
    }

    @Test
    void rejectsNullElement() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(); assertThrows(NullPointerException.class,()->queue.add(null));
    }

    @Test
    void reverseComparatorCreatesMaxHeap() {
        LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>(Comparator.reverseOrder()); for(int n:new int[]{5,2,8})queue.add(n);assertEquals(8,queue.poll());assertEquals(5,queue.poll());
    }

    @Test
    void customComparatorCanOrderStrings() {
        LabaTask1B_PriorityQueue<String> queue=new LabaTask1B_PriorityQueue<>(Comparator.comparingInt(String::length));queue.add("long");queue.add("a");assertEquals("a",queue.poll());
    }


}
