package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Priority Queue implementation supporting both Min-Heap and Max-Heap via Comparator.
 *
 * @param <T> type of elements held in this queue
 */
public class PriorityQueueCustomComparator<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;
    private final Comparator<? super T> comparator;