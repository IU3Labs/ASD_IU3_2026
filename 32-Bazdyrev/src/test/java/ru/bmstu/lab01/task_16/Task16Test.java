package ru.bmstu.lab01.task_16;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task16Test {

    private String runSolve(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task16.solve(in, new PrintStream(out));
        return out.toString().trim();
    }

    // Обычная проверка: массив с разными по модулю числами
    @Test
    void should_sort_array_by_absolute_value() {
        String input = "6\n-4 1 5 -1 0 4";
        String expected = "0 -1 1 -4 4 5";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка того, что  при равных модулях отрицательное число идет первым
    @Test
    void should_handle_equal_absolute_values_putting_negative_first() {
        String input = "6\n5 -5 2 -2 5 -5";
        String expected = "-2 2 -5 -5 5 5";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Случай, когда массив отсортирован
    @Test
    void should_handle_already_sorted_by_abs_array() {
        String input = "5\n0 -1 1 -2 2";
        String expected = "0 -1 1 -2 2";
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