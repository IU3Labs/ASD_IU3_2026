package ru.bmstu.lab1.task_15;
import java.util.Scanner;
/**
 * Задача 15. Сортирует массив пузырьковым методом
 * и подсчитывает количество обменов.
 * Сортировка завершается досрочно, если обменов за проход не было.
 */
public class Main {
    /**
     * Считывает массив и запускает сортировку.
     * Выводит отсортированный массив, затем на следующей
     * строке выводит количество обменов.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();// Считывание размеров массива
        int [] arr = new int[n];
        for(int i=0; i<n; i++){
            arr[i] = sc.nextInt();// Считывание элементов массива
        }
        long counter = bubblesort(arr);// Вызов метода пузырьковой сортировки
        printarr(arr);
        System.out.println(counter);

        sc.close();
    }
    /**
     * Сортирует исходный массив по неубыванию
     * и подсчитывает обмены соседних элементов.
     * Если за проход обменов не было, завершает сортировку.
     *
     * @param arr массив, который будет изменён при сортировке
     * @return количество выполненных обменов
     */
    public static long bubblesort(int[] arr){
        long counter =0;// Счетчик переходов
        for(int i=0; i<arr.length-1; i++){
            boolean flag = false;// Флаг предварительной сортировки
            for(int j=0; j<arr.length-1-i; j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flag = true;
                    counter++;
                }
            }
            if(!flag){
                break;
            }
        }
        return counter;
    }
    /**
     * Выводит элементы массива через пробел
     * и завершает вывод переводом строки.
     *
     * @param arr массив для вывода
     */
    public static void printarr(int[] arr){
        for(int x: arr){
            System.out.print(x+" ");
        }
        System.out.println();
    }
}
