package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Custom Binary Min-Heap Priority Queue implementation without using java.util.PriorityQueue.
 *
 * @param <T> type of elements held in this queue, must be comparable
 */
public class PriorityQueueCustom<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;