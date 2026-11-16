/*Задача 11. Элемент, ближайший к среднему
Вход: массив из (n) целых чисел.
Выход: элемент массива, ближайший к среднему арифметическому.
Если несколько — вывести первый по индексу.
Ограничения: среднее может быть дробным.
Сложность: средняя*/

package ru.bmstu.lab01;


//Находит элемент массива, ближайший к среднему арифметическому (при равенстве — первый по индексу).
public final class Task11 {

    private Task11() {
        // утилитарный класс
    }

    public static int closestToAverage(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("array must not be empty");
        }

        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }

        // Сравниваем расстояния без деления: |a[i]*n - sum| / n — общий знаменатель n,
        // поэтому достаточно сравнивать |a[i]*n - sum| в long.
        long n = array.length;
        int bestIndex = 0;
        long bestDistance = Math.abs((long) array[0] * n - sum);

        for (int i = 1; i < array.length; i++) {
            long distance = Math.abs((long) array[i] * n - sum);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestIndex = i;
            }
        }
        return array[bestIndex];
    }
}