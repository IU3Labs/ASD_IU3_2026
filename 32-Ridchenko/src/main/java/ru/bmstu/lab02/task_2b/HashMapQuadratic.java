package ru.bmstu.lab02.task_2b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 2B. Хеш-таблица с квадратичным пробированием.
 * Индекс: (base + c1*i + c2*i^2) % capacity.
 */
@SuppressWarnings("unchecked")
public class HashMapQuadratic<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private static final int C1 = 1;
    private static final int C2 = 3;
    private static final Object DELETED = new Object();

    private static class Entry<K, V> {
        K key;
        V value;
        Entry(K key, V value) { this.key = key; this.value = value; }
    }

    private Object[] table;
    private int size;
    private int capacity;
    private int usedSlots;

    //счетчик проб для замеров
    private long totalProbes = 0;
    private long getOperations = 0;

    public HashMapQuadratic() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new Object[capacity];
        this.size = 0;
        this.usedSlots = 0;
    }

    public void put(K key, V value) {
        if (key == null) throw new NullPointerException("key is null");
        if ((double) (usedSlots + 1) / capacity > LOAD_FACTOR) {
            resize(capacity * 2);
        }
        int index = findSlot(key);
        Entry<K, V> entry = (Entry<K, V>) table[index];
        if (entry != null) {
            entry.value = value;
            return;
        }
        table[index] = new Entry<>(key, value);
        size++;
        usedSlots++;
    }

    public V get(K key) {
        if (key == null) throw new NullPointerException("key is null");
        int index = findSlot(key);
        Entry<K, V> entry = (Entry<K, V>) table[index];
        return entry != null ? entry.value : null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean remove(K key) {
        if (key == null) throw new NullPointerException("key is null");
        int index = findSlot(key);
        Entry<K, V> entry = (Entry<K, V>) table[index];
        if (entry == null) return false;
        table[index] = DELETED;
        size--;
        return true;
    }

    public int size() { return size; }
    public int capacity() { return capacity; }
    
    public double getAverageProbes() {
        return getOperations == 0 ? 0 : (double) totalProbes / getOperations;
    }

    private int findSlot(K key) {
        int base = Math.abs(key.hashCode()) % capacity;
        int firstDeleted = -1;
        
        for (int i = 0; i < capacity; i++) {
            //квадратичное пробирование
            int probeIndex = (base + C1 * i + C2 * i * i) % capacity;
            Object slot = table[probeIndex];
            
            if (slot == null) {
                return firstDeleted != -1 ? firstDeleted : probeIndex;
            }
            if (slot == DELETED) {
                if (firstDeleted == -1) firstDeleted = probeIndex;
                continue;
            }
            Entry<K, V> entry = (Entry<K, V>) slot;
            if (entry.key.equals(key)) {
                return probeIndex;
            }
        }
        return firstDeleted != -1 ? firstDeleted : -1;
    }

    private void resize(int newCapacity) {
        Object[] oldTable = table;
        int oldCapacity = capacity;
        table = new Object[newCapacity];
        capacity = newCapacity;
        size = 0;
        usedSlots = 0;
        for (int i = 0; i < oldCapacity; i++) {
            Object slot = oldTable[i];
            if (slot != null && slot != DELETED) {
                Entry<K, V> entry = (Entry<K, V>) slot;
                put(entry.key, entry.value);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        if (line == null || line.trim().isEmpty()) return;
        int m = Integer.parseInt(line.trim());
        HashMapQuadratic<String, Integer> map = new HashMapQuadratic<>();

        for (int i = 0; i < m; i++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            String cmd = parts[0];
            switch (cmd) {
                case "put":
                    map.put(parts[1], Integer.parseInt(parts[2]));
                    break;
                case "get":
                    System.out.println(map.get(parts[1]));
                    break;
                case "containsKey":
                    System.out.println(map.containsKey(parts[1]));
                    break;
                case "remove":
                    System.out.println(map.remove(parts[1]));
                    break;
                case "size":
                    System.out.println(map.size());
                    break;
                case "capacity":
                    System.out.println(map.capacity());
                    break;
                default:
                    break;
            }
        }
    }
}