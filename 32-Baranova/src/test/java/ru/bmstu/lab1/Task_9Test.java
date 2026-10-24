package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_9Test {

    @Test
    void shouldReturnYesWhenArrayIsSorted() {
        String input = "4\n1 2 2 5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_9.main(new String[]{});

        assertEquals("YES", out.toString().trim());
    }

    @Test
    void shouldReturnNoWhenArrayIsNotSorted() {
        String input = "4\n1 3 2 5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_9.main(new String[]{});

        assertEquals("NO", out.toString().trim());
    }
}