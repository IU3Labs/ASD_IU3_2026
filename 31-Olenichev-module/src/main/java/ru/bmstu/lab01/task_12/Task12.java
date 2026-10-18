package ru.bmstu.lab01.task_12;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс для решения задачи 12: поиск пары элементов с заданной суммой.
 * <p>
 * Ключевая особенность: для поиска используется HashMap и два прохода по массиву.
 * Первый проход заполняет словарь числами и их индексами (первым и последним).
 * Второй проход находит пару с наименьшим первым индексом (i).
 * Это позволяет достичь сложности O(n).
 * </p>
 * 
 * Условие задачи:
 * Вход: массив из n целых чисел и число s.
 * Выход: индексы i &lt; j, для которых a[i] + a[j] == s.
 * Если пар несколько — вывести пару с наименьшим i, при равенстве —
 * с наименьшим j. Если пары нет — вывести "-1 -1".
 * Сложность: средняя.
 */
public class Task12 {

    /**
     * Ищет пару элементов, сумма которых равна заданному значению.
     * 
     * @param array  входной массив чисел
     * @param target искомая сумма
     * @return пара индексов или {-1, -1}, если пары нет
     */
    public int[] findPair(int[] array, long target) {
        // Храним: Число -> [первый индекс, последний индекс]
        Map<Long, int[]> map = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            long val = (long) array[i];
            if (!map.containsKey(val)) {
                // Первое вхождение: записываем и как первый, и как последний
                map.put(val, new int[]{i, i});
            } else {
                // Повторное вхождение: обновляем только последний индекс
                map.get(val)[1] = i;
            }
        }

        int bestI = -1;
        int bestJ = -1;

        for (int i = 0; i < array.length; i++) {
            long complement = target - array[i];

            if (map.containsKey(complement)) {
                int[] indices = map.get(complement);
                
                // Пробуем взять первый индекс (для минимального j)
                int j = indices[0];
                if (i != j) {
                    int currentI = Math.min(i, j);
                    int currentJ = Math.max(i, j);
                    if (bestI == -1 || currentI < bestI || (currentI == bestI && currentJ < bestJ)) {
                        bestI = currentI;
                        bestJ = currentJ;
                    }
                }
                
                // Также пробуем взять последний индекс 
                j = indices[1];
                if (i != j) {
                    int currentI = Math.min(i, j);
                    int currentJ = Math.max(i, j);
                    if (bestI == -1 || currentI < bestI || (currentI == bestI && currentJ < bestJ)) {
                        bestI = currentI;
                        bestJ = currentJ;
                    }
                }
            }
        }

        if (bestI != -1) {
            return new int[]{bestI, bestJ};
        }
        return new int[]{-1, -1};
    }

    /**
     * Точка входа: читает длину массива, искомую сумму и сам массив
     * из stdin, печатает пару индексов.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите длину массива: ");
            int arrayLength = scanner.nextInt();

            System.out.print("Введите искомую сумму: ");
            long targetSum = scanner.nextLong();

            int[] numbers = new int[arrayLength];
            System.out.print("Введите " + arrayLength + " чисел через пробел: ");
            for (int index = 0; index < arrayLength; index++) {
                numbers[index] = scanner.nextInt();
            }

            Task12 solver = new Task12();
            int[] result = solver.findPair(numbers, targetSum);
            System.out.println(result[0] + " " + result[1]);
        }
    }
}