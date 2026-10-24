package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_49Test {

    @Test
    void shouldReturnRowSumsWhenMatrixIsGiven() {
        String input = "2 3\n1 2 3\n4 5 6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_49.main(new String[]{});

        assertEquals("6 15", out.toString().trim());
    }

    @Test
    void shouldHandleNegativeNumbersInMatrix() {
        String input = "2 2\n-1 2\n3 -4\n"; // Суммы: 1 и -1
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_49.main(new String[]{});

        assertEquals("1 -1", out.toString().trim());
    }
}