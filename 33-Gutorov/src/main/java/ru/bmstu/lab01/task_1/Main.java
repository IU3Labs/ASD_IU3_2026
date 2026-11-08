package ru.bmstu.lab01.task_1;

import java.util.Scanner; // создали инструмент для чтения чисел

/* Найти минимальный и максимальный элементы массива. */
public class Main {

    /* Читаем числа и выводим минимум и максимум. */
    static void main() {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int min = input.nextInt();
        int max = min;

        for (int i = 1; i < n; i++) { // циклом проходим по массиву
            int x = input.nextInt();
            if (x < min) {
                min = x;
            }
            if (x > max) {
                max = x;
            }
        }

        System.out.println(min + " " + max); //выводим результат
        input.close(); //закрываем инструмент для чтения
    }
}