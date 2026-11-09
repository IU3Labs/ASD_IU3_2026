package ru.bmstu.lab01.task_15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class Task15Test {

    /**
     * Вспомогательный метод для имитации ввода с клавиатуры и перехвата вывода в консоль.
     */
    private String runSolve(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task15.solve(in, new PrintStream(out));
        return out.toString().trim();
    }

    // Обычная проверка
    @Test
    void should_sort_array_and_count_swaps() {
        String input = "5\n4 2 5 1 3";
        // Массив: 1 2 3 4 5, количество обменов: 6
        String expected = "1 2 3 4 5" + System.lineSeparator() + "6";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Лучший случай: массив уже отсортирован (счетчик обменов должен быть 0)
    @Test
    void should_handle_already_sorted_array_with_zero_swaps() {
        String input = "5\n1 2 3 4 5";
        String expected = "1 2 3 4 5" + System.lineSeparator() + "0";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Худший случай: массив отсортирован по убыванию (максимальное число обменов)
    @Test
    void should_sort_reverse_sorted_array_with_max_swaps() {
        String input = "5\n5 4 3 2 1";
        String expected = "1 2 3 4 5" + System.lineSeparator() + "10";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка корректной работы с отрицательными знаками и одинаковыми элементами
    @Test
    void should_handle_array_with_negative_numbers_and_duplicates() {
        String input = "6\n-1 0 -1 5 5 -10";
        // Массив: -10 -1 -1 0 5 5, количество инверсий: 6
        String expected = "-10 -1 -1 0 5 5" + System.lineSeparator() + "6";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка защиты от "дурака"
    @Test
    void should_handle_empty_input_gracefully() {
        String input = "  \n  ";
        String expected = "";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }
}