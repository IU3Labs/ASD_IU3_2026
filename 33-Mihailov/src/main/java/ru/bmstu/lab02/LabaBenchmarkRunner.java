/*
Лабораторная работа №2. Измерение скорости структур групп 1, 2 и задачи 3A.
Сравнение со стандартной библиотекой: размеры 10 000, 50 000 и 100 000;
построение кучи за O(n), min/max-heap, коллизии, пороги заполнения и Top-K.
*/
package ru.bmstu.lab02;

import java.util.*;

public final class LabaBenchmarkRunner {
    private LabaBenchmarkRunner() { }

    public static void main(String[] args) {
        System.out.println("| Структура | Операция | N | Среднее время (мс) | Примечание |");
        System.out.println("|---|---|---:|---:|---|");
        for (int n : new int[]{10_000, 50_000, 100_000}) {
            int[] data = randomValues(n);
            row("LabaTask1B_PriorityQueue", "add + poll", n, () -> {
                LabaTask1B_PriorityQueue<Integer> queue = new LabaTask1B_PriorityQueue<>();
                for (int v : data) queue.add(v);
                while (!queue.isEmpty()) queue.poll();
            }, "min-heap");
            row("java.util.PriorityQueue", "add + poll", n, () -> {
                PriorityQueue<Integer> queue = new PriorityQueue<>();
                for (int v : data) queue.add(v);
                while (!queue.isEmpty()) queue.poll();
            }, "библиотека Java");
            row("LabaTask2A_HashMap", "put + get", n, () -> {
                LabaTask2A_HashMap<Integer,Integer> map = new LabaTask2A_HashMap<>();
                for (int i = 0; i < data.length; i++) map.put(i, data[i]);
                for (int i = 0; i < data.length; i++) require(map.get(i) == data[i]);
            }, "линейное пробирование");
            row("java.util.HashMap", "put + get", n, () -> {
                HashMap<Integer,Integer> map = new HashMap<>();
                for (int i = 0; i < data.length; i++) map.put(i, data[i]);
                for (int i = 0; i < data.length; i++) require(map.get(i) == data[i]);
            }, "библиотека Java");
            row("LabaTask2B_QuadraticHashMap", "put + get", n, () -> {
                LabaTask2B_QuadraticHashMap<Integer,Integer> map = new LabaTask2B_QuadraticHashMap<>();
                for (int i = 0; i < data.length; i++) map.put(i, data[i]);
                for (int i = 0; i < data.length; i++) require(map.get(i) == data[i]);
            }, "квадратичное пробирование");
        }
        int[] input = randomValues(100_000);
        row("LabaTask1B_PriorityQueue", "min-heap", 100_000, () -> heapOrder(input, false), "одинаковые данные");
        row("LabaTask1B_PriorityQueue", "max-heap", 100_000, () -> heapOrder(input, true), "одинаковые данные");
        Integer[] array = Arrays.stream(input).boxed().toArray(Integer[]::new);
        row("LabaTask1D_PriorityQueue", "последовательные add", 100_000, () -> {
            LabaTask1D_PriorityQueue<Integer> h = new LabaTask1D_PriorityQueue<>();
            for (int v : input) h.add(v);
        }, "O(n log n) сверху");
        row("LabaTask1D_PriorityQueue", "конструктор из массива", 100_000, () -> {
            LabaTask1D_PriorityQueue<Integer> h = new LabaTask1D_PriorityQueue<>(array);
            require(h.size() == array.length);
        }, "O(n)");
        LabaTask2A_HashMap<Integer,Integer> linear = new LabaTask2A_HashMap<>();
        LabaTask2B_QuadraticHashMap<Integer,Integer> quadratic = new LabaTask2B_QuadraticHashMap<>();
        int[] queryKeys = randomValues(50_000);
        for(int i=0;i<50_000;i++) { linear.put(queryKeys[i],i); quadratic.put(queryKeys[i],i); }
        long linProbes=0, quadProbes=0;
        for(int key:queryKeys) {linProbes+=linear.getProbeCount(key);quadProbes+=quadratic.getProbeCount(key);}
        System.out.printf(Locale.US,"Средние пробы get (50 000): линейное %.3f; квадратичное %.3f%n",linProbes/50000.0,quadProbes/50000.0);
        for (double threshold : new double[]{0.5,0.9}) {
            LabaTask2D_LoadFactorHashMap<Integer,Integer> table=new LabaTask2D_LoadFactorHashMap<>(threshold);
            long t0=System.nanoTime();
            for(int i=0;i<100_000;i++)table.put(i,i);
            double putMs=(System.nanoTime()-t0)/1e6/100_000;
            t0=System.nanoTime();
            for(int i=0;i<50_000;i++)require(table.get(i)==i);
            double getMs=(System.nanoTime()-t0)/1e6/50_000;
            System.out.printf(Locale.US,"Порог %.1f: capacity=%d, put=%.6f мс/операция, get=%.6f мс/операция%n",threshold,table.capacity(),putMs,getMs);
        }
        int[] million=randomValues(1_000_000);
        List<Integer> inputMillion=new ArrayList<>(million.length);
        for(int value:million)inputMillion.add(value);
        row("LabaTask3A_TopK", "topK (k=100)", 1_000_000, () -> require(LabaTask3A_TopK.topK(inputMillion,100).size()==100),"собственная куча");
        for(int n:new int[]{1000,10000}) {
            row("CollisionKey + LabaTask2A_HashMap", "put + get + remove", n, () -> {
                LabaTask2A_HashMap<LabaTask2C_CollisionKey,Integer> map=new LabaTask2A_HashMap<>();
                for(int i=0;i<n;i++)map.put(new LabaTask2C_CollisionKey("k"+i),i);
                for(int i=0;i<n;i++)require(map.get(new LabaTask2C_CollisionKey("k"+i))==i);
                for(int i=0;i<n;i++)require(map.remove(new LabaTask2C_CollisionKey("k"+i)));
            },"одинаковый hashCode");
            row("String + LabaTask2A_HashMap", "put + get + remove", n, () -> {
                LabaTask2A_HashMap<String,Integer> map=new LabaTask2A_HashMap<>();
                for(int i=0;i<n;i++)map.put("k"+i,i);
                for(int i=0;i<n;i++)require(map.get("k"+i)==i);
                for(int i=0;i<n;i++)require(map.remove("k"+i));
            },"обычный hashCode");
        }
    }

    private static void heapOrder(int[] data, boolean reverse) {
        LabaTask1B_PriorityQueue<Integer> queue=new LabaTask1B_PriorityQueue<>(reverse?Comparator.reverseOrder():null);
        for(int v:data)queue.add(v);
        while(!queue.isEmpty())queue.poll();
    }

    private static int[] randomValues(int n) {
        Random r=new Random(1234);
        int[] data=new int[n];
        for(int i=0;i<n;i++)data[i]=r.nextInt();
        return data;
    }

    private static void require(boolean okay) {
        if(!okay)throw new AssertionError("benchmark failed");
    }

    private static void row(String structure,String operation,int n,Runnable run,String note) {
        run.run();
        int repetitions=n>=1_000_000?2:3;
        long start=System.nanoTime();
        for(int r=0;r<repetitions;r++)run.run();
        System.out.printf(Locale.US,"| %s | %s | %,d | %.3f | %s |%n",structure,operation,n,(System.nanoTime()-start)/1e6/repetitions,note);
    }
}
