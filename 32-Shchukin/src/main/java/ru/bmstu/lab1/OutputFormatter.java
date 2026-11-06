package ru.bmstu.lab1;

/** Форматирует массивы для вывода в стандартный поток. */
public final class OutputFormatter {
    private OutputFormatter() {
    }

    /** Объединяет элементы массива пробелами; для пустого массива возвращает пустую строку. */
    public static String format(int[] values) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            result.append(values[i]);
        }
        return result.toString();
    }
}
