
package ru.bmstu.lab2;

/**
 * Поиск K наибольших элементов массива
 * с использованием собственной очереди с приоритетом.
 */
public final class TopK {

    private TopK() {
        // Служебный класс не требует создания объектов.
    }

    /**
     * Находит K наибольших элементов массива.
     *
     * @param numbers исходный массив
     * @param k количество искомых элементов
     * @return K наибольших элементов по убыванию
     * @throws IllegalArgumentException если k выходит за допустимые границы
     * @throws NullPointerException если массив равен null
     */
    public static int[] topK(int[] numbers, int k) {
        if (numbers == null) {
            throw new NullPointerException("Массив не может быть null");
        }

        if (k < 0 || k > numbers.length) {
            throw new IllegalArgumentException("Недопустимое значение k");
        }

        if (k == 0) {
            return new int[0];
        }

        PriorityQueueCustom<Integer> heap = new PriorityQueueCustom<>();

        for (int number : numbers) {
            if (heap.size() < k) {
                heap.add(number);
            } else if (number > heap.peek()) {
                heap.poll();
                heap.add(number);
            }
        }

        int[] result = new int[k];

        for (int i = k - 1; i >= 0; i--) {
            result[i] = heap.poll();
        }

        return result;
    }
}
