package ru.bmstu.lab02;

/**
 * Задача 3D: минимум в скользящем окне фиксированного размера.
 *
 * Идея: в min-heap лежат пары (значение, индекс). Устаревшие элементы (вышедшие за левую границу окна) не удаляются
 * сразу: куча не умеет искать по значению быстрее, чем за O(n). Вместо этого они удаляются лениво, когда оказываются
 * на вершине. Если на вершине элемент внутри окна, он и есть минимум окна, потому что все остальные элементы кучи
 * не меньше него.
 *
 * Время O(n log n): каждый элемент добавляется один раз и удаляется не более одного раза, каждое действие O(log n).
 * Память O(n): в худшем случае (массив убывает) устаревшие элементы остаются в куче под новыми.
 */
public final class SlidingMinimum {

    private SlidingMinimum() {
    }

    /**
     * Возвращает минимумы всех окон размера k, слева направо; длина результата равна arr.length - k + 1.
     * Исходный массив не изменяется.
     *
     * @throws NullPointerException     если arr == null
     * @throws IllegalArgumentException если k < 1 или k больше длины массива
     */
    public static int[] slidingMin(int[] arr, int k) {
        if (arr == null) {
            throw new NullPointerException("Array must not be null");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Window size must be in [1, " + arr.length + "]: " + k);
        }

        int[] result = new int[arr.length - k + 1];
        PriorityQueueCustom<Entry> heap = new PriorityQueueCustom<>();

        for (int i = 0; i < arr.length; i++) {
            heap.add(new Entry(arr[i], i));
            int windowStart = i - k + 1;
            while (heap.peek().index() < windowStart) {
                heap.poll(); // элемент вышел за левую границу окна
            }
            if (windowStart >= 0) {
                result[windowStart] = heap.peek().value();
            }
        }
        return result;
    }

    /** Элемент кучи: значение и его позиция в массиве. Порядок: по значению, при равенстве по индексу. */
    private record Entry(int value, int index) implements Comparable<Entry> {
        @Override
        public int compareTo(Entry other) {
            int byValue = Integer.compare(value, other.value);
            return byValue != 0 ? byValue : Integer.compare(index, other.index);
        }
    }
}
