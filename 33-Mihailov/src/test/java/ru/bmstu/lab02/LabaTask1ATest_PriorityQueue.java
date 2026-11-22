/*
Задача 1A (базовая реализация). Реализуйте PriorityQueueCustom<T> на динамическом массиве с операциями:

- void add(T item) — добавить элемент, O(log n);
- T poll() — извлечь и вернуть минимальный элемент, при пустой очереди бросать NoSuchElementException, O(log n);
- T peek() — вернуть минимальный элемент без удаления, O(1);
- int size(), boolean isEmpty() — O(1).

Используйте индексацию: дети узла i — 2*i + 1 и 2*i + 2, родитель — (i - 1) / 2. Реализуйте просеивание вверх и вниз отдельными приватными методами siftUp и siftDown. Обеспечьте автоматическое расширение массива. Требование: элементы извлекаются в порядке неубывания.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask1ATest_PriorityQueue {

    @Test
    void newQueueIsEmpty() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); assertTrue(queue.isEmpty()); assertEquals(0,queue.size());
    }

    @Test
    void emptyPeekReturnsNull() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); assertNull(queue.peek());
    }

    @Test
    void emptyPollThrows() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void oneElementReturnsItself() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); queue.add(7); assertEquals(7,queue.poll()); assertTrue(queue.isEmpty());
    }

    @Test
    void keepsSmallestAtTop() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); queue.add(8);queue.add(-2);queue.add(3);assertEquals(-2,queue.peek());
    }

    @Test
    void peekDoesNotRemove() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); queue.add(9);assertEquals(9,queue.peek());assertEquals(1,queue.size());
    }

    @Test
    void acceptsDuplicates() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); queue.add(2);queue.add(2);assertEquals(2,queue.poll());assertEquals(2,queue.poll());
    }

    @Test
    void sortsReverseInput() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); for(int i=100;i>=1;i--)queue.add(i);for(int i=1;i<=100;i++)assertEquals(i,queue.poll());
    }

    @Test
    void growsBeyondInitialCapacity() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); for(int i=300;i>=0;i--)queue.add(i);assertEquals(301,queue.size());for(int i=0;i<=300;i++)assertEquals(i,queue.poll());
    }

    @Test
    void supportsIntegerExtremes() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); queue.add(Integer.MAX_VALUE);queue.add(Integer.MIN_VALUE);assertEquals(Integer.MIN_VALUE,queue.poll());
    }

    @Test
    void rejectsNullElement() {
        LabaTask1A_PriorityQueue<Integer> queue = new LabaTask1A_PriorityQueue<>(); assertThrows(NullPointerException.class,()->queue.add(null));
    }


}
