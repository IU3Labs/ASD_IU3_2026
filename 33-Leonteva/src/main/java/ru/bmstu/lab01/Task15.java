/*Задача 15. Пузырьковая сортировка с подсчётом обменов
Вход: массив из (n) целых чисел.
Выход: отсортированный массив и количество обменов, выполненных в процессе пузырьковой сортировки.
Требование: реализовать пузырьковую сортировку с досрочным выходом, если массив уже отсортирован.
Сложность: базовая*/

package ru.bmstu.lab01;


//Сортирует массив пузырьком (по неубыванию) с досрочным выходом; считает обмены.
public final class Task15 {

    private Task15() {
        // утилитарный класс
    }

    public static Result bubbleSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        int swaps = 0;
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return new Result(array, swaps);
    }

    //Результат сортировки: отсортированный массив и число обменов.
    public static final class Result {

        private final int[] sorted;
        private final int swaps;

        public Result(int[] sorted, int swaps) {
            this.sorted = sorted;
            this.swaps = swaps;
        }

        public int[] sorted() {
            return sorted;
        }

        public int swaps() {
            return swaps;
        }
    }
}