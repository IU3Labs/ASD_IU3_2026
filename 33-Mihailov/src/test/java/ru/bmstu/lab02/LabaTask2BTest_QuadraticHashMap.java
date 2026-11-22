/*
Задача 2B (квадратичное пробирование). Реализуйте хеш-таблицу, как в задаче 2A, но с квадратичным пробированием: индекс вычисляется как (index + c1*i + c2*i^2) % capacity (например, c1 = 1, c2 = 3). Подберите ёмкость так, чтобы таблица не зацикливалась при поиске. Сравните среднее число пробирований на операцию get с линейным вариантом на 50 000 операциях; результаты — в benchmarks.md.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask2BTest_QuadraticHashMap {
    static final class BadHash {
        final int id;
        BadHash(int id){this.id=id;}
        @Override public int hashCode(){return Integer.MIN_VALUE;}
        @Override public boolean equals(Object other){return other instanceof BadHash && id==((BadHash)other).id;}
    }

    @Test
    void startsEmpty() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); assertEquals(0,map.size());assertEquals(16,map.capacity());
    }

    @Test
    void missingKeyReturnsNull() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); assertNull(map.get(7));
    }

    @Test
    void insertCanBeRead() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); map.put(4,"four");assertEquals("four",map.get(4));
    }

    @Test
    void replacesValue() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); map.put(1,"a");map.put(1,"b");assertEquals("b",map.get(1));assertEquals(1,map.size());
    }

    @Test
    void containsExisting() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); map.put(9,"x");assertTrue(map.containsKey(9));
    }

    @Test
    void doesNotContainMissing() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); assertFalse(map.containsKey(9));
    }

    @Test
    void removeExisting() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); map.put(1,"a");assertTrue(map.remove(1));assertNull(map.get(1));
    }

    @Test
    void removeMissing() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); assertFalse(map.remove(3));
    }

    @Test
    void handlesNullValue() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); map.put(2,null);assertTrue(map.containsKey(2));assertNull(map.get(2));
    }

    @Test
    void rejectsNullKey() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); assertThrows(NullPointerException.class,()->map.put(null,"a"));
    }

    @Test
    void resizesAndRetainsValues() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); for(int i=0;i<2000;i++)map.put(i,"v"+i);assertTrue(map.capacity()>16);for(int i=0;i<2000;i++)assertEquals("v"+i,map.get(i));
    }

    @Test
    void retainsProbeChainAfterDelete() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); map.put(1,"a");map.put(17,"b");map.put(33,"c");map.remove(17);assertEquals("c",map.get(33));
    }

    @Test
    void supportsMinimumHash() {
        LabaTask2B_QuadraticHashMap<BadHash,String> map=new LabaTask2B_QuadraticHashMap<>();map.put(new BadHash(7),"x");assertEquals("x",map.get(new BadHash(7)));
    }

    @Test
    void reinsertsAfterDeletion() {
        LabaTask2B_QuadraticHashMap<Integer,String> map = new LabaTask2B_QuadraticHashMap<>(); map.put(1,"a");map.remove(1);map.put(1,"b");assertEquals("b",map.get(1));
    }

    @Test
    void quadraticCollisionRun() {
        LabaTask2B_QuadraticHashMap<BadHash,String> map=new LabaTask2B_QuadraticHashMap<>();for(int i=0;i<80;i++)map.put(new BadHash(i),"x"+i);for(int i=0;i<80;i++)assertEquals("x"+i,map.get(new BadHash(i)));
    }


}
