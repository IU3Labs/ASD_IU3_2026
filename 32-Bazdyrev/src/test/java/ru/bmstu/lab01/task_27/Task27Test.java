package ru.bmstu.lab01.task_27;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task27Test {

    private String runSolve(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task27.solve(in, new PrintStream(out));
        return out.toString().trim();
    }

    // Обычная проверка
    @Test
    void should_calculate_sum_of_squares() {
        String input = "3\n1 2 3";
        String expected = "14";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка отрицательных чисел и нуля
    @Test
    void should_handle_negatives_and_zeroes() {
        String input = "3\n-2 0 -3";
        String expected = "13";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка защиты от переполнения (вместо Int - long) при возведении в квадрат
    @Test
    void should_prevent_overflow_when_squaring_large_numbers() {
        // 100000^2 + 100000^2 = 10000000000 + 10000000000 = 20000000000
        String input = "2\n100000 100000";
        String expected = "20000000000";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }
}