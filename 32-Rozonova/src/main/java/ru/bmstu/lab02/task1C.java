package ru.bmstu.lab2;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Priority Queue implementation supporting removal by value in O(n) time.
 *
 * @param <T> type of elements held in this queue, must be comparable
 */
public class PriorityQueueWithRemove<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;