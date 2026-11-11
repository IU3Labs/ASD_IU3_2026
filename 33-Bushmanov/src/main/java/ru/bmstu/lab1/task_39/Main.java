package ru.bmstu.lab1.task_39;
import java.util.Scanner;
/**
 * Задача 39. Выполняет циклический сдвиг массива вправо.
 * Величина сдвига может превышать длину массива.
 */
public class Main {
    /**
     * Считывает массив и величину сдвига.
     * Выводит элементы сдвинутого массива через пробел.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();// Считывание размера массива
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();// Считывание элементов массива
        }
        int k = sc.nextInt();// Считывание числа сдвигов
        changer(arr, k);// Вызов метода сдвига
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(arr[i]);
        }
        System.out.println();
    }
    /**
     * Циклически сдвигает элементы исходного массива вправо.
     * Полные обороты не изменяют результат.
     * Пустой массив остаётся без изменений.
     * Отрицательное значение k соответствует сдвигу влево.
     *
     * @param a массив, элементы которого будут переставлены
     * @param k величина сдвига вправо
     */
    public static void changer(int[] a, int k) {
        int len = a.length;
        //Пустой массив не сдвигаем
        if (len == 0) {
            return;
        }
        int step = Math.floorMod(k, len); // Полные обороты не меняют массив, оставляем только остаток
        if (step == 0) {
            return;
        }
        int[] shift = new int[len]; // Временно сохраняем элементы на новых местах

        for (int i = 0; i < len; i++) {
            shift[(i + step) % len] = a[i]; // Остаток от деления возвращает индекс в начало массива
        }
        for (int i = 0; i < len; i++) {
            a[i] = shift[i]; // Копируем результат обратно в исходный массив
        }
    }
}
