/*
 * Задача 11. Ближайший к среднему.
 * Вход: массив из n целых чисел.
 * Выход: элемент, ближайший к среднему арифметическому (если несколько - первый по индексу).
 * Среднее может быть дробным.
 */
package ru.bmstu.lab1.task_11;

/** Поиск элемента, ближайшего к среднему арифметическому. */
public final class ClosestToAverage {

    private ClosestToAverage() {}

    /**
     * Находит элемент, ближайший к среднему. Если таких несколько, возвращает первый.
     *
     * <p>Среднее может быть дробным, а сравнивать дроби в double неточно. Поэтому сравниваю |a[i] *
     * n - sum| вместо |a[i] - sum / n| - это то же расстояние, умноженное на n, но в целых числах.
     * Используется long, чтобы не было переполнения.
     *
     * @param array непустой массив
     * @return ближайший к среднему элемент
     */
    public static int find(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив пустой");
        }
        long n = array.length;
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        int best = array[0];
        long bestDistance = Math.abs(best * n - sum);
        for (int i = 1; i < array.length; i++) {
            long distance = Math.abs(array[i] * n - sum);
            // строго меньше, чтобы при равенстве остался первый элемент
            if (distance < bestDistance) {
                bestDistance = distance;
                best = array[i];
            }
        }
        return best;
    }
}
