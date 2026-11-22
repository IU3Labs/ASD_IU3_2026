package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueCustomTest {

    // ---------------------------------------------------------- 1A: базовая реализация

    @Test
    void newQueueIsEmpty() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    @Test
    void pollOnEmptyThrows() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, pq::poll);
    }

    @Test
    void peekOnEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<Integer>().peek());
    }

    @Test
    void singleElement() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(7);
        assertEquals(7, pq.peek());
        assertEquals(1, pq.size());
        assertEquals(7, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void peekDoesNotRemove() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(5);
        pq.add(3);
        assertEquals(3, pq.peek());
        assertEquals(3, pq.peek());
        assertEquals(2, pq.size());
    }

    @Test
    void pollsInNonDecreasingOrder() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        for (int x : new int[]{5, 3, 8, 1, 9, 2, 7, 4, 6, 0}) {
            pq.add(x);
            assertTrue(pq.isValidHeap());
        }
        for (int expected = 0; expected < 10; expected++) {
            assertEquals(expected, pq.poll());
            assertTrue(pq.isValidHeap());
        }
    }

    @Test
    void duplicatesAreKept() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        for (int x : new int[]{2, 2, 1, 1, 3, 3, 2}) {
            pq.add(x);
        }
        List<Integer> out = drain(pq);
        assertEquals(List.of(1, 1, 2, 2, 2, 3, 3), out);
    }

    @Test
    void growsBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        int initial = pq.capacity();
        for (int i = 1000; i > 0; i--) {
            pq.add(i);
        }
        assertTrue(pq.capacity() > initial);
        assertEquals(1000, pq.size());
        assertEquals(1, pq.poll());
    }

    @Test
    void addNullThrows() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>().add(null));
    }

    @Test
    void randomDataMatchesSorting() {
        Random rnd = new Random(1);
        List<Integer> data = new ArrayList<>();
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        for (int i = 0; i < 5000; i++) {
            int v = rnd.nextInt(1000);
            data.add(v);
            pq.add(v);
        }
        Collections.sort(data);
        assertEquals(data, drain(pq));
    }

    @Test
    void worksWithStrings() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>();
        pq.add("pear");
        pq.add("apple");
        pq.add("fig");
        assertEquals(List.of("apple", "fig", "pear"), drain(pq));
    }

    @Test
    void interleavedAddAndPoll() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(10);
        pq.add(4);
        assertEquals(4, pq.poll());
        pq.add(1);
        pq.add(7);
        assertEquals(1, pq.poll());
        assertEquals(7, pq.poll());
        assertEquals(10, pq.poll());
        assertTrue(pq.isEmpty());
    }

    // ---------------------------------------------------------- 1B: компаратор, min/max

    @Test
    void minHeapWithExplicitNaturalComparator() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(Comparator.naturalOrder());
        Arrays.asList(4, 1, 3, 2).forEach(pq::add);
        assertEquals(List.of(1, 2, 3, 4), drain(pq));
    }

    @Test
    void maxHeapWithReverseOrder() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(Comparator.reverseOrder());
        Arrays.asList(4, 1, 3, 2, 5).forEach(pq::add);
        assertEquals(5, pq.peek());
        assertEquals(List.of(5, 4, 3, 2, 1), drain(pq));
    }

    @Test
    void customComparatorByLength() {
        PriorityQueueCustom<String> pq = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        pq.add("ccc");
        pq.add("a");
        pq.add("bb");
        assertEquals("a", pq.poll());
        assertEquals("bb", pq.poll());
        assertEquals("ccc", pq.poll());
    }

    @Test
    void minAndMaxHeapOnSameRandomData() {
        Random rnd = new Random(7);
        PriorityQueueCustom<Integer> min = new PriorityQueueCustom<>();
        PriorityQueueCustom<Integer> max = new PriorityQueueCustom<>(Comparator.reverseOrder());
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            int v = rnd.nextInt();
            data.add(v);
            min.add(v);
            max.add(v);
        }
        Collections.sort(data);
        assertEquals(data, drain(min));
        Collections.reverse(data);
        assertEquals(data, drain(max));
    }

    // ---------------------------------------------------------- 1C: remove(T)

    @Test
    void removeRoot() {
        PriorityQueueCustom<Integer> pq = heapOf(1, 2, 3, 4, 5, 6, 7);
        assertTrue(pq.remove(1));
        assertTrue(pq.isValidHeap());
        assertEquals(List.of(2, 3, 4, 5, 6, 7), drain(pq));
    }

    @Test
    void removeFromMiddle() {
        PriorityQueueCustom<Integer> pq = heapOf(5, 3, 8, 1, 9, 2, 7, 4, 6);
        assertTrue(pq.remove(8));
        assertTrue(pq.isValidHeap());
        assertEquals(8, pq.size());
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 9), drain(pq));
    }

    @Test
    void removeNonexistentReturnsFalse() {
        PriorityQueueCustom<Integer> pq = heapOf(1, 2, 3);
        assertFalse(pq.remove(42));
        assertEquals(3, pq.size());
        assertTrue(pq.isValidHeap());
    }

    @Test
    void removeFromEmptyReturnsFalse() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertFalse(pq.remove(1));
        assertEquals(0, pq.size());
    }

    @Test
    void removeLastLeaf() {
        PriorityQueueCustom<Integer> pq = heapOf(1, 2, 3, 4, 5);
        assertTrue(pq.remove(5));
        assertTrue(pq.isValidHeap());
        assertEquals(List.of(1, 2, 3, 4), drain(pq));
    }

    @Test
    void removeOnlyFirstOccurrence() {
        PriorityQueueCustom<Integer> pq = heapOf(2, 2, 2, 1);
        assertTrue(pq.remove(2));
        assertEquals(3, pq.size());
        assertEquals(List.of(1, 2, 2), drain(pq));
    }

    @Test
    void removeRequiresSiftUpCase() {
        // после замены удалённого узла «последним» элементом тому нужно подняться вверх
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        for (int x : new int[]{1, 10, 2, 11, 12, 3, 4, 13, 14, 15, 16, 5, 6, 7, 8, 0 + 9}) {
            pq.add(x);
        }
        assertTrue(pq.remove(11));
        assertTrue(pq.isValidHeap());
        assertFalse(drain(pq).contains(11));
    }

    @Test
    void randomRemovalsKeepHeapValid() {
        Random rnd = new Random(3);
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        List<Integer> ref = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            int v = rnd.nextInt(100);
            pq.add(v);
            ref.add(v);
        }
        for (int i = 0; i < 300; i++) {
            int v = rnd.nextInt(100);
            assertEquals(ref.remove((Integer) v), pq.remove(v));
            assertTrue(pq.isValidHeap());
        }
        Collections.sort(ref);
        assertEquals(ref, drain(pq));
    }

    // ---------------------------------------------------------- 1D: построение за O(n)

    @Test
    void buildFromArrayYieldsSortedPolls() {
        Integer[] arr = {9, 4, 7, 1, 8, 2, 6, 3, 5, 0};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        assertTrue(pq.isValidHeap());
        assertEquals(10, pq.size());
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), drain(pq));
    }

    @Test
    void buildFromEmptyArray() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(pq.isEmpty());
        pq.add(1);
        assertEquals(1, pq.poll());
    }

    @Test
    void buildFromSingleElementArray() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(new Integer[]{42});
        assertEquals(42, pq.peek());
    }

    @Test
    void buildDoesNotModifySourceArray() {
        Integer[] arr = {3, 2, 1};
        new PriorityQueueCustom<>(arr);
        assertArrayEquals(new Integer[]{3, 2, 1}, arr);
    }

    @Test
    void buildWithComparatorMaxHeap() {
        Integer[] arr = {3, 9, 1, 7};
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr, Comparator.reverseOrder());
        assertEquals(List.of(9, 7, 3, 1), drain(pq));
    }

    @Test
    void buildFromLargeRandomArray() {
        Random rnd = new Random(5);
        Integer[] arr = new Integer[10_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt();
        }
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(arr);
        assertTrue(pq.isValidHeap());
        Integer[] sorted = arr.clone();
        Arrays.sort(sorted);
        assertEquals(Arrays.asList(sorted), drain(pq));
    }

    @Test
    void buildWithNullElementThrows() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{1, null}));
    }

    @Test
    void addAfterBuildKeepsHeap() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(new Integer[]{5, 3, 8});
        pq.add(1);
        pq.add(9);
        assertTrue(pq.isValidHeap());
        assertEquals(List.of(1, 3, 5, 8, 9), drain(pq));
    }

    // ---------------------------------------------------------- вспомогательные методы

    private static PriorityQueueCustom<Integer> heapOf(int... values) {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        for (int v : values) {
            pq.add(v);
        }
        return pq;
    }

    private static <T> List<T> drain(PriorityQueueCustom<T> pq) {
        List<T> out = new ArrayList<>();
        while (!pq.isEmpty()) {
            out.add(pq.poll());
        }
        return out;
    }

    // ---------------------------------------------------------- 3A: Top-K

    @Test
    void topKMatchesSorting() {
        Random rnd = new Random(1);
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 50_000; i++) {
            data.add(rnd.nextInt(1_000_000));
        }
        List<Integer> sorted = new ArrayList<>(data);
        sorted.sort(Collections.reverseOrder());
        for (int k : new int[]{1, 5, 100, 1000}) {
            assertEquals(sorted.subList(0, k), PriorityQueueCustom.topK(data, k), "k=" + k);
        }
    }

    @Test
    void topKEdgeCases() {
        assertEquals(List.of(), PriorityQueueCustom.topK(List.of(1, 2, 3), 0));
        assertEquals(List.of(), PriorityQueueCustom.topK(new ArrayList<Integer>(), 3));
        assertEquals(List.of(3, 2, 1), PriorityQueueCustom.topK(List.of(2, 3, 1), 10));
        assertThrows(IllegalArgumentException.class, () -> PriorityQueueCustom.topK(List.of(1), -1));
    }

    @Test
    void topKWithDuplicatesAndInputUnchanged() {
        List<Integer> data = new ArrayList<>(List.of(5, 5, 1, 5, 2, 2));
        assertEquals(List.of(5, 5, 5), PriorityQueueCustom.topK(data, 3));
        assertEquals(List.of(5, 5, 1, 5, 2, 2), data);
    }

    // ---------------------------------------------------------- 3D: скользящий минимум

    @Test
    void slidingMinSmall() {
        assertArrayEquals(new int[]{-1, -3, -3, -3, 3, 3},
                PriorityQueueCustom.slidingMin(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
    }

    @Test
    void slidingMinWindowOneAndFull() {
        int[] arr = {4, 2, 7, 1};
        assertArrayEquals(arr, PriorityQueueCustom.slidingMin(arr, 1));
        assertArrayEquals(new int[]{1}, PriorityQueueCustom.slidingMin(arr, 4));
    }

    @Test
    void slidingMinInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> PriorityQueueCustom.slidingMin(new int[]{1, 2}, 0));
        assertThrows(IllegalArgumentException.class, () -> PriorityQueueCustom.slidingMin(new int[]{1, 2}, 3));
    }

    @Test
    void slidingMinLargeMatchesBruteForce() {
        Random rnd = new Random(2);
        int[] arr = new int[200_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt();
        }
        int k = 1000;
        int[] fast = PriorityQueueCustom.slidingMin(arr, k);
        assertEquals(arr.length - k + 1, fast.length);
        // выборочная сверка с перебором
        for (int j = 0; j < fast.length; j += 997) {
            int min = Integer.MAX_VALUE;
            for (int t = j; t < j + k; t++) {
                min = Math.min(min, arr[t]);
            }
            assertEquals(min, fast[j]);
        }
        assertEquals(Arrays.stream(arr, fast.length - 1, arr.length).min().getAsInt(), fast[fast.length - 1]);
    }
}
