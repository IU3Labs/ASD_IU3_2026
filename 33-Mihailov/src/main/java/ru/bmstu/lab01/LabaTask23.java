/*Задача 23. Проверка анаграммы массивов
Вход: два массива одинаковой длины (n).
Выход: YES, если один массив является перестановкой другого; NO иначе.
Сложность: средняя*/

package ru.bmstu.lab01;

import java.util.HashMap;
import java.util.Map;


//Проверяет, являются ли два массива перестановками друг друга (анаграммами).
public final class LabaTask23 {

    private static final String YES = "YES";
    private static final String NO = "NO";

    private LabaTask23() {
        // утилитарный класс
    }

    public static String areAnagrams(int[] first, int[] second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("arrays must not be null");
        }
        if (first.length != second.length) {
            return NO;
        }

        Map<Integer, Integer> balance = new HashMap<>();
        for (int i = 0; i < first.length; i++) {
            balance.merge(first[i], 1, Integer::sum);
            balance.merge(second[i], -1, Integer::sum);
        }

        for (int value : balance.values()) {
            if (value != 0) {
                return NO;
            }
        }
        return YES;
    }
}