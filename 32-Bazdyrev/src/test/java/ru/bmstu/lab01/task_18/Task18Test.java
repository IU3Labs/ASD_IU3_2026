package ru.bmstu.lab01.task_18;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task18Test {

    private String runSolve(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task18.solve(in, new PrintStream(out));
        return out.toString().trim();
    }

    // Обычная проверка
    @Test
    void should_sort_only_even_numbers() {
        // Чётные: 8, 4, 2 -> после сортировки 2, 4, 8
        // Нечётные: 7, 1, 3 остаются на своих местах
        String input = "6\n7 8 4 1 2 3";
        String expected = "7 2 4 1 8 3";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка, если чётных элементов вообще нет
    @Test
    void should_handle_array_without_even_numbers() {
        String input = "3\n1 3 5";
        String expected = "1 3 5";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка, если все элементы чётные
    @Test
    void should_handle_array_with_only_even_numbers() {
        String input = "4\n10 4 6 2";
        String expected = "2 4 6 10";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Отрицательные числа и ноль
    @Test
    void should_handle_negatives_and_zeroes() {
        // Чётные: -2, 0, 4, -4 -> после сортировки -4, -2, 0, 4
        String input = "5\n-3 -2 0 4 -4";
        String expected = "-3 -4 -2 0 4";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Защита от "дурака"
    @Test
    void should_handle_empty_input_gracefully() {
        String input = "  \n  ";
        String expected = "";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }
}