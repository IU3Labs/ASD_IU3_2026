/*Задача 24. Чётно-нечётная сортировка
Вход: массив из (n) целых чисел.
Выход: массив, в котором все чётные элементы отсортированы по возрастанию и идут первыми,
а нечётные отсортированы по убыванию и идут после.
Сложность: средняя*/

package ru.bmstu.lab01;


//Сортирует чётные по возрастанию (в начале), нечётные — по убыванию (в конце).
public final class LabaTask24 {

    private LabaTask24() {
        // утилитарный класс
    }

    public static void evenOddSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        int left = 0;
        int right = array.length - 1;
        // Разделяем массив: слева — чётные, справа — нечётные.
        while (left < right) {
            while (left < right && array[left] % 2 == 0) {
                left++;
            }
            while (left < right && array[right] % 2 != 0) {
                right--;
            }
            if (left < right) {
                int tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
                left++;
                right--;
            }
        }

        // Сортируем чётную часть (слева) по возрастанию.
        int evenCount = 0;
        while (evenCount < array.length && array[evenCount] % 2 == 0) {
            evenCount++;
        }
        insertionSortAscending(array, 0, evenCount);

        // Сортируем нечётную часть (справа) по убыванию.
        insertionSortDescending(array, evenCount, array.length);
    }

    //Сортировка вставками по возрастанию на полуинтервале [from, to).
    private static void insertionSortAscending(int[] array, int from, int to) {
        for (int i = from + 1; i < to; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= from && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    //Сортировка вставками по убыванию на полуинтервале [from, to).
    private static void insertionSortDescending(int[] array, int from, int to) {
        for (int i = from + 1; i < to; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= from && array[j] < key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}