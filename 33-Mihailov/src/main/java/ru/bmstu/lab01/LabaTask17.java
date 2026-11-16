/*Задача 17. Сортировка по сумме цифр
Вход: массив из (n) неотрицательных целых чисел.
Выход: массив, отсортированный по возрастанию суммы цифр каждого числа.
При равных суммах — сохранить исходный порядок.
Сложность: средняя*/

package ru.bmstu.lab01;


//Сортирует массив по возрастанию суммы цифр (устойчиво: при равных суммах сохраняется исходный порядок).
public final class LabaTask17 {

    private LabaTask17() {
        // утилитарный класс
    }

    public static void sortByDigitSum(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        // Сортировка вставками — устойчивая, поэтому при равных суммах порядок сохраняется.
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

    //Возвращает сумму цифр неотрицательного числа.
    private static int digitSum(int value) {
        int sum = 0;
        int x = value;
        while (x > 0) {
            sum += x % 10;
            x /= 10;
        }
        return sum;
    }
}