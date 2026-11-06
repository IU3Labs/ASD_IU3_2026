package ru.bmstu.lab1.task_12;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ru.bmstu.lab1.ConsoleInput;

/** Задача 12. Найти пару i < j с суммой s, минимальную сначала по i, затем по j. */
public final class Main {
    private Main() {
    }

    /** Решает задачу для массива по ограничениям условия, не изменяя его. */
    public static int[] solve(int[] values, long s) {
        Map<Integer, Integer> firstIndexOnRight = new HashMap<>();
        int[] result = {-1, -1};
        for (int i = values.length - 1; i >= 0; i--) {
            long complement = s - values[i];
            if (complement >= Integer.MIN_VALUE && complement <= Integer.MAX_VALUE) {
                Integer j = firstIndexOnRight.get((int) complement);
                if (j != null) {
                    result[0] = i;
                    result[1] = j;
                }
            }
            // Справа сохраняется наименьший j. Обход справа налево минимизирует i.
            firstIndexOnRight.put(values[i], i);
        }
        return result;
    }

    /** Читает n, массив и при необходимости дополнительное число; печатает ответ. */
    public static void main(String[] args) throws IOException {
        ConsoleInput input = new ConsoleInput();
        int[] values = input.readArray();
        System.out.println(ConsoleInput.format(solve(values, input.readLong())));
    }
}
