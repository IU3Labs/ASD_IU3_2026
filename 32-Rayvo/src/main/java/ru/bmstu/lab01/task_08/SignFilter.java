package ru.bmstu.lab01.task_08;

public class SignFilter {

    /**
     * Результат фильтрации: положительные и отрицательные элементы
     * в исходном порядке.
     *
     * @param positive положительные элементы
     * @param negative отрицательные элементы
     */
    public record FilterResult(int[] positive, int[] negative) {
    }

    /**
     * Разделяет массив на положительные и отрицательные элементы,
     * сохраняя исходный порядок. Нули отбрасываются.
     *
     * @param array входной массив целых чисел
     * @return пара массивов {@code (positive, negative)}
     */
    public static FilterResult filterBySign(int[] array) {

        // Первый проход: подсчёт положительных и отрицательных
        int positiveCount = 0;
        int negativeCount = 0;
        for (int value : array) {
            if (value > 0) {
                positiveCount++;
            } else if (value < 0) {
                negativeCount++;
            }
        }

        // Второй проход: заполнение массивов в исходном порядке
        int[] positive = new int[positiveCount];
        int[] negative = new int[negativeCount];
        int pi = 0;
        int ni = 0;
        for (int value : array) {
            if (value > 0) {
                positive[pi++] = value;
            } else if (value < 0) {
                negative[ni++] = value;
            }
        }

        return new FilterResult(positive, negative);
    }
}