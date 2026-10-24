package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_8Test {

    @Test
    void shouldFilterPositivesAndNegativesIgnoringZeros() {
        String input = "5\n3 -1 0 5 -2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_8.main(new String[]{});

        assertEquals("3 5 -1 -2", out.toString().trim());
    }

    @Test
    void shouldHandleOnlyPositivesAndNegatives() {
        String input = "4\n1 2 -3 -4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_8.main(new String[]{});

        assertEquals("1 2 -3 -4", out.toString().trim());
    }
}