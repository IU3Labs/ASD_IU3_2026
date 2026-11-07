package ru.bmstu.lab1.task_21;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 21. Найти k-й наименьший элемент массива.
 * Массив сортируется по неубыванию, затем выбирается элемент k - 1.
 * Используется пирамидальная сортировка.
 * Сложность по времени — O(n log n).
 * Память для массива — O(n), дополнительная память сортировки — O(1).
 */
public class Main {

    /** Запускает программу через стандартные потоки. */
    public static void main(String[] args) {
        solve(new Scanner(System.in), System.out);
    }

    /**
     * Читает n, массив из n чисел и число k (1 ≤ k ≤ n), затем выводит k-й наименьший элемент.
     * После сортировки он стоит на позиции k - 1, так как индексы идут с нуля.
     */
    static void solve(Scanner input, PrintStream output) {
        int n = input.nextInt();
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }

        int k = input.nextInt();

        sort(values);
        output.println(values[k - 1]);
    }

    /** Сортирует массив по неубыванию пирамидальным методом (на месте, без доп. массива). */
    private static void sort(int[] values) {
        // Этап 1: строим кучу. Вторая половина массива — листья, у них нет детей, и они уже
        // корректны, поэтому идём от последнего родителя (n / 2 - 1) к корню, опуская элементы вниз.
        for (int root = values.length / 2 - 1; root >= 0; root--) {
            siftDown(values, root, values.length);
        }

        // Этап 2: end — последняя ячейка кучи, всё правее неё уже отсортировано.
        for (int end = values.length - 1; end > 0; end--) {
            // Максимум из корня уходит на своё место в конец кучи, а бывший последний элемент
            // становится корнем.
            int temporary = values[0];
            values[0] = values[end];
            values[end] = temporary;

            // Куча стала короче на один элемент, новый корень мог нарушить правило — чиним.
            siftDown(values, 0, end);
        }
    }

    /**
     * Опускает элемент root вниз, пока он меньше своих детей. Предполагается, что поддеревья
     * под root уже являются кучами, а правило мог нарушить только сам root.
     *
     * @param values массив, в начале которого лежит куча
     * @param root   индекс опускаемого элемента
     * @param size   размер кучи: элементы с индексами size и больше в неё не входят
     */
    private static void siftDown(int[] values, int root, int size) {
        while (root * 2 + 1 < size) {
            int child = root * 2 + 1;

            // Поднимать нужно большего из детей (правого может не быть).
            if (child + 1 < size && values[child + 1] > values[child]) {
                child++;
            }

            // Родитель не меньше детей — куча уже корректна, дальше смотреть не нужно.
            if (values[root] >= values[child]) {
                break;
            }

            int temporary = values[root];
            values[root] = values[child];
            values[child] = temporary;

            // Продолжаем с новой позиции элемента; каждый шаг уходит на уровень глубже (≈ log n шагов).
            root = child;
        }
    }
}
