package ru.bmstu.lab01.task_18;

import java.util.Scanner;

/**
 * Класс для решения задачи 18: сортировка только чётных элементов.
 * <p>
 * Ключевая особенность: алгоритм находит все чётные числа, собирает их 
 * в отдельный массив, сортирует его быстрой сортировкой по невозрастанию 
 * и возвращает на исходные позиции. Нечётные числа остаются нетронутыми.
 * </p>
 * 
 * Условие задачи:
 * Вход: массив из (n) целых чисел.
 * Выход: тот же массив, в котором все чётные элементы отсортированы по невозрастанию, 
 * а нечётные остались на своих местах.
 * Сложность: средняя.
 */
public class Task18 {
    
     /**
     * Сортирует только чётные элементы массива по убыванию.
     * 
     * @param a исходный массив целых чисел
     */
    public void sortEvenDescending(int[] a) {
        int evenCount = 0;
        for (int num : a) {
            if (num % 2 == 0) evenCount++;
        }
        if (evenCount <= 1) return;

        int[] evens = new int[evenCount];
        int index = 0;
        for (int num : a) {
            if (num % 2 == 0) {
                evens[index++] = num;
            }
        }
        
        // Используем быструю сортировку (QuickSort) вместо пузырька, так как сложность n^2 не принимается
        quickSort(evens, 0, evens.length - 1);

        index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] % 2 == 0) {
                a[i] = evens[index++];
            }
        }
    }

    /**
     * Быстрая сортировка (QuickSort) по убыванию.
     */
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = arr[high];
            int i = (low - 1);

            for (int j = low; j < high; j++) {
                // Для убывания: если текущий элемент больше опорного
                if (arr[j] > pivot) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }

            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;

            int pi = i + 1;

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("Введите количество элементов:");
            if (!scanner.hasNextInt()) return;
            int n = scanner.nextInt();
            
            int[] arr = new int[n];
            System.out.println("Введите элементы массива:");
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }
            
            Task18 solver = new Task18();
            solver.sortEvenDescending(arr);
            
            System.out.println("Результат:");
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println();
            
        }
    }
}