package ru.bmstu.lab1.task_42;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 42. Вставка элемента в отсортированный массив.
 *
 * <p>Вход: {@code n}, {@code n} чисел по неубыванию, затем {@code x}. Выход: массив длины {@code n + 1}
 * с {@code x} на правильной позиции.
 *
 * <p>Позиция ищется бинарным поиском (верхняя граница — первый элемент, строго больший {@code x}) за O(log n).
 * Копирование в новый массив занимает O(n), поэтому итог O(n) по времени и O(n) памяти под результат.
 */
public final class InsertSorted {

    private InsertSorted() {
    }

    /**
     * Возвращает новый массив с вставленным элементом.
     *
     * @param sorted массив по неубыванию
     * @param value  вставляемое значение
     * @return массив длины {@code n + 1} по неубыванию
     */
    public static int[] insert(int[] sorted, int value) {
        int position = upperBound(sorted, value);
        int[] result = new int[sorted.length + 1];
        System.arraycopy(sorted, 0, result, 0, position);
        result[position] = value;
        System.arraycopy(sorted, position, result, position + 1, sorted.length - position);
        return result;
    }

    /**
     * Бинарный поиск первого индекса, где элемент строго больше {@code value}.
     *
     * <p>Инвариант: все элементы левее {@code low} не больше {@code value}, а начиная с {@code high} — больше.
     *
     * @param sorted массив по неубыванию
     * @param value  искомое значение
     * @return индекс в диапазоне {@code [0, n]}
     */
    public static int upperBound(int[] sorted, int value) {
        int low = 0;
        int high = sorted.length;
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (sorted[mid] <= value) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    /**
     * Точка входа: читает массив и {@code x}, печатает новый массив.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> {
            int[] sorted = in.nextIntArray(in.nextInt());
            out.println(ConsoleIo.join(insert(sorted, in.nextInt())));
        });
    }
}
