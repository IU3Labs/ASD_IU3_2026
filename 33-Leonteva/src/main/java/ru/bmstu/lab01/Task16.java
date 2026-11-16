/*Задача 16. Сортировка по модулю
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию модуля элементов.
При равных модулях первым идёт отрицательный элемент.
Сложность: средняя*/

package ru.bmstu.lab01;


//Сортирует массив по возрастанию модуля; при равных модулях отрицательное значение идёт раньше.
public final class Task16 {

    private Task16() {
        // утилитарный класс
    }

    public static void sortByAbs(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && compareByAbs(array[j], key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    //Сравнивает два числа по модулю; при равных модулях отрицательное считается меньшим.
    private static int compareByAbs(int a, int b) {
        long absA = Math.abs((long) a);
        long absB = Math.abs((long) b);
        if (absA != absB) {
            return Long.compare(absA, absB);
        }
        // При равных модулях отрицательный идёт раньше: -x < x.
        return Integer.compare(a, b);
    }
}