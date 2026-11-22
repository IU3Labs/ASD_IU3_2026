/*Задача 9. Проверка отсортированности
Вход: массив из (n) целых чисел.
Выход: YES, если массив неубывающий; NO иначе.
Сложность: базовая*/

package ru.bmstu.lab01;


//Проверяет, является ли массив неубывающим (каждый следующий элемент не меньше предыдущего).
public final class Task9 {

    private static final String YES = "YES";
    private static final String NO = "NO";

    private Task9() {
        // утилитарный класс
    }

    public static String isNonDecreasing(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return NO;
            }
        }
        return YES;
    }
}