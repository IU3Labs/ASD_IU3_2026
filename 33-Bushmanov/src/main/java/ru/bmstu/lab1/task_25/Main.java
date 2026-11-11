package ru.bmstu.lab1.task_25;
import java.util.Scanner;

/**
 * Задача 25. Вычисляет сумму целых чисел.
 * Числа суммируются при чтении без хранения массива.
 * Для суммы используется тип long.
 */
public class Main {
    /**
     * Считывает количество чисел, затем сами числа.
     * Выводит сумму всех прочитанных элементов.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); //Считывание размеров массива
        long sum = 0; //Создание переменной суммы

        for (int i = 0; i < n; i++) {
            sum += sc.nextInt();//Считывание и одновременное суммирование элементов массива
        }

        System.out.println(sum);//Вывод суммы элементов
        sc.close();
    }
}
