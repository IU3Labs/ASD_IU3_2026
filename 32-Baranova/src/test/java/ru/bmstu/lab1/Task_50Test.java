package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_50Test {

    @Test
    void shouldReturnColumnSumsWhenMatrixIsGiven() {
        String input = "2 3\n1 2 3\n4 5 6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_50.main(new String[]{});

        assertEquals("5 7 9", out.toString().trim());
    }

    @Test
    void shouldHandleNegativeNumbersInMatrixColumns() {
        String input = "2 2\n1 -2\n-3 4\n"; // Столбцы: 1+(-3) = -2, (-2)+4 = 2
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_50.main(new String[]{});

        assertEquals("-2 2", out.toString().trim());
    }
}