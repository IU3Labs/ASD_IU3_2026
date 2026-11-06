/*
 * Задача 38. Циклический сдвиг влево.
 * Вход: массив из n целых чисел и число k.
 * Выход: массив, циклически сдвинутый влево на k позиций.
 * Ограничения: 1 < n < 10^5; элементы имеют тип int.
 * Требование: O(n), O(1) доп. памяти (через тройной разворот).
 * Сложность: средняя
 */
package ru.bmstu.lab1.task_38;

import java.util.Scanner;
import ru.bmstu.lab1.common.ArrayReversal;
import ru.bmstu.lab1.common.ConsoleIo;

/** Задача 38. Циклический сдвиг влево: сдвинуть массив на k позиций тройным разворотом. */
public final class LeftRotation {
    private LeftRotation() {}

    /**
     * Сдвигает массив на месте; отрицательный сдвиг означает движение вправо.
     *
     * @param values массив, удовлетворяющий ограничениям задачи
     * @param distance число позиций сдвига влево
     */
    public static void solve(int[] values, int distance) {
        int shift = Math.floorMod(distance, values.length);
        if (shift == 0) {
            return;
        }
        // Для частей A и B: reverse(reverse(A) + reverse(B)) = B + A.
        ArrayReversal.reverse(values, 0, shift);
        ArrayReversal.reverse(values, shift, values.length);
        ArrayReversal.reverse(values, 0, values.length);
    }

    /**
     * Запрашивает входные данные и выводит результат или сообщение об ошибке.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] values = ConsoleIo.readArray(scanner);
            solve(values, ConsoleIo.readInt(scanner, "Введите k"));
            System.out.println(ConsoleIo.format(values));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка: " + exception.getMessage());
        }
    }
}
