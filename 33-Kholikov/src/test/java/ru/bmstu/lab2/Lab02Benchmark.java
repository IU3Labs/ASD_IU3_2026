package ru.bmstu.lab2;

import com.sun.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/** Воспроизводимые замеры для задач 1A, 1C, 1D и 2A. */
public final class Lab02Benchmark {
    private static final int[] SIZES = {10_000, 50_000, 100_000};
    private static final int HEAP_BUILD_SIZE = 100_000;
    private static final long SEED = 42L;
    private static final int WARMUP_ROUNDS = 5;
    private static final int MEASUREMENT_ROUNDS = 9;
    private static final int QUEUE_REMOVALS = 256;

    // Заполненные структуры остаются доступными за пределами измеряемого вызова.
    private static volatile Object populatedStructure;

    private Lab02Benchmark() {
    }

    /**
     * Печатает CSV с длительностью и объёмом выделенной памяти для каждого измерения.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        ThreadMXBean allocationBean = allocationBean();
        System.out.println("structure,operation,size,round,elapsed_ns,allocated_bytes");
        for (int size : SIZES) {
            Data data = dataFor(size);
            List<Workload> workloads = workloads(data);
            List<Sample> samples = measureRounds(workloads, allocationBean);
            for (Sample sample : samples) {
                System.out.printf(Locale.ROOT, "%s,%s,%d,%d,%d,%d%n", sample.structure(),
                    sample.operation(), size, sample.round(), sample.nanos(), sample.allocatedBytes());
            }
        }
    }

    private static Data dataFor(int size) {
        Integer[] keys = new Random(SEED).ints().distinct().limit(size).boxed().toArray(Integer[]::new);
        Integer[] searchOrder = keys.clone();
        Collections.shuffle(Arrays.asList(searchOrder), new Random(SEED + 1));
        long sum = 0;
        for (int key : keys) {
            sum += key;
        }
        return new Data(keys, searchOrder, sum);
    }

    private static List<Workload> workloads(Data data) {
        List<Workload> workloads = new ArrayList<>();
        workloads.addAll(queueWorkloads("PriorityQueueCustom", data,
            PriorityQueueCustom<Integer>::new, PriorityQueueCustom::add,
            PriorityQueueCustom::poll, PriorityQueueCustom::remove));
        workloads.addAll(queueWorkloads("java.util.PriorityQueue", data,
            PriorityQueue<Integer>::new, PriorityQueue::add, PriorityQueue::poll, PriorityQueue::remove));
        workloads.addAll(mapWorkloads("HashMapCustom", data, HashMapCustom<Integer, Integer>::new,
            (map, key) -> map.put(key, key), HashMapCustom::get, HashMapCustom::remove, HashMapCustom::size));
        workloads.addAll(mapWorkloads("java.util.HashMap", data, HashMap<Integer, Integer>::new,
            (map, key) -> map.put(key, key), HashMap::get, (map, key) -> map.remove(key) != null, HashMap::size));
        if (data.keys().length == HEAP_BUILD_SIZE) {
            workloads.addAll(heapBuildWorkloads(data.keys()));
        }
        return workloads;
    }

    private static List<Workload> heapBuildWorkloads(Integer[] keys) {
        Workload successiveAdds = new Workload("PriorityQueueCustom", "build_add", keys.length, () -> () -> {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
            for (Integer key : keys) {
                queue.add(key);
            }
            populatedStructure = queue;
            return queue.size();
        });
        Workload bottomUp = new Workload("PriorityQueueCustom", "build_heapify", keys.length, () -> () -> {
            PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(keys);
            populatedStructure = queue;
            return queue.size();
        });
        return List.of(successiveAdds, bottomUp);
    }

    private static <Q> List<Workload> queueWorkloads(String name, Data data, Supplier<Q> factory,
            BiConsumer<Q, Integer> add, Function<Q, Integer> poll, BiPredicate<Q, Integer> remove) {
        Workload addAndPoll = new Workload(name, "add_poll", data.checksum(), () -> () -> {
            Q queue = factory.get();
            for (Integer key : data.keys()) {
                add.accept(queue, key);
            }
            long sum = 0;
            for (int count = 0; count < data.keys().length; count++) {
                sum += poll.apply(queue);
            }
            return sum;
        });
        Workload removeByValue = new Workload(name, "remove_value", QUEUE_REMOVALS, () -> {
            Q queue = factory.get();
            for (Integer key : data.keys()) {
                add.accept(queue, key);
            }
            return () -> countRemovals(queue, data.searchOrder(), QUEUE_REMOVALS, remove);
        });
        return List.of(addAndPoll, removeByValue);
    }

    private static <M> List<Workload> mapWorkloads(String name, Data data, Supplier<M> factory,
            BiConsumer<M, Integer> put, BiFunction<M, Integer, Integer> get,
            BiPredicate<M, Integer> remove, ToIntFunction<M> size) {
        Workload insert = new Workload(name, "put", data.keys().length, () -> () -> {
            M map = populate(factory.get(), data.keys(), put);
            populatedStructure = map;
            return size.applyAsInt(map);
        });
        Workload lookup = new Workload(name, "get", data.checksum(), () -> {
            M map = populate(factory.get(), data.keys(), put);
            return () -> {
                long sum = 0;
                for (Integer key : data.searchOrder()) {
                    sum += get.apply(map, key);
                }
                return sum;
            };
        });
        Workload delete = new Workload(name, "remove", data.keys().length, () -> {
            M map = populate(factory.get(), data.keys(), put);
            return () -> countRemovals(map, data.searchOrder(), data.keys().length, remove);
        });
        return List.of(insert, lookup, delete);
    }

    private static <M> M populate(M map, Integer[] keys, BiConsumer<M, Integer> put) {
        for (Integer key : keys) {
            put.accept(map, key);
        }
        return map;
    }

    private static <S> long countRemovals(S structure, Integer[] keys, int count,
            BiPredicate<S, Integer> remove) {
        int removed = 0;
        for (int index = 0; index < count; index++) {
            if (remove.test(structure, keys[index])) {
                removed++;
            }
        }
        return removed;
    }

    private static List<Sample> measureRounds(List<Workload> workloads, ThreadMXBean allocationBean) {
        List<Sample> samples = new ArrayList<>();
        for (int round = -WARMUP_ROUNDS; round < MEASUREMENT_ROUNDS; round++) {
            for (int step = 0; step < workloads.size(); step++) {
                int index = round % 2 == 0 ? step : workloads.size() - 1 - step;
                Sample sample = measure(workloads.get(index), round, allocationBean);
                if (round >= 0) {
                    samples.add(sample);
                }
            }
        }
        return samples;
    }

    private static Sample measure(Workload workload, int round, ThreadMXBean allocationBean) {
        LongSupplier operation = workload.prepare().get();
        long allocatedBefore = allocatedBytes(allocationBean);
        long start = System.nanoTime();
        long result = operation.getAsLong();
        long nanos = System.nanoTime() - start;
        long bytes = allocationBean == null ? -1 : allocatedBytes(allocationBean) - allocatedBefore;
        if (result != workload.expected()) {
            throw new IllegalStateException(workload.structure() + " " + workload.operation() + ": " + result);
        }
        return new Sample(workload.structure(), workload.operation(), round + 1, nanos, bytes);
    }

    private static ThreadMXBean allocationBean() {
        if (ManagementFactory.getThreadMXBean() instanceof ThreadMXBean bean
                && bean.isThreadAllocatedMemoryEnabled()) {
            return bean;
        }
        return null;
    }

    private static long allocatedBytes(ThreadMXBean bean) {
        return bean == null ? -1 : bean.getCurrentThreadAllocatedBytes();
    }

    private record Data(Integer[] keys, Integer[] searchOrder, long checksum) {
    }

    private record Workload(String structure, String operation, long expected, Supplier<LongSupplier> prepare) {
    }

    private record Sample(String structure, String operation, int round, long nanos, long allocatedBytes) {
    }
}
