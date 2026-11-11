package ru.bmstu.lab1.task_5;
import java.util.Scanner;
/**
 * Задача 5. Вычисляет сумму элементов массива
 * с индексами 0, 2, 4 и так далее.
 * Для хранения суммы используется тип long.
 */
public class Main {
    /**
     * Считывает массив и выводит сумму элементов
     * с чётными индексами.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();// Считывание размера массива
        int [] arr = new int[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt();// Считывание элементов массива
        }
        long sum =0;
        for(int i=0; i<n; i+=2){
            sum +=arr[i];//Суммируем элементы только с четными индексами
        }
        System.out.println(sum);
        sc.close();
    }
}
