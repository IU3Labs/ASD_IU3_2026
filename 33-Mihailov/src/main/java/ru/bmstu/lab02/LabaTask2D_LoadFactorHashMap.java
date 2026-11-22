/*
Задача 2D (сравнение порогов ресайза). Реализуйте хеш-таблицу с настраиваемым порогом загрузки (по умолчанию 0.75). Проведите эксперимент с порогами 0.5 и 0.9: измерьте итоговую ёмкость (потребление памяти) и среднее время get/put при 100 000 вставках и 50 000 поисках. Оформите сравнительную таблицу и вывод о компромиссе «память vs скорость».
*/

package ru.bmstu.lab02;

public class LabaTask2D_LoadFactorHashMap<K, V> extends LabaTask2A_HashMap<K, V> {
    public LabaTask2D_LoadFactorHashMap() { super(); }
    public LabaTask2D_LoadFactorHashMap(double threshold) { super(threshold); }
}
