/*
Задача 1C (удаление по значению). Добавьте операцию boolean remove(T item) — удаление первого вхождения элемента по значению за O(n). После удаления свойство кучи должно восстанавливаться. Тесты: удаление корня, удаление элемента из середины кучи, удаление несуществующего элемента, удаление из пустой очереди.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask1CTest_PriorityQueue {

    @Test
    void newQueueIsEmpty() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); assertTrue(queue.isEmpty()); assertEquals(0,queue.size());
    }

    @Test
    void emptyPeekReturnsNull() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); assertNull(queue.peek());
    }

    @Test
    void emptyPollThrows() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void oneElementReturnsItself() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); queue.add(7); assertEquals(7,queue.poll()); assertTrue(queue.isEmpty());
    }

    @Test
    void keepsSmallestAtTop() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); queue.add(8);queue.add(-2);queue.add(3);assertEquals(-2,queue.peek());
    }

    @Test
    void peekDoesNotRemove() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); queue.add(9);assertEquals(9,queue.peek());assertEquals(1,queue.size());
    }

    @Test
    void acceptsDuplicates() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); queue.add(2);queue.add(2);assertEquals(2,queue.poll());assertEquals(2,queue.poll());
    }

    @Test
    void sortsReverseInput() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); for(int i=100;i>=1;i--)queue.add(i);for(int i=1;i<=100;i++)assertEquals(i,queue.poll());
    }

    @Test
    void growsBeyondInitialCapacity() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); for(int i=300;i>=0;i--)queue.add(i);assertEquals(301,queue.size());for(int i=0;i<=300;i++)assertEquals(i,queue.poll());
    }

    @Test
    void supportsIntegerExtremes() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); queue.add(Integer.MAX_VALUE);queue.add(Integer.MIN_VALUE);assertEquals(Integer.MIN_VALUE,queue.poll());
    }

    @Test
    void rejectsNullElement() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); assertThrows(NullPointerException.class,()->queue.add(null));
    }

    @Test
    void removeRoot() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); for(int n:new int[]{1,2,3})queue.add(n);assertTrue(queue.remove(1));assertEquals(2,queue.poll());
    }

    @Test
    void removeMiddle() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); for(int i=0;i<9;i++)queue.add(i);assertTrue(queue.remove(5));assertEquals(8,queue.size());for(int i=0;i<9;i++)if(i!=5)assertEquals(i,queue.poll());
    }

    @Test
    void removeAbsent() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); queue.add(1);assertFalse(queue.remove(2));
    }

    @Test
    void removeFromEmpty() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); assertFalse(queue.remove(3));
    }

    @Test
    void removeDuplicateOnce() {
        LabaTask1C_PriorityQueue<Integer> queue = new LabaTask1C_PriorityQueue<>(); queue.add(4);queue.add(4);assertTrue(queue.remove(4));assertEquals(1,queue.size());
    }


}
