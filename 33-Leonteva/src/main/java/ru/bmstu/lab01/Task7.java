/*Задача 7. Второй максимум
Вход: массив из (n) целых чисел. Все элементы различны.
Выход: второй по величине элемент массива.
Сложность: базовая*/

package ru.bmstu.lab01;


//Находит второй по величине (различный) элемент массива за один проход.
public final class Task7 {

    /** По условию задачи для второго максимума нужно минимум два элемента. */
    private static final int MIN_SIZE = 2;

    private Task7() {
        // утилитарный класс
    }

    public static int secondMax(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (array.length < MIN_SIZE) {
            throw new IllegalArgumentException("array must contain at least two elements");
        }

        int max = Math.max(array[0], array[1]);
        int second = Math.min(array[0], array[1]);

        for (int i = 2; i < array.length; i++) {
            int value = array[i];
            if (value > max) {
                second = max;
                max = value;
            } else if (value > second) {
                second = value;
            }
        }
        return second;
    }
}