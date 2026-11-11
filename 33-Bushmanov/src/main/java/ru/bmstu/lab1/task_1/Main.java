package ru.bmstu.lab1.task_1;
import java.util.Scanner;
/**
 * Задача 1. Находит минимальный и максимальный
 * элементы целочисленного массива.
 */
public class Main {
    /**
     * Считывает размер и элементы массива.
     * Выводит минимум и максимум через пробел.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //Размер массива
        int [] arr = new int[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt(); //Считывание элементов массива
        }
        int max = arr[0], min = arr[0];
        for(int i=0; i<n; i++){
            if(arr[i]>max){
                max=arr[i];
            } else if (arr[i]<min) {
                min=arr[i];
            }
        }
        System.out.print(min+ " "+ max);
        System.out.println();
    }
}