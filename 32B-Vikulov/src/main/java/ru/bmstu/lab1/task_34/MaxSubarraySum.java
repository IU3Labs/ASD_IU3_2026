package ru.bmstu.lab1.task_34;

import ru.bmstu.lab1.common.ConsoleIo;

/**
 * Задача 34. Максимальная сумма подмассива.
 *
 * <p>Вход: {@code n}, затем {@code n} целых чисел. Выход: максимальная сумма непустого непрерывного подмассива.
 *
 * <p>Алгоритм Кадане: {@code bestEndingHere} — лучшая сумма подмассива, заканчивающегося в текущей позиции.
 * Её либо продолжают текущим элементом, либо начинают заново с него (если накопленная сумма отрицательна).
 * Ответ — максимум по всем позициям. Если все числа отрицательны, ответом будет наибольшее из них.
 * O(n) по времени, O(1) доп. памяти; суммы в {@code long}, чтобы не было переполнения.
 */
public final class MaxSubarraySum {

    private MaxSubarraySum() {
    }

    /**
     * Находит максимальную сумму непустого непрерывного подмассива.
     *
     * @param values непустой массив
     * @return максимальная сумма
     * @throws IllegalArgumentException если массив пуст
     */
    public static long solve(int[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        long bestEndingHere = values[0];
        long best = values[0];
        for (int i = 1; i < values.length; i++) {
            bestEndingHere = Math.max(values[i], bestEndingHere + values[i]);
            best = Math.max(best, bestEndingHere);
        }
        return best;
    }

    /**
     * Точка входа: читает массив, печатает максимальную сумму.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        ConsoleIo.run((in, out) -> out.println(solve(in.nextIntArray(in.nextInt()))));
    }
}
