package ru.bmstu.lab01.task_25;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task25Test {

    private String runSolve(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task25.solve(in, new PrintStream(out));
        return out.toString().trim();
    }

    // Обычная проверка
    @Test
    void should_calculate_sum_of_positive_numbers() {
        String input = "5\n1 2 3 4 5";
        String expected = "15";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка с нулями и отрицательными числами
    @Test
    void should_handle_negatives_and_zeroes() {
        String input = "6\n-10 5 0 -6 10 0";
        String expected = "-1";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка защиты от переполнения (не Int, а long)
    @Test
    void should_prevent_integer_overflow() {
        // Два числа по 2 миллиарда. В сумме 4 миллиарда, что больше Int
        String input = "2\n2000000000 2000000000";
        String expected = "4000000000";
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