/*
Задача 2D (сравнение порогов ресайза). Реализуйте хеш-таблицу с настраиваемым порогом загрузки (по умолчанию 0.75). Проведите эксперимент с порогами 0.5 и 0.9: измерьте итоговую ёмкость (потребление памяти) и среднее время get/put при 100 000 вставках и 50 000 поисках. Оформите сравнительную таблицу и вывод о компромиссе «память vs скорость».
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask2DTest_LoadFactorHashMap {
    static final class BadHash {
        final int id;
        BadHash(int id){this.id=id;}
        @Override public int hashCode(){return Integer.MIN_VALUE;}
        @Override public boolean equals(Object other){return other instanceof BadHash && id==((BadHash)other).id;}
    }

    @Test
    void startsEmpty() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); assertEquals(0,map.size());assertEquals(16,map.capacity());
    }

    @Test
    void missingKeyReturnsNull() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); assertNull(map.get(7));
    }

    @Test
    void insertCanBeRead() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); map.put(4,"four");assertEquals("four",map.get(4));
    }

    @Test
    void replacesValue() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); map.put(1,"a");map.put(1,"b");assertEquals("b",map.get(1));assertEquals(1,map.size());
    }

    @Test
    void containsExisting() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); map.put(9,"x");assertTrue(map.containsKey(9));
    }

    @Test
    void doesNotContainMissing() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); assertFalse(map.containsKey(9));
    }

    @Test
    void removeExisting() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); map.put(1,"a");assertTrue(map.remove(1));assertNull(map.get(1));
    }

    @Test
    void removeMissing() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); assertFalse(map.remove(3));
    }

    @Test
    void handlesNullValue() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); map.put(2,null);assertTrue(map.containsKey(2));assertNull(map.get(2));
    }

    @Test
    void rejectsNullKey() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); assertThrows(NullPointerException.class,()->map.put(null,"a"));
    }

    @Test
    void resizesAndRetainsValues() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); for(int i=0;i<2000;i++)map.put(i,"v"+i);assertTrue(map.capacity()>16);for(int i=0;i<2000;i++)assertEquals("v"+i,map.get(i));
    }

    @Test
    void retainsProbeChainAfterDelete() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); map.put(1,"a");map.put(17,"b");map.put(33,"c");map.remove(17);assertEquals("c",map.get(33));
    }

    @Test
    void supportsMinimumHash() {
        LabaTask2D_LoadFactorHashMap<BadHash,String> map=new LabaTask2D_LoadFactorHashMap<>();map.put(new BadHash(7),"x");assertEquals("x",map.get(new BadHash(7)));
    }

    @Test
    void reinsertsAfterDeletion() {
        LabaTask2D_LoadFactorHashMap<Integer,String> map = new LabaTask2D_LoadFactorHashMap<>(); map.put(1,"a");map.remove(1);map.put(1,"b");assertEquals("b",map.get(1));
    }

    @Test
    void lowerThresholdUsesMoreSpace() {
        LabaTask2D_LoadFactorHashMap<Integer,Integer> small=new LabaTask2D_LoadFactorHashMap<>(0.5);LabaTask2D_LoadFactorHashMap<Integer,Integer> large=new LabaTask2D_LoadFactorHashMap<>(0.9);for(int i=0;i<100;i++){small.put(i,i);large.put(i,i);}assertTrue(small.capacity()>=large.capacity());
    }

    @Test
    void rejectsInvalidLoad() {
        assertThrows(IllegalArgumentException.class,()->new LabaTask2D_LoadFactorHashMap<>(1.0));
    }


}
