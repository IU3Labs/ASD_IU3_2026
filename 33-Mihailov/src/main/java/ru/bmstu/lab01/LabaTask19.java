/*Задача 19. Объединение двух отсортированных массивов
Вход: два массива, каждый отсортирован по неубыванию; размеры (n) и (m).
Выход: один отсортированный массив — результат слияния.
Требование: O(n + m).
Сложность: средняя*/

package ru.bmstu.lab01;


//Сливает два неубывающих массива в один неубывающий за O(n + m).
public final class LabaTask19 {

    private LabaTask19() {
        // утилитарный класс
    }

    public static int[] merge(int[] first, int[] second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("arrays must not be null");
        }

        int[] result = new int[first.length + second.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < first.length && j < second.length) {
            if (first[i] <= second[j]) {
                result[k++] = first[i++];
            } else {
                result[k++] = second[j++];
            }
        }
        while (i < first.length) {
            result[k++] = first[i++];
        }
        while (j < second.length) {
            result[k++] = second[j++];
        }
        return result;
    }
}