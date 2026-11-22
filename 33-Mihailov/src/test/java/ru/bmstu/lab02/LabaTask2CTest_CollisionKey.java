/*
Задача 2C (искусственные коллизии). Создайте обёртку CollisionKey над строкой, у которой hashCode() всегда возвращает одну и ту же константу. Проверьте, что ваша таблица корректно обрабатывает 10 000 таких ключей: вставка, поиск, удаление — без ошибок и без потери элементов. Продемонстрируйте деградацию производительности и объясните её в README.md.
*/

package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LabaTask2CTest_CollisionKey {

    @Test
    void sameHashCode() {
        assertEquals(new LabaTask2C_CollisionKey("x").hashCode(),new LabaTask2C_CollisionKey("y").hashCode());
    }

    @Test
    void equalStringsAreEqual() {
        assertEquals(new LabaTask2C_CollisionKey("x"),new LabaTask2C_CollisionKey("x"));
    }

    @Test
    void differentStringsNotEqual() {
        assertNotEquals(new LabaTask2C_CollisionKey("a"),new LabaTask2C_CollisionKey("b"));
    }

    @Test
    void rejectsNull() {
        assertThrows(NullPointerException.class,()->new LabaTask2C_CollisionKey(null));
    }

    @Test
    void hasReadableString() {
        assertEquals("abc",new LabaTask2C_CollisionKey("abc").toString());
    }

    @Test
    void collisionKeysCanBeStored() {
        LabaTask2A_HashMap<LabaTask2C_CollisionKey,Integer> map=new LabaTask2A_HashMap<>(); map.put(new LabaTask2C_CollisionKey("one"),1);map.put(new LabaTask2C_CollisionKey("two"),2);assertEquals(2,map.size());
    }

    @Test
    void allCollisionKeysRetrieved() {
        LabaTask2A_HashMap<LabaTask2C_CollisionKey,Integer> map=new LabaTask2A_HashMap<>(); for(int i=0;i<200;i++)map.put(new LabaTask2C_CollisionKey("k"+i),i);for(int i=0;i<200;i++)assertEquals(i,map.get(new LabaTask2C_CollisionKey("k"+i)));
    }

    @Test
    void deleteDoesNotBreakChain() {
        LabaTask2A_HashMap<LabaTask2C_CollisionKey,Integer> map=new LabaTask2A_HashMap<>(); for(int i=0;i<50;i++)map.put(new LabaTask2C_CollisionKey("k"+i),i);map.remove(new LabaTask2C_CollisionKey("k1"));assertEquals(49,map.get(new LabaTask2C_CollisionKey("k49")));
    }

    @Test
    void missingCollisionKey() {
        LabaTask2A_HashMap<LabaTask2C_CollisionKey,Integer> map=new LabaTask2A_HashMap<>(); map.put(new LabaTask2C_CollisionKey("a"),1);assertNull(map.get(new LabaTask2C_CollisionKey("b")));
    }

    @Test
    void reinsertCollisionKey() {
        LabaTask2A_HashMap<LabaTask2C_CollisionKey,Integer> map=new LabaTask2A_HashMap<>(); map.put(new LabaTask2C_CollisionKey("a"),1);map.remove(new LabaTask2C_CollisionKey("a"));map.put(new LabaTask2C_CollisionKey("a"),3);assertEquals(3,map.get(new LabaTask2C_CollisionKey("a")));
    }

    @Test
    void tenThousandCollisions() {
        LabaTask2A_HashMap<LabaTask2C_CollisionKey,Integer> map=new LabaTask2A_HashMap<>(); for(int i=0;i<10000;i++)map.put(new LabaTask2C_CollisionKey("id"+i),i);assertEquals(10000,map.size());for(int i=0;i<10000;i++)assertEquals(i,map.get(new LabaTask2C_CollisionKey("id"+i)));for(int i=0;i<10000;i++)assertTrue(map.remove(new LabaTask2C_CollisionKey("id"+i)));assertEquals(0,map.size());
    }


}
