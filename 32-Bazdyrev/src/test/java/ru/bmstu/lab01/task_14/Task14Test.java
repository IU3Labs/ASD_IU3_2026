package ru.bmstu.lab01.task_14;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task14Test {

    /**
     * Вспомогательный метод для имитации ввода с клавиатуры и перехвата вывода в консоль.
     */
    private String runSolve(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task14.solve(in, new PrintStream(out));
        return out.toString().trim();
    }

    // Обычная проверка: рандомный массив сортируется по невозрастанию
    @Test
    void should_sort_array_in_descending_order() {
        String input = "6\n3 1 4 1 5 9";
        String expected = "9 5 4 3 1 1";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Лучший случай: массив уже отсортирован верно
    @Test
    void should_handle_already_sorted_array() {
        String input = "5\n5 4 3 2 1";
        String expected = "5 4 3 2 1";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Худший случай: массив отсортирован по неубыванию
    @Test
    void should_sort_reverse_sorted_array() {
        String input = "5\n1 2 3 4 5";
        String expected = "5 4 3 2 1";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка корректной работы с отрицательными знаками и одинаковыми элементами
    @Test
    void should_handle_array_with_negative_numbers() {
        String input = "5\n-5 2 -10 0 2";
        String expected = "2 2 0 -5 -10";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Защита от "дурака": пустой ввод не должен приводить к ошибке
    @Test
    void should_handle_empty_input_gracefully() {
        String input = "  \n  ";
        String expected = "";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }
}