package ru.bmstu.lab1.task19;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayIo;

/**
 * Задача 19. Объединение двух отсортированных массивов.
 * Ввод: число n и n чисел первого массива, затем число m и m чисел второго.
 * Оба массива отсортированы по неубыванию.
 * Вывод: элементы объединённого отсортированного массива через пробел.
 */
public class Main {

    private Main() {
    }

    /**
     * Читает два отсортированных массива с клавиатуры и выводит результат их слияния.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] first = ArrayIo.readArray(scanner);
        int[] second = ArrayIo.readArray(scanner);
        System.out.println(ArrayIo.toLine(SortedMerger.merge(first, second)));
    }
}
