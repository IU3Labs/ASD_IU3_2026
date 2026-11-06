package ru.bmstu.lab01.task_04;

public class AllIndicesFinder {

    /**
     * Возвращает все индексы элементов массива, равных {@code x},
     * в порядке возрастания.
     *
     * <p>Если массив не содержит {@code x},
     * возвращается пустой массив.
     *
     * @param array входной массив целых чисел
     * @param x     искомое значение
     * @return массив индексов в порядке возрастания; пустой, если
     *         вхождений нет
     */
    public static int[] findAllIndices(int[] array, int x) {

        // Первый проход: считаем количество вхождений
        int count = 0;
        for (int value : array) {
            if (value == x) {
                count++;
            }
        }

        // Второй проход: заполняем массив индексов
        int[] indices = new int[count];
        int pos = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                indices[pos++] = i;
            }
        }

        return indices;
    }
}