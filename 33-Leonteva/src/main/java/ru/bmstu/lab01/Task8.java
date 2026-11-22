/*Задача 8. Фильтрация по знаку
Вход: массив из (n) целых чисел.
Выход: два массива — из положительных и из отрицательных элементов,
в исходном порядке. Нули не включать ни в один.
Сложность: базовая*/

package ru.bmstu.lab01;

import java.util.ArrayList;
import java.util.List;


//Разделяет массив на положительные и отрицательные элементы с сохранением порядка.
public final class Task8 {

    private Task8() {
        // утилитарный класс
    }

    public static Result splitBySign(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        List<Integer> positives = new ArrayList<>();
        List<Integer> negatives = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            if (value > 0) {
                positives.add(value);
            } else if (value < 0) {
                negatives.add(value);
            }
        }
        return new Result(positives, negatives);
    }

    //Результат разбиения: положительные и отрицательные элементы в исходном порядке.
    public static final class Result {

        private final List<Integer> positives;
        private final List<Integer> negatives;

        public Result(List<Integer> positives, List<Integer> negatives) {
            this.positives = positives;
            this.negatives = negatives;
        }

        public List<Integer> positives() {
            return positives;
        }

        public List<Integer> negatives() {
            return negatives;
        }
    }
}