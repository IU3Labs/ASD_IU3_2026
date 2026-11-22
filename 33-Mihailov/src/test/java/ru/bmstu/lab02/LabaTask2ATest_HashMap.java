/*
Задача 2A (линейное пробирование). Реализуйте HashMapCustom<K, V> с линейным пробированием:

- void put(K key, V value) — вставка или обновление значения по ключу;
- V get(K key) — значение по ключу либо null;
- boolean containsKey(K key);
- boolean remove(K key);
- int size(), int capacity().

Хеш-функция: Math.abs(key.hashCode()) % capacity. Коэффициент загрузки не более 0.75, при превышении — увеличение ёмкости в 2 раза с перехешированием. Для удалённых ячеек используйте маркер DELETED, чтобы не ломать цепочки пробирования.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask2ATest_HashMap {
    static final class BadHash {
        final int id;
        BadHash(int id){this.id=id;}
        @Override public int hashCode(){return Integer.MIN_VALUE;}
        @Override public boolean equals(Object other){return other instanceof BadHash && id==((BadHash)other).id;}
    }

    @Test
    void startsEmpty() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); assertEquals(0,map.size());assertEquals(16,map.capacity());
    }

    @Test
    void missingKeyReturnsNull() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); assertNull(map.get(7));
    }

    @Test
    void insertCanBeRead() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); map.put(4,"four");assertEquals("four",map.get(4));
    }

    @Test
    void replacesValue() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); map.put(1,"a");map.put(1,"b");assertEquals("b",map.get(1));assertEquals(1,map.size());
    }

    @Test
    void containsExisting() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); map.put(9,"x");assertTrue(map.containsKey(9));
    }

    @Test
    void doesNotContainMissing() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); assertFalse(map.containsKey(9));
    }

    @Test
    void removeExisting() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); map.put(1,"a");assertTrue(map.remove(1));assertNull(map.get(1));
    }

    @Test
    void removeMissing() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); assertFalse(map.remove(3));
    }

    @Test
    void handlesNullValue() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); map.put(2,null);assertTrue(map.containsKey(2));assertNull(map.get(2));
    }

    @Test
    void rejectsNullKey() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); assertThrows(NullPointerException.class,()->map.put(null,"a"));
    }

    @Test
    void resizesAndRetainsValues() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); for(int i=0;i<2000;i++)map.put(i,"v"+i);assertTrue(map.capacity()>16);for(int i=0;i<2000;i++)assertEquals("v"+i,map.get(i));
    }

    @Test
    void retainsProbeChainAfterDelete() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); map.put(1,"a");map.put(17,"b");map.put(33,"c");map.remove(17);assertEquals("c",map.get(33));
    }

    @Test
    void supportsMinimumHash() {
        LabaTask2A_HashMap<BadHash,String> map=new LabaTask2A_HashMap<>();map.put(new BadHash(7),"x");assertEquals("x",map.get(new BadHash(7)));
    }

    @Test
    void reinsertsAfterDeletion() {
        LabaTask2A_HashMap<Integer,String> map = new LabaTask2A_HashMap<>(); map.put(1,"a");map.remove(1);map.put(1,"b");assertEquals("b",map.get(1));
    }


}
