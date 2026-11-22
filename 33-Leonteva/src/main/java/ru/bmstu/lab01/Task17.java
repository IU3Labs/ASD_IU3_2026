/*Задача 17. Сортировка по сумме цифр
Вход: массив из (n) неотрицательных целых чисел.
Выход: массив, отсортированный по возрастанию суммы цифр каждого числа.
При равных суммах — сохранить исходный порядок.
Сложность: средняя*/

package ru.bmstu.lab01;


//Сортирует массив по возрастанию суммы цифр; при равных суммах сохраняет исходный порядок.
public final class Task17 {

    private Task17() {
        // утилитарный класс
    }

    public static void sortByDigitSum(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        // Сортировка вставками с устойчивым сдвигом: строгое сравнение > сохраняет порядок равных.
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int keySum = digitSum(key);
            int j = i - 1;
            while (j >= 0 && digitSum(array[j]) > keySum) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    //Сумма цифр неотрицательного числа.
    private static int digitSum(int value) {
        int sum = 0;
        int v = value;
        while (v > 0) {
            sum += v % 10;
            v /= 10;
        }
        return sum;
    }
}