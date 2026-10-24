package ru.bmstu.lab01.task_26;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task26Test {

    private String runSolve(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Task26.solve(in, new PrintStream(out));
        return out.toString().trim();
    }

    // Обычная проверка
    @Test
    void should_calculate_product_of_small_numbers() {
        String input = "4\n1 2 3 4";
        String expected = "24";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка с нулём
    @Test
    void should_return_zero_if_contains_zero() {
        String input = "5\n100 500 0 999 1";
        String expected = "0";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка отрицательных чисел и корректности модуля
    @Test
    void should_handle_negative_numbers_correctly() {
        // -1 * -1 * -1 = -1. По модулю 10^9+7 это должно быть 1000000006
        String input = "3\n-1 -1 -1";
        String expected = "1000000006";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }

    // Проверка больших чисел
    @Test
    void should_handle_large_numbers_with_modulo() {
        // 1000000000 * 1000000000.
        // Остаток при делении 10^9 = 7. 7 * 7 = 49.
        String input = "2\n1000000000 1000000000";
        String expected = "49";
        String actual = runSolve(input);
        assertEquals(expected, actual);
    }
}