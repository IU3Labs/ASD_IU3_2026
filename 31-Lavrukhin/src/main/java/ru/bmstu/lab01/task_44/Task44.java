/*
 * Задача 44. Чередование знаков.
 * Вход: массив из n целых чисел, содержащий равное число положительных и отрицательных элементов
 * (нулей нет).
 * Выход: массив, в котором положительные и отрицательные элементы чередуются, начиная с положительного.
 * Относительный порядок внутри положительных и отрицательных сохранить.
 */
package ru.bmstu.lab01.task_44;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Перестановка массива так, чтобы знаки чередовались: +, -, +, -, ...
 * Положительные и отрицательные числа сначала раскладываются в два отдельных массива
 * (так сохраняется их порядок), потом по очереди записываются в ответ.
 */
public class Task44 {

    /**
     * Читает массив (n, затем n чисел) и печатает массив с чередующимися знаками.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = ArrayUtils.readArray(scanner);
        System.out.println(ArrayUtils.toLine(alternate(array)));
    }

    /**
     * Возвращает новый массив с чередующимися знаками, начиная с положительного. Исходный массив не меняется.
     *
     * @param array массив без нулей с одинаковым количеством положительных и отрицательных чисел
     * @return переставленный массив
     * @throws IllegalArgumentException если в массиве есть ноль или количество + и - разное
     */
    public static int[] alternate(int[] array) {
        int half = array.length / 2;
        int[] positives = new int[half];
        int[] negatives = new int[half];
        int p = 0;
        int q = 0;
        for (int value : array) {
            if (value == 0) {
                throw new IllegalArgumentException("В массиве не должно быть нулей");
            }
            // в каждом из массивов место только для половины чисел: если какого-то знака
            // больше половины (или длина нечётная), для очередного числа места не найдётся
            if (value > 0 && p < half) {
                positives[p++] = value;
            } else if (value < 0 && q < half) {
                negatives[q++] = value;
            } else {
                throw new IllegalArgumentException("Положительных и отрицательных чисел должно быть поровну");
            }
        }

        int[] result = new int[array.length];
        for (int i = 0; i < half; i++) {
            result[2 * i] = positives[i];
            result[2 * i + 1] = negatives[i];
        }
        return result;
    }
}
