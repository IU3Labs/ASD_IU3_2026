package ru.bmstu.lab1.task_2;
import java.util.Scanner;
/**
 * Задача 2. Находит индекс первого вхождения
 * заданного числа в целочисленный массив.
 */
public class Main {
    /**
     * Считывает массив и искомое число.
     * Выводит индекс первого совпадения или -1,
     * если число отсутствует. Индексация начинается с нуля.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n  = sc.nextInt();// Размер массива
        int[] arr = new int[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt(); //Считывание элементов массива
        }
        int x = sc.nextInt();// Считывание элемента x
        int xindex = -1;
        for(int i=0; i<n; i++){
            if(arr[i]==x){
                xindex =i;
                break;
            }
        }
        System.out.println(xindex);
    }
}
