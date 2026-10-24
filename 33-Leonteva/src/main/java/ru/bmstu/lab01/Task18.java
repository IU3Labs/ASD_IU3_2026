/*Задача 18. Сортировка только чётных
Вход: массив из (n) целых чисел.
Выход: тот же массив, в котором все чётные элементы отсортированы
по неубыванию, а нечётные остались на своих местах.
Сложность: средняя*/

package ru.bmstu.lab01;


//Сортирует только чётные элементы по неубыванию; нечётные остаются на своих индексах.
public final class Task18 {

    private Task18() {
        // утилитарный класс
    }

    public static void sortEvenOnly(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        // Собираем значения чётных элементов.
        int evenCount = 0;
        for (int i = 0; i < array.length; i++) {
            if (isEven(array[i])) {
                evenCount++;
            }
        }
        int[] evens = new int[evenCount];
        int p = 0;
        for (int i = 0; i < array.length; i++) {
            if (isEven(array[i])) {
                evens[p++] = array[i];
            }
        }

        // Сортируем извлечённые чётные (вставками, без встроенных сортировок).
        insertionSort(evens);

        // Возвращаем отсортированные чётные на места чётных позиций в исходном массиве.
        p = 0;
        for (int i = 0; i < array.length; i++) {
            if (isEven(array[i])) {
                array[i] = evens[p++];
            }
        }
    }

    //Проверяет чётность (для отрицательных работает корректно: -4 чётное).
    private static boolean isEven(int value) {
        return value % 2 == 0;
    }

    //Сортировка вставками по неубыванию на месте.
    private static void insertionSort(int[] values) {
        for (int i = 1; i < values.length; i++) {
            int key = values[i];
            int j = i - 1;
            while (j >= 0 && values[j] > key) {
                values[j + 1] = values[j];
                j--;
            }
            values[j + 1] = key;
        }
    }
}