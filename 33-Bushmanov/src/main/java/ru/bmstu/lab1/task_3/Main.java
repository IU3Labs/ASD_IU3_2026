package ru.bmstu.lab1.task_3;
import java.util.Scanner;
/**
 * Задача 3. Подсчитывает количество элементов массива,
 * равных заданному числу.
 */
public class Main {
    /**
     * Считывает массив и искомое число.
     * Выводит количество совпадений.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();// Размер массива
        int [] arr = new int[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt();// Считывание элементов массива
        }
        int x = sc.nextInt();// Считывание элемента x
        int counter = 0;//Счетчик
        for(int i=0; i<n; i++){
            if(arr[i]==x) counter++;
        }
        System.out.println(counter);
    }
}
