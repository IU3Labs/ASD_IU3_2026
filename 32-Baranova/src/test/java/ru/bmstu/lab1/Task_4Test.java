package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_4Test {

    @Test
    void shouldReturnAllIndicesWhenElementAppearsMultipleTimes() {
        String input = "5\n10 20 10 40 10\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_4.main(new String[]{});

        // Ожидаем индексы через пробел
        assertEquals("0 2 4", out.toString().trim());
    }

    @Test
    void shouldReturnEmptyWhenElementDoesNotExist() {
        String input = "3\n1 2 3\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_4.main(new String[]{});

        assertEquals("", out.toString().trim());
    }
}