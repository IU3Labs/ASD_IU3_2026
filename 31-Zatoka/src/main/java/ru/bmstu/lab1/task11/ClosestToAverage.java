package ru.bmstu.lab1.task11;

/**
 * Задача 11. Ближайший к среднему.
 * Вход: массив из n целых чисел.
 * Выход: элемент массива, ближайший к среднему арифметическому.
 * Если таких несколько, выводится первый по индексу.
 * Ограничения: среднее может быть дробным.
 *
 * <p>Чтобы не работать с дробями, расстояние |a[i] - sum / n| умножаем на n:
 * сравнивать |a[i] * n - sum| можно в целых числах, и результат сравнения
 * тот же, потому что n больше нуля.
 */
public class ClosestToAverage {

    private ClosestToAverage() {
    }

    /**
     * Находит элемент, ближайший к среднему арифметическому массива.
     *
     * @param arr непустой массив чисел
     * @return ближайший к среднему элемент (первый по индексу при равенстве)
     * @throws IllegalArgumentException если массив пустой
     */
    public static int find(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }
        long n = arr.length;
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        int bestIndex = 0;
        long bestDistance = Math.abs(arr[0] * n - sum);
        for (int i = 1; i < arr.length; i++) {
            long distance = Math.abs(arr[i] * n - sum);
            // строго меньше: при равном расстоянии остаётся более ранний элемент
            if (distance < bestDistance) {
                bestDistance = distance;
                bestIndex = i;
            }
        }
        return arr[bestIndex];
    }
}
