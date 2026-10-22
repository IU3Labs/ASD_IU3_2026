/*
* Задача 2. Индекс первого вхождения
*Входные данные: массив из (n) целых чисел и число (x).
*Выходные данные: индекс первого элемента, равного (x). Если такого нет — вывести -1.
*Сложность: базовая
 */


package ru.bmstu.lab1.task_2;

import java.util.Scanner;

/**
 * Решение задачи 2: поиск индекса первого элемента, равного x.
 */
public class FirstIndex {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 99_999;
    /** Ответ, если элемент не найден. */
    private static final int NOT_FOUND = -1;

    /**
     * Читает n, n целых чисел и x, печатает индекс первого элемента массива, равного x.
     *
     * @param args не используются
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return; 
        }
        int n = scanner.nextInt();
        if (n < MIN_LENGTH || n > MAX_LENGTH) {
            System.out.println("Ошибка: некорректная длина массива");
            return;
        }

        // 2. Читаем сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
        }

        
        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int x = scanner.nextInt();

       
        int index = NOT_FOUND; 
        
        for (int i = 0; i < n; i++) {
            if (a[i] == x) {
                index = i; 
                break;     
            }
        }

        
        System.out.println(index);
        
        scanner.close();
    }
}

