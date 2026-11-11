package ru.bmstu.lab1.task_9;
import java.util.Scanner;
/**
 * Задача 9. Проверяет, расположены ли элементы массива
 * в неубывающем порядке. Равные соседние элементы допускаются.
 */
public class Main {
    /**
     * Считывает массив и проверяет порядок элементов.
     * Выводит YES, если массив неубывающий, иначе NO.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();// Считывание размера массива
        int [] arr = new int[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt();// Считывание элементов массива
        }
        boolean flag = true;// Флаг отсортированности массива
        for(int i=1; i<n; i++){
            if(arr[i]<arr[i-1]){
                flag = false;
                break;
            }
        }
        String result  = (flag) ? "YES" : "NO";
        System.out.println(result);
        sc.close();
    }
}
