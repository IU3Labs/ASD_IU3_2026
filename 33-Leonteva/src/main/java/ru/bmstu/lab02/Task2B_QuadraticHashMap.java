/*Задача 2B (квадратичное пробирование). Реализуйте хеш-таблицу, как в задаче 2A,
но с квадратичным пробированием: индекс вычисляется как (index + c1*i + c2*i^2) %
capacity (например, c1 = 1, c2 = 3). Подберите ёмкость так, чтобы таблица не зацикливалась при поиске.
Сравните среднее число пробирований на операцию get с линейным вариантом на 50 000 операциях; результаты —
в benchmarks.md.*/

package ru.bmstu.lab02;

public final class Task2B_QuadraticHashMap<K, V> extends Task2A_HashMap<K, V> {
    public Task2B_QuadraticHashMap() {
        super(0.75, true);
    }
}
