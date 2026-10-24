package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_1Test {

    @Test
    void shouldReturnMinAndMaxWhenArrayIsGiven() {
        String input = "4\n10 3 20 5\n"; // Минимальный: 3, Максимальный: 20
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_1.main(new String[]{});

        assertEquals("3 20", out.toString().trim());
    }

    @Test
    void shouldHandleSingleElementOrIdenticalElements() {
        String input = "3\n7 7 7\n"; // Все элементы одинаковые
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_1.main(new String[]{});

        assertEquals("7 7", out.toString().trim());
    }
}